package com.islamassem.utils.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.islamassem.utils.R
import com.islamassem.utils.firstNonNull
import com.islamassem.utils.inflate
import com.islamassem.utils.interfaces.LifecycleStatus
import com.islamassem.utils.interfaces.LogLifecycleObserver
import com.islamassem.utils.logE

abstract class BaseDialogFragment : DialogFragment() {
    private val lifecycleObserver = LogLifecycleObserver(javaClass.simpleName)
    lateinit var messagesHandler: MessagesHandler
    var status = LifecycleStatus.NONE


    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag).addToBackStack(null)
            ft.commitAllowingStateLoss()
            manager.executePendingTransactions()
        } catch (e: IllegalStateException) {
            logE("ABSDIALOGFRAG", "Exception", e)
        }
    }

    override fun showNow(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitNowAllowingStateLoss()
            manager.executePendingTransactions()
        } catch (e: IllegalStateException) {
            logE("ABSDIALOGFRAG", "Exception", e)
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycleObserver.onAttach()
        if (!::messagesHandler.isInitialized) {
            messagesHandler = if (context is BaseActivity && context.isMessageHandlerInitialized())
                context.messagesHandler
            else MessagesHandler(context)
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        lifecycleObserver.onAttach()
        if (!::messagesHandler.isInitialized) {
            messagesHandler = if (activity is BaseActivity && activity.isMessageHandlerInitialized())
                activity.messagesHandler
            else MessagesHandler(activity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.custom_dialog);
        lifecycleObserver.onCreate()
        status = LifecycleStatus.CREATED
    }


    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE);
        return if(getLayoutId() != 0)
            inflate(getLayoutId(),container,inflater,context)
        else doBinding(container,inflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleObserver.onViewCreated()
        firstNonNull(savedInstanceState,arguments)?.let { initData(it) }
        initViewModel()
        context?.let {
            initVariables(it)
        }
        initViews()
    }


    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        lifecycleObserver.onStart()
        status = LifecycleStatus.RUNNING
    }

    override fun onPause() {
        super.onPause()
        lifecycleObserver.onPause()
        status = LifecycleStatus.PAUSED
    }

    override fun onResume() {
        super.onResume()
        lifecycleObserver.onResume()
        status = LifecycleStatus.RUNNING
    }

    override fun onStop() {
        super.onStop()
        lifecycleObserver.onStop()
        status = LifecycleStatus.PAUSED
    }

    final override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveInstanceState(outState)
        lifecycleObserver.onSaveInstanceState()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        lifecycleObserver.onDestroyView()
        status = LifecycleStatus.DESTROYED_VIEW
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleObserver.onDestroy()
        status = LifecycleStatus.DESTROYED
    }


    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        var animation = super.onCreateAnimation(transit, enter, nextAnim)
        view?.let {
            val preLayerType = it.layerType
            if (animation == null && nextAnim != 0) animation = AnimationUtils.loadAnimation(
                activity, nextAnim)
            if (animation != null) {
                it.setLayerType(View.LAYER_TYPE_HARDWARE, null)
                animation?.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}
                    override fun onAnimationEnd(animation: Animation) {
                        it.setLayerType(preLayerType, null)
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })
            }
        }
        return animation
    }
    @LayoutRes
    open fun getLayoutId() = 0
    open fun doBinding(container: ViewGroup?, inflater: LayoutInflater): View{
        throw NotImplementedError()
    }
    abstract fun initViews()
    abstract fun saveInstanceState(savedInstanceState: Bundle)
    abstract fun initData(data: Bundle)
    abstract fun initVariables(context: Context)
    abstract fun initViewModel()

    override fun toString(): String {
        return javaClass.simpleName
//        return BaseFragment::class.java.simpleName
    }
}