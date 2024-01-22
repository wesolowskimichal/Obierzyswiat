package com.example.obierzyswiat.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "monsters"
)
data class MonsterRecord (
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val number: Long
)