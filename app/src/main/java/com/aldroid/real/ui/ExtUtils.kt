package com.aldroid.real.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.drawable.Drawable
import com.aldroid.real.ui.model.AppInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.ExperimentalTime


fun Context.openApp(app: AppInfo){
    val launchIntent = packageManager.getLaunchIntentForPackage(app.packageName.toString())
    startActivity(launchIntent)
}

fun Drawable.toBitmap() : Bitmap {
    val bitmap = Bitmap.createBitmap(
        intrinsicWidth,
        intrinsicHeight, ARGB_8888
    )
    val canvas = android.graphics.Canvas(bitmap)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)
    return bitmap
}

fun Context.getApps(type: String = Intent.CATEGORY_LAUNCHER) : List<AppInfo>{
    val pm: PackageManager = packageManager
    val appsList = ArrayList<AppInfo>()

    val i = Intent(Intent.ACTION_MAIN, null)
    i.addCategory(type)

    val allApps = pm.queryIntentActivities(i, 0)
    for (ri in allApps) {
        val app = AppInfo(
            label = ri.loadLabel(pm),
            packageName = ri.activityInfo.packageName,
            icon = ri.activityInfo.loadIcon(pm)
        )
        appsList.add(app)
    }
    return appsList
}


@OptIn(ExperimentalTime::class)
fun tickerFlow(period: Duration, initialDelay: Duration = Duration.ZERO) = flow {
    delay(initialDelay)
    while (true) {
        emit(Unit)
        delay(period)
    }
}
