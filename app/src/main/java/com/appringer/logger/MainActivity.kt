package com.appringer.logger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.appringer.logger.databinding.ActivityMainBinding
import com.appringer.remote_logger.`interface`.RemoteLog
import com.appringer.remote_logger.enum.LogTagEnum
import com.appringer.remote_logger.model.LogRequest
import com.appringer.remote_logger.util.GSONUtils
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.tv.setOnClickListener {
            lifecycleScope.launch {
                RemoteLog.sendLog(
                    LogRequest(
                        LogTagEnum.ERROR.value,
                        LogTagEnum.ERROR.value,
                        "Uncaught Exception",
                        GSONUtils.toString(TempDO("Temp","Desc")),
                    )
                )
            }
        }
        binding.tvUncaughtException.setOnClickListener {
            throw IOException()
        }
    }

    private fun sendError() {

    }
}