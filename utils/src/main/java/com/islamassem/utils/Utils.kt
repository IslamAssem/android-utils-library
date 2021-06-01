@file:Suppress("unused")

package com.islamassem.utils

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaDrm
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.MarginLayoutParamsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.islamassem.utils.SharedPreferencesHelper.getSharedPreferences
import com.islamassem.utils.SharedPreferencesHelper.saveSharedPreferences
import java.io.File
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.math.roundToInt

fun handleStrings(list: List<String>) {
    if (list is ArrayList) {
        list[0].length
        // `list` is smart-cast to `ArrayList<String>`
    }
}
fun setImageViewAnimatedChange(imageView: ImageView, @DrawableRes newImage: Int){
    val drawable=ContextCompat.getDrawable(imageView.context, newImage);
    imageView.rotationY = 0f
    imageView.animate().rotationY(90f).setListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator?) {
        }

        override fun onAnimationEnd(p0: Animator?) {
            imageView.setImageDrawable(drawable)
            imageView.rotationY = 270f
            imageView.animate().rotationY(360f).setListener(null)
        }

        override fun onAnimationCancel(p0: Animator?) {
        }

        override fun onAnimationRepeat(p0: Animator?) {
        }
    })
}
//*********** Glide ********//
fun loadImage(
    imageView: ImageView,
    url: String?,
    progressBar: ProgressBar? = null,
    @DrawableRes placeholder: Int = 0,
    @DrawableRes error: Int = 0,
    asBitmap: Boolean = false,
    listener: RequestListener<Bitmap>? = null
){
    val errorResource = if (error == 0)placeholder else error
    val placeholderResource = if (placeholder == 0)error else placeholder
    if (url.isNullOrBlank()){
        progressBar?.visibility = GONE
        if (errorResource != 0)
        imageView.setImageResource(errorResource)
        return
    }
    if (asBitmap){
        var glide =Glide.with(imageView).asBitmap().load(url)
        if (placeholderResource != 0)
            glide = glide.placeholder(placeholderResource)
        if (errorResource != 0)
            glide = glide.error(errorResource)
        glide = glide.addListener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = GONE
                listener?.onLoadFailed(e, model, target, isFirstResource)
                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = GONE
                listener?.onResourceReady(resource, model, target, dataSource, isFirstResource)
                return false
            }
        })
        glide.into(imageView)
    }else {
        var glide = Glide.with(imageView).load(url)
        if (placeholderResource != 0)
            glide = glide.placeholder(placeholderResource)
        if (errorResource != 0)
            glide = glide.error(errorResource)
        glide = glide.addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = GONE

                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar?.visibility = GONE
                return false
            }
        })
        glide.into(imageView)
    }

}

fun loadImage(
    imageView: ImageView,
    file : File   ){
    Glide.with(imageView).load(file).into(imageView)
}
//*********** Log ********//test\n
fun logE(tag: String, message: String, e: Throwable? = null){
    if (tag.isBlank() || message.isNullOrBlank())
        return
    val length = message.length
    var i =0
    while (i<length){
        var newLine = message.indexOf("\n", i)
        newLine = if ( newLine !=-1 ) newLine else length
        do {
            val end = newLine.coerceAtMost(i + 3000)
            Log.e(tag, message.substring(i, end), e)
            i = end
        }while (i<newLine)
    }
}
fun logE(message: String, e: Throwable? = null){
  logE("Utils",message,e)
}

//*********** Check Empty ********//

//    public static String isValidPhone(String phone) {
//        if(phone.length()!=11){
//            return getStringRes(R.string.enter_valid_phone)
//        }
//        String s=phone.substring(0,3)
//        if("010".equalsIgnoreCase(s))
//            return null
//        if("011".equalsIgnoreCase(s))
//            return null
//        if("012".equalsIgnoreCase(s))
//            return null
//        if("015".equalsIgnoreCase(s))
//            return null
//        return getStringRes(R.string.phone_number_start_with_)
//    }

fun isEmpty(charSequence: CharSequence?): Boolean {
    charSequence?.let {
        isEmpty(charSequence.toString())
    }
    return true
}

fun isEmpty(editText: EditText): Boolean {
    return isEmpty(editText.text)
}

fun isEmpty(editable: Editable): Boolean {
    return isEmpty(editable.toString())
}

fun isEmpty(str: String?): Boolean {
    return str == null || str.replace(" ", "").replace(System.lineSeparator(), "")
        .trim().trimMargin().trimIndent()
        .isBlank()
}

fun isEmpty(text: TextView): Boolean {
    return isEmpty(text.text.toString())
}

fun<T> isEmpty(items : Collection<T>): Boolean {
    return items.isNotEmpty()
}
//*********** Check Android Version ********//
fun isAndroidR30(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
}
fun isAndroidQ29(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
}
fun isAndroidP28(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
}
fun isAndroidO26(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
}
fun isAndroidN24(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
}
fun isAndroidM23(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}
fun isAndroidL21(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}
fun isAndroidK19(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
}
//**** Used to Print the HashKey (SHA) ***//

fun byte2HexFormatted(bytes: ByteArray):String {
    val str = StringBuilder(bytes.size * 2)
    for (i in bytes.indices) {
        var h = Integer.toHexString(bytes[i].toInt())
        val l = h.length
        if (l == 1) h = "0$h"
        if (l > 2) h = h.substring(l - 2, l)
        str.append(h.toUpperCase(Locale.ENGLISH))
        if (i < (bytes.size - 1))
            str.append(':')
    }
    return str.toString()
}
//another way to convert byte to hex use byte2HexFormatted if you want it formatted
fun byte2Hex(bytes: ByteArray): String {
    val hexArray = charArrayOf(
        '0',
        '1',
        '2',
        '3',
        '4',
        '5',
        '6',
        '7',
        '8',
        '9',
        'A',
        'B',
        'C',
        'D',
        'E',
        'F'
    )
    val hexChars = CharArray(bytes.size * 2)
    var v: Int
    for (j in bytes.indices) {
        v = bytes[j].toInt() and 0xFF
        hexChars[j * 2] = hexArray[v.ushr(4)]
        hexChars[j * 2 + 1] = hexArray[v and 0x0F]
    }
    return String(hexChars)
}
//*********** print facebook BASE64 and SHA1 ********//
fun getApplicationSignature(context: Context): List<String> {
    val packageName = context.packageName
    val signatureList: List<String>
    try {
        if (isAndroidP28()) {
            // New signature
            val sig = context.packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNING_CERTIFICATES
            ).signingInfo
            signatureList = if (sig.hasMultipleSigners()) {
                // Send all with apkContentsSigners
                sig.apkContentsSigners.map {
                    val digest = MessageDigest.getInstance("SHA")
                    digest.update(it.toByteArray())
                    logE("SHA", "------------------- Printing Hash Key ------------------------")
                    logE("SHA", "Md String: $digest")
                    logE("SHA", "Base 64  : ${encodeToString(digest.digest(), DEFAULT)}")
                    logE("SHA", "SHA1     : ${byte2HexFormatted(digest.digest())}")
                    logE("SHA", "------------------- Printing Hash Key ------------------------")
                    byte2Hex(digest.digest())
                }
            } else {
                // Send one with signingCertificateHistory
                sig.signingCertificateHistory.map {
                    val digest = MessageDigest.getInstance("SHA")
                    digest.update(it.toByteArray())
                    logE("SHA", "------------------- Printing Hash Key ------------------------")
                    logE("SHA", "Md String: $digest")
                    logE("SHA", "Base 64  : ${encodeToString(digest.digest(), DEFAULT)}")
                    logE("SHA", "SHA1     : ${byte2HexFormatted(digest.digest())}")
                    logE("SHA", "------------------- Printing Hash Key ------------------------")
                    byte2Hex(digest.digest())
                }
            }
        } else {
            val sig = context.packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
            ).signatures
            signatureList = sig.map {
                val digest = MessageDigest.getInstance("SHA")
                digest.update(it.toByteArray())
                logE("SHA", "------------------- Printing Hash Key ------------------------")
                logE("SHA", "Md String: $digest")
                logE("SHA", "Base 64  : ${encodeToString(digest.digest(), DEFAULT)}")
                logE("SHA", "SHA1     : ${byte2HexFormatted(digest.digest())}")
                logE("SHA", "------------------- Printing Hash Key ------------------------")
                byte2Hex(digest.digest())
            }
        }

        return signatureList
    } catch (e: Exception) {
        // Handle error
    }
    return emptyList()
}
//*********** Returns information of the Device ********//

/*Get Device IMEI*/
@SuppressLint("MissingPermission")
fun getImei(context: Context): String? {
    var serial: String? = ""
    //        String num = "";
//        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
//        } else {
//            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            num = telephonyManager.getDeviceId();
//        }
//        Log.e("imei", num);
//        //num=num+getRandomNonce(5);
//        Log.e("imei+getRandomNonce", num);
    serial =
        when {
            isAndroidQ29() -> getUniqueId()
            isAndroidO26() -> Build.getSerial()
            else -> Build.SERIAL
        }
    if (Build.UNKNOWN.equals(serial, ignoreCase = true) || isEmpty(serial))
        serial = id(context)
    return serial
}
private const val PREF_UNIQUE_ID = "PREF_UNIQUE_ID"
var uniqueID: String? = null
private fun id(context: Context): String {
    if (uniqueID == null) {
        uniqueID = SharedPreferencesHelper.getSharedPreferences(PREF_UNIQUE_ID, "", context)
        if (uniqueID.isNullOrBlank()) {
            uniqueID = UUID.randomUUID().toString()
            SharedPreferencesHelper.saveSharedPreferences(PREF_UNIQUE_ID, uniqueID!!, context)
        }
    }
    return uniqueID!!
}
/**
 * UUID for the Widevine DRM scheme.
 * <p>
 * Widevine is supported on Android devices running Android 4.3 (API Level 18) and up.
 */
private  fun  getUniqueId(): String? {

    val WIDEVINE_UUID = UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L)
    var wvDrm: MediaDrm? = null
    try {
        wvDrm = MediaDrm(WIDEVINE_UUID)
        val widevineId = wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID)
        val md = MessageDigest.getInstance("SHA-256")
        md.update(widevineId)
        return  md.digest().toHexString()
    } catch (e: Exception) {
        return null
    } finally {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            wvDrm?.close()
        } else {
            wvDrm?.release()
        }
    }
}


private fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }

//*********** Check if device is emulator ********//

fun isEmulator(): Boolean {
    return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
            || Build.FINGERPRINT.startsWith("generic")
            || Build.FINGERPRINT.startsWith("unknown")
            || Build.HARDWARE.contains("goldfish")
            || Build.HARDWARE.contains("ranchu")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.MANUFACTURER.contains("Genymotion")
            || Build.PRODUCT.contains("sdk_google")
            || Build.PRODUCT.contains("google_sdk")
            || Build.PRODUCT.contains("sdk")
            || Build.PRODUCT.contains("sdk_x86")
            || Build.PRODUCT.contains("vbox86p")
            || Build.PRODUCT.contains("emulator")
            || Build.PRODUCT.contains("simulator")
}

//*********** Convert given String to Md5Hash ********//

//*********** Convert given String to Md5Hash ********//
fun getMd5Hash(input: String): String? {
    return try {
        val md = MessageDigest.getInstance("MD5")
        val messageDigest = md.digest(input.toByteArray())
        val number = BigInteger(1, messageDigest)
        var md5 = number.toString(16)
        while (md5.length < 32) md5 = "0$md5"
        md5
    } catch (e: NoSuchAlgorithmException) {
        logE("MD5", e.localizedMessage, e)
        null
    }
}


fun getRandomNonce(length: Int, moreCharacters: String = ""): String {

    //random max 32
    val allowedCharacters = TextUtils.concat(
        moreCharacters,
        "0123456789qwertyuiopasdfghjklzxcvbnm-"
    )
    val generator = Random()
    val randomStringBuilder = StringBuilder(length)
    for (i in 0 until length) randomStringBuilder.append(
        allowedCharacters[generator.nextInt(
            allowedCharacters.length
        )]
    )
    return randomStringBuilder.toString()
}


//*********** Get Resources ********//

fun getStringRes(@StringRes string: Int, context: Context): String? {
   return context.getString(string)
}

fun getStringRes(context: Context, @StringRes string: Int): String {
    return context.getString(string)
}

@ColorInt
fun getColorRes(@ColorRes color: Int, context: Context): Int {
    return ContextCompat.getColor(context, color)
}

@ColorInt
fun getColorRes(context: Context, @ColorRes color: Int): Int {
    return ContextCompat.getColor(context, color)
}

fun fetchAccentColor(context: Context): Int {
    val typedValue = TypedValue()
    val a = context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorAccent))
    val color = a.getColor(0, 0)
    a.recycle()
    return color
}

fun fetchPrimaryDarkColor(context: Context): Int {
    val typedValue = TypedValue()
    val a = context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorPrimaryDark))
    val color = a.getColor(0, 0)
    a.recycle()
    return color
}
fun fetchPrimaryColor(context: Context): Int {
    val typedValue = TypedValue()
    val a = context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorPrimary))
    val color = a.getColor(0, 0)
    a.recycle()
    return color
}
fun getDrawableRes(@DrawableRes drawableRes: Int, context: Context): Drawable? {
    return ContextCompat.getDrawable(context, drawableRes)
}

fun getDrawableRes(context: Context, @DrawableRes drawableRes: Int): Drawable? {
    return ContextCompat.getDrawable(context, drawableRes)
}
//*********** PX DP Conversion ********//

fun pxToDp(px: Float, context: Context): Int {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val dip1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, displayMetrics)
    val dip2 =
        (px / (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    return dip2
}

fun dpToPx(dp: Float, context: Context): Int {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val px1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dp, displayMetrics)
    val px2 =
        (dp * (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    return px2
}
fun dpToPx(dp: Float): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}
//*********** View ********//

fun setMargins(view: View, start: Int, end: Int, top: Int, bottom: Int){
    val params = view.layoutParams
    if (params is ViewGroup.MarginLayoutParams){
        params.bottomMargin = bottom
        params.topMargin = top
        MarginLayoutParamsCompat.setMarginStart(params, start)
        MarginLayoutParamsCompat.setMarginEnd(params, end)
        view.layoutParams = params
        view.requestLayout()
    }
}

fun getYouTubeId(youTubeUrl: String): String? {
    val pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed/)[^#&?]*"
    val compiledPattern = Pattern.compile(pattern)
    val matcher = compiledPattern.matcher(youTubeUrl)
    return if (matcher.find()) {
        matcher.group()
    } else {
        "error"
    }
}
fun <T> firstNonNull(vararg items: T):T?{
    if (items.isNullOrEmpty())
        return null
    for (t in items)
        if (t!=null)
            return t
    return null
}


fun  hasNull(vararg items: Any?):Boolean{
    if (items.isNullOrEmpty())
        return true
    for (t in items)
        if (t == null )
            return true
    return false
}

private fun isOpen(start: Int, end: Int, time: Int):Boolean{
    return if (start>end){
        time>=start || time<end
    }else {
        time in start until end
    }
}
fun checkOpen(open: Int, close: Int):Boolean{
    val  currentTimeMilliSeconds = System.currentTimeMillis()
    val formatter = SimpleDateFormat("HH", Locale.UK)
    val hour = formatter.format(Date(currentTimeMilliSeconds))
    return isOpen(open, close, hour.toInt())
}

fun getType(rawClass: Class<Any>, parameterClass: Class<Any>) : Type{
    return object :ParameterizedType {

        override fun getActualTypeArguments(): Array<Type> {
            return arrayOf(parameterClass)
        }

        override fun getRawType(): Type {
            return rawClass
        }

        override fun getOwnerType(): Type? {
            return null
        }

    };
}
fun openShareIntent(context: Context, url: String) {
    val sendIntent = Intent();
    sendIntent.action = Intent.ACTION_SEND;
    sendIntent.putExtra(Intent.EXTRA_TEXT, "please check out our product at: $url");
    sendIntent.type = "text/plain";
    context.startActivity(sendIntent);
}
fun getFixedLengthString(s: String, length: Int) : String{
    return String.format("%{$length}s", s);
}
fun getFixedLengthString(int: Int, length: Int) : String{
    return String.format("%0{$length}d", int);
}
fun openLink(context: Context, link: String?){
    if (link.isNullOrBlank())
        return
    if (link.toLowerCase(Locale.ENGLISH).startsWith("http://")||link.toLowerCase(Locale.ENGLISH).startsWith(
            "https://"
        ))
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
        else context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://$link")))
}

fun setTextHtml(textView: TextView, textHtml: String){
    textView.text = getHtml(textHtml)
}
fun  getHtml(string: String?) : Spanned{
    return if (isAndroidN24())
        Html.fromHtml(string, Html.FROM_HTML_MODE_COMPACT);
    else Html.fromHtml(string);
}

fun getAuthorization(): String {
    return getUserTokenType() + " " + getUserToken()
}

private fun getUserTokenType(): String {
    return KEY_TOKEN_DEFAULT
}

fun setUserToken(token: String) {
    saveSharedPreferences(KEY_TOKEN, token)
}

fun getUserToken(): String {
    return getSharedPreferences(KEY_TOKEN, "")
}
//fun isValidEmail(CharSequence target) : Boolean{
//    return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
//}
//fun isValidPassword(password : String) : Boolean{
//    if(isEmpty(password) || password.contains(" ")||pass)
//        return Boolean.FALSE;
//    return password.length() > 5;
//}
//fun locationToLatlng(userLocation: Location): LatLng? {
//    return LatLng(userLocation.latitude, userLocation.longitude)
//}

/*
    public static void initSlider(Context context, SliderLayout sliderLayout, String baseURl, List<String> images) {
        if (images == null || images.size() == 0)
            return;
        for (String img : images) {
            if (TextUtils.isEmpty(img))
                continue;
            BaseSliderView slide = new DefaultSliderView(context).image(baseURl.concat(img), false);
//            slide.error(R.drawable.ic_placeholder);
//            slide.empty(R.drawable.ic_placeholder);
//            slide.loading(R.drawable.ic_placeholder);
            sliderLayout.addSlider(slide);
        }
    }
    public static void initSlider(Context context, SliderLayout sliderLayout, List<Slider> images) {
        if (images == null || images.size() == 0)
            return;
        for (Slider img : images) {
            if (TextUtils.isEmpty(img.getImage()))
                continue;
            BaseSliderView slide = new DefaultSliderView(context).image( img.getImage(), false);
//            slide.error(R.drawable.ic_placeholder);
//            slide.empty(R.drawable.ic_placeholder);
//            slide.loading(R.drawable.ic_placeholder);
            sliderLayout.addSlider(slide);
        }
    }
    public static void initSlider(Context context, SliderLayout sliderLayout, String baseURl, String... images) {
        if (images == null || images.length == 0)
            return;
        for (String img : images) {
            if (TextUtils.isEmpty(img))
                continue;
            BaseSliderView slide = new DefaultSliderView(context).image(baseURl.concat(img), false);
//            slide.error(R.drawable.ic_placeholder);
//            slide.empty(R.drawable.ic_placeholder);
//            slide.loading(R.drawable.ic_placeholder);
            sliderLayout.addSlider(slide);
        }
    }
    public static void initSlider(View.OnClickListener onClickListener, Context context, SliderLayout sliderLayout, String baseURl, String... images) {
        if (images == null || images.length == 0)
            return;
        for (String img : images) {
            if (TextUtils.isEmpty(img))
                continue;
            BaseSliderView slide = new DefaultSliderView(context).image(baseURl.concat(img), false);
//            slide.error(R.drawable.ic_placeholder);
//            slide.empty(R.drawable.ic_placeholder);
//            slide.loading(R.drawable.ic_placeholder);
            slide.setOnImageClickListener(onClickListener);
            sliderLayout.addSlider(slide);
        }
    }
    //    public  void initSlider(Context context, SliderLayout sliderLayout, String[] items) {
//        if (items == null || items .length== 0)
//            return;
//        int i=0;
//         for (Object img : items) {
//            if (img==null||TextUtils.isEmpty(String.valueOf(img)))
//                continue;
//            Log.e("slider","s : "+i++);
//            DefaultSliderView f=new DefaultSliderView(context);
//            if(img.toString().toLowerCase().contains(APIClient.BASE_URL_IMAGES.toLowerCase()))
//                sliderLayout.addSlider(f.image(img.toString(), false));
//            else sliderLayout.addSlider(f.image(APIClient.BASE_URL_IMAGES+ img, false));
//        }
//
//        sliderLayout.stopAutoCycle();
//    }

 //    public static boolean showPasswordCheckBoxListener(boolean prevStatus, EditText password, ImageView view) {
//        if (prevStatus) {
//            view.setImageResource(R.drawable.ic_visibility_off);
//            password.setTransformationMethod(new PasswordTransformationMethod());  //hide the password from the edit text
//        } else {
//            view.setImageResource(R.drawable.ic_visibility_on);
//            password.setTransformationMethod(null); //show the password from the edit text
//        }
//        return !prevStatus;
//    }

//    public static boolean showPasswordCheckBoxListener(boolean prevStatus, EditText password, EditText passwordConfirm, ImageView view) {
//        if (prevStatus) {
//            view.setImageResource(R.drawable.ic_visibility_off);
//            password.setTransformationMethod(new PasswordTransformationMethod());  //hide the password from the edit text
//            passwordConfirm.setTransformationMethod(new PasswordTransformationMethod());  //hide the password from the edit text
//        } else {
//            view.setImageResource(R.drawable.ic_visibility_on);
//            password.setTransformationMethod(null); //show the password from the edit text
//            passwordConfirm.setTransformationMethod(null); //show the password from the edit text
//        }
//        return !prevStatus;
//    }
    public static boolean isValidName(CharSequence target) {
        return !isEmpty(target);
    }
//    public static String getAm(int hour){
//        String am=Utils2.getStringRes(R.string.am);
//        if(hour>11){
//            am=Utils2.getStringRes(R.string.pm);
//            hour=hour-12;
//        }
//        return hour+" "+am;
//    }
//    public static String isValidPhone(String phone) {
//        if(phone.length()!=11){
//            return getStringRes(R.string.enter_valid_phone);
//        }
//        String s=phone.substring(0,3);
//        if("010".equalsIgnoreCase(s))
//            return null;
//        if("011".equalsIgnoreCase(s))
//            return null;
//        if("012".equalsIgnoreCase(s))
//            return null;
//        if("015".equalsIgnoreCase(s))
//            return null;
//        return getStringRes(R.string.phone_number_start_with_);
//    }

//    public static void setImageViewAnimatedChange(Context c, final ImageView v,@DrawableRes final int new_image) {
//        try {
//            if (v.getTag() != null)
//                if (new_image == Integer.valueOf(v.getTag() + ""))
//                    return;
//        }catch (Exception e){}
//        final Animation anim_out = AnimationUtils.loadAnimation(c, R.anim.slide_out_right);
//        final Animation anim_in  = AnimationUtils.loadAnimation(c, R.anim.slide_in_left);
//        anim_out.setAnimationListener(new Animation.AnimationListener()
//        {
//            @Override public void onAnimationStart(Animation animation) {}
//            @Override public void onAnimationRepeat(Animation animation) {}
//            @Override public void onAnimationEnd(Animation animation)
//            {
//                //v.setImageBitmap(new_image);
//                v.setImageResource(new_image);
//                v.setTag(new_image);
//                v.startAnimation(anim_in);
//            }
//        });
//        v.startAnimation(anim_out);
//    }

    public static String formatDouble(double num) {
        return String.format("%.2f", num) ;
    }

    public static int size(List productList) {
        if (productList == null)
            return 0;
        return productList.size();
    }

    public static<T> void removeDuplicate(List<T> removeFrom, List<T> srcToCompare) {
        if (isEmpty(removeFrom)||isEmpty(srcToCompare))
            return ;
        for (int i =0; i <removeFrom.size();i++)
            if (srcToCompare.contains(removeFrom.get(i)))
                removeFrom.remove(i--);

    }
    public static String isValidPhone(String phone) {
        if(phone.length()!=11){
            return getStringRes(R.string.enter_valid_phone);
        }
        String s=phone.substring(0,3);
        if("010".equalsIgnoreCase(s))
            return null;
        if("011".equalsIgnoreCase(s))
            return null;
        if("012".equalsIgnoreCase(s))
            return null;
        if("015".equalsIgnoreCase(s))
            return null;
        return getStringRes(R.string.phone_number_start_with_);
    }
    public File getCacheFolder(Context context) {

        File cacheDir = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            cacheDir = new File(Environment.getExternalStorageDirectory(), "cachefolder");

            if(!cacheDir.isDirectory()) {

                cacheDir.mkdirs();

            }

        }


        if(!cacheDir.isDirectory()) {

            cacheDir = context.getCacheDir(); //get system cache folder

        }



        return cacheDir;
    }

    //*********** Used to Animate the MenuIcons ********//

    /*public static void animateView(View view) {
        Animation animation = AnimationUtils.loadAnimation(LocaleApp.getInstance(), R.anim.shake_icon);
        animation.setRepeatMode(Animation.ZORDER_TOP);
        animation.setRepeatCount(2);
        view.startAnimation(animation);

    }*/

    // To animate view slide out from top to bottom
    public static void slideToBottom(View view) {
        TranslateAnimation animate = new TranslateAnimation
                (0, 0, 0,1000);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }
    // slide the view from its current position to below itself
    public static void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                1000); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.post(()->view.setVisibility(View.GONE));
                Log.e("test","setVisibility : GONE");

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animate);
    }

    // slide the view from below itself to the current position
    public static void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        Log.e("test","setVisibility : VISIBLE");
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animate);
    }

    //*********** Returns the current DataTime of Device ********//

    //*********** Used to Calculate the Discount Percentage between New and Old Prices ********//

    public static String checkDiscount(String actualPrice, String discountedPrice) {

        if (discountedPrice == null) {
            discountedPrice = actualPrice;
        }

        Double oldPrice = Double.parseDouble(actualPrice);
        Double newPrice = Double.parseDouble(discountedPrice);

        double discount = (oldPrice - newPrice)/oldPrice * 100;

        return (discount > 0) ? Math.round(discount) +"% " + LocaleApp.getInstance().getString(R.string.off) : null;
    }
    private static double getDiscount(String actualPrice, String discountedPrice) {

        if (discountedPrice == null) {
            discountedPrice = actualPrice;
        }

        Double oldPrice = Double.parseDouble(actualPrice);
        Double newPrice = Double.parseDouble(discountedPrice);

        double discount = (oldPrice - newPrice)/oldPrice * 100;
        return round(discount,2);
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static String formatNumber(int myNumber){
        return NumberFormat.getInstance().format(myNumber);
    }

    public static String getLE() {
        return getStringRes(R.string.le);
    }


    //*********** Used to check if the LocaleApp is running in Foreground ********//
//
//    public static boolean isLocaleAppInForeground() {
//        ActivityManager activityManager = (ActivityManager) LocaleApp.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningLocaleAppProcessInfo> services = activityManager.getRunningLocaleAppProcesses();
//        boolean isActivityFound = false;
//
//        if (services.get(0).processName.equalsIgnoreCase(LocaleApp.getInstance().getPackageName())) {
//            Log.i("VC_Shop", "isLocaleAppInForeground_PackageName= "+ LocaleApp.getInstance().getPackageName());
//            isActivityFound = true;
//        }
//
//        return isActivityFound;
//    }



    //*********** Used to Print the HashKey (SHA) ********//



/*    public static DeviceInfo getDeviceInfo(@NonNull Context context,String token) {

        double lat = 0;
        double lng = 0;
        String IMEI = "";
        String NETWORK = "";
        String PROCESSORS = "";



        String UNIQUE_ID ="";// Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if(TextUtils.isEmpty(UNIQUE_ID))
            UNIQUE_ID = id(context);//
        else {
            if(TextUtils.isEmpty(UNIQUE_ID.toLowerCase().replace("unknown","")))
                UNIQUE_ID = id(context);//
        }
        PROCESSORS = String.valueOf(Runtime.getRuntime().availableProcessors());

        ActivityManager actManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        double totalRAM = Math.round( ((memInfo.totalMem /1024.0) /1024.0)  /1024.0 );


        if (CheckPermissions.is_PHONE_STATE_PermissionGranted()) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            try {
                IMEI = telephonyManager.getDeviceId();
                NETWORK = telephonyManager.getNetworkOperatorName();

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }


        if (CheckPermissions.is_LOCATION_PermissionGranted()) {
            LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            try {
                boolean gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                Location location = null;
                String provider = locationManager.getBestProvider(new Criteria(), true);
                final LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location loc) {}
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {}
                    @Override
                    public void onProviderEnabled(String provider) {}
                    @Override
                    public void onProviderDisabled(String provider) {}
                };
//                locationManager.requestLocationUpdates(provider, 1000, 0, locationListener);

                if (gps_enabled) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } else if (network_enabled) {
                    location = locationManager.getLastKnownLocation(provider);
                }

                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                }
                locationManager.removeUpdates(locationListener);

            } catch (SecurityException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        DeviceInfo device = new DeviceInfo();

        device.setDeviceID(UNIQUE_ID);

        if(isEmulator())
            device.setDeviceType("And_emulator");
        else device.setDeviceType("Android");
        device.setDeviceUser(Build.USER);
        device.setFirebaseToken(token);
        device.setDeviceModel(Build.BRAND +" "+Build.MODEL);
        device.setDeviceBrand(Build.BRAND);
        try {
            device.setDeviceSerial(Build.SERIAL);
        }
        catch (Exception e){

        }
        device.setDeviceSystemOS(System.getProperty("os.name"));
        device.setDeviceAndroidOS("Android "+ Build.VERSION.RELEASE);
        device.setDeviceManufacturer(Build.MANUFACTURER);
        device.setDeviceIMEI(IMEI);
        device.setDeviceRAM(totalRAM +"GB");
        device.setDeviceCPU(Build.UNKNOWN);
        device.setDeviceStorage(Build.UNKNOWN);
        device.setDeviceProcessors(PROCESSORS);
        device.setDeviceIP(Build.UNKNOWN);
        device.setDeviceMAC(Build.UNKNOWN);
        device.setDeviceNetwork(NETWORK);
        device.setDeviceLocation(lat +","+ lng);
        device.setDeviceBatteryLevel(Build.UNKNOWN);
        device.setDeviceBatteryStatus(Build.UNKNOWN);
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                device.setVersionCode(""+context.getPackageManager().getPackageInfo(context.getPackageName(), 0).getLongVersionCode());
            }
            else
                device.setVersionCode(""+context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
        }catch (Exception e){
            device.setVersionCode("error");
        }
        try{
            device.setVersionName(""+context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
        }catch (Exception e){
            device.setVersionCode("error");

        }
        return device;
    }


    public static void RegisterDeviceForFCM(final Context context,String token) {

        if(TextUtils.isEmpty(token))
            return;
        DeviceInfo device = Utils2.getDeviceInfo(context,token);
        Timber.e(device.toString());
        String userID="";
        UserDetails userDetails=UserDetails.readUser();
        if(userDetails!=null&&!TextUtils.isEmpty(userDetails.getUser_id()))
            userID=userDetails.getUser_id();
        else return;
        Call<EmptyResponse> call = APIClient.getInstance()
                .registerDevice
                        (
                                device.getDeviceID(),
                                device.getDeviceIMEI(),
                                device.getFirebaseToken(),
                                device.getDeviceModel(),
                                device.getDeviceType(),
                                device.getDeviceRAM(),
                                device.getDeviceLocation(),
                                device.getDeviceProcessors(),
                                device.getDeviceAndroidOS(),
                                "1",
                                "0",
                                "0",
                                userID,
                                device.getVersionCode(),
                                device.getVersionName()
                        );

        call.enqueue(new retrofit2.Callback<EmptyResponse>() {
            @Override
            public void onResponse(Call<EmptyResponse> call, retrofit2.Category<EmptyResponse> response) {
            }

            @Override
            public void onFailure(Call<EmptyResponse> call, Throwable t) {
            }
        });

    }
*/
*
* */