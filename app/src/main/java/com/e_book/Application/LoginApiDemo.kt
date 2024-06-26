package com.e_book.Application

import android.app.Application
import android.content.SharedPreferences
import com.e_book.model.SharedPreferenceClass
class LoginApiDemo:Application() {
    var editor:SharedPreferences.Editor?=null
    private val PREF_NAME="testing"
    var pref:SharedPreferences?=null

    private val KEY_SET_APP_RUN_FIRST_TIME="KEY_SET_APP_RUN_FIRST_TIME"
    companion object {
       /* lateinit var sharedPreferenceClass: SharedPreferenceClass*/
        var mContext: LoginApiDemo? = null

    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
       /* sharedPreferenceClass = SharedPreferenceClass(applicationContext)*/

    }


}