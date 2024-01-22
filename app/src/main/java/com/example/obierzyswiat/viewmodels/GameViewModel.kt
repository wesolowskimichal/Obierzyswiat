package com.example.obierzyswiat.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.obierzyswiat.controllers.GPSController
import com.google.android.gms.maps.model.LatLng

class GameViewModel(private val gpsController: GPSController): ViewModel() {
    fun start() = gpsController.startLocationUpdates()
    fun stop() = gpsController.stopLocationUpdates()
    fun getPosition(): LiveData<LatLng> = gpsController.position
    fun isMoving(): Boolean = gpsController.isMoving
}