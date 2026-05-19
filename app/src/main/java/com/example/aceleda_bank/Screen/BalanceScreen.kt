package com.example.aceleda_bank.Screen

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aceleda_bank.Component.AutoSlidingBanner
import com.example.aceleda_bank.Component.AutoSlidingBanner1
import com.example.aceleda_bank.Component.Backgroundimage
import com.example.aceleda_bank.Component.Balancecard
import com.example.aceleda_bank.Component.Bodyquickaction1
import com.example.aceleda_bank.Component.Bodyquickaction2
import com.example.aceleda_bank.Component.FullBottomQuickAction
import com.example.aceleda_bank.Component.Profile
import com.example.aceleda_bank.Component.RecentTransaction
import com.example.aceleda_bank.Component.Topquickaction
import com.example.aceleda_bank.R
import com.example.pinentry.PinEntryScreen

@Composable
fun BalanceScreen(navController: NavController){
    Box(modifier = Modifier
        .fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = 3.dp)

        )
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.chevron_left),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { navController.navigate("home") }
                    )
                    Text(
                        " Accounts",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.size(14.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_info),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ac_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(50.dp)
                )
            }
            //Top Menu
            Spacer(modifier= Modifier.size(19.dp))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                topMenu(title = "Balance", modifier = Modifier.weight(1f), backcolor = Color.White, textColor = Color.Black)
                topMenu(title = "Trading", modifier = Modifier.weight(1f), backcolor = Color.Transparent, textColor = Color.White)
                topMenu(title = "Cards", modifier = Modifier.weight(1f), backcolor = Color.Transparent, textColor = Color.White)
                topMenu(title = "Link", modifier = Modifier.weight(1f), backcolor = Color.Transparent, textColor = Color.White)
            }
            //Chart
            Spacer(modifier= Modifier.size(19.dp))
            Row(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment= Alignment.CenterVertically
                ) {
                chart()
                Column(modifier = Modifier
                    .width(200.dp)
                    .height(110.dp),
                    ) {
                    cardAmount(R.drawable.cambodia_riel_crop,"100,000",Modifier.weight(1f))
                    Spacer(modifier = Modifier.size(10.dp))
                    cardAmount(R.drawable.dollar_symbol,"100,000",Modifier.weight(1f))
                }
            }
                LazyColumn(
                    modifier = Modifier
                        .padding(top=10.dp)
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .fillMaxWidth()
                        .height(900.dp)
                        .background(color=Color(0xFFE0E0E0)),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                    item {  }
                    item { AmountCard()  }
                    item { AmountCard() }
                    item { AmountCard() }
                    item { AmountCard() }

                }
            }
        Row(modifier = Modifier
            .background(color=Color(0xFF362513))
            .height(90.dp)
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)){
                Icon(
                    painter = painterResource(id = R.drawable.museum),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier= Modifier.size(10.dp))
                Text("New Account",color=Color.White, fontSize = 16.sp)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)){
                Icon(
                    painter = painterResource(id = R.drawable.time),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
                Text("Quick Service",color=Color.White, fontSize = 16.sp)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)){
                Icon(
                    painter = painterResource(id = R.drawable.history),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier= Modifier.size(10.dp))
                Text("History",color=Color.White, fontSize = 16.sp)
            }
        }
    }
}


@Composable
fun topMenu(title:String, modifier: Modifier, backcolor: Color, textColor: Color){
    Box(modifier= modifier
        .clip(RoundedCornerShape(15.dp))
        .background(color = backcolor)
        .height(30.dp),
        contentAlignment=Alignment.Center
    ){
        Text(text = title, color= textColor, fontSize = 16.sp)
    }
}

@Composable
fun AmountCard(){
    Box(modifier = Modifier
        .clip(RoundedCornerShape(20.dp))
        .background(color = Color.White)
        .height(210.dp)
        .width(375.dp)){
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(
                        painter = painterResource(id = R.drawable.more),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp),
                        tint = Color(0xFF18334F)
                    )
                    Text(
                        "Available",
                        fontSize = 17.sp,
                        color = Color.Gray,
                    )
                }
                Row(
                    modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.deposit),
                        contentDescription = null,
                        modifier = Modifier.size(47.dp),
                        tint = Color.Gray
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "100,000",
                            fontSize = 21.sp,
                            color = Color(0xFF18334F),
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.dollar_symbol),
                            contentDescription = null,
                            modifier= Modifier.size(40.dp)
                        )
                    }
                }

                Text(
                    "096 *** 263",
                    fontSize = 18.sp,
                    color = Color(0xFF18334F),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Wallet KHR",
                    fontSize = 18.sp,
                    color = Color.Gray,
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.Gray,
                )
                Row(
                    modifier= Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bakong),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .size(20.dp)
                    )
                    Box(){
                        Row(modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .width(95.dp)
                            .height(22.dp)
                            .background(
                            color = Color(0xFF8BC58E).copy(alpha = 0.3f))
                        ) {
                            Text(
                                "Default",
                                fontSize = 19.sp,
                                color = Color(0xFF8BC58E),
                                modifier= Modifier.padding(start = 11.dp)
                            )
                            Icon(
                                painter=painterResource(id = R.drawable.chevron_right),
                                contentDescription = null,
                                tint = Color(0xFF8BC58E)
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun chart(){
    Box(
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(110.dp)
        ) {

            drawArc(
                color = Color(0xFFD4A62A),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 70f)
            )

            drawArc(
                color = Color(0xFF29B6F6),
                startAngle = 230f,
                sweepAngle = 50f,
                useCenter = false,
                style = Stroke(width = 70f)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.wallet_solid_full__1_),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Text("Total",color=Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun cardAmount(image: Int, text: String, modifier: Modifier){
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .background(color=Color.White)
            .height(40.dp),
        contentAlignment = Alignment.CenterEnd
    ){
        Box(
            modifier = Modifier
                .padding(end=7.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text=text, fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.size(20.dp))
                Image(
                    painter = painterResource(image),
                    contentDescription=null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(7.dp))
                        .width(30.dp)
                        .background(color=Color(0xFF18334F))
                )
            }
        }
    }
}
