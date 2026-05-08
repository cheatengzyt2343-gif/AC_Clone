package com.example.aceleda_bank.Component
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aceleda_bank.R

//Top Quick Action
@Composable
fun QuickActionCard2(image: Painter,title: String,modifier: Modifier = Modifier){
    Box(modifier = modifier
        .padding(start=6.dp,end=6.dp)
        .height(50.dp)
        .border(
            width = 1.dp,
            color = Color(0xFFD4A44D).copy(alpha = 0.8f),
            shape = RoundedCornerShape(16.dp),
        )
        .background(
            color = Color.Black.copy(alpha = 0.9f),
            shape = RoundedCornerShape(14.dp)
        ),
        contentAlignment = Alignment.Center
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxSize()
        ) {

            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = title,
                color = Color.White,
                fontSize = 15.sp
            )
        }
    }
}
//Make it Row

@Composable
fun Topquickaction(){
    Row(modifier = Modifier
        .padding(start=10.dp,end=10.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        QuickActionCard2(image = painterResource(id = R.drawable.invoice), title = "Payments",modifier = Modifier.weight(1f))
        QuickActionCard2(image = painterResource(id = R.drawable.phone), title = "Mobile Top-up",modifier = Modifier.weight(1f))

    }
}
//Body Quick Action 6
@Composable
fun QuickActionCard6(image: Painter,title:String,modifier: Modifier = Modifier){
    Box(modifier = modifier
        .padding(start=6.dp,end=6.dp,top=16.dp)
        .height(100.dp)
        .border(
            width = 1.dp,
            color = Color(0xFFD4A44D).copy(alpha = 0.8f),
            shape = RoundedCornerShape(16.dp),
        )
        .background(
            color = Color.Black.copy(alpha = 0.9f),
            shape = RoundedCornerShape(14.dp)
        ),
        contentAlignment = Alignment.Center
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}
//Make it Row
@Composable
fun Bodyquickaction1(){
    Row(modifier = Modifier
        .padding(start=10.dp,end=10.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        QuickActionCard6(image = painterResource(id = R.drawable.card), title = "Card",modifier = Modifier.weight(1f))
        QuickActionCard6(image = painterResource(id = R.drawable.scanner), title = "Scan",modifier = Modifier.weight(1f))
        QuickActionCard6(image = painterResource(id = R.drawable.transfer), title = "Transfer",modifier = Modifier.weight(1f))
    }
}
@Composable
fun Bodyquickaction2(){
    Row(modifier = Modifier
        .padding(start=10.dp,end=10.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        QuickActionCard6(image = painterResource(id = R.drawable.deposit), title = "Deposit",modifier = Modifier.weight(1f))
        QuickActionCard6(image = painterResource(id = R.drawable.loan), title = "Loans",modifier = Modifier.weight(1f))
        QuickActionCard6(image = painterResource(id = R.drawable.payment), title = "Quick-Cash",modifier = Modifier.weight(1f))
    }
}
//Bottom Quick Action
@Composable
fun BttomQAE(image: Painter, title: String){
    Column(
        modifier=Modifier
            .padding(start=6.dp,end=6.dp,top=16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
    Box(modifier = Modifier
        .width(60.dp)
        .height(60.dp)
        .border(
            width = 1.dp,
            color = Color(0xFFD4A44D).copy(alpha = 0.8f),
            shape = RoundedCornerShape(16.dp),
        )
        .background(
            color = Color.Black.copy(alpha = 0.9f),
            shape = RoundedCornerShape(14.dp)
        ),
        contentAlignment = Alignment.Center
    ){
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier=Modifier.size(6.dp))
        Text(
            text = title,
            color = Color.White,
            lineHeight = 14.sp,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(60.dp)
        )
    }
}
//Bottom Quick Action with its background
@Preview(showBackground=true)
@Composable
fun FullBottomQuickAction() {
    val actions = listOf(
        QickActionBottom(R.drawable.coin, "My Points"),
        QickActionBottom(R.drawable.shop, "Toanchet Pay"),
        QickActionBottom(R.drawable.museum, "Public Service"),
        QickActionBottom(R.drawable.chart, "Account Summary"),
        QickActionBottom(R.drawable.location, "Locator"),
        QickActionBottom(R.drawable.marketplace, "Cambodia Market"),
        QickActionBottom(R.drawable.csx, "CSX Trade"),
        QickActionBottom(R.drawable.exchange, "Exchange Rate"),
        QickActionBottom(R.drawable.bill, "Pay-Me"),
    )
    Box(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .size(120.dp)
            .background(
                color = Color.Black.copy(alpha = 0.7f),
            )
    ) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {

            items(actions) { action ->
                BttomQAE(
                    image = painterResource(id = action.image),
                    title = action.title
                )
            }
        }
    }
}
data class QickActionBottom(
    val image: Int,
    val title: String
)






