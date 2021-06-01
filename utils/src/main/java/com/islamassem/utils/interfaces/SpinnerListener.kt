package com.islamassem.utils.interfaces

import android.view.View
import android.widget.AdapterView
import com.islamassem.utils.logE
import kotlinx.coroutines.runBlocking

class SpinnerListener<T>(private var skipFirst : Boolean,val block : (T)->Unit): AdapterView.OnItemSelectedListener{
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (skipFirst){
            skipFirst = false
            return
        }
        try {
            val t = parent!!.selectedItem;
            @Suppress("UNCHECKED_CAST")
            block(t as T);
        }catch ( e : Exception){
            logE("onItemSelected","onItemSelected exception in SpinnerListener",e);
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

}