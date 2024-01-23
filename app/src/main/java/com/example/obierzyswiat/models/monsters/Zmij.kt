package com.example.obierzyswiat.models.monsters

import com.example.obierzyswiat.engine.AnimationManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class Zmij(
                 override var pos: LatLng,
                 override val maxHealth: Int,
                 override val attackDamage: Int,
                 override val attackSpeed: Float,
                 override var animationManager: AnimationManager
): Monster {
    override val name: String
        get() = "Zmij"
    override var _lastPos = LatLng(0.0, 0.0)
    override var healt = maxHealth
    override var marker: Marker? = null
    override var position = AnimationManager.POSITION.FRONT
    override var state = AnimationManager.STATE.STAY

    override fun update(deltaTime: Float) {
//        marker?.setIcon(animationManager.update(position, state, deltaTime))

    }
    override fun restart() {
        _lastPos = LatLng(_lastPos.longitude, _lastPos.latitude)
    }

    override fun draw(gMap: GoogleMap) {
        if(_lastPos != pos) {
            marker?.remove()
            marker = gMap.addMarker(
                MarkerOptions()
                    .position(pos)
                    .title(name)
            )
        marker?.setIcon(animationManager.update(position, state, 0.0f))
            _lastPos = pos
        }
    }
}