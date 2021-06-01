package com.islamassem.library_example

import android.app.Application
import android.content.Context
import com.islamassem.utils.LocalHelper
import com.islamassem.utils.base.BaseApp
import com.islamassem.utils.logE

class App:BaseApp{
    constructor(){
    }

    override fun attachBaseContext(base: Context?) {
        base?.let {
            super.attachBaseContext(LocalHelper.onAttachApp(it))
        }
    }

    override fun onCreate() {
        super.onCreate()
        logE("TestUtils","test\n")
    }
}