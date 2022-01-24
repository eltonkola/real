package com.aldroid.real.clock

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Preview(showBackground = true)
@Composable
fun AnalogClock(viewModel: ClockViewModel = viewModel()) {
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
                    color = Color.Black,
                    start = Offset(size.width / 2, size.width / 2),
                    end = Offset(size.width / 2, 200f),
                    strokeWidth = 14f
                )
            }
            val minute = time.minute * 6f
            rotate(minute) {
                drawLine(
                    color = Color.Black,
                    start = Offset(size.width / 2, size.width / 2 + 40),
                    end = Offset(size.width / 2, 75f),
                    strokeWidth = 10f
                )
            }
            val second = time.second * 6f
            rotate(second) {
                drawLine(
                    color = Color.Red,
                    start = Offset(size.width / 2, size.width / 2),
                    end = Offset(size.width / 2, 75f),
                    strokeWidth = 6f
                )
            }

            drawCircle(
                color = Color.Black,
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