package com.aldroid.real.ui.desktop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aldroid.real.AppViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect
import kotlin.math.absoluteValue


@OptIn(ExperimentalPagerApi::class)
@Composable
fun DesktopPagerUi(
    viewModel: AppViewModel = viewModel(),
    modifier: Modifier = Modifier
){
    val pagerState = rememberPagerState()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    HorizontalPager(
        modifier = Modifier.fillMaxWidth().weight(1f).padding(top = 56.dp),
        count = 3,
        state = pagerState,
        //  contentPadding = PaddingValues(start = 64.dp, end = 64.dp),
    ) { page ->

        Box(
            Modifier
                .fillMaxSize()
                //    .background(color)

                .graphicsLayer() {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset =
                        calculateCurrentOffsetForPage(page).absoluteValue

                    // We animate the scaleX + scaleY, between 85% and 100%
                    lerp(
                        start = 0.85f.dp,
                        stop = 1f.dp,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale.value
                        scaleY = scale.value
                    }

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f.dp,
                        stop = 1f.dp,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).value
                },

        ) {
            when(page){
                0 -> StatsDesktop()
                1 -> MainDesktop()
                2 -> NoteDesktop()
            }
        }
    }

    HorizontalPagerIndicator(
        pagerState = pagerState,
        modifier = Modifier
          //  .align(Alignment.CenterHorizontally)
            .padding(16.dp),
    )
    }

    LaunchedEffect(pagerState){
        //  scope.launch {
        pagerState.scrollToPage(1)
        snapshotFlow { pagerState.currentPage }.collect { page ->
            println("page changed $page")
        }
        //}
    }

}
