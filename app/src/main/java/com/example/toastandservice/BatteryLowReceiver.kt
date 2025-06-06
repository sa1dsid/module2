package com.example.toastandservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class BatteryLowReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            Intent.ACTION_POWER_CONNECTED ->
                Toast.makeText(context, "Устройство заряжается", Toast.LENGTH_SHORT).show()
            Intent.ACTION_POWER_DISCONNECTED ->
                Toast.makeText(context, "Устройство не заряжается", Toast.LENGTH_SHORT).show()

        }
    }
}