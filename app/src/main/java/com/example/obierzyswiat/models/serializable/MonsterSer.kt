package com.example.obierzyswiat.models.serializable

import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.Serializable

@Serializable
data class MonsterSer(
    val name: String,
    val maxHealth: Int,
    val attackDamage: Int,
    val attackSpeed: Float
)
