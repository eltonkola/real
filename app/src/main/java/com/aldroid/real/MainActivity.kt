package com.aldroid.real

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.aldroid.real.ui.LauncherScreen
import com.aldroid.real.ui.theme.RealPhoneTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    @OptIn(
        ExperimentalPagerApi::class,
        kotlinx.coroutines.InternalCoroutinesApi::class,
        androidx.compose.material.ExperimentalMaterialApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Turn off the decor fitting system windows, which means we need to through handling
        // insets
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {

            val systemUiController = rememberSystemUiController()
            val useDarkIcons = MaterialTheme.colors.isLight

            SideEffect {
                systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = useDarkIcons)
            }

            RealPhoneTheme {
                ProvideWindowInsets {
                    LauncherScreen()
                }
            }



        }
    }

}
