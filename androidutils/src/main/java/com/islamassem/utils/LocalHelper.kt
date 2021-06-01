@file:Suppress("unused")

package com.islamassem.utils

import android.content.Context
import androidx.core.text.TextUtilsCompat
import androidx.core.view.ViewCompat
import java.util.*
object LocalHelper {


    private const val LANGUAGE: String = "Lang"

    fun onAttachActivity(context: Context) :Context {
//        return if (isAndroidN24())updateResources(context, getLanguage(context)) else updateResourcesLegacy(context,  getLanguage(context))
        return if (isAndroidN24())updateResources(context, getLocal(context).language) else updateResourcesLegacy(context, getLocal(context).language)
    }
    fun onAttachApp(context: Context) :Context {
        val lang = getPersistedData(context,getDefaultLanguage(context))
        return if (isAndroidN24())updateResources(context,lang) else updateResourcesLegacy(context,lang)
    }
    fun isRtl(context: Context) : Boolean{
        return isRtl(getLocal(context))
    }
    fun isRtl(locale: Locale) : Boolean{
        return TextUtilsCompat.getLayoutDirectionFromLocale(locale) == ViewCompat.LAYOUT_DIRECTION_RTL
    }
    fun isEnglishEnabled(context: Context) : Boolean{
        return getLanguage(context).toLowerCase(Locale.ENGLISH).contains("en")
    }
    fun getLanguage(context: Context) : String{
        return getPersistedData(getDefaultLanguage(context))
    }
    fun getLocal(context: Context) : Locale{
        return Locale(getLanguage(context))
    }

    fun setLocale(language: String){
        persist(language)
    }

    fun getDefaultLanguage(context: Context? = null) : String {
//        return context.resources.configuration.locale.language
        return Locale.getDefault().language
    }

    fun getPersistedData(defaultLanguage: String) :String {
        val lang = SharedPreferencesHelper.getSharedPreferences(LANGUAGE, defaultLanguage)
        return if (isEmpty(lang))
             defaultLanguage
        else lang//"en"//"ar"//
    }


    fun getPersistedData(context: Context,defaultLanguage: String) :String {
        return SharedPreferencesHelper.getSharedPreferences(LANGUAGE, defaultLanguage,context)//"en"//"ar"//
    }

    fun persist(language: String) {
        SharedPreferencesHelper.saveSharedPreferences(LANGUAGE, language)
    }

    fun updateResources(context: Context, language: String) : Context {
        println("language is 24 : $language")
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    fun updateResourcesLegacy(context: Context, language: String) : Context {
        println("language is <24 :$language")
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}