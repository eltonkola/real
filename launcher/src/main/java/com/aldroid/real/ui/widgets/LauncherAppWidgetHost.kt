package com.aldroid.real.ui.widgets

import android.appwidget.AppWidgetHost
import android.appwidget.AppWidgetHostView
import android.appwidget.AppWidgetProviderInfo
import android.content.Context

/**
 * Specific [AppWidgetHost] that creates our [LauncherAppWidgetHostView]
 * which correctly captures all long-press events. This ensures that users can
 * always pick up and move widgets.
 */
class LauncherAppWidgetHost(context: Context?, hostId: Int) : AppWidgetHost(context, hostId) {
    override fun onCreateView(
        context: Context, appWidgetId: Int,
        appWidget: AppWidgetProviderInfo
    ): AppWidgetHostView {
        return LauncherAppWidgetHostView(context)
    }
}