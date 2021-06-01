package com.islamassem.utils.base


import android.content.Context
import androidx.multidex.MultiDexApplication
import com.islamassem.utils.LocalHelper
import com.islamassem.utils.SharedPreferencesHelper

open class BaseApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesHelper.mContext = this
    }
    override fun attachBaseContext(base: Context?) {
        SharedPreferencesHelper.mContext = base
        super.attachBaseContext(base?.let { LocalHelper.onAttachApp(it) })
    }
}