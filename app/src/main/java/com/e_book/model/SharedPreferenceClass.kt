package com.e_book.model

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceClass(context: Context) {
    private val USER_PREFS = "E_Book"
    private val appSharedPrefs: SharedPreferences =
        context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE)
    private val prefsEditor: SharedPreferences.Editor = appSharedPrefs.edit()

    fun getString(stringKeyValue: String): String {
        return appSharedPrefs.getString(stringKeyValue, "") ?: ""
    }

    fun setString(stringKeyValue: String, _stringValue: String) {
        prefsEditor.putString(stringKeyValue, _stringValue).apply()
    }

    fun clearData() {
        prefsEditor.clear().apply()
    }

    fun removeKey(key: String) {
        prefsEditor.remove(key).apply()
    }
}
