package com.example.obierzyswiat

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.google.android.gms.maps.GoogleMap

class Game(context: Context, attributeSet: AttributeSet): SurfaceView(context, attributeSet), SurfaceHolder.Callback {
    private val _engine = GameLoop(this, holder)
    private val _entities = mutableListOf<Entity>()
    private val _player = Player("MAIN")
    private lateinit var _gMap: GoogleMap

    init {
        holder.addCallback(this)
        isFocusable = true
        _player.posX = (width / 2).toFloat()
        _player.posY = (height / 2).toFloat()
        setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSPARENT)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        Log.d("STARTED", "STARTED LOOP")
        _engine.startLoop()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        _player.posX = (width / 2).toFloat()
        _player.posY = (height / 2).toFloat()
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        _engine.stopLoop()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawColor(Color.TRANSPARENT, android.graphics.PorterDuff.Mode.CLEAR) // Clear the canvas
        drawUPS(canvas)
        drawFPS(canvas)
        drawEntities(canvas)
    }

    private fun drawEntities(canvas: Canvas) {
        _player.draw(canvas)
        for(entity in _entities) {
            entity.draw(canvas)
        }
    }

    private fun drawUPS(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.WHITE
        paint.textSize = 25F
        canvas.drawText(_engine.UPS.toString(), 20F, 20F, paint)
    }

    private fun drawFPS(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.WHITE
        paint.textSize = 25F
        canvas.drawText(_engine.FPS.toString(), 20F, 60F, paint)
    }

    fun update() {
    }

    fun addGMap(googleMap: GoogleMap) {
        _gMap = googleMap
    }

}