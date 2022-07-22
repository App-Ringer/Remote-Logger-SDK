package com.appringer.logger

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.appringer.remoteLogger.enum.LogLevelEnum
import com.appringer.remoteLogger.repo.logger.RemoteLogger

class MyApplication : Application(), LifecycleObserver {

    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null
        fun context(): MyApplication {
            return instance as MyApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        RemoteLogger.apply {
            register(this@MyApplication, "SUA-786-JGK-754")
            setTag("Relevant Tag")
            setLevel(LogLevelEnum.INFO)
        }
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        RemoteLogger.unregister()
    }
}