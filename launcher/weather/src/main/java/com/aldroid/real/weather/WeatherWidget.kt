package com.aldroid.real.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WeatherWidget(modifier : Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {

            Text(
                text = "Weather Widget"
            )

    }
}

@Preview
@Composable
private fun preview(){
    MaterialTheme {
        WeatherWidget()
    }
}