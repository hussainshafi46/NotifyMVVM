package com.ae.notify.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.ae.notify.R
import com.ae.notify.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!isServiceRunning())
            requestSettings()
    }

    private fun requestSettings() {
        startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
    }

    private fun isServiceRunning(): Boolean {
        val check = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return check != null && check.contains(packageName,false)
    }
}