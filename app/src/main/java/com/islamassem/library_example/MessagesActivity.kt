package com.islamassem.library_example

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import com.islamassem.utils.base.BaseActivity
import com.islamassem.utils.getColorRes
import com.islamassem.utils.interfaces.LogLifecycleObserver
import kotlinx.android.synthetic.main.activity_messages.*


class MessagesActivity() : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_messages
    }
    init {
        lifecycleObserver = LogLifecycleObserver("TestMy")
        lifecycle.addObserver(LogLifecycleObserver("TestMy"))
    }
    override fun initViews() {
        show_message_text.setOnClickListener{
            messagesHandler.showMessageDialog("title", "Showing Message Dialog")
        }
        show_toast_text.setOnClickListener{
            messagesHandler.showToast("Showing Message Toast")
        }
        show_toast_res.setOnClickListener{
            messagesHandler.showToast(R.string.app_name)
        }
    }

    override fun saveInstanceState(savedInstanceState: Bundle) {
        
    }

    override fun initData(data: Bundle) {
        
    }

    override fun initVariables(context: Context) {
        
    }

    override fun initViewModel() {
        
    }

    fun show_message_res_ask_not_cancel(view: View) {
        messagesHandler.showMessageDialog(R.string.app_name, R.string.second_fragment_label, {
            messagesHandler.showToast("ok clicked")
        }, false)
    }
    fun show_message_res_ask_not_cancel_colored(view: View) {
        val message = SpannableStringBuilder()
        val span =  ForegroundColorSpan(getColorRes(this_, R.color.red))
        val span2 =  ForegroundColorSpan(getColorRes(this_, R.color.blue))
        message.append("Hello")
        message.append("Islam")
        message.setSpan(span,0,5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        message.setSpan(span2,5,10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        messagesHandler.showMessageDialog(R.string.app_name, message, {
            messagesHandler.showToast("ok clicked")
        }, false)
    }
    fun show_message_text_ask_not_cancel(view: View) {
        messagesHandler.showMessageDialog("Logout Confirmation", "Are you sure you want to logout?", {
            messagesHandler.showToast("ok clicked")
        }, false)
    }
    fun show_message_res_ask(view: View) {
        messagesHandler.showMessageDialog(R.string.app_name, R.string.second_fragment_label, {
            messagesHandler.showToast("ok clicked")
        })
    }
    fun show_message_text_ask(view: View) {
        messagesHandler.showMessageDialog("title", "Showing Message Dialog", {
            messagesHandler.showToast("ok clicked")
        })
    }
    fun show_message_res(view: View) {
        messagesHandler.showMessageDialog(R.string.app_name, R.string.second_fragment_label)
    }
}