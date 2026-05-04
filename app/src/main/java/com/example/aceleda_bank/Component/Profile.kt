package com.example.aceleda_bank.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aceleda_bank.R

@Composable
fun Profile(){
    Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id=R.drawable.profile),
            contentDescription = null,
            modifier = Modifier.size(46.dp)
        )
        Column() {
            Text(modifier=Modifier
                .padding(start=6.dp),
                text = "Hello, Teng",
                color=Color.White,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(modifier=Modifier
                .padding(start=6.dp),
                text = "Profile >",
                color=Color.White,
                fontSize = 12.sp
            )
        }
    }
}