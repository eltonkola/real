package com.aldroid.real.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aldroid.real.ui.model.AppInfo
import com.aldroid.real.ui.theme.RealPhoneTheme

@Composable
fun AppCellUi(app: AppInfo) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(6.dp)
            .clickable {
                context.openApp(app)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .padding(2.dp)
                .size(64.dp),
            bitmap = app.icon.toBitmap().asImageBitmap(), contentDescription = app.label.toString()
        )

        Spacer(
            modifier = Modifier.size(2.dp),
        )

        Text(text = app.label.toString())
    }

}

//@Preview(showBackground = true)
//@Composable
//private fun Preview() {
//    RealPhoneTheme {
//        AppCellUi(mockApp)
//    }
//}