package com.aldroid.real.ui.widgets

import android.annotation.SuppressLint
import android.app.Activity
import android.appwidget.AppWidgetHostView
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.SeekBar
import androidx.appcompat.view.menu.MenuAdapter
import androidx.appcompat.view.menu.MenuBuilder
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.view.updateLayoutParams
import androidx.core.widget.NestedScrollView
import androidx.core.widget.PopupWindowCompat
import androidx.fragment.app.Fragment
import com.aldroid.real.R
import com.aldroid.real.databinding.FragmentWidgetListBinding
import com.aldroid.real.databinding.LayoutWidgetPopupBinding
import com.aldroid.real.databinding.WidgetsListPageLayoutBinding

import java.util.*
@Composable
fun FragmentWidgetContainer() {
    AndroidViewBinding(WidgetsListPageLayoutBinding::inflate) {
    }
}


class NoInterceptScrollView(context: Context, attrs: AttributeSet?) :
    NestedScrollView(context, attrs) {

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return false
    }
}

class WidgetsListPage : Fragment() {
    /*
    * Used to handle and add widgets to widgetContainer.
    */
    private var appWidgetManager: AppWidgetManager? = null
    private var appWidgetHost: LauncherAppWidgetHost? = null
    private lateinit var appWidgetContainer: LinearLayout

    /*
     * List containing widgets ID.
     */
    private lateinit var widgetsList: ArrayList<String>

    /*
     * The PopupWindow used during widget resize.
     */
    private var popupWindow: PopupWindow? = null

    private var binding: FragmentWidgetListBinding? = null

    private var isFavouritesShowing: Boolean = true

    private var widgetsMap: MutableMap<Int, Int> = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWidgetListBinding.inflate(inflater, container, false)

        appWidgetManager = AppWidgetManager.getInstance(requireContext())
        appWidgetHost = LauncherAppWidgetHost(requireContext(), WIDGET_HOST_ID)

        widgetsList = PreferenceHelper.widgetList()
        return binding?.root
    }

    fun atLeastOreo(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    }


    override fun onDestroyView() {
        super.onDestroyView()

        popupWindow?.dismiss()

        // Workaround to prevent widgets from being stuck (not updating).
        // https://github.com/Neamar/KISS/commit/3d5410307b8a8dc29b1fdc48d9f7c6ea1864dcd6
        if (atLeastOreo()) {
            appWidgetHost?.stopListening()
        }

        PreferenceHelper.updateWidgets(widgetsList)

        binding = null
    }

    override fun onPause() {
        super.onPause()
        popupWindow?.dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var scrollYPosition = 0

        appWidgetContainer = binding !!.widgetContainer
        val widgetScroller: NoInterceptScrollView = binding !!.widgetScroller
        val addWidget: Button = binding !!.addWidget

        addWidget.backgroundTintList = ColorStateList.valueOf(Color.GREEN)

        if (widgetsList.isNotEmpty()) {
            PreferenceHelper.widgetList()
                .filter { it.isNotEmpty() }
                .forEach { widgets ->
                    val widgetSplit = widgets.split("-")
                    val widgetId =
                        if (widgetSplit.size == 2) widgetSplit[0].toInt() else widgets.toInt()
                    val widgetSize =
                        if (widgetSplit.size == 2) widgetSplit[1].toInt() else WIDGET_DEFAULT_SIZE
                    val widgetIntent = Intent()
                    widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)

                    // Don't add ALL the widgets at once.
                    // TODO: Handle this a bit better, because not all devices are made equally.
                    appWidgetContainer.postDelayed({
                        addWidget(
                            widgetIntent,
                            widgetSize,
                            false
                        )
                    }, 300)
                }
        }

        addWidget.setOnClickListener {
            // Don't pull the panel just yet.
            //getLauncherActivity().requestPanelLock()

            Intent(AppWidgetManager.ACTION_APPWIDGET_PICK).apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetHost?.allocateAppWidgetId())
            }.also {
                startActivityForResult(it, WIDGET_CONFIG_START_CODE)
            }
        }

        widgetScroller.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            val scrollYDelta = scrollY - oldScrollY

            if (! widgetScroller.canScrollVertically(NestedScrollView.FOCUS_DOWN)) {
                if (! isFavouritesShowing) {
                  //  getLauncherActivity().showPinnedApps()

                    addWidget.visibility = View.GONE

                    isFavouritesShowing = true
                }
                scrollYPosition = 0
            } else {
                if (scrollYPosition < - 48 && isFavouritesShowing) {
                   // getLauncherActivity().hidePinnedApps()
                    addWidget.visibility = View.VISIBLE
                    isFavouritesShowing = false
                    scrollYPosition = 0
                }
            }

            if (scrollYDelta < 0) {
                scrollYPosition += scrollYDelta
            }
        }
    }

    fun atLeastLollipop(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            val widgetId =
                data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, WIDGET_CONFIG_DEFAULT_CODE)

            // Add the widget first.
            addWidget(data, WIDGET_DEFAULT_SIZE, true)

            // Launch widget configuration if it exists.
            if (requestCode != WIDGET_CONFIG_RETURN_CODE) {
                appWidgetManager?.getAppWidgetInfo(widgetId)?.configure?.apply {
                    if (atLeastLollipop()) {
                        appWidgetHost?.startAppWidgetConfigureActivityForResult(
                            requireActivity(),
                            widgetId,
                            0,
                            WIDGET_CONFIG_RETURN_CODE,
                            null
                        )
                    } else {
                        with(Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE)) {
                            component = this@apply
                            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
                        }.also {
                            startActivityForResult(it, WIDGET_CONFIG_RETURN_CODE)
                        }
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED && data != null) {
            val widgetId =
                data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, WIDGET_CONFIG_DEFAULT_CODE)
            if (widgetId != WIDGET_CONFIG_DEFAULT_CODE) {
                appWidgetHost?.deleteAppWidgetId(widgetId)
            }
        }
    }
    fun sdkIsAround(version: Int): Boolean {
        return Build.VERSION.SDK_INT >= version
    }
    /**
     * Adds a widget to the desktop.
     *
     * @param data Intent used to receive the ID of the widget being added.
     */
    private fun addWidget(data: Intent, size: Int, newWidget: Boolean) {
        if (! isAdded || activity == null) {
            // Nope. Not doing anything.
            return
        }

        val widgetId =
            data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, WIDGET_CONFIG_DEFAULT_CODE)

        // New widget can't be already allocated.
        if (widgetsList.contains("$widgetId-$size") && newWidget) return

        // Add this widget to the map early on.
        // It will be removed later if it doesn't exist,
        // so we won't have any lingering entries.
        widgetsMap[widgetId] = size

        appWidgetManager?.getAppWidgetInfo(widgetId)?.apply {
            appWidgetHost?.createView(requireActivity().applicationContext, widgetId, this)?.apply {
                // Notify widget of the available minimum space.
                setAppWidget(widgetId, appWidgetInfo)
                if (sdkIsAround(16)) {
                    minimumHeight = appWidgetInfo.minHeight
                    updateAppWidgetSize(
                        null,
                        appWidgetInfo.minWidth,
                        appWidgetInfo.minHeight,
                        appWidgetInfo.minWidth,
                        appWidgetInfo.minHeight
                    )
                }

                // Calculate size then add the widget.
                val actualHeight =
                    if (size >= 10) appWidgetInfo.minHeight * (size / 10) else appWidgetInfo.minHeight
                appWidgetContainer.addView(this, - 1, actualHeight)

                // Immediately listens for the widget.
                appWidgetHost?.startListening()
                addWidgetActionListener(this)
                if (newWidget) {
                    // Update our list.
                    widgetsList.add("$widgetId-$size")

                    // Apply preference changes.
                    PreferenceHelper.updateWidgets(widgetsList)
                } else if (! widgetsList.contains("$widgetId-$size")) {
                    widgetsList.add("$widgetId-$size")
                }
            }
        } ?: run {
            // We can't find anything, so remove this widget.
            // The index value doesn't matter here as we
            // don't have a view reference anyway.
            removeWidget(- 1, widgetId)
        }
    }

    private fun resizeWidget(index: Int, baseSize: Int, newSize: Int) {
        val view = appWidgetContainer.getChildAt(index) as AppWidgetHostView
        widgetsMap[view.appWidgetId] = newSize
        widgetsList[index] = "${view.appWidgetId}-$newSize"

        // Don't obliterate the widget by setting it to a 0-height view.
        // If 0 is received, set the actual size to 1 instead.
        view.updateLayoutParams<ViewGroup.LayoutParams> {
            height = if (newSize >= 10) baseSize * (newSize / 10) else baseSize
        }
    }

    private fun removeWidget(index: Int, id: Int) {
        appWidgetContainer.getChildAt(index)?.apply {
            val view = appWidgetContainer.getChildAt(index) as AppWidgetHostView

            // Now we can safely remove it from the container itself.
            appWidgetContainer.removeView(view)
        }.also {
            // Remove the widget from the list and map, then deallocate it from the host.
            widgetsList.remove("$id-${widgetsMap[id]}")
            widgetsMap.remove(id)
            appWidgetHost?.deleteAppWidgetId(id)

            // Update the preference by having the new list on it.
            PreferenceHelper.updateWidgets(widgetsList)
        }
    }

    private fun swapWidget(one: Int, two: Int) {
        val top = appWidgetContainer.getChildAt(one)
        val bottom = appWidgetContainer.getChildAt(two)

        // Swap the list and update preferences.
        Collections.swap(widgetsList, one, two)
        PreferenceHelper.updateWidgets(widgetsList)

        // Update our views.
        with(appWidgetContainer) {
            removeView(top)
            addView(top, two)
            removeView(bottom)
            addView(bottom, one)
        }
    }

    /**
     * Adds a long press action to widgets.
     */
    private fun addWidgetActionListener(view: View?) {
        view?.setOnLongClickListener {
            createPopupWindow(it as AppWidgetHostView)
            true
        }
    }


    fun sdkIsBelow(version: Int): Boolean {
        return Build.VERSION.SDK_INT < version
    }


    @SuppressLint("RestrictedApi")
    private fun createPopupWindow(view: AppWidgetHostView?) {
        val index = appWidgetContainer.indexOfChild(view)

        popupWindow = PopupWindow(requireActivity(), null).apply {
            with(LayoutWidgetPopupBinding.inflate(layoutInflater)) {
                contentView = this.root

                isOutsideTouchable = true
                isFocusable = true
                isSplitTouchEnabled = false

                // Our magic code is jumping over a library restriction here.
                // but we don't necessarily care for that.
                val menu = MenuBuilder(requireActivity())
                MenuInflater(requireActivity()).inflate(R.menu.menu_widget, menu).apply {
                    // Move actions should only be added when there is more than one widget.
                    menu.getItem(2).isVisible = appWidgetContainer.childCount > 1 && index > 0
                    menu.getItem(3).isVisible = appWidgetContainer.childCount != index + 1
                }
                widgetPopupList.adapter =
                    MenuAdapter(
                        menu,
                        layoutInflater,
                        false,
                        androidx.appcompat.R.layout.abc_popup_menu_item_layout // Technically we shouldn't do this.
                    )
                widgetPopupList.setOnItemClickListener { _, _, position, _ ->
                    when (position) {
                        0 -> {
                            // Don't pull the panel just yet.
                            //getLauncherActivity().requestPanelLock()

                            Intent(AppWidgetManager.ACTION_APPWIDGET_PICK).apply {
                                putExtra(
                                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                                    appWidgetHost?.allocateAppWidgetId()
                                )
                            }.also {
                                startActivityForResult(it, WIDGET_CONFIG_START_CODE)
                            }
                        }
                        1 -> view?.let { removeWidget(index, it.appWidgetId) }
                        // Position 2 can be 'upwards' or 'downwards'
                        // depending on the position of the widget
                        // and the availability of other widgets.
                        2 -> if (index - 1 != - 1) {
                            swapWidget(index, index - 1)
                        } else {
                            swapWidget(index, index + 1)
                        }
                        3 -> swapWidget(index, index + 1)
                    }
                    dismiss()
                }

                // Set the progress to the current size of the widget.
                widgetPopupResize.apply {
                   // applyAccent()
                    progress = widgetsMap[view?.appWidgetId] ?: 0
                    setOnSeekBarChangeListener(object :
                        SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                            view?.appWidgetInfo?.let { resizeWidget(index, it.minHeight, progress) }
                        }

                        override fun onStartTrackingTouch(p0: SeekBar?) {}
                        override fun onStopTrackingTouch(p0: SeekBar?) {
                            PreferenceHelper.updateWidgets(widgetsList)
                        }
                    })
                }
            }

            view?.let { anchor ->
                PopupWindowCompat.setOverlapAnchor(this, true)
                if (sdkIsBelow(21)) {
                    PopupWindowCompat.showAsDropDown(this, anchor, 0, - anchor.height, Gravity.TOP)
                } else {
                    showAsDropDown(anchor)
                }
                update(anchor.measuredWidth / 2, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }
    }

    companion object {
        private const val WIDGET_CONFIG_START_CODE = 1
        private const val WIDGET_CONFIG_RETURN_CODE = 2
        private const val WIDGET_CONFIG_DEFAULT_CODE = - 1
        private const val WIDGET_HOST_ID = 314

        private const val WIDGET_DEFAULT_SIZE = 20
    }
}