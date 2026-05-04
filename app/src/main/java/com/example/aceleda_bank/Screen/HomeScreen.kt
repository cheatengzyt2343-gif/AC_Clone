package com.example.aceleda_bank.Screen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aceleda_bank.Component.Backgroundimage
import com.example.aceleda_bank.Component.Balancecard
import com.example.aceleda_bank.Component.Profile
import com.example.aceleda_bank.Component.Topbar
@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Backgroundimage()
        Column(modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxSize()) {
            Topbar()
            Profile()
            Balancecard()
        }
    }
}