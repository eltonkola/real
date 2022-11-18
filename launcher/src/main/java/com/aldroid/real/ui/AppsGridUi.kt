package com.aldroid.real.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aldroid.real.AppViewModel
import com.aldroid.real.AppsState.Loaded
import com.aldroid.real.AppsState.Loading
import com.aldroid.real.model.AppInfo


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppsDrawerUi(viewModel: AppViewModel = viewModel()){

    val appState = viewModel.apps.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

        when(appState.value){
            Loading -> AppsLoadingUi()
            is Loaded -> AppsGridUi((appState.value as Loaded).apps)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppsGridUi(apps: List<AppInfo>) {

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns =  GridCells.Fixed(4),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(apps) { app ->
            AppCellUi(app)
        }
    }

}

@Composable
fun AppsLoadingUi() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
       CircularProgressIndicator(modifier = Modifier.size(46.dp))
    }

}


