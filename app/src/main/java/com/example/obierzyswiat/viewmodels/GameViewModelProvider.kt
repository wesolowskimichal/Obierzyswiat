package com.example.obierzyswiat.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.obierzyswiat.controllers.GPSController
import com.example.obierzyswiat.controllers.MonstersController

class GameViewModelProvider(
    val gpsController: GPSController,
    val monstersController: MonstersController
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GameViewModel(gpsController, monstersController) as T
    }
}