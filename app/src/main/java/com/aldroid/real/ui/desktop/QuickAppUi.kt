package com.aldroid.real.ui.desktop

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aldroid.real.AppViewModel
import com.aldroid.real.QuickAppState
import com.aldroid.real.QuickAppState.Edit
import com.aldroid.real.QuickAppState.Empty
import com.aldroid.real.QuickAppState.Ready
import com.aldroid.real.ui.openApp
import com.aldroid.real.ui.toBitmap

@Composable
fun QuickAppUi(viewModel: AppViewModel = viewModel()) {

    val quickApps = viewModel.quickApps.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(92.dp)
            .padding(start = 12.dp, end = 12.dp).alpha(0.4f),
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.LightGray
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            quickApps.value.forEach { app ->
                QuickAppCellUi(modifier = Modifier.weight(1f), app)
            }

        }
    }

}


@Composable
fun QuickAppCellUi(modifier: Modifier = Modifier, appstate: QuickAppState) {
    val context = LocalContext.current



        when (appstate) {
            Empty -> {
                Card(
                    modifier = modifier
                        //.fillMaxWidth()
                        //.padding(6.dp)
                        .size(84.dp)
                    ,
                    elevation = 4.dp,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add"
                    )
                }
            }
            is Ready -> {
                Image(
                    modifier = Modifier
                        .clickable {
                            context.openApp(appstate.app)
                        }
                        .padding(2.dp)
                        .size(84.dp),
                    bitmap = appstate.app.icon.toBitmap().asImageBitmap(),
                    contentDescription = appstate.app.label.toString()
                )
            }
            is Edit -> {
                Image(
                    modifier = Modifier
                        .clickable {
                            context.openApp(appstate.app)
                        }
                        .padding(2.dp)
                        .size(84.dp),
                    bitmap = appstate.app.icon.toBitmap().asImageBitmap(),
                    contentDescription = appstate.app.label.toString()
                )
            }
        }


}



