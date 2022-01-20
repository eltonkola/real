package com.aldroid.real.ui.desktop

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aldroid.real.AppViewModel
import com.aldroid.real.AppsState.Loaded
import com.aldroid.real.ui.model.AppInfo
import com.aldroid.real.ui.openApp
import com.aldroid.real.ui.toBitmap

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun QuickAppReadyCell(viewModel: AppViewModel = viewModel(),
                      app: AppInfo,
                    position: Int
                    ) {
    Row(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        viewModel.editQuickApp(app , position)
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
