package com.islamassem.utils.base

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes as StrRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.islamassem.utils.dialog.MessageDialog
import com.islamassem.utils.*
import com.islamassem.utils.logE
import kotlin.test.todo

@Suppress("MemberVisibilityCanBePrivate")
class MessagesHandler(val context: Context) {
    companion object {
        @JvmStatic
        val HIDE_DIALOG = "HIDE_DIALOG"
        var dialog : MessageDialog? = null
    }
    private lateinit var  toast: Toast


    /////////////////////////////////////////////////
    //message methods
    /////////////////////////////////////////////////
    fun showToast(@StrRes res: Int, duration: Int = Toast.LENGTH_LONG, cancelOld: Boolean = true) {
        if (cancelOld)
            hideToast()
        toast = Toast.makeText(context, res, duration)
        toast.show()
    }

    fun showToast(string: CharSequence, duration: Int = Toast.LENGTH_LONG, cancelOld: Boolean = true) {
        if (cancelOld)
            hideToast()
        toast = Toast.makeText(context, string, duration)
        toast.show()
    }

    private fun hideToast() {
        try {
            if (::toast.isInitialized)
            toast.cancel()
        } catch (e: Exception) {
            logE(javaClass.simpleName, e.message.toString(), e)
        }
    }
    /////////////////////////////////////////////////
    //message methods
    /////////////////////////////////////////////////

    fun showProgressBar() {
        showDialog(MessageDialog().apply { isProgress = true })

    }
    fun showProgressBar(@StrRes message: Int) {
        if (message == 0)
            hideDialog(MessageDialog());
        else showProgressBar(getStringRes(context, message));

    }

    fun showProgressBar(message: CharSequence) {
        val messageDialog = MessageDialog()
        messageDialog.messageText = message
        messageDialog.isProgress = true
        showDialog(messageDialog)
    }

    fun showMessageDialog(t: Throwable) {
        t.message?.let { showMessageDialog(it) }
    }

    fun showMessageDialog(message: CharSequence) {
        val messageDialog = MessageDialog()
        messageDialog.messageText = message
        showDialog(messageDialog)
    }

    fun showMessageDialog(@StrRes message: Int) {
        val messageDialog = MessageDialog()
        messageDialog.messageResource = message
        showDialog(messageDialog)
    }

    fun showMessageDialog(title: CharSequence, message: CharSequence) {
        val messageDialog = MessageDialog()
        messageDialog.titleText = title
        messageDialog.messageText = message
        showDialog(messageDialog)
    }
    fun showMessageDialog(
        title: CharSequence,
        message: CharSequence,
        onOkClick: View.OnClickListener,
        canCancel: Boolean = true
    ) {
        val messageDialog = MessageDialog()
        messageDialog.titleText = title
        messageDialog.messageText = message
        messageDialog.onOkClickListener = View.OnClickListener { onOkClick.onClick(it);hideDialog(
            messageDialog)}
        messageDialog.onCancelClickListener = View.OnClickListener {hideDialog(messageDialog)}
        messageDialog.canCancel = canCancel
        showDialog(messageDialog)
    }
    fun showMessageDialog(
        title: CharSequence,
        message: CharSequence,
        onOkClick: View.OnClickListener,
        onCancelClick: View.OnClickListener,
        canCancel: Boolean = true
    ) {
        val messageDialog = MessageDialog()
        messageDialog.titleText = title
        messageDialog.messageText = message
        messageDialog.onOkClickListener = View.OnClickListener { onOkClick.onClick(it);hideDialog(
            messageDialog)}
        messageDialog.onCancelClickListener = View.OnClickListener {onCancelClick.onClick(it);hideDialog(
            messageDialog)}
        messageDialog.canCancel = canCancel
        showDialog(messageDialog)
    }

    fun showMessageDialog(@StrRes title: Int, message: CharSequence) {
        val messageDialog = MessageDialog()
        messageDialog.titleResource = title
        messageDialog.messageText = message
        showDialog(messageDialog)
    }
    fun showMessageDialog(
        @StrRes title: Int,
        @StrRes message: Int,
        onOkClick: View.OnClickListener,
        canCancel: Boolean = true
    ) {
        val messageDialog = MessageDialog()
        messageDialog.titleResource = title
        messageDialog.messageResource = message
        messageDialog.onOkClickListener = View.OnClickListener { onOkClick.onClick(it);hideDialog(
            messageDialog)}
        messageDialog.onCancelClickListener = View.OnClickListener {hideDialog(messageDialog)}
        messageDialog.canCancel = canCancel
        showDialog(messageDialog)
    } 
    fun showMessageDialog(
        @StrRes title: Int,
        message: CharSequence,
        onOkClick: View.OnClickListener,
        canCancel: Boolean = true
    ) {
        val messageDialog = MessageDialog()
        messageDialog.titleResource = title
        messageDialog.messageText = message
        messageDialog.onOkClickListener = View.OnClickListener { onOkClick.onClick(it);hideDialog(
            messageDialog)}
        messageDialog.onCancelClickListener = View.OnClickListener {hideDialog(messageDialog)}
        messageDialog.canCancel = canCancel
        showDialog(messageDialog)
    }

    fun showMessageDialog(
        @StrRes title: Int,
        @StrRes message: Int,
        onOkClick: View.OnClickListener,
        onCancelClick: View.OnClickListener,
        canCancel: Boolean = true
    ) {
        val messageDialog = MessageDialog()
        messageDialog.titleResource = title
        messageDialog.messageResource = message
        messageDialog.onOkClickListener = View.OnClickListener { onOkClick.onClick(it);hideDialog(
            messageDialog)}
        messageDialog.onCancelClickListener = View.OnClickListener {onCancelClick.onClick(it);hideDialog(
            messageDialog)}
        messageDialog.canCancel = canCancel
        showDialog(messageDialog)
    }
    fun showMessageDialog(
        @StrRes ok: Int,
        @StrRes cancel: Int,
        @StrRes message: Int,
        onOkClick: View.OnClickListener,
        onCancelClick: View.OnClickListener,
        canCancel: Boolean = true
    ) {
        val messageDialog = MessageDialog()
        messageDialog.okResource = ok
        messageDialog.cancelResource = cancel
        messageDialog.messageResource = message
        messageDialog.onOkClickListener = View.OnClickListener { onOkClick.onClick(it);hideDialog(
            messageDialog)}
        messageDialog.onCancelClickListener = View.OnClickListener {onCancelClick.onClick(it);hideDialog(
            messageDialog)}
        messageDialog.canCancel = canCancel
        showDialog(messageDialog)
    }
    fun showMessageDialog(
        @StrRes title: Int,
        message: CharSequence,
        onOkClick: View.OnClickListener,
        onCancelClick: View.OnClickListener,
        canCancel: Boolean = true
    ) {
        val messageDialog = MessageDialog()
        messageDialog.titleResource = title
        messageDialog.messageText= message
        messageDialog.onOkClickListener = View.OnClickListener { onOkClick.onClick(it);hideDialog(
            messageDialog)}
        messageDialog.onCancelClickListener = View.OnClickListener {onCancelClick.onClick(it);hideDialog(
            messageDialog)}
        messageDialog.canCancel = canCancel
        showDialog(messageDialog)
    }

    fun showMessageDialog(@StrRes title: Int, @StrRes message: Int) {
        val messageDialog = MessageDialog()
        messageDialog.titleResource = title
        messageDialog.messageResource = message
        showDialog(messageDialog)
    }


    fun showMessageDialog(
        @StrRes ok: Int,
        @StrRes cancel: Int,
        @StrRes title: Int,
        @StrRes message: Int,
        onOkClick: View.OnClickListener,
        onCancelClick: View.OnClickListener,
        canCancel: Boolean = true
    ) {
        val messageDialog = MessageDialog()
        messageDialog.okResource = ok
        messageDialog.cancelResource = cancel
        messageDialog.titleResource = title
        messageDialog.messageResource = message
        messageDialog.onOkClickListener = View.OnClickListener { onOkClick.onClick(it);hideDialog(
            messageDialog)}
        messageDialog.onCancelClickListener = View.OnClickListener {onCancelClick.onClick(it);hideDialog(
            messageDialog)}
        messageDialog.canCancel = canCancel
        showDialog(messageDialog)
    }
    fun showMessageDialog(
        @StrRes ok: Int,
        @StrRes cancel: Int,
        @StrRes title: Int,
        message: CharSequence,
        onOkClick: View.OnClickListener,
        onCancelClick: View.OnClickListener,
        canCancel: Boolean = true
    ) {
        val messageDialog = MessageDialog()
        messageDialog.okResource = ok
        messageDialog.cancelResource = cancel
        messageDialog.titleResource = title
        messageDialog.messageText = message
        messageDialog.onOkClickListener = View.OnClickListener { onOkClick.onClick(it);hideDialog(
            messageDialog)}
        messageDialog.onCancelClickListener = View.OnClickListener {onCancelClick.onClick(it);hideDialog(
            messageDialog)}
        messageDialog.canCancel = canCancel
        showDialog(messageDialog)
    }

    private fun showDialog(messageDialog: MessageDialog) {
        try {
            hideDialog()
            dialog = messageDialog
            if (context is AppCompatActivity)
                messageDialog.showNow(context.supportFragmentManager, javaClass.simpleName);
        } catch (e: Exception) {
            logE(javaClass.simpleName, e.message.toString(), e)
        }
    }

    fun hideDialog() {
        dialog?.let { hideDialog(it) }
    }
    fun hideDialog(messageDialog: MessageDialog) {
        try {
            if (!messageDialog.isHidden)
                messageDialog.dismiss();
//            messageDialog = null;
        } catch (e: Exception) {
            logE(javaClass.simpleName, e.message.toString(), e)
        }
        try {
            if (!messageDialog.isHidden)
                messageDialog.dismiss();
        } catch (e: Exception) {
            messageDialog.dismissAllowingStateLoss();
            logE(javaClass.simpleName, e.message.toString(), e)
        }

    }
    fun isShowingDialog(activity: AppCompatActivity) : Boolean{
        val dialog = activity.supportFragmentManager.findFragmentByTag(MessageDialog::class.java.simpleName)
        //todo check later
        return  dialog != null && dialog.isAdded
    }
}