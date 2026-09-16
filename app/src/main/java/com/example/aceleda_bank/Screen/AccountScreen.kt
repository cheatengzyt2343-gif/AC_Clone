package com.example.aceleda_bank.Screen
import com.example.aceleda_bank.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.aceleda_bank.Component.AccountInfoCard
import com.example.aceleda_bank.Component.Backgroundimage
import com.example.aceleda_bank.Component.TopBar
import com.example.aceleda_bank.ViewModel.UserViewModel

@Composable
fun AccountScreen(navController: NavController,userViewModel: UserViewModel = hiltViewModel()) {
    val user= userViewModel.localUser.collectAsState().value
     val accountInfoList = listOf(
        AccountInfo("Last Name", user?.lastName ?:"", R.drawable.ic_person),
        AccountInfo("First Name", user?.firstName ?:"", R.drawable.ic_person),
        AccountInfo("Phone Number", user?.phoneNumber ?:"", R.drawable.ic_phone),
        AccountInfo("Gender", user?.gender?:"", R.drawable.ic_gender),
        AccountInfo("DOB", user?.dob ?:"", R.drawable.ic_date),
        AccountInfo("Profession", user?.profession ?:"", R.drawable.ic_work),
        AccountInfo("Nationality", user?.nationality ?:"", R.drawable.ic_flage),
        AccountInfo("Address", user?.address ?:"", R.drawable.ic_location),
//        AccountInfo("Image", user?.imageUrl ?:"", R.drawable.ic_phone)
    )

    Box(modifier = Modifier.fillMaxSize()) {

        Backgroundimage()

        TopBar(
            modifier = Modifier.align(Alignment.TopCenter),
            title = "Profile",
            navController = navController
        )

        Column(modifier = Modifier
            .padding(top=120.dp)
            .background(color=Color.White,RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .fillMaxSize(),
        ) {
            Spacer(modifier = Modifier.size(30.dp))
            ProfileAvatar()
            Box(modifier = Modifier
                .background(Color.Gray.copy(alpha = 0.4f))
                .padding(start=16.dp,end=16.dp)
                .height(40.dp)
                .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ){
                Text("Account Information",modifier=Modifier.padding(start=16.dp),Color(0xFF1A365D), fontWeight = FontWeight.Bold)
            }
            LazyColumn {
                items(accountInfoList) { item ->
                    AccountInfoCard(
                        title = item.title,
                        value = item.value,
                        icon = item.icon
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileAvatar() {
   Row(modifier = Modifier
       .padding(start=30.dp)
       .fillMaxWidth(),
       horizontalArrangement = Arrangement.spacedBy(10.dp)
   ) {
       Box(modifier= Modifier.size(80.dp,115.dp)){
           Box(
               modifier = Modifier.clip(CircleShape)
           ) {

               Image(
                   painter = painterResource(id=R.drawable.profile),
                   contentDescription = null
               )


               Box(
                   modifier = Modifier
                       .fillMaxWidth()
                       .fillMaxHeight(0.20f)
                       .align(Alignment.BottomCenter)
                       .background(Color.White.copy(alpha = 0.5f))
               )
           }
           Box(modifier = Modifier
               .clip(RoundedCornerShape(50.dp))
               .align(Alignment.CenterEnd)
               .background(color=Color.White)
               .size(20.dp)
           ){
               Image(
                   painter=painterResource(id=R.drawable.camera),
                   contentDescription = null,
                   modifier = Modifier
                       .size(10.dp)
                       .align(Alignment.Center)
               )
           }
       }
       Column(modifier = Modifier.padding(top=10.dp)) {
           Text("CHEA TENG",modifier=Modifier.padding(start=16.dp),Color(0xFF1A365D))
           Spacer(modifier = Modifier.size(8.dp))
           Text("0965763438",modifier=Modifier.padding(start=16.dp),Color(0xFFD4A44D))
           Spacer(modifier = Modifier.size(8.dp))
           Row(verticalAlignment = Alignment.CenterVertically) {
               Image(
                   painter = painterResource(id=R.drawable.verified),
                   contentDescription = null,
                   modifier = Modifier
                       .padding(start=14.dp)
                       .size(17.dp)
               )
               Text("CamDx Verified",modifier=Modifier.padding(start=5.dp),color=Color.DarkGray, fontSize = 10.sp)
           }
       }
   }
}

@Preview(showBackground = true)
@Composable
fun AccountScreenPreview(){
    AccountScreen(navController = NavController(LocalContext.current))
}

data class AccountInfo(
    val title: String,
    val value: String,
    val icon: Int
)
