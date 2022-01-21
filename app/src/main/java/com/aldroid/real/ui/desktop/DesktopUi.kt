package com.aldroid.real.ui.desktop

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aldroid.real.AppViewModel
import com.aldroid.real.ui.desktop.quickapps.QuickAppUi
import com.aldroid.real.ui.toBitmap
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DesktopUi(
    viewModel: AppViewModel = viewModel(),
){
    Box(modifier = Modifier.fillMaxSize()){

        val wallpaper by viewModel.wallpaper.collectAsState()

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = wallpaper),
            contentDescription = "Wallpaper",
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {

            DesktopPagerUi(modifier = Modifier.fillMaxWidth().weight(1f))
            Spacer(modifier = Modifier.size(4.dp))
            QuickAppUi()
            Spacer(modifier = Modifier.size(12.dp))
            SearchBarUi()
            Spacer(modifier = Modifier.size(12.dp))
        }

    }


}


//val keyboardController = LocalSoftwareKeyboardController.current