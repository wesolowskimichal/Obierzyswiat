package com.example.obierzyswiat.engine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.obierzyswiat.R
import com.example.obierzyswiat.models.MonsterRecord
import com.example.obierzyswiat.models.monsters.Czart
import com.example.obierzyswiat.models.monsters.Ghul
import com.example.obierzyswiat.models.monsters.Zmij
import com.example.obierzyswiat.models.monsters.Monster
import com.example.obierzyswiat.models.monsters.Utopiec
import com.example.obierzyswiat.models.serializable.MonsterSer
import com.google.android.gms.maps.model.LatLng

class MonsterCreator {
    companion object{
        fun createMonster(context: Context,monsterSer: MonsterSer) : Monster? {
            var monster: Monster? = null
            when(monsterSer.name){
                "Zmij" -> {
                    monster = Zmij(
                        pos = LatLng(0.0, 0.0),
                        maxHealth = monsterSer.maxHealth,
                        attackDamage = monsterSer.attackDamage,
                        attackSpeed = monsterSer.attackSpeed,
                        animationManager = AnimationManager(
                            BitmapFactory.decodeResource(
                                context.resources, R.drawable.zmij1_2
                            ), 3, 2, 0.3f)
                    )
                }
                "Czart" -> {
                    monster = Czart(
                        pos = LatLng(0.0, 0.0),
                        animationManager = AnimationManager(
                            BitmapFactory.decodeResource(
                                context.resources, R.drawable.czart240
                            ), 3, 2, 0.3f)
                    )
                }
                "Ghul" -> {
                    monster = Ghul(
                        pos = LatLng(0.0, 0.0),
                        animationManager = AnimationManager(
                            BitmapFactory.decodeResource(
                                context.resources, R.drawable.ghul240
                            ), 3, 2, 0.3f)
                    )
                }
                "Utopiec" -> {
                    monster = Utopiec(
                        pos = LatLng(0.0, 0.0),
                        animationManager = AnimationManager(
                            BitmapFactory.decodeResource(
                                context.resources, R.drawable.utopiec240
                            ), 3, 2, 0.3f)
                    )
                }
            }
            return monster
        }

        fun getHead(context: Context, name: String): Bitmap? {
            var bitmap: Bitmap? = null
            when(name){
                "Zmij" -> {
                    bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.zmij_head)
                }
                "Czart" -> {
                    bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.czart_head)
                }
                "Ghul" -> {
                    bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ghul_head)
                }
                "Utopiec" -> {
                    bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.utopiec_head)
                }
            }
            return bitmap
        }
        fun createMonsterRecord(monster: Monster): MonsterRecord {
            return MonsterRecord(
                name = monster.name,
                number = 1
            )
        }
    }

}