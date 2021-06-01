package com.islamassem.utils.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.islamassem.utils.firstNonNull
import com.islamassem.utils.inflate
import com.islamassem.utils.interfaces.LifecycleStatus
import com.islamassem.utils.interfaces.LogLifecycleObserver
import com.islamassem.utils.interfaces.OnFragmentBackPressed
import java.lang.Exception

abstract class BaseFragment : Fragment() {
    var onFragmentBackPressed : OnFragmentBackPressed? = null
    private val lifecycleObserver = LogLifecycleObserver(javaClass.simpleName)
    lateinit var messagesHandler: MessagesHandler
    var status = LifecycleStatus.NONE

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycleObserver.onAttach()
        if (!::messagesHandler.isInitialized) {
            messagesHandler = if (context is BaseActivity  && context.isMessageHandlerInitialized())
                context.messagesHandler
            else MessagesHandler(context)
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        lifecycleObserver.onAttach()
        if (!::messagesHandler.isInitialized) {
            messagesHandler = if (activity is BaseActivity  && activity.isMessageHandlerInitialized())
                activity.messagesHandler
            else MessagesHandler(activity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleObserver.onCreate()
        status = LifecycleStatus.CREATED
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
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
        onFragmentBackPressed?.let {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object :
                OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (it.onBackPressed())
                    {
                        this.isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }

            })
        }
    }

    override fun onStart() {
        super.onStart()
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
        try{
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
        }catch (e : Exception){

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