package com.example.aceleda_bank.Component
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aceleda_bank.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue
import androidx.compose.ui.util.lerp

@Preview(showBackground = true)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutoSlidingBanner() {

    val banners = remember {
        listOf(
            R.drawable.temple1,
            R.drawable.temple2,
            R.drawable.temple3,
            R.drawable.temple4
        )
    }

    val pagerState = rememberPagerState(
        initialPage = 1000,
        pageCount = { Int.MAX_VALUE }
    )

    // Improved Auto slide logic for infinite circular scrolling
    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(3000)
            if (!pagerState.isScrollInProgress && banners.isNotEmpty()) {
                // To keep moving forward infinitely, we just increment the current page
                val nextPage = pagerState.currentPage + 1
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Column {

        // Title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Cambodia Tourism",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(6.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Banner slider
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 60.dp),
            modifier = Modifier
                .fillMaxWidth(),

        ) { page ->
            val actualPage = page % banners.size
            val pageOffset = (
                    (pagerState.currentPage - page) +
                            pagerState.currentPageOffsetFraction
                    ).absoluteValue

            val scale = lerp(
                start = 0.85f,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                        .width(300.dp)
                        .height(150.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(id = banners[actualPage]),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(24.dp)),
                        contentScale = ContentScale.Crop
                    )

                    // Bottom overlay
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .height(45.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                            .background(
                                Color.Black.copy(alpha = 0.3f)
                            )
                            .padding(12.dp)
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = Color.White
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = "Phnom Bayang Temple - Takeo",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Indicators
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            repeat(banners.size) { index ->

                val selected =
                    (pagerState.currentPage % banners.size) == index

                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .height(8.dp)
                        .width(
                            if (selected) 24.dp
                            else 8.dp
                        )
                        .clip(CircleShape)
                        .background(
                            if (selected)
                                Color.White
                            else
                                Color.LightGray
                        )
                )
            }
        }
    }
}
