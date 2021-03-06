package com.aldroid.real

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.aldroid.real.AppsState.Loaded
import com.aldroid.real.AppsState.Loading
import com.aldroid.real.QuickAppState.Add
import com.aldroid.real.QuickAppState.Edit
import com.aldroid.real.QuickAppState.Empty
import com.aldroid.real.QuickAppState.Ready
import com.aldroid.real.model.AppInfo
import com.aldroid.real.ui.getApps
import com.aldroid.real.ui.openApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class AppsState {
    object Loading : AppsState()
    class Loaded(val apps: List<AppInfo>) : AppsState()
}


sealed class QuickAppState {
    object Empty : QuickAppState()
    class Edit(val app: AppInfo) : QuickAppState()
    object Add : QuickAppState()
    class Ready(val app: AppInfo) : QuickAppState()
}

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val _apps = MutableStateFlow<AppsState>(Loading)
    val apps: StateFlow<AppsState> get() = _apps


    private val _quickApps = MutableStateFlow<List<QuickAppState>>(
        listOf(Empty, Empty, Empty, Empty)
    )
    val quickApps: StateFlow<List<QuickAppState>> get() = _quickApps

    private val _note = MutableStateFlow("")
    val note: StateFlow<String> get() = _note

    fun updateNote(noteValue: String) {
        _note.value = noteValue
    }

    private val _wallpaper = MutableStateFlow(R.drawable.wallpaper_2)
    val wallpaper: StateFlow<Int> get() = _wallpaper

    init {

        getApplication<Application>().applicationContext.let {

            viewModelScope.launch {
                val apps = it.getApps()

                _apps.value = Loaded(apps)

                val phone = it.getApps(Intent.CATEGORY_APP_CONTACTS).first()
                val msg = it.getApps(Intent.CATEGORY_APP_MESSAGING).first()
                val maps = it.getApps(Intent.CATEGORY_APP_MAPS).first()
                val gallery = it.getApps(Intent.CATEGORY_APP_GALLERY).first()

                _quickApps.value = listOf(Ready(phone), Ready(msg), Ready(maps), Ready(gallery))
            }


        }


    }


    fun addQuickApp(appInfo: AppInfo, position: Int) {
        val apps = _quickApps.value.toMutableList()
        apps[position] = Ready(appInfo)
        _quickApps.value = apps
    }

    fun addQuickApp(position: Int) {
        val apps = _quickApps.value.toMutableList()
        apps[position] = Add
        _quickApps.value = apps
        resetEdit()
    }

    fun deleteQuickApp(position: Int) {
        val apps = _quickApps.value.toMutableList()
        apps[position] = Empty
        _quickApps.value = apps
    }


    fun editQuickApp(appInfo: AppInfo, position: Int) {
        val apps = _quickApps.value.toMutableList()
        apps[position] = Edit(appInfo)
        _quickApps.value = apps
    }

    fun resetEdit() {
        _quickApps.value = _quickApps.value.toMutableList().map {
            if (it is Edit) {
                Ready(it.app)
            } else {
                it
            }
        }
    }

    fun openApp(app: AppInfo) {
        getApplication<Application>().applicationContext.openApp(app)
        resetEdit()
    }


}
