package com.aldroid.real.ui.desktop.quickapps

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aldroid.real.AppViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun QuickAppEmptyCell(
    viewModel: AppViewModel = viewModel(),
    position: Int
) {
    Row(
        modifier = Modifier
            .clickable { viewModel.addQuickApp(position) }
            .size(78.dp),
    ) {
        Icon(
            modifier = Modifier.size(78.dp),
            imageVector = Filled.Add,
            contentDescription = "Add"
        )
    }
}
