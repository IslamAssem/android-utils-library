package com.islamassem.utils.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.islamassem.utils.inflate

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseViewHolder<T : Any>(view : View,val adapter: BaseAdapter<T, *>) :
    RecyclerView.ViewHolder(view) {
    constructor(parent: ViewGroup, layoutRes: Int,  adapter: BaseAdapter<T, *>):this(inflate(layoutRes, parent,),adapter)

    lateinit var item: T

    abstract fun bind(t: T, onTClickListener: OnItemClickListener<T, View?>?)
    var selected: Int?
        get() = adapter.getSelected()
        set(selected) {
            setSelected(selected)
        }
    fun setSelected(selected: Int?,revers: Boolean = true){
        adapter.setSelected(selected?:-1,revers)
    }
}