package com.example.activityandnavigationex.ui.entry

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class MyObserver : DefaultLifecycleObserver {
    override fun onResume(owner: LifecycleOwner) {
//        connect()
        //handle logic start
    }

    override fun onPause(owner: LifecycleOwner) {
//        disconnect()
        //remove logic, runnable
    }
}

