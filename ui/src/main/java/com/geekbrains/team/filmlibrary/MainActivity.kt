package com.geekbrains.team.filmlibrary

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.geekbrains.team.filmlibrary.Const.MIN_LUX
import com.geekbrains.team.filmlibrary.adapters.OnActorSelectedListener
import com.geekbrains.team.filmlibrary.adapters.OnItemSelectedListener
import com.geekbrains.team.filmlibrary.fragments.favorites.FavoriteFragment
import com.geekbrains.team.filmlibrary.fragments.favorites.FavoriteFragmentDirections
import com.geekbrains.team.filmlibrary.fragments.mainScreen.MainScreenFragmentDirections
import com.geekbrains.team.filmlibrary.fragments.movieDetails.FullFilmInfoFragmentDirections
import com.geekbrains.team.filmlibrary.util.LocaleHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity: AppCompatActivity(), OnItemSelectedListener, OnActorSelectedListener {
    companion object {
        private const val firstRunKey = "first_run"
        private const val darkThemeKey = "dark_theme"
        private const val languageKey = "language_key"
        private const val adaptiveBrightnessKey = "adaptive_brightness"
    }

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private lateinit var sharedPreferences: SharedPreferences
    private var initLocale: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        initLocale = LocaleHelper.getPersistedLocale(this)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        setContentView(R.layout.activity_main)
        setUpNavigation()

    }



    override fun onResume() {
        super.onResume()
//        setSensor(sharedPreferences.getBoolean(getString(R.string.adaptive_brightness), false))
        setSensor(false)
        setDarkMode(sharedPreferences.getBoolean(getString(R.string.night_mode), false))
        if (initLocale != null && !initLocale.equals(LocaleHelper.getPersistedLocale(this))) {
            recreate()
        }
    }


    private fun setUpNavigation() {
        NavigationUI.setupWithNavController(btv, navController)
    }

    private fun setSensor(adaptiveBrightness: Boolean) {
        Log.d("MainActivity", "setSensor($adaptiveBrightness)")
        if (adaptiveBrightness) {
            // Init default night mode.
            val mySensorManager =
                getSystemService(Context.SENSOR_SERVICE) as? SensorManager
            val lightSensor: Sensor? = mySensorManager?.getDefaultSensor(Sensor.TYPE_LIGHT)

            lightSensor?.let {

                mySensorManager.registerListener(
                    lightSensorListener,
                    it,
                    SensorManager.SENSOR_DELAY_NORMAL
                )
            }
        }
    }

    fun openSetting(view: View) {
        val directions = FavoriteFragmentDirections.actionFavoritesFragmentToSettingsFragment()
        navController.navigate(directions)
    }

    private val lightSensorListener: SensorEventListener =
        object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            }

            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_LIGHT) {
                    val currentLux = event.values.first()
                    if (currentLux < MIN_LUX && AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        recreate()
                    } else if (currentLux > MIN_LUX && AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        recreate()
                    }
                }
            }
        }

    override fun attachBaseContext(newBase: Context?) {
        newBase?.let {
            super.attachBaseContext(LocaleHelper.onAttach(it))
        }
    }

    private fun setDarkMode(isDarkMode: Boolean) {
        Log.d("MainActivity", "setDarkMode($isDarkMode)")

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun openMovieDetails(id: Int) {
        val directions = MainScreenFragmentDirections.navigateToMovieDetails(id)
        navController.navigate(directions)
    }

    override fun showProgress() {
        mainProgress.visibility = VISIBLE
        host.visibility = GONE
    }

    override fun hideProgress() {
        mainProgress.visibility = GONE
        host.visibility = VISIBLE
    }

    override fun openSeriesDetails(id: Int) {
        val directions = MainScreenFragmentDirections.navigateToSeriesDetails(id)
        navController.navigate(directions)
    }

    override fun openExtensionFragmentWithParameter(enum: String, id: Int) {
        Log.d("my_log", enum)
        val directions = MainScreenFragmentDirections.navigateToExtensionFragment(enum, id)
        navController.navigate(directions)
    }

    override fun openExtensionFragment(enum: String) {
        Log.d("my_log", enum)
        val directions = MainScreenFragmentDirections.navigateToExtensionFragment(enum)
        navController.navigate(directions)
    }

    override fun openActorDetails(id: Int) {
        val directions = FullFilmInfoFragmentDirections.navigateToActorDetails(id)
        navController.navigate(directions)
    }
}