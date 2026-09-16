package com.example.aceleda_bank.Screen

import android.net.Uri
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aceleda_bank.Component.FieldLabel
import com.example.aceleda_bank.Component.ShieldIcon
import com.example.aceleda_bank.Component.acledaTextFieldColors
import com.example.aceleda_bank.DTO.AuthDTO.RegisterRequestDTO
import com.example.aceleda_bank.Navigation.Routes
import com.example.aceleda_bank.ViewModel.AuthViewModel
import com.example.aceleda_bank.dataStore.TokenManager
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

// ── Colors ────────────────────────────────────────────────────────────────────
private val NavyDark   = Color(0xFF0D2B5E)

// ── Screen ────────────────────────────────────────────────────────────────────
@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = viewModel(),
    navController: NavController,
) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var profession by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var image by remember { mutableStateOf<Uri?>(null) }
    val activity = LocalActivity.current

    val callbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(
                credential: PhoneAuthCredential
            ) {

            }

            override fun onVerificationFailed(
                e: FirebaseException
            ) {

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                viewModel.storedVerificationId = verificationId

                navController.navigate(Routes.OTP_SCREEN)
            }
        }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NavyDark)
            .verticalScroll(rememberScrollState())
    ) {

        // ── Header───────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, bottom = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text       = "Register here",
                color      = Color.White,
                fontSize   = 20.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.3.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Shield graphic
            ShieldIcon()

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text     = "©ACLEDA Bank Plc. Version 3.1",
                color    = Color(0xFFB0C4DE),
                fontSize = 12.sp
            )
        }

        // ── White Card Form ───────────────────────────────────────────────────
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape    = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            color    = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {

                // First Name
                FieldLabel("First Name")
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value         = firstName,
                    onValueChange = { firstName = it },
                    modifier      = Modifier.fillMaxWidth(),
                    singleLine    = true,
                    shape         = RoundedCornerShape(8.dp),
                    colors        = acledaTextFieldColors(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                // Last Name
                FieldLabel("Last Name")
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value         = lastName,
                    onValueChange = { lastName = it },
                    modifier      = Modifier.fillMaxWidth(),
                    singleLine    = true,
                    shape         = RoundedCornerShape(8.dp),
                    colors        = acledaTextFieldColors(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                // Phone Number
                FieldLabel("Phone Number")
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value         = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    modifier      = Modifier.fillMaxWidth(),
                    singleLine    = true,
                    shape         = RoundedCornerShape(8.dp),
                    colors        = acledaTextFieldColors(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                // Gender
                FieldLabel("Gender")
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value         = gender,
                    onValueChange = { gender = it },
                    modifier      = Modifier.fillMaxWidth(),
                    singleLine    = true,
                    shape         = RoundedCornerShape(8.dp),
                    colors        = acledaTextFieldColors(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                // Profession
                FieldLabel("profession")
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value         = profession,
                    onValueChange = { profession = it },
                    modifier      = Modifier.fillMaxWidth(),
                    singleLine    = true,
                    shape         = RoundedCornerShape(8.dp),
                    colors        = acledaTextFieldColors(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                // Address
                FieldLabel("Address")
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value         = address,
                    onValueChange = { address = it },
                    modifier      = Modifier.fillMaxWidth(),
                    singleLine    = true,
                    shape         = RoundedCornerShape(8.dp),
                    colors        = acledaTextFieldColors(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                // Date of birt
                FieldLabel("Date of Birth")
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value         = dateOfBirth,
                    onValueChange = { dateOfBirth = it },
                    modifier      = Modifier.fillMaxWidth(),
                    singleLine    = true,
                    shape         = RoundedCornerShape(8.dp),
                    colors        = acledaTextFieldColors(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                // Nationality
                FieldLabel("Nationality")
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value         = nationality,
                    onValueChange = { nationality = it },
                    modifier      = Modifier.fillMaxWidth(),
                    singleLine    = true,
                    shape         = RoundedCornerShape(8.dp),
                    colors        = acledaTextFieldColors(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                // Image Id Card
                FieldLabel("National ID Card")
                Spacer(Modifier.height(4.dp))
                // Launcher
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    image = uri
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .clickable {
                            launcher.launch("image/*")
                        },
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (image == null) {
                        Text(
                            text = "Tap to upload image",
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    } else {
                        Text(
                            text = "Image selected ✓",
                            color = Color(0xFF2E7D32),
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Spacer(Modifier.height(32.dp))

                // if register success, navigate to created screen
                val registerSuccess by viewModel.registerSuccess.collectAsState()

                LaunchedEffect(registerSuccess) {
                    if (registerSuccess) {
                        navController.navigate(Routes.SET_PIN_SCREEN)
                    }
                }

                // Register Button
                Button(
                    onClick = {
                    val  formatPhoneNumber=  viewModel.formatPhoneNumber(phoneNumber)
                        viewModel.register(
                            RegisterRequestDTO(
                                firstName = firstName,
                                lastName = lastName,
                                phoneNumber = formatPhoneNumber,
                                gender = gender,
                                profession = profession,
                                address = address,
                                dob = dateOfBirth,
                                nationality = nationality,
                                imageUrl = image
                            )
                        )
                        viewModel.phoneNumber=phoneNumber
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape  = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NavyDark)
                ) {
                    Text(
                        text       = "Register",
                        color      = Color.White,
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(20.dp))

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}



// ── Preview ───────────────────────────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    MaterialTheme {
        RegisterScreen(navController = NavController(LocalContext.current))
    }
}