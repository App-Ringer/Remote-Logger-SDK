package com.appringer.logger

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.appringer.remote_logger.AppRingerExceptionHandler
import com.appringer.remote_logger.`interface`.RemoteLog

class MyApplication : Application(), LifecycleObserver {

    init {
        instance = this
        RemoteLog.register("BLOCK_CALLS")
    }

    companion object {
        private var instance: MyApplication? = null
        fun context(): MyApplication {
            return instance as MyApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        RemoteLog.unregister()
    }
}