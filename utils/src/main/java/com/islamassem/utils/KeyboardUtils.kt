package com.islamassem.utils

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun hideSoftKeyboard(editText : EditText): Boolean{
    val im = editText.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
    im?.let {
        return it.hideSoftInputFromWindow(editText.windowToken,0)
    }
    return false
}
fun hideSoftKeyboard(activity : Activity) : Boolean{
    val im = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
    im?.let {
        if (im.isActive) {
            if (activity.currentFocus != null&&im.isAcceptingText) {
                return im.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0);
            }
        }
    }
    return false
}