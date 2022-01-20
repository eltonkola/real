package com.aldroid.real.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells.Fixed
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aldroid.real.ui.model.AppInfo


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppsGridUi(apps: List<AppInfo>, quickLaunch: List<AppInfo>){

    Column(
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxSize(),
    ) {
        Row(   modifier = Modifier.background(Color.Red).fillMaxWidth()) {
            AppCellUi(quickLaunch[0])
            AppCellUi(quickLaunch[1])

            AppCellUi(quickLaunch[2])
            AppCellUi(quickLaunch[3])
        }


        LazyVerticalGrid(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxSize(),
            cells = Fixed(3),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(apps) { app ->
                AppCellUi(app)
            }

        }


    }


}