package com.example.obierzyswiat.models

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import com.example.obierzyswiat.R
import com.example.obierzyswiat.engine.AnimationManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class Player( val name: String,  var pos: LatLng,
              val maxHealth: Int,
              var healt: Int,
              val context: Context
) {
    private var marker: Marker? = null
    private var _lastPos = LatLng(0.0, 0.0)
    private val animationManager = AnimationManager(BitmapFactory.decodeResource(
        context.resources, R.drawable.c
    ), 3, 6, 0.3f)

    fun update(position: AnimationManager.POSITION, state: AnimationManager.STATE, deltaTime: Float) {
        if(state == AnimationManager.STATE.MOVE) {
            marker?.setIcon(animationManager.update(position, state, deltaTime))
        }
        marker?.setIcon(animationManager.update(position, state, deltaTime))
    }

    fun restart() {
        _lastPos = LatLng(_lastPos.longitude, _lastPos.latitude)
    }

    fun draw(gMap: GoogleMap) {
        if(_lastPos != pos) {
            marker?.remove()
            marker = gMap.addMarker(
                MarkerOptions()
                    .position(pos)
                    .title("MAIN_PLAYER")
            )
            _lastPos = pos
        }
    }

}