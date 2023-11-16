package com.example.obierzyswiat

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Player(override val name: String, override var posX: Float, override var posY: Float) : Entity {
    constructor(name: String): this(name, 0f, 0f)

    override fun update() {
        TODO("Not yet implemented")
    }

    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.RED
        canvas.drawCircle(posX, posY, 50f, paint)
    }
}