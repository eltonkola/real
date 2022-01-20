package com.aldroid.real

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aldroid.real.ui.LauncherScreen
import com.aldroid.real.ui.theme.RealPhoneTheme
import com.google.accompanist.pager.ExperimentalPagerApi

class MainActivity : ComponentActivity() {
    @OptIn(
        ExperimentalPagerApi::class,
        kotlinx.coroutines.InternalCoroutinesApi::class,
        androidx.compose.material.ExperimentalMaterialApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RealPhoneTheme {
                LauncherScreen()
            }
        }
    }

}
