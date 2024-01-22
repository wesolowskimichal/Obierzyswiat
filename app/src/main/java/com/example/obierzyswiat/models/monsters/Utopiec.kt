package com.example.obierzyswiat.models.monsters

import com.example.obierzyswiat.engine.AnimationManager
import com.google.android.gms.maps.model.LatLng

class Utopiec(
    pos: LatLng,
    animationManager: AnimationManager
): BaseMonster(pos, animationManager) {
    override val name: String
        get() = "Utopiec"
    override val maxHealth: Int
        get() = 200
    override var healt: Int = maxHealth
    override val attackDamage: Int
        get() = 20
    override val attackSpeed: Float
        get() = 4.5f
}