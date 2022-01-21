package com.aldroid.real.ui.desktop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aldroid.real.AppViewModel
import com.aldroid.real.ui.desktop.clock.AnalogClock
import com.aldroid.real.ui.desktop.clock.DigitalClock

@Composable
fun StatsDesktop(
    viewModel: AppViewModel = viewModel(),
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(80.dp))

        DigitalClock()
    }
}