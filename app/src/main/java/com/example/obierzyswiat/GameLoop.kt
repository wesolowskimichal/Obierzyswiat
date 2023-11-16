package com.example.obierzyswiat

import android.graphics.Canvas
import android.view.SurfaceHolder

class GameLoop(private val _game: Game, private val _surfaceHolder: SurfaceHolder): Thread() {
    private val MAX_UPS = 60.0
    private val UPS_PERIOD = 1e+3 / MAX_UPS

    private var _isRunning = false
    val isRunning
        get() = _isRunning
    private var _FPS = 0.0
    private var _UPS = 0.0
    val FPS
        get() = _FPS
    val UPS
        get() = _UPS

    fun startLoop() {
        _isRunning = true
        start()
    }

    fun stopLoop() {
        _isRunning = false
    }

    override fun run() {
        super.run()

        var canvas: Canvas? = null

        var updateCount = 0
        var frameCount = 0

        var startTime = 0L
        var elapsedTime = 0L
        var sleepTime = 0L

        while (_isRunning) {
            try{
                canvas = _surfaceHolder.lockCanvas()
                synchronized(_surfaceHolder) {
                    _game.update()
                    updateCount++
                    if(canvas != null) {
                        _game.draw(canvas)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try{
                        _surfaceHolder.unlockCanvasAndPost(canvas)
                        frameCount++
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            elapsedTime = System.currentTimeMillis() - startTime

            sleepTime = (updateCount * UPS_PERIOD).toLong() - elapsedTime
            if(sleepTime > 0) {
                sleep(sleepTime)
            }

            while(sleepTime < 0 && updateCount < MAX_UPS - 1) {
                _game.update()
                updateCount++
                elapsedTime = System.currentTimeMillis() - startTime
                sleepTime = (updateCount * UPS_PERIOD).toLong() - elapsedTime
            }

            elapsedTime = System.currentTimeMillis() - startTime
            if(elapsedTime >= 1000) {
                _UPS = updateCount / (1e-3 * elapsedTime)
                _FPS = frameCount / (1e-3 * elapsedTime)
                updateCount = 0
                frameCount = 0
                startTime = System.currentTimeMillis()
            }
        }
    }
}