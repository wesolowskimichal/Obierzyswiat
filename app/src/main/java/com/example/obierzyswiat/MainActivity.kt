package com.example.obierzyswiat

import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var gameSurfaceView: Game
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameSurfaceView = findViewById(R.id.gameSurfaceView)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        val success: Boolean = p0.setMapStyle(
            MapStyleOptions(
                resources
                    .getString(R.string.style_json)
            )
        )

        if (!success) {
            Log.e("TAG", "Style parsing failed.")
        }
        p0.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-34.0, 151.0)))
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        p0.isMyLocationEnabled = true

        gameSurfaceView.addGMap(p0)
    }
}