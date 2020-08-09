package com.geekbrains.team.filmlibrary

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.geekbrains.team.filmlibrary.adapters.OnActorSelectedListener
import com.geekbrains.team.filmlibrary.adapters.OnItemSelectedListener
import com.geekbrains.team.filmlibrary.fragments.mainScreen.MainScreenFragmentDirections
import com.geekbrains.team.filmlibrary.fragments.movieDetails.FullFilmInfoFragmentDirections
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity(), OnItemSelectedListener, OnActorSelectedListener {
    companion object {
        private const val firstRunKey = "first_run"
        private const val darkThemeKey = "dark_theme"
        private const val languageKey = "language_key"
        private const val adaptiveBrightnessKey = "adaptive_brightness"
    }

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences(Const.PACKAGE, Context.MODE_PRIVATE)

        setContentView(R.layout.activity_main)
        setUpNavigation()
        val firstAppLaunch = (savedInstanceState == null)
//        setSensor(firstAppLaunch)
    }

    override fun onResume() {
        super.onResume()
        if(sharedPreferences.getBoolean(firstRunKey, true)) {
            sharedPreferences.edit().putBoolean(firstRunKey, false).apply()
        }
    }

    private fun setUpNavigation() {
        NavigationUI.setupWithNavController(btv, navController)
    }

//    private fun setSensor(firstAppLaunch: Boolean) {
//        if (firstAppLaunch) {
//            // Init default night mode.
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }
//
//        val mySensorManager =
//            getSystemService(Context.SENSOR_SERVICE) as? SensorManager
//        val lightSensor: Sensor? = mySensorManager?.getDefaultSensor(Sensor.TYPE_LIGHT)
//
//        lightSensor?.let {
//
//            mySensorManager.registerListener(
//                lightSensorListener,
//                it,
//                SensorManager.SENSOR_DELAY_NORMAL
//            )
//        }
//    }

//    private val lightSensorListener: SensorEventListener =
//        object : SensorEventListener {
//            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
//            }
//
//            override fun onSensorChanged(event: SensorEvent) {
//                if (event.sensor.type == Sensor.TYPE_LIGHT) {
//                    val currentLux = event.values.first()
//                    if (currentLux < MIN_LUX && AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES) {
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                        recreate()
//                    } else if (currentLux > MIN_LUX && AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO) {
//                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                        recreate()
//                    }
//                }
//            }
//        }

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