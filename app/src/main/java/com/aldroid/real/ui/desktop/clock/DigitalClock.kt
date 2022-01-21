package com.aldroid.real.ui.desktop.clock

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.layout.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aldroid.real.AppViewModel
import java.util.concurrent.TimeUnit

import kotlin.time.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.time.LocalDateTime


@OptIn(ExperimentalTime::class)
@Composable
fun DigitalClock(viewModel: AppViewModel = viewModel(),) {

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
