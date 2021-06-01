package com.islamassem.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

//
//fun inflate(@LayoutRes layoutResourceId: Int, parent: ViewGroup): View {
//    return inflate( layoutResourceId, parent,LayoutInflater.from(parent.context))
//}
//can use @JvmOverloads and delete the upper inflate method buy not yet good with kotlin to decide what is better
@JvmOverloads
fun inflate(@LayoutRes layoutResourceId: Int, parent: ViewGroup?,inflater: LayoutInflater? = null,context: Context? = null): View {
    if (inflater !=null)
    return inflater.inflate(layoutResourceId, parent, false)
    return inflate(layoutResourceId, parent,LayoutInflater.from(context?:parent!!.context))
}