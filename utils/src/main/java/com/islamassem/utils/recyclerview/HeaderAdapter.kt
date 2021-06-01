package com.islamassem.utils.recyclerview

import android.view.View
import androidx.annotation.LayoutRes

abstract class HeaderAdapter<T,VH : BaseViewHolder<T>>: BaseAdapter<T, BaseViewHolder<T>> where T:Any,T:HeaderItem {
    constructor(onItemClickListener: OnItemClickListener<T, View?>) : super(onItemClickListener)
    constructor(items: MutableList<T>) : super(items)
    constructor(
        items: MutableList<T>,
        onItemClickListener: OnItemClickListener<T, View?>
    ) : super(items, onItemClickListener)


    /**
     * This method gets called by {@link StickHeaderItemDecoration} to fetch the position of the header item in the adapter
     * that is used for (represents) item at specified position.
     * @param itemPosition int. Adapter's position of the item for which to do the search of the position of the header item.
     * @return int. Position of the header item in the adapter.
     */
    abstract fun getHeaderPositionForItem(itemPosition: Int) : Int

    /**
     * This method gets called by {@link StickHeaderItemDecoration} to get layout resource id for the header item at specified adapter's position.
     * @param headerPosition int. Position of the header item in the adapter.
     * @return int. Layout resource id.
     */
    @LayoutRes
    abstract fun getHeaderLayout(headerPosition: Int) : Int

    /**
     * This method gets called by {@link StickHeaderItemDecoration} to setup the header View.
     * @param header View. Header to set the data on.
     * @param headerPosition int. Position of the header item in the adapter.
     */
    abstract fun bindHeaderData(header: View, headerPosition: Int)

    /**
     * This method gets called by [StickHeaderItemDecoration] to verify whether the item represents a header.
     * @param itemPosition int.
     * @return true, if item at the specified adapter's position represents a header.
     */
    fun isHeader(itemPosition: Int): Boolean{
        return if (getItem(itemPosition) == null) false else getItem(itemPosition)!!.isHeader()
    }
}