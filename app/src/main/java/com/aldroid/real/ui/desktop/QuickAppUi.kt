package com.aldroid.real.ui.desktop

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aldroid.real.AppViewModel
import com.aldroid.real.AppsState.Loaded
import com.aldroid.real.QuickAppState
import com.aldroid.real.QuickAppState.Add
import com.aldroid.real.QuickAppState.Edit
import com.aldroid.real.QuickAppState.Empty
import com.aldroid.real.QuickAppState.Ready
import com.aldroid.real.ui.model.AppInfo
import com.aldroid.real.ui.openApp
import com.aldroid.real.ui.toBitmap

@Composable
fun QuickAppUi(viewModel: AppViewModel = viewModel()) {

    val quickApps = viewModel.quickApps.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
            .padding(start = 8.dp, end = 8.dp)
            .alpha(0.4f),
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.LightGray
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            quickApps.value.forEachIndexed { position, appState ->
                Box(modifier = Modifier.weight(1f)){
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
