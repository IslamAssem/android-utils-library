package com.islamassem.utils.recyclerview

import android.view.View
import androidx.annotation.CallSuper

@Suppress("UNUSED_PARAMETER", "unused")
open class OnItemClickListener<T : Any?, V : View?>  {
    enum class ClickType {
        ADD, EDIT, DELETE, SET_DEFAULT
    }

    protected fun onItemClick() {}
    protected fun onItemClick(t: T) {}
    protected fun onItemClick(v: V) {}
    protected fun onItemClick(position: Int) {}
    protected fun onItemClick(t: T, position: Int) {}
    protected fun onItemClick(v: V, position: Int) {}
    @CallSuper
    open fun onItemClick(t: T, v: V, position: Int) {
        onItemClick()
        onItemClick(t)
        onItemClick(v)
        onItemClick(position)
        onItemClick(t, position)
        onItemClick(v, position)
    }

    @CallSuper
    open fun onItemClick(t: T, type: ClickType?, position: Int) {
        onItemClick()
        onItemClick(t)
        onItemClick(position)
        onItemClick(t, position)
        onItemClick(t, type)
    }

    open fun onItemClick(t: T, clickType: ClickType?) {}
}
