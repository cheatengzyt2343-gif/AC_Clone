package com.example.aceleda_bank.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aceleda_bank.R

@Preview(showBackground=true)
@Composable
fun RecentTransaction() {
    val actions = listOf(

        Account(
            Color(0xFFFFC107),
            "CT",
            "Chea Teng"
        ),

        Account(
            Color(0xFF4CAF50),
            "BP",
            "Bun Peng"
        ),

        Account(
            Color(0xFF2196F3),
            "KS",
            "Kuy Seng"
        ),

        Account(
            Color(0xFF9C27B0),
            "VT",
            "Vathanak Tola"
        ),

        Account(
            Color(0xFFFF5722),
            "LV",
            "Ly Vannak"
        ),

        Account(
            Color(0xFF009688),
            "BR",
            "Bun Raksmey"
        ),

        Account(
            Color(0xFF3F51B5),
            "PP",
            "Put Phearo"
        ),

        Account(
            Color(0xFFF44336),
            "CR",
            "Chom Rong"
        )
    )
    Text(
        modifier = Modifier.padding(start=16.dp),
        text = "Recent Transactions",
        color = Color.White,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
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
                PreviousAcc(
                    color = action.color,
                    name = action.name,
                    title = action.title
                )
            }
        }
    }
}
@Composable
fun PreviousAcc(color: Color ,name: String, title: String){
    Column(
        modifier=Modifier
            .padding(start=6.dp,end=6.dp,top=16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .width(60.dp)
            .height(60.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(40.dp)
            ),
            contentAlignment = Alignment.Center
        ){
            Text(text = name,
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
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
data class Account(
    val color: Color,
    val name: String,
    val title: String
)