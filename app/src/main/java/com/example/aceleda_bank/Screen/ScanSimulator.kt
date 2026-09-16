package com.example.aceleda_bank.Screen

import com.example.aceleda_bank.R
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.aceleda_bank.Component.Company
import com.example.aceleda_bank.Component.ScanMenu
import com.example.aceleda_bank.Component.TopBar
import com.example.aceleda_bank.Navigation.Routes
import com.example.aceleda_bank.ViewModel.TransactionViewModel
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

@Composable
fun QRScannerScreen(navController: NavController,transferViewModel: TransactionViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
            Log.d("QR_SCANNER", "Permission result: $granted")
        }
    )

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    // Reset scan state when entering the screen
    var isScanned by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if (hasCameraPermission) {
            CameraPreview(onBarcodeScanned = { result ->
                if (!isScanned) {
                    isScanned = true
                    Log.d("QR_SCANNER", "QR RESULT: $result")
                    Toast.makeText(context, "Scanned: $result", Toast.LENGTH_LONG).show()

                    val parts = result.split("|")

                    val data = mutableMapOf<String, String>()

                    for (part in parts) {
                        if (part.contains("=")) {
                            val pair = part.split("=")
                            data[pair[0]] = pair[1]
                        }
                    }

                    val accountNumber = data["accountNumber"]
                    val merchantName = data["merchant"]
                    val currency = data["currency"]

                    transferViewModel.updateAccountNumber(accountNumber ?: "")
                    transferViewModel.updateMerchantName(merchantName ?: "")
                    transferViewModel.updateCurrency(currency ?: "")

                    navController.navigate(Routes.PAYSCREEN)
                }
            })
        } else {
            Text(
                text = "Please grant camera permission to use the scanner",
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }

        ScannerOverlay()

        ScannerUI(navController)
    }
}


@Composable
fun CameraPreview(onBarcodeScanned: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx).apply {
                implementationMode = PreviewView.ImplementationMode.PERFORMANCE
            }

            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(ctx)) { imageProxy ->
                    processImageProxy(imageProxy, onBarcodeScanned)
                }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                    Log.d("QR_SCANNER", "Camera bound successfully")
                } catch (e: Exception) {
                    Log.e("QR_SCANNER", "Use case binding failed", e)
                }
            }, ContextCompat.getMainExecutor(ctx))

            previewView
        },
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalGetImage::class)
private fun processImageProxy(
    imageProxy: ImageProxy,
    onBarcodeScanned: (String) -> Unit
) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        val scanner = BarcodeScanning.getClient()

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    barcode.rawValue?.let { value ->
                        onBarcodeScanned(value)
                    }
                }
            }
            .addOnFailureListener {
                Log.e("QR_SCANNER", "ML Kit Error", it)
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    } else {
        imageProxy.close()
    }
}


@Composable
fun ScannerOverlay() {
    val infiniteTransition = rememberInfiniteTransition(label = "scan")
    val animateOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 280f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "line"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
        ) {
            val scanSize = 280.dp.toPx()
            val left = (size.width - scanSize) / 2
            val top = (size.height - scanSize) / 2 - 100.dp.toPx()

            // 1. Semi-transparent overlay
            drawRect(color = Color.Black.copy(alpha = 0.5f))

            // 2. Clear hole
            drawRoundRect(
                color = Color.Transparent,
                topLeft = Offset(left, top),
                size = Size(scanSize, scanSize),
                blendMode = BlendMode.Clear
            )

            // 3. Corners
            val stroke = 8f
            val len = 60f
            val color = Color(0xFFD4A44D)

            // Top Left
            drawLine(color, Offset(left, top), Offset(left + len, top), stroke)
            drawLine(color, Offset(left, top), Offset(left, top + len), stroke)
            // Top Right
            drawLine(color, Offset(left + scanSize, top), Offset(left + scanSize - len, top), stroke)
            drawLine(color, Offset(left + scanSize, top), Offset(left + scanSize, top + len), stroke)
            // Bottom Left
            drawLine(color, Offset(left, top + scanSize), Offset(left + len, top + scanSize), stroke)
            drawLine(color, Offset(left, top + scanSize), Offset(left, top + scanSize - len), stroke)
            // Bottom Right
            drawLine(color, Offset(left + scanSize, top + scanSize), Offset(left + scanSize - len, top + scanSize), stroke)
            drawLine(color, Offset(left + scanSize, top + scanSize), Offset(left + scanSize, top + scanSize - len), stroke)

        }
        //Scan line
        Box(
            modifier = Modifier
                .size(280.dp)
                .align(Alignment.Center)
                .offset(y = (-100).dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .offset(y = animateOffset.dp)
                    .background(
                        Color(0xFFD4A44D).copy(alpha = 0.7f)
                    )
            )
        }
    }
}

@Composable
fun ScannerUI(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        TopBar(
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.TopCenter),
            title = "QR Scanner",
            navController = navController
        )

        Column(
            modifier = Modifier
                .padding(bottom = 100.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Accepted",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            FlowRow(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Company(image = R.drawable.aceleda)
                Company(image = R.drawable.bakong)
                Company(image = R.drawable.visa)
                Company(image = R.drawable.master)
                Company(image = R.drawable.coin)
            }
            Spacer(modifier = Modifier.size(35.dp))
            Row {
                ScanMenu(image = R.drawable.flashlight, "Flashlight")
                Spacer(modifier = Modifier.width(40.dp))
                ScanMenu(image = R.drawable.file, "Select QR")
            }
        }
    }
}