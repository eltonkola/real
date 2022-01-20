package com.aldroid.real.ui.desktop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DesktopUi(){


    Column() {

        DesktopPagerUi(modifier = Modifier.fillMaxWidth().weight(1f))
        Spacer(modifier = Modifier.size(8.dp))
        QuickAppUi()
        SearchBarUi()

    }

}


//val keyboardController = LocalSoftwareKeyboardController.current