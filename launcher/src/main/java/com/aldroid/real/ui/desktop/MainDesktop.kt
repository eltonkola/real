package com.aldroid.real.ui.desktop

import android.widget.AnalogClock
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aldroid.real.AppViewModel
import com.aldroid.real.clock.AnalogClock


@Composable
fun MainDesktop(
    viewModel: AppViewModel = viewModel(),
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(20.dp))

        AnalogClock()

        //TODO - if we ever decide we want android widgets
//        AndroidViewBinding(WidgetHostBinding::inflate) {
//
//        }

    }

}