package com.geekbrains.team.filmlibrary.util

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.preference.PreferenceManager
import com.geekbrains.team.filmlibrary.R
import java.util.*

class LocaleHelper {
    companion object {
        fun onAttach(context: Context): Context {
            val locale = getPersistedLocale(context)
            return setLocale(context, locale)
        }
        fun getPersistedLocale(context: Context): String {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(context.resources.getString(R.string.language), "") ?: ""
        }
        fun setLocale(context: Context, localeSpec: String): Context {
            var locale: Locale? = null
            if(localeSpec == "system") {
                locale = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Resources.getSystem().configuration.locales[0]
                } else {
                    Resources.getSystem().configuration.locale
                }
            } else {
                locale = Locale(localeSpec)
            }
            Locale.setDefault(locale)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                updateResources(context, locale)
            } else {
                updateResources(context, locale)
            }
        }
        @TargetApi(Build.VERSION_CODES.N)
        private fun updateResources(context: Context, locale: Locale?): Context {
            val configuration = context.resources.configuration
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)
            return context.createConfigurationContext(configuration)
        }
        private fun updateResourcesLegacy(context: Context, locale: Locale?): Context {
            val resources = context.resources
            val configuration = resources.configuration
            configuration.locale = locale
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLayoutDirection(locale)
            }
            resources.updateConfiguration(configuration, resources.displayMetrics)
            return context
        }
    }

}