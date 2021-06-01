package com.islamassem.utils.interfaces

import android.content.Context
import android.text.Spanned
import android.text.TextUtils
import com.islamassem.utils.LocalHelper
import com.islamassem.utils.getHtml
import com.islamassem.utils.isEmpty

abstract class HasArEnName {

     abstract fun getArName(): String
    abstract fun getEnName(): String
    fun getName(context: Context): String {
        return if (LocalHelper.isEnglishEnabled(context)) {
            if (isEmpty(getEnName())) getArName() else getEnName()
        }
        else if (isEmpty(getArName())) getEnName() else getArName()
    }

    fun getArDescription(): String {
        return ""
    }

    fun getEnDescription(): String {
        return ""
    }

    fun getDescription(context: Context): Spanned {
        return if (LocalHelper.isEnglishEnabled(context)) getHtml(if (isEmpty(getEnDescription())) getArDescription() else getEnDescription())
        else getHtml(if (isEmpty(getArDescription())) getEnDescription() else getArDescription())
    }

    fun getArFullDescription(): String {
        return ""
    }

    fun getEnFullDescription(): String {
        return ""
    }

    fun getFullDescription(context: Context): Spanned {
        return if (LocalHelper.isEnglishEnabled(context)) getHtml(if (TextUtils.isEmpty(getEnFullDescription())) getArFullDescription() else getEnFullDescription()) else getHtml(
            if (TextUtils.isEmpty(getArFullDescription())) getEnFullDescription() else getArFullDescription())
    }
}