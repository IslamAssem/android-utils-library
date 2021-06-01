package com.islamassem.library_example.recycler

import android.view.View
import android.view.ViewGroup
import com.islamassem.library_example.R
import com.islamassem.utils.recyclerview.*
import kotlinx.android.synthetic.main.recycler_message.view.*
import kotlinx.android.synthetic.main.recycler_message.view.date

class MessagesAdapter(items: MutableList<MessageHeader>) : HeaderAdapter<MessageHeader, BaseViewHolder<MessageHeader>>(items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MessageHeader> {
        if (viewType == HEADER)
        return HeaderHolder(parent, R.layout.recycler_message_header,this@MessagesAdapter)
        return MessageHolder(parent, R.layout.recycler_message,this@MessagesAdapter)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position)!!.isHeader()) HEADER else TYPE_1
    }

    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        for (i in itemPosition downTo 0)
            if (isHeader(i))
                return i
        return 0
    }

    override fun getHeaderLayout(headerPosition: Int): Int = R.layout.recycler_message_header_fixed

    override fun bindHeaderData(header: View, headerPosition: Int) {
        header.date.text = getItem(headerPosition)!!.date
    }
}
class MessageHolder(parent: ViewGroup, layoutRes: Int, adapter: BaseAdapter<MessageHeader, *>) : BaseViewHolder<MessageHeader>(parent, layoutRes, adapter){
    override fun bind(t: MessageHeader, onTClickListener: OnItemClickListener<MessageHeader, View?>?) {
        if (t is Message){
            itemView.title.text = t.sender
            itemView.message.text = t.message
            itemView.date.text = t.date
        }
    }
}
class HeaderHolder(parent: ViewGroup, layoutRes: Int, adapter: BaseAdapter<MessageHeader, *>) : BaseViewHolder<MessageHeader>(parent, layoutRes, adapter ){
    override fun bind(t: MessageHeader, onTClickListener: OnItemClickListener<MessageHeader, View?>?) {
        itemView.date.text = t.date
    }
}
