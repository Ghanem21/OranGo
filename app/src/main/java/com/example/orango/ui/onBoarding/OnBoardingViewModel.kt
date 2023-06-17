package com.example.orango.ui.onBoarding

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.orango.util.VIEWPAGER_CURRENTPOSITION

class OnBoardingViewModel : ViewModel() {
    private val _currentPosition = MutableLiveData<Int>()
    val currentPosition : LiveData<Int> = _currentPosition

    var isNewlyCreated = true

    init {
        _currentPosition.value = 0
    }

    fun inc(){
        if(_currentPosition.value!! < 4)
            _currentPosition.value = _currentPosition.value?.inc()
    }
    fun dec(){
        if(_currentPosition.value!! > 0)
            _currentPosition.value = _currentPosition.value?.dec()
    }

    fun setCurrentPosition(currentItem: Int) {
        _currentPosition.value = currentItem
    }

    fun saveState(outState: Bundle) {
        outState.putInt(VIEWPAGER_CURRENTPOSITION,currentPosition.value ?: 0)
    }

    fun restoreState(savedInstanceState: Bundle) {
        setCurrentPosition(savedInstanceState.getInt(VIEWPAGER_CURRENTPOSITION,0))
    }
}