package com.aldroid.real.ui.desktop.quickapps

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aldroid.real.AppViewModel
import com.aldroid.real.AppsState.Loaded
import com.aldroid.real.ui.toBitmap

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun QuickAppAddCell(
    viewModel: AppViewModel = viewModel(),
    position: Int
) {

    val isOpen = remember { mutableStateOf(true) }

    Box {

        Icon(
            modifier = Modifier.size(78.dp),
            imageVector = Filled.Add,
            contentDescription = "Adding"
        )

        if (isOpen.value) {
            val apps = (viewModel.apps.value as Loaded).apps
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = true,
                onDismissRequest = {
                    isOpen.value = false
                    viewModel.deleteQuickApp(position)
                },
            ) {
                apps.forEach { app ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            isOpen.value = false
                            viewModel.addQuickApp(app, position)
                        }
                    ) {

                        Image(
                            modifier = Modifier
                                .padding(2.dp)
                                .size(32.dp),
                            bitmap = app.icon.toBitmap().asImageBitmap(),
                            contentDescription = app.label.toString()
                        )

                        Spacer(
                            modifier = Modifier.size(2.dp),
                        )

                        Text(text = app.label.toString())
                    }
                }
            }
        }


    }
}
