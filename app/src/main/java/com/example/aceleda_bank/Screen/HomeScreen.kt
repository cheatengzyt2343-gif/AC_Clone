package com.example.aceleda_bank.Screen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aceleda_bank.Component.AutoSlidingBanner
import com.example.aceleda_bank.Component.AutoSlidingBanner1
import com.example.aceleda_bank.Component.Backgroundimage
import com.example.aceleda_bank.Component.Balancecard
import com.example.aceleda_bank.Component.Bodyquickaction1
import com.example.aceleda_bank.Component.Bodyquickaction2
import com.example.aceleda_bank.Component.BottomBar
import com.example.aceleda_bank.Component.FullBottomQuickAction
import com.example.aceleda_bank.Component.Profile
import com.example.aceleda_bank.Component.RecentTransaction
import com.example.aceleda_bank.Component.Topbar
import com.example.aceleda_bank.Component.Topquickaction

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    val listState = rememberLazyListState()

    var previousIndex by remember { mutableIntStateOf(0) }
    var previousOffset by remember { mutableIntStateOf(0) }
    var bottomBarVisible by remember { mutableStateOf(true) }

    LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
        val currentIndex = listState.firstVisibleItemIndex
        val currentOffset = listState.firstVisibleItemScrollOffset

        if (currentIndex > previousIndex) {
            bottomBarVisible = false
        } else if (currentIndex < previousIndex) {
            bottomBarVisible = true
        } else {
            if (currentOffset > previousOffset) {
                bottomBarVisible = false
            } else if (currentOffset < previousOffset) {
                bottomBarVisible = true
            }
        }

        previousIndex = currentIndex
        previousOffset = currentOffset
    }

    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Backgroundimage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {

            Topbar()

            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f)
            ) {

                item { Profile() }
                item { Balancecard() }
                item { Topquickaction() }
                item { Bodyquickaction1() }
                item { Bodyquickaction2() }
                item { FullBottomQuickAction() }
                item { AutoSlidingBanner() }
                item { RecentTransaction() }
                item { AutoSlidingBanner1() }

            }
        }

        AnimatedVisibility(
            visible = bottomBarVisible,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            BottomBar()
        }
    }
}
