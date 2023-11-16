package com.example.obierzyswiat

import android.graphics.Canvas

interface Entity {
    val name: String
    var posX: Float
    var posY: Float
    // img?

    fun update()
    fun draw(canvas: Canvas)
}