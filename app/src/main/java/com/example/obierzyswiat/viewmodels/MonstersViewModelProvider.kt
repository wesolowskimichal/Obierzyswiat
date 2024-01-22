package com.example.obierzyswiat.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.obierzyswiat.controllers.GPSController
import com.example.obierzyswiat.repository.MonstersRepository

class MonstersViewModelProvider(
    val monstersRepository: MonstersRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MonstersViewModel(monstersRepository) as T
    }
}