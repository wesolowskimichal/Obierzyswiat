package com.example.obierzyswiat.models.monsters

import com.example.obierzyswiat.engine.AnimationManager
import com.google.android.gms.maps.model.LatLng

class Ghul(
    pos: LatLng,
    animationManager: AnimationManager
): BaseMonster(pos, animationManager) {
    override val name: String
        get() = "Ghul"
    override val maxHealth: Int
        get() = 400
    override var healt: Int = maxHealth
    override val attackDamage: Int
        get() = 15
    override val attackSpeed: Float
        get() = 1.0f
}