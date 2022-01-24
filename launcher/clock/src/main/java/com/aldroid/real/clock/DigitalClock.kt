package com.aldroid.real.clock

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
@Composable
fun DigitalClock(
    viewModel: ClockViewModel = viewModel()
) {

    val time by viewModel.time.collectAsState()

    Text(
        text = "${time.hour.toInt()}:${time.minute.toInt()}:${time.second.toInt()}",
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
            fontSize = 30.sp
        )
    )
}
