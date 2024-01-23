package com.example.obierzyswiat.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FightViewModel: ViewModel() {
    private var _clicks: MutableLiveData<Int> = MutableLiveData(0)
    val clicks: LiveData<Int>
        get() = _clicks

    fun resetClicks() {
        _clicks.postValue(0)
    }

    fun onClick() {
        val v = (_clicks.value ?: 0) + 1
        _clicks.postValue(v)
    }
}