package com.appringer.logger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.appringer.logger.databinding.ActivityMainBinding
import com.appringer.remote_logger.enum.LogTagEnum
import com.appringer.remote_logger.logger.RemoteLogger
import com.appringer.remote_logger.model.LogRequest
import com.appringer.remote_logger.util.GSONUtils
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.tv.setOnClickListener {
                RemoteLogger.sendLog(LogRequest(
                        LogTagEnum.ERROR.value,
                        LogTagEnum.ERROR.value,
                        "Uncaught Exception",
                        GSONUtils.toString(TempDO("Temp","Desc")),
                    )
                )
        }
        binding.tvUncaughtException.setOnClickListener {
            try {
                throwException()
            }catch (e:Exception){
                RemoteLogger.sendException(e)
            }
        }
    }

    private fun throwException() {
        throw IOException()
    }
}