package com.islamassem.library_example

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.islamassem.utils.LocalHelper
import com.islamassem.utils.LocalHelper.onAttachActivity
import com.islamassem.utils.LocalHelper.setLocale
import com.islamassem.utils.base.BaseActivity
import com.islamassem.utils.isEmpty

class TestActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_local
    override fun initViews() {
    }

    override fun saveInstanceState(savedInstanceState: Bundle) {
    }

    override fun initData(data: Bundle) {
    }

    override fun initVariables(context: Context) {
    }

    override fun initViewModel() {
    }

    fun change_default_language(view: View) {
        if (setDefaultLanguage())
            messagesHandler.showToast("No need to restart activity")
        else {
            finish()
            startActivity(TestActivity::class.java)
        }
    }
    fun change(view: View) {
        val v = view as TextView
        if (v.text.toString().toLowerCase().contains("change"))
            setLocale("ar")
            else setLocale("en")

        finish()
        startActivity(TestActivity::class.java)
    }
}