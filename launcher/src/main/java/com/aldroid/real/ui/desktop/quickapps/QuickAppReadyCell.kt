package com.aldroid.real.ui.desktop.quickapps

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aldroid.real.AppViewModel
import com.aldroid.real.model.AppInfo
import com.aldroid.real.ui.toBitmap

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun QuickAppReadyCell(
    viewModel: AppViewModel = viewModel(),
    app: AppInfo,
    position: Int
) {
    Row(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        viewModel.editQuickApp(app, position)
                    },
                    onTap = {
                        viewModel.openApp(app)
                    }
                )
            }
            .size(78.dp),
    ) {
        Image(
            modifier = Modifier
                .padding(2.dp)
                .fillMaxSize(),
            bitmap = app.icon.toBitmap().asImageBitmap(),
            contentDescription = app.label.toString()
        )
    }
}
