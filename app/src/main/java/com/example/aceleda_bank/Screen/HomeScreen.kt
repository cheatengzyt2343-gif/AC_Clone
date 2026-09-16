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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
import com.example.aceleda_bank.Component.TopbarHome
import com.example.aceleda_bank.Component.Topquickaction
import com.example.aceleda_bank.ViewModel.AccountViewModel
import com.example.aceleda_bank.ViewModel.TokenViewModel
import com.example.aceleda_bank.ViewModel.TransactionViewModel
import com.example.aceleda_bank.ViewModel.UserViewModel


@Composable
fun HomeScreen(navController: NavController,
               viewModel: TokenViewModel=hiltViewModel(),
               userViewModel: UserViewModel =hiltViewModel(),
               accountViewModel: AccountViewModel =hiltViewModel(),
               transactionViewModel: TransactionViewModel=hiltViewModel()
               ) {
    //User fetch
    val user by userViewModel.localUser.collectAsState()

    // fetch new data
    LaunchedEffect(Unit) {
        userViewModel.refreshProfile()
        accountViewModel.refreshProfile()
        transactionViewModel.refreshProfile()
    }



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

            TopbarHome(navController)

            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f)
            ) {

                item { Profile(user,navController) }
                item { Balancecard(navController) }
                item { Topquickaction() }
                item { Bodyquickaction1(navController) }
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
