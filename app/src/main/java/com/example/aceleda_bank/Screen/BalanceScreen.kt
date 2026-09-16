package com.example.aceleda_bank.Screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.aceleda_bank.Component.TopBar
import com.example.aceleda_bank.Navigation.Routes
import com.example.aceleda_bank.R
import com.example.aceleda_bank.Room.AccountEntity
import com.example.aceleda_bank.ViewModel.AccountViewModel

@Preview
@Composable
fun BalancePreview(){
    BalanceScreen(navController = NavController(LocalContext.current))
}

@Composable
fun BalanceScreen(navController: NavController,
                  accountViewModel: AccountViewModel=hiltViewModel()
                  ){
    //account here
    val accounts by accountViewModel.allAccounts.collectAsState()
    //local account info
    val account by remember { mutableStateOf<AccountEntity?>(null) }
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
                TopBar( modifier = Modifier.align(Alignment.CenterHorizontally),"Accounts",navController)
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
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        accounts.forEach { account ->
                            cardAmount(account, modifier = Modifier.weight(1f))

                        }
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
                    item {VisibleCard1()}
                    items(accounts) { account ->
                        AmountCard(account)
                    }
                    item {VisibleCard2()}
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
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier= Modifier.size(10.dp))
                    Text("New Account",color=Color.White, fontSize = 13.sp)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)){
                    Icon(
                        painter = painterResource(id = R.drawable.time),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(35.dp)
                    )
                    Text("Quick Service",color=Color.White, fontSize = 13.sp)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable{
                            navController.navigate(Routes.HISTORY_SCREEN)
                        }
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.history),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier= Modifier.size(10.dp))
                    Text("History",color=Color.White, fontSize = 13.sp)
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
fun VisibleCard2(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .heightIn(min = 90.dp)
    )
}

@Composable
fun VisibleCard1(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .heightIn(min = 7.dp)
    )
}

@Composable
fun AmountCard(account: AccountEntity,modifier: Modifier=Modifier) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .heightIn(min = 180.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            // Top Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.more),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = Color(0xFF18334F)
                )

                Text(
                    text = "Available",
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }

            // Amount Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.deposit),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp),
                    tint = Color.Gray
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "${account.balance}",
                        fontSize = 17.sp,
                        color = Color(0xFF18334F),
                        fontWeight = FontWeight.Bold
                    )

                    Icon(
                        if(account.currency=="USD")
                            painterResource(id = R.drawable.dollar_symbol)
                        else
                            painterResource(id = R.drawable.cambodia_riel_crop),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Text(
                text = "${account.accountNumber}",
                fontSize = 16.sp,
                color = Color(0xFF18334F),
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Wallet ${account.currency}",
                fontSize = 16.sp,
                color = Color.Gray
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray
            )

            // Bottom Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.bakong),
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .clip(RoundedCornerShape(20.dp))
                )

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(
                            Color(0xFF8BC58E).copy(alpha = 0.3f)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Default",
                        fontSize = 14.sp,
                        color = Color(0xFF8BC58E)
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.chevron_right),
                        contentDescription = null,
                        tint = Color(0xFF8BC58E),
                        modifier = Modifier.size(16.dp)
                    )
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
            modifier = Modifier.size(100.dp)
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
fun cardAmount(account: AccountEntity,modifier: Modifier=Modifier){
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .background(color=Color.White)
            .height(10.dp),
        contentAlignment = Alignment.CenterEnd
    ){
        Box(
            modifier = Modifier
                .padding(end=7.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text="${account.balance}", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.size(20.dp))
                Image(
                    if(account.currency=="USD")
                        painterResource(id = R.drawable.dollar_symbol)
                    else
                        painterResource(id = R.drawable.cambodia_riel_crop),

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
