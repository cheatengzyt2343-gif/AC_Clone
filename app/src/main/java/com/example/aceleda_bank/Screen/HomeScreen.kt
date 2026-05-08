package com.example.aceleda_bank.Screen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aceleda_bank.Component.Backgroundimage
import com.example.aceleda_bank.Component.Balancecard
import com.example.aceleda_bank.Component.Bodyquickaction1
import com.example.aceleda_bank.Component.Bodyquickaction2
import com.example.aceleda_bank.Component.FullBottomQuickAction
import com.example.aceleda_bank.Component.Profile
import com.example.aceleda_bank.Component.Topbar
import com.example.aceleda_bank.Component.Topquickaction

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Backgroundimage()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {
            item { Topbar() }
            item { Profile() }
            item { Balancecard() }
            item { Topquickaction() }
            item { Bodyquickaction1() }
            item { Bodyquickaction2() }
            item { FullBottomQuickAction() }
        }
    }
}