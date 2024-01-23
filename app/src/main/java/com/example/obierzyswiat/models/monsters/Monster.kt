package com.example.obierzyswiat.models.monsters

import com.example.obierzyswiat.engine.AnimationManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.serialization.Serializable

interface Monster {
    val name: String
    var pos: LatLng
    val maxHealth: Int
    var healt: Int
    val attackDamage: Int
    val attackSpeed: Float
    var marker: Marker?
    var animationManager: AnimationManager
    var state: AnimationManager.STATE
    var position: AnimationManager.POSITION
    var _lastPos: LatLng

    fun restart()
    fun update(deltaTime: Float)
    fun draw(gMap: GoogleMap)
}