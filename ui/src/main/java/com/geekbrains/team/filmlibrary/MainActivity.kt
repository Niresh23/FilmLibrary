package com.geekbrains.team.filmlibrary

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.geekbrains.team.domain.base.UseCase
import com.geekbrains.team.domain.base.model.MovieAndTVShow
import com.geekbrains.team.domain.base.model.Param
import com.geekbrains.team.filmlibrary.Const.MIN_LUX
import com.geekbrains.team.filmlibrary.adapters.OnActorSelectedListener
import com.geekbrains.team.filmlibrary.adapters.OnExtensionSelectedListener
import com.geekbrains.team.filmlibrary.adapters.OnItemSelectedListener
import com.geekbrains.team.filmlibrary.fragments.mainScreen.MainScreenFragmentDirections
import com.geekbrains.team.filmlibrary.fragments.movieDetails.FullFilmInfoFragmentDirections
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity(), OnItemSelectedListener, OnActorSelectedListener, OnExtensionSelectedListener<List<MovieAndTVShow>, Param> {

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setUpNavigation()
        val firstAppLaunch = (savedInstanceState == null)
        setSensor(firstAppLaunch)
    }

    private fun setUpNavigation() {
        NavigationUI.setupWithNavController(btv, navController)
    }

    private fun setSensor(firstAppLaunch: Boolean) {
        if (firstAppLaunch) {
            // Init default night mode.
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

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

    override fun openActorDetails(id: Int) {
        val directions = FullFilmInfoFragmentDirections.navigateToActorDetails(id)
        navController.navigate(directions)
    }

    override fun onExtensionSelected(id: Int, useCase: UseCase<List<MovieAndTVShow>, Param>) {

    }

}