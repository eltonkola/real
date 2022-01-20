package com.aldroid.real.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aldroid.real.ui.model.AppInfo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LauncherScreen(apps: List<AppInfo>, quickLaunch: List<AppInfo>) {
    Box(modifier = Modifier
        .padding(top = 50.dp, bottom = 16.dp)
        .fillMaxSize(),
    ){


        val scaffoldState = rememberBottomSheetScaffoldState()
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                AppsGridUi(apps = apps, quickLaunch = quickLaunch)
            },

            ){
            DesktopUi()
        }
        val scope = rememberCoroutineScope()

        Button(onClick = {

            scope.launch {
                scaffoldState.bottomSheetState.apply {
                    if (isCollapsed) expand() else collapse()
                }
            }

        }) {
            Text(text = "APPS")
        }

    }
}