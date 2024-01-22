package com.example.obierzyswiat.controllers

import android.content.Context
import android.graphics.BitmapFactory
import com.example.obierzyswiat.R
import com.example.obierzyswiat.engine.AnimationManager
import com.example.obierzyswiat.models.monsters.Czart
import com.example.obierzyswiat.models.monsters.Ghul
import com.example.obierzyswiat.models.monsters.Zmij
import com.example.obierzyswiat.models.monsters.Monster
import com.example.obierzyswiat.models.monsters.Utopiec
import com.google.android.gms.maps.model.LatLng
import kotlin.math.cos
import kotlin.math.sqrt
import kotlin.random.Random

class MonstersController(val context: Context) {
// =============================
// ||         CONFIG          ||
// =============================
    private val MAX_MONSTERS_IN_AREA = 5
    private val MIN_DISTANCE: Int = 5
    private val MAX_DISTANCE: Int = 50
// =============================


    private var position: LatLng = LatLng(0.0, 0.0)
    private var _monsters: MutableList<Monster> = mutableListOf()
    val monsters: List<Monster>
        get() = _monsters

    fun spawn() {
        val random = Random(System.currentTimeMillis())
        for (i in 0 until MAX_MONSTERS_IN_AREA - _monsters.size){
            if(random.nextBoolean()) {
                _monsters.add(randomMonster())
            }
        }
    }

    private fun randomMonster(): Monster {
        val random = Random(System.currentTimeMillis())
        val dec = random.nextDouble()
        val randomDistance = MIN_DISTANCE + random.nextInt((MAX_DISTANCE - MIN_DISTANCE + 1))
        val randomBearing = random.nextDouble() * 360
        val latLng = calculateDestination(position, randomBearing, randomDistance.toDouble() / 1000.0)
        if(dec < 0.1) {
            return Zmij(
                pos = latLng,
                maxHealth = 200,
                attackDamage = 20,
                attackSpeed = 3.0f,
                animationManager = AnimationManager(
                    BitmapFactory.decodeResource(
                        context.resources, R.drawable.zmij1
                    ), 3, 2, 0.3f)
            )
        } else if(dec < 0.25) {
            return Czart(
                pos = latLng,
                animationManager = AnimationManager(
                    BitmapFactory.decodeResource(
                        context.resources, R.drawable.czart32
                    ), 3, 2, 0.3f)
            )
        } else if(dec < 0.5) {
            return Ghul(
                pos = latLng,
                animationManager = AnimationManager(
                    BitmapFactory.decodeResource(
                        context.resources, R.drawable.ghul32
                    ), 3, 2, 0.3f)
            )
        }
        return Utopiec(
            pos = latLng,
            animationManager = AnimationManager(
                BitmapFactory.decodeResource(
                    context.resources, R.drawable.utopiec32
                ), 3, 2, 0.3f)
        )
    }

    var once = true

    fun remove(monster: Monster) {
        _monsters.remove(monster)
    }

    fun update(currentPosition: LatLng) {
        position = currentPosition
        val monstersInsideCircle = _monsters.filter { monster ->
            calculateDistance(currentPosition, monster.pos) <= MAX_DISTANCE / 111.0
        }
        // Update the aliveMonsters LiveData with the count of monsters inside the circle
        _monsters = monstersInsideCircle.toMutableList()
        if(_monsters.size == 0) {
            spawn()
            once = false
        }
    }

    private fun calculateDestination(startPoint: LatLng, bearing: Double, distance: Double): LatLng {
        val earthRadius = 6371 // Earth radius in kilometers

        val lat1 = Math.toRadians(startPoint.latitude)
        val lon1 = Math.toRadians(startPoint.longitude)

        val lat2 = Math.asin(
            Math.sin(lat1) * Math.cos(distance / earthRadius) +
                    Math.cos(lat1) * Math.sin(distance / earthRadius) * Math.cos(Math.toRadians(bearing))
        )

        val lon2 = lon1 + Math.atan2(
            Math.sin(Math.toRadians(bearing)) * Math.sin(distance / earthRadius) * Math.cos(lat1),
            Math.cos(distance / earthRadius) - Math.sin(lat1) * Math.sin(lat2)
        )

        val finalLat = Math.toDegrees(lat2)
        val finalLon = Math.toDegrees(lon2)

        return LatLng(finalLat, finalLon)
    }

    private fun calculateDistance(point1: LatLng, point2: LatLng): Double {
        val x = (point2.longitude - point1.longitude) * cos((point1.latitude + point2.latitude) / 2)
        val y = point2.latitude - point1.latitude
        return sqrt(x * x + y * y) * 111.0
    }
}