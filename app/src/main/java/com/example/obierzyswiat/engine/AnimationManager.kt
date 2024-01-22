package com.example.obierzyswiat.engine

import android.graphics.Bitmap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import kotlin.math.abs

class AnimationManager(
    private val spriteSheet: Bitmap,
    val imgCntX: Int,
    private val imgCntY: Int,
    var switchTime: Float
) {
    enum class POSITION(val value: Int) {
        FRONT(0),
        SIDE(1),
        BACK(2)
    }
    enum class STATE(val value: Int) {
        STAY(0),
        MOVE(1),    // ALSO ATTACK
    }
    private var totalTime = 0.0f
    private var currImgX = 0
    private var currImgY = 0
    private var uvWidth = spriteSheet.width / imgCntX
    private var uvHeight = spriteSheet.height / imgCntY
    private var uvTop = 0
    private var uvLeft = 0

    fun updateRBM(position: POSITION, state: STATE, deltaTime: Float): Pair<Bitmap, Boolean> {
        var finished = false
        currImgY = position.value + state.value
        totalTime += deltaTime
        if (totalTime >= switchTime) {
            totalTime -= switchTime
            if (currImgX++ >= imgCntX - 1) {
                currImgX = 0
                finished = true
            }
        }
        uvTop = currImgY * uvHeight
        uvLeft = currImgX * uvWidth
        uvWidth = abs(uvWidth)

        return Pair(Bitmap.createBitmap(
            spriteSheet,
            uvLeft,
            uvTop,
            uvWidth,
            uvHeight
        ), finished)
    }

    fun update(position: POSITION, state: STATE, deltaTime: Float): BitmapDescriptor {
        currImgY = position.value + state.value
        totalTime += deltaTime
        if (totalTime >= switchTime) {
            totalTime -= switchTime
            if (currImgX++ >= imgCntX - 1) {
                currImgX = 0
            }
        }
        uvTop = currImgY * uvHeight
        uvLeft = currImgX * uvWidth
        uvWidth = abs(uvWidth)

        val frameBitmap = Bitmap.createBitmap(
            spriteSheet,
            uvLeft,
            uvTop,
            uvWidth,
            uvHeight
        )
        return BitmapDescriptorFactory.fromBitmap(frameBitmap)
        /*val frameBitmap = Bitmap.createBitmap(
            spriteSheet,
            frameIndex * frameWidth,
            0,
            frameWidth,
            frameHeight
        )*/
    }
}