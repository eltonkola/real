package com.aldroid.real.ui.desktop

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode.Reverse
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aldroid.real.AppViewModel
import com.aldroid.real.ui.model.AppInfo
import com.aldroid.real.ui.toBitmap

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun QuickAppEditCell(
    viewModel: AppViewModel = viewModel(), position: Int, app: AppInfo
) {
    val infiniteTransition = rememberInfiniteTransition()


    val rotateAnimation by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(125, easing = LinearEasing),
            repeatMode = Reverse
        )
    )
    val scaleX by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(125, easing = LinearEasing),
            repeatMode = Reverse
        )
    )
    val scaleY by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(125, easing = LinearEasing),
            repeatMode = Reverse
        )
    )
    Box(
        modifier = Modifier
            .size(78.dp)
            .scale(scaleX, scaleY)
            .rotate(rotateAnimation)
            .clickable {
                viewModel.deleteQuickApp(position)
            },
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            bitmap = app.icon.toBitmap().asImageBitmap(),
            contentDescription = app.label.toString()
        )

        Card(
            modifier = Modifier,
            elevation = 6.dp,
            shape = RoundedCornerShape(14.dp),
            backgroundColor = Color.LightGray
        ) {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .padding(2.dp),
                imageVector = Filled.Clear,
                contentDescription = "Delete"
            )
        }

    }
}
