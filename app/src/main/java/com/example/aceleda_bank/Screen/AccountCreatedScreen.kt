package com.example.aceleda_bank.Screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.aceleda_bank.Component.TopBar
import com.example.aceleda_bank.Navigation.Routes
import com.example.aceleda_bank.R
import com.example.aceleda_bank.ViewModel.AuthViewModel

private val NavyDark   = Color(0xFF0D2B5E)
private val GoldAccent = Color(0xFFC9A227)

@Composable
fun CreatedScreen(navController: NavController,viewModel: AuthViewModel = hiltViewModel()){
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier= Modifier
        .fillMaxSize()
        .background(Color.LightGray)
    ){
        Column(modifier = Modifier.fillMaxSize()){
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .graphicsLayer {
                        shadowElevation = 40.dp.toPx()
                        ambientShadowColor = Color.Black
                        spotShadowColor = Color.Black
                    }
                    .background(
                        color = NavyDark,
                    )
            ) {
                TopBar(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .align(Alignment.TopCenter),
                    title = "Register",
                    navController = navController
                )
            }

            Box(modifier=Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 40.dp, start = 20.dp, end = 20.dp)
                .background(color=NavyDark, RoundedCornerShape(20.dp))
            ){
               Column(
                   modifier = Modifier.fillMaxSize(),
                   horizontalAlignment = Alignment.CenterHorizontally
               ) {
                   Image(
                       painter= painterResource(id=R.drawable.thanks),
                       contentDescription=null,
                       modifier=Modifier
                           .size(150.dp)
                   )
                   Text(
                       text = "Congratulations!",
                       fontSize = 24.sp,
                       fontWeight = FontWeight.Bold,
                       color = Color.White
                   )
                   Spacer(modifier = Modifier.size(10.dp))
                   Text(
                       text = "Your account has been created",
                       fontSize = 18.sp,
                       textAlign = TextAlign.Center,
                       color = Color.White
                   )
               }
            }
            Spacer(modifier = Modifier.size(40.dp))
            Button(
                onClick = {
                    val formattedPhone = viewModel.formatPhoneNumber(viewModel.phoneNumber)
                    Log.d("REGISTER", "Phone sent: ${viewModel.phoneNumber}")
                    viewModel.checkUser(
                        formattedPhone
                    ) { result ->
                        Log.d("REGISTER", "checkUser: $result")
                        if (result){
                            navController.navigate(Routes.HOME)
                        }
                        else{
                            showDialog = true
                        }
                    }
                          },
                modifier = Modifier
                    .padding(start = 70.dp, end = 70.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NavyDark
                )
            ) {
                Text(
                    text = "Get Started",
                    color = Color.White
                )
            }
        }

        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Your registration is being reviewed.\nYour ACLEDA mobile will be activated within 24 hours.\nFor more information, please contact\n023 994 444/015 999 233.",
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            fontSize = 15.sp,
                            lineHeight = 22.sp,
                            fontWeight = FontWeight.Medium
                        )
                        
                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = {
                                showDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .width(120.dp)
                                .height(48.dp)
                        ) {
                            Text(
                                text = "OK",
                                color = GoldAccent,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateScreenPre(){
    CreatedScreen(navController = NavController(LocalContext.current))
}
