package com.example.obierzyswiat.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.obierzyswiat.controllers.GPSController
import com.example.obierzyswiat.controllers.MonstersController
import com.example.obierzyswiat.models.Player

class GameViewModelProvider(
    val gpsController: GPSController,
    val monstersController: MonstersController,
    var player: Player
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GameViewModel(gpsController, monstersController, player) as T
    }
}