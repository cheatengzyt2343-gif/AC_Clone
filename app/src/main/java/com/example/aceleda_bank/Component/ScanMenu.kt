package com.example.aceleda_bank.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScanMenu(image:Int, title: String){
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
                color = Color.Black.copy(alpha = 0.2f),
                shape = RoundedCornerShape(40.dp)
            ),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(id = image),
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