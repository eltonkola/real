package com.aldroid.real.ui.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.preference.PreferenceManager
import java.util.*

/**
 * The class that performs CRUD towards preferences.
 *
 * It is preferable that any call to PreferenceManager and SharedPreferences
 * are directed here to unify and simplify their calls.
 */
object PreferenceHelper {

    private var widgets_list: ArrayList<String> = ArrayList()
    lateinit var preference: SharedPreferences
        private set
    var editor: Editor? = null
        private set

    fun widgetList(): ArrayList<String> {
        return widgets_list
    }

    @SuppressLint("CommitPrefEdits")
    fun initPreference(context: Context?) {
        preference = PreferenceManager.getDefaultSharedPreferences(context!!)
        editor = preference.edit()

        // Initialise widgets early on.
        preference.getString("widgets_list", "")?.split(";")
            ?.filterTo(widgets_list) { it.isNotEmpty() }
    }

    private fun parseDelimitedSet(set: HashSet<String>, map: MutableMap<String, String>) {
        set.forEach { it.split("|").apply { map[this[0]] = this[1] } }
    }

    fun updateWidgets(list: ArrayList<String>) {
        update("widgets_list", list.filter { it.isNotEmpty() }.joinToString(";"))
    }

    fun update(id: String?, stringSet: HashSet<String>?) {
        editor?.putStringSet(id, stringSet)?.apply()
    }

    fun update(id: String?, string: String?) {
        editor?.putString(id, string)?.apply()
    }

    fun update(id: String?, state: Boolean) {
        editor?.putBoolean(id, state)?.apply()
    }

    fun update(id: String?, integer: Int) {
        editor?.putInt(id, integer)?.apply()
    }

    fun reset() {
        editor?.clear()?.apply()

        // We have to individually clear the collections here.
        widgets_list.clear()
    }

}