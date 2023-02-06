package com.example.orango.ui.onBoarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnBoardingViewModel : ViewModel() {
    // TODO: Implement the
    private val _currentPosition = MutableLiveData<Int>()
    val currentPosition : LiveData<Int> = _currentPosition

    init {
        _currentPosition.value = 0
    }

    fun inc(){
        if(_currentPosition.value!! < 3)
            _currentPosition.value = _currentPosition.value?.inc()
    }
    fun dec(){
        if(_currentPosition.value!! > 0)
            _currentPosition.value = _currentPosition.value?.dec()
    }
}