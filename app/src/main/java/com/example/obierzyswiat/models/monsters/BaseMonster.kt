package com.example.obierzyswiat.models.monsters
import com.example.obierzyswiat.engine.AnimationManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

abstract class BaseMonster(
    override var pos: LatLng,
    override var animationManager: AnimationManager
): Monster {
    override var _lastPos = LatLng(0.0, 0.0)
    override var marker: Marker? = null
    override var position = AnimationManager.POSITION.FRONT
    override var state = AnimationManager.STATE.STAY

    override fun update(deltaTime: Float) {
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