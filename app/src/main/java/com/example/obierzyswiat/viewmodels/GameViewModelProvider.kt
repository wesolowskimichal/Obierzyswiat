package com.example.obierzyswiat.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.obierzyswiat.controllers.GPSController

class GameViewModelProvider(
    val gpsController: GPSController
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GameViewModel(gpsController) as T
    }
}