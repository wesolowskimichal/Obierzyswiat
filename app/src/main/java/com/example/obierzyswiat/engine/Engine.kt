package com.example.obierzyswiat.engine

import android.graphics.Canvas
import com.example.obierzyswiat.fragments.GameFragment

class Engine(private val _game: SubProcess) : Runnable {
    private val MAX_UPS = 144.0
    private val UPS_PERIOD = 1e+3 / MAX_UPS

    private var _isRunning = false
    private var _lastFrameTime = System.currentTimeMillis()
    private var _FPS = 0.0
    private var _UPS = 0.0
    val FPS
        get() = _FPS
    val UPS
        get() = _UPS

    fun startLoop() {
        if (!_isRunning) {
            _isRunning = true
            Thread(this).start()
        }
    }

    fun stopLoop() {
        _isRunning = false
    }

    override fun run() {
        var updateCount = 0
        var frameCount = 0

        var startTime = 0L
        var elapsedTime = 0L
        var sleepTime = 0L

        while (_isRunning) {
            val currentTime = System.currentTimeMillis()
            elapsedTime = currentTime - _lastFrameTime
            _lastFrameTime = currentTime

            val deltaTime = elapsedTime.toFloat() / 1000.0f

            _game.update(deltaTime)
            updateCount++

            elapsedTime = System.currentTimeMillis() - startTime
            if (elapsedTime >= 1000) {
                _UPS = updateCount / (1e-3 * elapsedTime)
                _FPS = frameCount / (1e-3 * elapsedTime)
                updateCount = 0
                frameCount = 0
                startTime = System.currentTimeMillis()
            }

            sleepTime = (updateCount * UPS_PERIOD).toLong() - elapsedTime
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}

//class Engine(private val _game: SubProcess): Thread() {
//    private val MAX_UPS = 144.0
//    private val UPS_PERIOD = 1e+3 / MAX_UPS
//
//    private var _isRunning = false
//    private var _lastFrameTime = System.currentTimeMillis()
//    val isRunning
//        get() = _isRunning
//    private var _FPS = 0.0
//    private var _UPS = 0.0
//    val FPS
//        get() = _FPS
//    val UPS
//        get() = _UPS
//
//    fun startLoop() {
//        _isRunning = true
//        start()
//    }
//
//    fun stopLoop() {
//        _isRunning = false
//    }
//
//    override fun run() {
//        super.run()
//
//        var updateCount = 0
//        var frameCount = 0
//
//        var startTime = 0L
//        var elapsedTime = 0L
//        var sleepTime = 0L
//
//        while (_isRunning) {
//            val currentTime = System.currentTimeMillis()
//            elapsedTime = currentTime - _lastFrameTime
//            _lastFrameTime = currentTime
//
//            val deltaTime = elapsedTime.toFloat() / 1000.0f
//
//            // Update game logic with deltaTime
//            _game.update(deltaTime)
//            updateCount++
//
//
//            // Calculate UPS and FPS
//            elapsedTime = System.currentTimeMillis() - startTime
//            if (elapsedTime >= 1000) {
//                _UPS = updateCount / (1e-3 * elapsedTime)
//                _FPS = frameCount / (1e-3 * elapsedTime)
//                updateCount = 0
//                frameCount = 0
//                startTime = System.currentTimeMillis()
//            }
//
//            // Calculate sleepTime to achieve the desired UPS
//            sleepTime = (updateCount * UPS_PERIOD).toLong() - elapsedTime
//            if (sleepTime > 0) {
//                sleep(sleepTime)
//            }
//        }
//    }
//}