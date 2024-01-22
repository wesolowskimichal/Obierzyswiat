package com.example.obierzyswiat.repository

import androidx.lifecycle.LiveData
import com.example.obierzyswiat.database.MonstersDatabase
import com.example.obierzyswiat.models.MonsterRecord

class MonstersRepository(
    val db: MonstersDatabase
) {
    suspend fun upsert(monsterRecord: MonsterRecord): Long {
        return db.getMonsterDao().upsert(monsterRecord)
    }

    fun getAllMonsters(): LiveData<List<MonsterRecord>> = db.getMonsterDao().getAllMonsters()

    suspend fun getMonsterByName(name: String): MonsterRecord? =db.getMonsterDao().getMonsterByName(name)
    suspend fun updateMonsterNumberByName(name: String, newNumber: Long): Int = db.getMonsterDao().updateMonsterNumberByName(name, newNumber)
}