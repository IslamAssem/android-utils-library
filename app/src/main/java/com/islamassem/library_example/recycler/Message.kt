package com.islamassem.library_example.recycler

import com.islamassem.utils.recyclerview.HeaderItem

data class Message (override val date:String, val id:Int, val sender : String, val message: String) :
    MessageHeader(date) {
    override fun isHeader(): Boolean {
        return false
    }
}