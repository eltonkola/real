package com.aldroid.real

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.provider.MediaStore


fun getCameraPackageName(context: Context?, pm: PackageManager): ResolveInfo? {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    var cameraInfo: ResolveInfo? = null
    val pkgList = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    if (pkgList != null && pkgList.size > 0) {
        cameraInfo = pkgList[0]
    }
    return cameraInfo
}
