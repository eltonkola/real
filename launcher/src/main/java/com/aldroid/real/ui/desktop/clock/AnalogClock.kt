package com.aldroid.real.ui.desktop.clock

import androidx.compose.ui.tooling.preview.Preview
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import java.util.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aldroid.real.AppViewModel
import java.util.concurrent.TimeUnit

@Preview(showBackground = true)
@Composable
fun AnalogClock(viewModel: AppViewModel = viewModel(),) {
    Box(
        modifier = Modifier.size(350.dp),
        contentAlignment = Alignment.Center
    ) {

        val time by viewModel.time.collectAsState()

        Canvas(modifier = Modifier.size(250.dp)) {

            for (angle in 0..60) {
                rotate(angle * 6f) {
                    if (angle % 5 == 0) {
                        drawLine(
                            color = Color.Black,
                            start = Offset(size.width / 2, 0f),
                            end = Offset(size.width / 2, 40f),
                            strokeWidth = 4f
                        )
                    } else {
                        drawLine(
                            color = Color.Black,
                            start = Offset(size.width / 2, 15f),
                            end = Offset(size.width / 2, 25f),
                            strokeWidth = 4f
                        )
                    }
                }
            }

            val hour = ((time.hour + (time.minute / 60f)) * 6f * 5)
            rotate(hour) {
                drawLine(
                    color = Color.Black ,
                    start = Offset(size.width / 2 , size.width / 2),
                    end = Offset(size.width / 2 , 200f),
                    strokeWidth = 14f
                )
            }
            val  minute = time.minute * 6f
            rotate(minute) {
                drawLine(
                    color = Color.Black ,
                    start = Offset(size.width / 2 , size.width / 2 + 40),
                    end = Offset(size.width / 2 , 75f),
                    strokeWidth = 10f
                )
            }
            val second = time.second * 6f
            rotate(second) {
                drawLine(
                    color = Color.Red,
                    start = Offset(size.width / 2 , size.width / 2),
                    end = Offset(size.width / 2 , 75f),
                    strokeWidth = 6f
                )
            }

            drawCircle(
                color = Color.Black ,
                radius = 20f
            )
        }
    }
}

data class Time(
    var hour: Float,
    var minute: Float,
    var second: Float
)