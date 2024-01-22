package com.example.obierzyswiat.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.obierzyswiat.models.MonsterRecord

@Database(
    entities = [MonsterRecord::class],
    version = 1
)
abstract class MonstersDatabase: RoomDatabase() {
    abstract fun getMonsterDao(): MonsterDao

    companion object{
        @Volatile
        private var instance: MonstersDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MonstersDatabase::class.java,
                "monsters_db.db"
            ).build()
    }
}