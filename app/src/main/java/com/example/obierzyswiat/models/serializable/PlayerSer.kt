package com.example.obierzyswiat.models.serializable

import kotlinx.serialization.Serializable

@Serializable
data class PlayerSer (
    var health: Int,
    val maxHealth: Int,
    val attackDamage: Int
)