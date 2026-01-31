package com.example.activityandnavigationex.ui.coroutineex.timerex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {

    private val _timeMillis = MutableLiveData(0L)
    val timeMillis: LiveData<Long> = _timeMillis

    private val _isRunning = MutableLiveData(false)
    val isRunning: LiveData<Boolean> = _isRunning

    private var timerJob: Job? = null

    private var startTime = 0L
    private var elapsedBefore = 0L

    fun startOrResume() {
        if (_isRunning.value == true) return

        _isRunning.value = true
        startTime = System.currentTimeMillis()

        timerJob = viewModelScope.launch {
            while (isActive) {
                val now = System.currentTimeMillis()
                _timeMillis.value = elapsedBefore + (now - startTime)

                // refresh UI (~60fps), renders ~60 times per second
                delay(16)// 1000ms / 60 = 16
            }
        }
    }

    fun stop() {
        if (_isRunning.value != true) return

        elapsedBefore = _timeMillis.value ?: 0L
        _isRunning.value = false
        timerJob?.cancel()
        timerJob = null
    }

    fun reset() {
        stop()
        elapsedBefore = 0L
        _timeMillis.value = 0L
    }
}
