package com.example.activityandnavigationex.ui.coroutineex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressViewmodel: ViewModel() {//alt+enter -> import thu vien

    private var _progress = MutableLiveData(0)
    val progress: LiveData<Int> = _progress

    var job: Job? = null

    fun startDownload() {
        if (job?.isActive == true) return
        job = viewModelScope.launch(Dispatchers.IO) {
            for (i in 1.. 100) {
                delay(100)
                _progress.postValue(i)
            }
        }
    }

    fun stopDownload() {
        job?.cancel()
        job = null
    }
}