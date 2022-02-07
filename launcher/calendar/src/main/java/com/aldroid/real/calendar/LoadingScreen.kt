
package com.aldroid.real.calendar

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        CircularProgressIndicator(
            modifier = Modifier.width(60.dp).height(60.dp),
            color = MaterialTheme.colors.primary,
            strokeWidth = 2.dp
        )
    }
}

@ExperimentalAnimationApi
@Preview("LoadingScreenPreview", widthDp = 360, heightDp = 640)
@Composable
fun LoadingScreenPreview() {
    MaterialTheme {
        LoadingScreen()
    }
}
