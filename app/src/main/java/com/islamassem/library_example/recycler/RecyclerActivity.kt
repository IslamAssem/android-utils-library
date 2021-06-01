package com.islamassem.library_example.recycler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.islamassem.library_example.R
import com.islamassem.utils.getDrawableRes
import com.islamassem.utils.recyclerview.DividerItemDecoration
import com.islamassem.utils.recyclerview.ItemDecoration
import kotlinx.android.synthetic.main.activity_items.*

class RecyclerActivity :AppCompatActivity{
    constructor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        recyclerView.adapter = MessagesAdapter(
            items = MutableList(30){
                if (it % 5 == 0)
                    return@MutableList MessageHeader(date = "$it / 12 /2020")
                return@MutableList Message(id = it,date = "$it / 12 /2020",sender = "$it : Sender",message = "$it : bla bla bla bla bla bla bla bla bla ...etc")
            })
        recyclerView.addItemDecoration(ItemDecoration(recyclerView.adapter as MessagesAdapter))
        recyclerView.addItemDecoration(DividerItemDecoration(this,(recyclerView.layoutManager as LinearLayoutManager).orientation)
            .apply {
                getDrawableRes(this@RecyclerActivity,R.drawable.ic_rectangle)?.let {
                   setDrawable(it)
                }
        })
    }
}