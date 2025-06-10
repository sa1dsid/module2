package com.example.toastandservice

import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.Manifest
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toastandservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var batteryLowReceiver: BatteryLowReceiver
    lateinit var binding: ActivityMainBinding
    private val itemAdapter = ItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        batteryLowReceiver = BatteryLowReceiver()
        Log.d("said", "onCreate")
        enableEdgeToEdge()
        setContentView(binding.root)
        val intent = Intent(this, CountingService::class.java)

        // кнопки для запуска/остановки сервиса
        binding.buttonStart.setOnClickListener{
            startService(intent)
        }
        binding.buttonStop.setOnClickListener{
            stopService(intent)
        }

        // добавил ресайклер
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = itemAdapter

        // запрос разрешения
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_CONTACTS), 100)
        } else {
            loadContacts()
        }
    }

    override fun onResume() {
        super.onResume()
        // реагирование рисивера на 2 события
        val intentFilter: IntentFilter = IntentFilter().apply {
            addAction(	Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(batteryLowReceiver, intentFilter)
    }

    // загрузка контактов
    private fun loadContacts() {
        val contacts = mutableListOf<String>()
        val contentResolver = contentResolver
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME),
            null,
            null,
            null
        )
        cursor?.use {
            val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            while (cursor.moveToNext()) {
                val name = cursor.getString(nameIndex)
                contacts.add(name)
            }
        }
        Log.d("said", "Contacts loaded: ${contacts.size}")
        itemAdapter.addItems(contacts)
    }

    override fun onStart() {
        super.onStart()
        Log.d("said", "onStart")
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