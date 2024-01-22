package com.example.obierzyswiat.models.monsters

import com.example.obierzyswiat.engine.AnimationManager
import com.google.android.gms.maps.model.LatLng

class Czart(
    pos: LatLng,
    animationManager: AnimationManager
): BaseMonster(pos, animationManager) {
    override val name: String
        get() = "Czart"
    override val maxHealth: Int
        get() = 600
    override var healt: Int = maxHealth
    override val attackDamage: Int
        get() = 25
    override val attackSpeed: Float
        get() = 1.5f
}