package com.appringer.logger

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.appringer.remote_logger.logger.LoggerImpl
import com.appringer.remote_logger.logger.RemoteLogger

class MyApplication : Application(), LifecycleObserver {

    init {
        instance = this
        RemoteLogger.register("BLOCK_CALLS")
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
        RemoteLogger.unregister()
    }
}