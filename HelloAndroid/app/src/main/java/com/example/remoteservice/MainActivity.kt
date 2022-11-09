package com.example.remoteservice

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.remoteservice.IMyAidlInterface

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var m : EditText
    private lateinit var kg : EditText
    private var bound = false;
    private lateinit var textMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.calcButton)
        m = findViewById(R.id.heightNum)
        kg = findViewById(R.id.weightNum)
        textMessage = findViewById(R.id.bmiValue)
    }

    override fun onStart() {
        super.onStart()
        if (iRemoteService == null) {
            val it = Intent("MyRemoteService")
            it.setPackage("com.example.remoteservice")
            bindService(it, mConnection, BIND_AUTO_CREATE)
            Log.d("TAG",bindService(it, mConnection, BIND_AUTO_CREATE).toString())
        }
        button.setOnClickListener {
            var height = m.text.toString().toFloat()
            var weight = kg.text.toString().toFloat()
            var bmiValue = iRemoteService?.calcBmi(height, weight)
            Log.d("TAG",bmiValue.toString())
            textMessage.setText(bmiValue.toString())
        }
    }

    override fun onResume() {
        super.onResume()
    }


    var iRemoteService: IMyAidlInterface? = null

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, service: IBinder) {
            iRemoteService = IMyAidlInterface.Stub.asInterface(service)
        }

        // Called when the connection with the service disconnects unexpectedly
        override fun onServiceDisconnected(className: ComponentName?) {
            Log.e("ClientApp", "Service has unexpectedly disconnected")
            iRemoteService = null
        }
    }
}