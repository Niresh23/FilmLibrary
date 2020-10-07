package com.geekbrains.team.filmlibrary.fragments.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.geekbrains.team.filmlibrary.R
import com.geekbrains.team.filmlibrary.util.LocaleHelper

class SettingsFragment: PreferenceFragmentCompat() {

    private lateinit var preferences: SharedPreferences
    private lateinit var preferencesListener: SharedPreferences.OnSharedPreferenceChangeListener

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_fragment, rootKey)

        preferences = PreferenceManager.getDefaultSharedPreferences(requireActivity())
        preferencesListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            val darkModePref = findPreference<SwitchPreferenceCompat>(getString(R.string.night_mode))
            val adaptivePref = findPreference<SwitchPreferenceCompat>(getString(R.string.adaptive_brightness))
            Log.d("Settings", "dark mode = ${darkModePref?.isChecked}, adaptive mode = ${adaptivePref?.isChecked}")
            if (key == getString(R.string.adaptive_brightness)) {
                adaptivePref?.isChecked?.let {
                    if (it) {
                        darkModePref?.isChecked = false
                    }
                }
            } else if (key == getString(R.string.night_mode)) {
                darkModePref?.isChecked?.let {
                    if (it) {
                        adaptivePref?.isChecked = false
                    }
                }
            }
            when(key) {
                resources.getString(R.string.language) -> {
                    var language = ""
                    if (PreferenceManager.getDefaultSharedPreferences(requireContext()).getString(key, "") == resources.getString(R.string.enRegion)) {
                        language = resources.getString(R.string.enRegion)
                    } else {
                        language = resources.getString(R.string.ruRegion)
                    }
                    LocaleHelper.setLocale(requireContext(), language)
                }
            }
        }

        preferences.registerOnSharedPreferenceChangeListener(preferencesListener)
    }
}