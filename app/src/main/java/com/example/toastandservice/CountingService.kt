package com.example.toastandservice

import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


class CountingService: android.app.Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        coroutineScope.launch{
            while (true) {
                delay(1000)
                Log.d("said", "${Random.nextInt(1,6)}")
            }
        }


        return START_STICKY
    }

    override fun onDestroy() {
рефа        coroutineScope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}