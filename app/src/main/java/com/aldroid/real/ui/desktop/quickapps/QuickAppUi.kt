package com.aldroid.real.ui.desktop.quickapps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aldroid.real.AppViewModel
import com.aldroid.real.QuickAppState.Add
import com.aldroid.real.QuickAppState.Edit
import com.aldroid.real.QuickAppState.Empty
import com.aldroid.real.QuickAppState.Ready

@Composable
fun QuickAppUi(viewModel: AppViewModel = viewModel()) {

    val quickApps = viewModel.quickApps.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
            .padding(start = 8.dp, end = 8.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(Color.White.copy(alpha = 0.3f))
        ,
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            quickApps.value.forEachIndexed { position, appState ->
                Box(modifier = Modifier.weight(1f)) {
                    when (appState) {
                        Empty -> {
                            QuickAppEmptyCell(position = position)
                        }
                        is Ready -> {
                            QuickAppReadyCell(position = position, app = appState.app)
                        }
                        is Edit -> {
                            QuickAppEditCell(position = position, app = appState.app)
                        }
                        is Add -> {
                            QuickAppAddCell(position = position)
                        }
                    }
                }
            }

        }
    }

}
