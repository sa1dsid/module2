package com.example.toastandservice

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.toastandservice.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope

class MainActivity : AppCompatActivity() {
    lateinit var batteryLowReceiver: BatteryLowReceiver

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        Log.d("said", "onCreate")
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.buttonStart.setOnClickListener{
            val intent = Intent(this, CountingService::class.java)
            startService(intent)
        }
        binding.buttonStop.setOnClickListener{
            val intent = Intent(this, CountingService::class.java)
            stopService(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("said", "onStart")
    }

    override fun onResume() {
        super.onResume()
        batteryLowReceiver = BatteryLowReceiver()
        val intentFilter: IntentFilter = IntentFilter().apply {
            addAction(	Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(batteryLowReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        Log.d("said", "onPause")
        unregisterReceiver(batteryLowReceiver)
    }

    override fun onStop() {
        super.onStop()
        Log.d("said", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("said", "onDestroy")
    }
}