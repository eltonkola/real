package com.aldroid.real.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomDrawer
import androidx.compose.material.BottomDrawerValue.Closed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aldroid.real.ui.desktop.DesktopUi

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LauncherScreen() {
    Box(modifier = Modifier
        .padding(top = 50.dp, bottom = 16.dp)
        .fillMaxSize(),
    ){

        val drawerState = rememberBottomDrawerState(Closed)
        BottomDrawer(

            drawerState = drawerState,
            drawerContent = {
                AppsDrawerUi()
            }
        ) {
           DesktopUi()
        }
    }
}
