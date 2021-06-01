@file:Suppress("PropertyName", "unused")

package com.islamassem.utils.base

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration.*
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.provider.Settings
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.app.ActivityCompat
import com.islamassem.utils.*
import com.islamassem.utils.interfaces.LifecycleStatus
import com.islamassem.utils.interfaces.LogLifecycleObserver
import java.lang.RuntimeException
import java.util.*
import kotlin.reflect.KClass

@Suppress("MemberVisibilityCanBePrivate", "LeakingThis")
abstract class BaseActivity : AppCompatActivity() {
    fun hasPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
    companion object {
        const val REQUEST_CODE = "requestCode"
        fun setClipboard(context: Context, text: String) {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = android.content.ClipData.newPlainText("Robatoia Promo Code", text)
            clipboard.setPrimaryClip(clip)
        }
    }
    val this_ = this@BaseActivity
    protected var lifecycleObserver = LogLifecycleObserver(javaClass.simpleName)
    lateinit var messagesHandler: MessagesHandler
    fun isMessageHandlerInitialized() = ::messagesHandler.isInitialized
    protected lateinit var defaultLanguage: String
    var status = LifecycleStatus.NONE
    var requestCode: Int = 0
    fun setClipboard(text: String) {
        setClipboard(this_, text)
    }

    override fun onBackPressed() {
        if (!hideSoftKeyboard(this_)) {
            //not used now
//            if (!(this instanceof HomeActivity)){
//                while (getOnBackPressedDispatcher().hasEnabledCallbacks()) {
//                    super.onBackPressed()
//                }
//            }
            super.onBackPressed()
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

    override fun onDestroy() {
        super.onDestroy()
        lifecycleObserver.onDestroy()
        status = LifecycleStatus.DESTROYED
    }

    open override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDefaultNightMode(SharedPreferencesHelper.getSharedPreferences(THEME, MODE_NIGHT_NO, this))
//        when (resources.configuration.uiMode and UI_MODE_NIGHT_MASK) {
//            UI_MODE_NIGHT_NO -> {
//            } // Night mode is not active, we're using the light theme
//            UI_MODE_NIGHT_YES -> {
//            } // Night mode is active, we're using dark theme
//        }
        window.decorView.layoutDirection = getLayoutDirection()
        lifecycleObserver.onCreate()
        status = LifecycleStatus.CREATED
        val layoutID = getLayoutId()
        val view = getLayoutView()
        if (layoutID != 0 && view != null)
            throw RuntimeException("You can't use both getLayoutView() and getLayoutId()")
        if (layoutID == 0 && view == null)
            doBinding()
        else if (layoutID != 0)
            setContentView(layoutID)
        else setContentView(view)
        if (savedInstanceState == null)
            initViewsInternal(intent.extras)
    }

    fun getLayoutDirection(): Int {
        return if (LocalHelper.isRtl(this)) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
    }

    override fun attachBaseContext(newBase: Context?) {
        logE("DefaultLocale", "before attaching = ${Locale.getDefault().language} vs ${newBase!!.resources.configuration.locale.language}")
        defaultLanguage = LocalHelper.getDefaultLanguage(newBase)
//        messagesHandler = MessagesHandler(new)
        super.attachBaseContext(LocalHelper.onAttachActivity(newBase))
        logE("DefaultLocale", "after attaching = ${Locale.getDefault().language} vs ${newBase!!.resources.configuration.locale.language}")
    }


    fun setDefaultLanguage(): Boolean {
        val current = LocalHelper.getLanguage(this_)
        LocalHelper.setLocale("")
        return !isEmpty(defaultLanguage) && !defaultLanguage.equals(current, false)
    }
//    protected void onCreateMap(Bundle savedInstanceState){
//
//    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        initViewsInternal(savedInstanceState)
    }

    private fun initViewsInternal(data: Bundle?) {
        window.decorView.isFocusable = true
        window.decorView.isFocusableInTouchMode = true
        messagesHandler = MessagesHandler(this_)
//         AndroidInjection.inject(this)
        firstNonNull(data, intent.extras)?.let {
            initData(it)
            requestCode = it.getInt(REQUEST_CODE)
        }
        initVariables(this)
        initViewModel()
        initViews()
    }

//    public void observe(BaseViewModel model) {
//        observe(this_,messagesHandler,model)
//    }
//    public static void observe(LifecycleOwner owner, MessagesHandler messagesHandler, BaseViewModel model) {
//        if (messagesHandler == null||owner == null || model == null)
//            return
//        model.getProgress().observe(owner, res->{
//            if (res <= 0)
//                messagesHandler.hideDialog()
//            else messagesHandler.showProgressBar(res)
//        })
//        model.getMessageResource().observe(owner,res->{
//            if (res <= 0)
//                messagesHandler.hideDialog()
//            else messagesHandler.showMessageDialog(res)
//        })
//        model.getMessageString().observe(owner, messagesHandler::showMessageDialog)
//        model.getToast().observe(owner, messagesHandler::showToast)
//    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        saveInstanceState(outState)
        outState.putInt(REQUEST_CODE, requestCode)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveInstanceState(outState)
        outState.putInt(REQUEST_CODE, requestCode)
    }

    fun saveParcelable(parcelable: Parcelable, outState: Bundle) {
        outState.putParcelable(DATA, parcelable)
    }

    fun saveParcelable(parcelables: Map<String, Parcelable>, outState: Bundle) {
        parcelables.onEach {
            outState.putParcelable(it.key, it.value)
        }
    }

    fun startActivity(activityClass: Class<*>) {
        startActivity(Intent(this, activityClass))
    }

    fun startActivity(key: String = DATA, parcelable: Parcelable, activityClass: Class<*>) {
        startActivity(Intent(this, activityClass).apply { putExtra(key, parcelable) })
    }

    fun startActivityForResult(requestCode: Int, activityClass: Class<*>) {
        startActivityForResult(Intent(this, activityClass).apply {
            putExtra(REQUEST_CODE,
                    requestCode)
        }, requestCode)
    }

    fun startActivityForResult(
            requestCode: Int,
            key: String = DATA,
            parcelable: Parcelable,
            activityClass: Class<*>
    ) {

        startActivityForResult(Intent(this, activityClass).apply {
            putExtra(key, parcelable)
            putExtra(REQUEST_CODE, requestCode)
        }, requestCode)
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) {
        intent.putExtra(REQUEST_CODE, requestCode)
        super.startActivityForResult(intent, requestCode, options)
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        intent.putExtra(REQUEST_CODE, requestCode)
        super.startActivityForResult(intent, requestCode)
    }

    fun startActivity(parcelables: Map<String, Parcelable>, activityClass: Class<*>) {
        startActivity(Intent(this, activityClass).apply {
            parcelables.onEach { putExtra(it.key, it.value) }
        })
    }

    fun getIntentWithParcelable(key: String = DATA, parcelable: Parcelable, activityClass: Class<*>): Intent {
        return Intent(this, activityClass).apply { putExtra(key, parcelable) }
    }

    fun getIntentWithParcelable(key: String = DATA, value: String, activityClass: Class<*>): Intent {
        return Intent(this, activityClass).apply { putExtra(key, value) }
    }

//
//    public void goToHome() {
//        if (User.isLoginOrGuest()) {
//            startActivity(HomeActivity.class)
//        } else {
//            Intent intent = new Intent(this,LoginActivity.class)
//            intent.putExtra("source","home")
//            startActivity(intent)
//        }
//        finishAffinity()
//    }


    /////////////////////////////
    var number: String? = null
    fun call(number: String) {
        this.number = number
        if (!isEmpty(number)) {
            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(Intent(Intent.ACTION_CALL).apply {
                        data = Uri.parse("tel:$number")
                    })
                } else ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        REQUEST_PHONE_CALL)

            } catch (e: Exception) {
                messagesHandler.showMessageDialog(e)
            }
        } else messagesHandler.showMessageDialog("لا يوجد رقم هاتف")
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                number?.let { call(it) }
        }
    }

    @LayoutRes
    open fun getLayoutId(): Int = 0
    open fun getLayoutView(): View? = null
    open fun doBinding(){
        throw RuntimeException("You must implement dataBinding/viewBinding or use  override getLayoutId()/getLayoutView()")
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
