package com.appringer.logger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.appringer.logger.databinding.ActivityMainBinding
import com.appringer.remoteLogger.repo.logger.RemoteLogger
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            btnException.setOnClickListener {
                val dummyJson = JSONObject().also {
                    it.put("id", "unique id")
                    it.put("name", "Dummy Name")
                }
                val desc = "desc"
                val tag = "tag"
                RemoteLogger.enableLogCat()

                //RemoteLogger.error(desc, dummyJson, tag)

                RemoteLogger.error(Exception())
            }

            btnUncaughtException.setOnClickListener {
                throwException()
            }
        }
    }

    private fun throwException() {
        throw IOException()
    }
}