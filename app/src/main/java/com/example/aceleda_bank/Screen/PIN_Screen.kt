package com.example.pinentry

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.aceleda_bank.R
import kotlinx.coroutines.delay

private val BackgroundDark   = Color(0xFF1A1310)
private val BackgroundMid    = Color(0xFF2C1F15)
private val GoldPrimary      = Color.White
private val GoldLight        = Color(0xFFE8C068)
private val White            = Color.White
private val WhiteAlpha60     = Color(0x99FFFFFF)
private val WhiteAlpha20     = Color(0x33FFFFFF)
private val ButtonBorder     = Color(0xFF4A3A2A)
private val ButtonBg         = Color(0x22FFFFFF)
private val PinFilled        = Color.White
private val PinEmpty         = Color(0x00000000)

private const val PIN_LENGTH = 6
var passcode = "123456"
@Composable
fun PinEntryScreen(
    userName: String = "",
    phoneNumber: String = "",
    correctPin: String = "123456",
    onSuccess: () -> Unit = {},
    onError: () -> Unit = {}
) {
    var pin by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    LaunchedEffect(pin) {
        if (pin.length == PIN_LENGTH) {
            if (pin == correctPin) {
                onSuccess()
            } else {
                isError = true
                delay(500)       // short shake delay
                pin = ""         // reset
                isError = false
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = 20.dp)

        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.45f))
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(56.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "‹",
                    color = White,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .clickable() {  }
                )
            }
            Spacer(Modifier.height(24.dp))
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id= R.drawable.profile),
                    contentDescription = null,
                    modifier = Modifier.size(88.dp)
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Pon Kouyseng",
                color =Color(0xFFCB9A3E),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Spacer(Modifier.height(6.dp))
            Text(
                text = "0977473280",
                color = White,
                fontSize = 16.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Please Enter PIN",
                color = WhiteAlpha60,
                fontSize = 15.sp
            )
            Spacer(Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(PIN_LENGTH) { index ->
                    val filled = index < pin.length
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(if (filled) PinFilled else PinEmpty)
                            .border(
                                width = 1.5.dp,
                                color = when {
                                    isError -> Color.Red
                                    filled  -> GoldPrimary
                                    else    -> WhiteAlpha60
                                },
                                shape = CircleShape
                            )
                    )
                }
            }
            Spacer(Modifier.height(40.dp))
            PinPad(onKeyPress = { number ->         // 👈 add this
                if (pin.length < PIN_LENGTH) {
                    pin += number.toString()
                }
            })
           // Spacer(Modifier.height(50.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Forgot PIN?",
                    color = WhiteAlpha60,
                    fontSize = 15.sp
                )
                    if (pin.isNotEmpty()) {
                        Text(
                            text = "Delete",
                            color = WhiteAlpha60,
                            fontSize = 15.sp,
                            modifier = Modifier.clickable {
                                if (pin.isNotEmpty()) {
                                    pin = pin.dropLast(1)
                                }
                            }
                        )
                    }else{
                        Text(
                            text = "Cancel",
                            color = WhiteAlpha60,
                            fontSize = 15.sp
                        )
                    }

            }

        }
    }
}
@Composable
fun KeyButton(number: Int,onClick: ()->Unit ) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(PinEmpty)
            .border(
                width = 1.5.dp,
                color = WhiteAlpha60,
                shape = CircleShape
            )
            .clickable{onClick()},
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$number",
            fontSize = 32.sp,
            color = WhiteAlpha60,
        )
    }
}

@Composable
fun NumberKey(numbers: List<Int>, onKeyPress: (Int) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy( 40.dp,Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        numbers.forEach { number ->
            KeyButton(number = number, onClick = { onKeyPress(number) })
        }
    }
}

@Composable
fun PinPad(onKeyPress: (Int) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        NumberKey(numbers = listOf(1, 2, 3), onKeyPress = onKeyPress)
        NumberKey(numbers = listOf(4, 5, 6), onKeyPress = onKeyPress)
        NumberKey(numbers = listOf(7, 8, 9), onKeyPress = onKeyPress)
        NumberKey(numbers = listOf(0),       onKeyPress = onKeyPress)
    }
}

@Composable
fun PinEntryScreenPreview(navController: NavController) {
    val context = LocalContext.current
    PinEntryScreen(
        userName = "CHEA TENG",
        phoneNumber = "067676767",
        correctPin = "123456",
        onSuccess = { navController.navigate("home") },
        onError = { Toast.makeText(context, "no", Toast.LENGTH_SHORT).show() }
    )
}
@Preview(showBackground = true, backgroundColor = 0xFF1A1310)
@Composable
fun PinEntryScreenPreviewOnly() {   // 👈 no NavController param
    PinEntryScreen(
        userName = "CHEA TENG",
        phoneNumber = "067676767",
        correctPin = "123456",
        onSuccess = {},
        onError = {}
    )
}