package com.islamassem.library_example.recycler

import com.islamassem.utils.recyclerview.HeaderItem

open class MessageHeader (open val date:String) : Any(),HeaderItem {
    override fun isHeader(): Boolean {
       return true
    }
}