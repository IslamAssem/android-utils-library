package com.islamassem.utils

import android.view.View
import android.view.View.*

fun View.hide(gone : Boolean = true){
    this.visibility = if (gone) GONE else INVISIBLE
}
fun View.show(){
    this.visibility = VISIBLE
}