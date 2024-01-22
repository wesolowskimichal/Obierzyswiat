package com.example.obierzyswiat.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.obierzyswiat.models.MonsterRecord
import com.example.obierzyswiat.repository.MonstersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MonstersViewModel(
    val monstersRepository: MonstersRepository
): ViewModel() {

    suspend fun upsert(monsterRecord: MonsterRecord): Long = monstersRepository.upsert(monsterRecord)
    fun getAllMonsters(): LiveData<List<MonsterRecord>> = monstersRepository.getAllMonsters()
    suspend fun getMonsterByName(name: String): MonsterRecord? {
        return withContext(Dispatchers.IO) {
            monstersRepository.getMonsterByName(name)
        }
    }
    suspend fun updateMonsterNumberByName(name: String, newNumber: Long): Int {
        return withContext(Dispatchers.IO) {
            monstersRepository.updateMonsterNumberByName(name, newNumber)
        }
    }

}