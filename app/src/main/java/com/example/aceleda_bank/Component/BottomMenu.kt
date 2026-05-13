package com.example.aceleda_bank.Component

import android.R
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Preview(showBackground = true)
@Composable
fun BottomBar() {

    var selectedIndex by remember { mutableStateOf(0) }

    val items = listOf(
        Icons.Default.Home to "Home",
        Icons.Default.FavoriteBorder to "Favorites",
        Icons.Default.MailOutline to "LiveChat",
        Icons.Default.Menu to "Menu"
    )

    Box(
        modifier = Modifier
            .padding(start = 10.dp, bottom = 20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .size(200.dp, 80.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF6D4C00),
                                Color(0xFF1A1200),
                                Color(0xFF000000)
                            )
                        )
                    )
            ) {

                Row {
                    items.forEachIndexed { index, item ->
                        MenuItem(
                            icon = item.first,
                            title = item.second,
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index }
                        )
                    }
                }
            }

            // Chatbot button
            Box(
                modifier = Modifier
                    .padding(start = 7.dp)
                    .clip(CircleShape)
                    .size(80.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF6D4C00),
                                Color(0xFF1A1200),
                                Color(0xFF000000)
                            )
                        )
                    )
            ) {
                Image(
                    painter = painterResource(id = com.example.aceleda_bank.R.drawable.chatbot),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(46.dp),
                )
            }
        }
    }
}
@Composable
fun MenuItem(
    icon: ImageVector,
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue =
            if (selected) Color(0x33FFFFFF)
            else Color.Transparent,
        label = ""
    )

    val iconColor by animateColorAsState(
        targetValue =
            if (selected) Color.Yellow
            else Color.White,
        label = ""
    )
    Column(
        modifier = Modifier
            .padding(start=4.dp,top=3.dp,bottom=3.dp)
            .size(80.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(backgroundColor)
            .padding(6.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = iconColor
        )

        Text(
            text = title,
            color = iconColor,
            fontSize = 14.sp
        )
    }
}