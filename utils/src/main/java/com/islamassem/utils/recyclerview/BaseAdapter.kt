package com.islamassem.utils.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
@Suppress("MemberVisibilityCanBePrivate", "unused", "PropertyName")
abstract class BaseAdapter<T : Any, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH> {
    companion object {
        const val TYPE_1 = 900
        const val TYPE_2 = 901
        const val TYPE_3 = 902
        const val TYPE_4 = 903
        const val TYPE_5 = 904
        const val TYPE_6 = 905
        const val TYPE_7 = 906
        const val TYPE_8 = 907
        const val TYPE_9 = 908
        const val FIRST = 909
        const val MIDDLE = 910
        const val LAST = 911
        const val SINGLE = 912
        const val SELECTED = 913
        const val NOT_SELECTED = 914
        const val GRID = 915
        const val LIST = 916
        const val GRID_FEATURED = 917
        const val LIST_FEATURED = 918
        const val HEADER = 919
        fun<A : Any> initialize(items : MutableList<A>,@LayoutRes res : Int,block : (View,A) -> Unit) : BaseAdapter<A,BaseViewHolder<A>> =
            object : BaseAdapter<A,BaseViewHolder<A>>(items){
                 override fun onCreateViewHolder(
                     parent: ViewGroup,
                     viewType: Int
                 ): BaseViewHolder<A> {
                     return object:BaseViewHolder<A>(parent,res,this){
                          override fun bind(
                              t: A,
                              onTClickListener: OnItemClickListener<A, View?>?
                          ) {
                              block(itemView,t)
                          }

                      }
                 }

             }
    }

    constructor() {
        this.items = ArrayList()
    }

    constructor(onItemClickListener: OnItemClickListener<T, View?>) {
        this.items = ArrayList()
        this.onItemClickListener = onItemClickListener
    }

    constructor(items: MutableList<T>) {
        this.items = items
    }

    constructor(items: MutableList<T>, onItemClickListener: OnItemClickListener<T, View?>) {
        this.items = items
        this.onItemClickListener = onItemClickListener
    }

     val items: MutableList<T>

    //    var this_ : BaseAdapter<T, VH> = this
    private var selected: Int? = null


    var onItemClickListener: OnItemClickListener<T, View?>? = null
    var onSelectedItemChange: OnSelectedItemChange<T>? = null

    fun setSelected(selected: Int, revers: Boolean = true): Boolean {
        val old = this.selected ?: -1
        this.selected = selected
        if (old == -1 && selected == -1)
            return false
        if (old == selected && revers) {
            return setSelected(-1)
        }
        if (old in 0 until itemCount) {
            notifyItemChanged(old)
        }
        if (selected in 0 until itemCount) {
            notifyItemChanged(selected)
        }
        if (onSelectedItemChange != null) onSelectedItemChange!!.onSelectedItemChange(
            old,
            getItem(old),
            selected, getItem(selected)
        )
        return true
    }


    open fun getSelectedItem(): T? {
        return if (selected in 0 until itemCount) items[selected!!] else null
    }


    open fun getItem(position: Int): T? {
        return if (position in 0 until itemCount) items[position] else null
    }

    fun indexOf(t: T): Int {
        return items.indexOf(t)
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)?.let { holder.bind(it, onItemClickListener) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    open fun clear() {
        val size = itemCount
        items.clear()
        if (size > 0) notifyItemRangeRemoved(0, size)
    }

    fun updateItems(items: List<T>) {
        for (t in items)
            updateItems(t)
    }

    fun updateItems(t: T) {
        val index = indexOf(t)
        if (index in 0 until itemCount){
            items[index] = t
            notifyItemChanged(index)
        } else {
            addItems(t)
        }
    }
    fun addItems(items: List<T>) {
        for (t in items)
            addItems(t)
    }

    fun addItems(t: T) {
        val index = indexOf(t)
        if (index in 0 until itemCount)
            updateItems(t)
        else {
            items.add(t)
            notifyItemInserted(indexOf(t))
        }
    }
    fun remove(items: List<T>) {
        for (t in items)
            remove(t)
    }
    fun remove(t: T) {
        val index = indexOf(t)
        if (index in 0 until itemCount) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }
    fun setItems(items: List<T>){
        clear()
        addItems(items)
    }

    fun getSelected(): Int? {
        return selected
    }

}
