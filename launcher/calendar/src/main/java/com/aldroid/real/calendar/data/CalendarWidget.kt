package com.aldroid.real.calendar.data

import android.graphics.ColorSpace
import android.graphics.ColorSpace.Named
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.convertTo
import androidx.core.graphics.toColor

@RequiresApi(VERSION_CODES.ICE_CREAM_SANDWICH)
@Composable
fun CalendarWidget(modifier : Modifier = Modifier) {

    val context = LocalContext.current
    val manager by remember { mutableStateOf(CalendarManager(context)) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        manager.getCalendars().forEach { calendar ->
            Text(
                text = "${calendar.displayName} - ${calendar.name}  (${calendar.events.size})"
            )

            calendar.events.forEach {  event ->
                EventRow(event = event)
            }
        }


    }
}

@Composable
fun EventRow(event: EventItem){
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.size(24.dp)
                .background(color = event.displayColor?.toComposeColor() ?: Color.Black),
        )
        Text(
            text = "${event.title}  ${event.dtStart} - (${event.dtEnd})",
        )
    }
}

@RequiresApi(VERSION_CODES.ICE_CREAM_SANDWICH)
@Preview
@Composable
private fun preview(){
    MaterialTheme {
        CalendarWidget()
    }
}

fun Int.toComposeColor() : Color {
    return Color(this)
}

