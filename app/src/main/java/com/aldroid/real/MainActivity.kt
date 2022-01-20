package com.aldroid.real

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aldroid.real.ui.LauncherScreen
import com.aldroid.real.ui.getApps
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

        val apps = getApps()

        val phone = getApps(Intent.CATEGORY_APP_CONTACTS).first()
        val msg = getApps(Intent.CATEGORY_APP_MESSAGING).first()
        val maps = getApps(Intent.CATEGORY_APP_MAPS).first()
        val gallery = getApps(Intent.CATEGORY_APP_GALLERY).first()

        val quickLaunch = listOf(phone, msg, maps, gallery)

        setContent {
            RealPhoneTheme {

                LauncherScreen(apps, quickLaunch)

            }
        }
    }

}
