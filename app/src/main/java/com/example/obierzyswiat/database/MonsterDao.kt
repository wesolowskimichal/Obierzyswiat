package com.example.obierzyswiat.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.obierzyswiat.models.MonsterRecord

@Dao
interface MonsterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun upsert(monsterRecord: MonsterRecord): Long

    @Query("SELECT * FROM monsters")
    fun getAllMonsters(): LiveData<List<MonsterRecord>>

    @Query("SELECT * FROM monsters WHERE name = :name")
    suspend fun getMonsterByName(name: String): MonsterRecord?

    @Query("UPDATE monsters SET number = :newNumber WHERE name = :name")
    suspend fun updateMonsterNumberByName(name: String, newNumber: Long): Int
}