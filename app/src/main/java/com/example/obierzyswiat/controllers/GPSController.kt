package com.example.obierzyswiat.controllers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng

class GPSController(val context: Context) {
    private val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private var _position: MutableLiveData<LatLng> = MutableLiveData()
    val position: LiveData<LatLng>
        get() = _position

    private var _isMoving: Boolean = false
    val isMoving: Boolean
        get() = _isMoving

    private var _lastKnownLocation: Location? = null
    val location: Location?
        get() = _lastKnownLocation

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d("locationListener", "locatio  changed")
            val newReading = LatLng(location.latitude, location.longitude)
            _position.postValue(newReading)

            val distanceMoved = _lastKnownLocation?.distanceTo(location) ?: 0f
            _isMoving = distanceMoved > 5 // Adjust the threshold as needed

            _lastKnownLocation = location
        }
    }

    init {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (lastKnownLocation != null) {
                val initialReading = LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
                _position.postValue(initialReading)
            } else {
                _position.postValue(LatLng(0.0, 0.0))
            }
        }
    }

    fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000,   // 1000 milliseconds = 1 second
            5f,   // 0 meters, all location changes
            locationListener
        )
    }

    fun stopLocationUpdates() {
        locationManager.removeUpdates(locationListener)
    }

    private fun addPos(pos: LatLng) {
        _position.postValue(pos)
    }
}