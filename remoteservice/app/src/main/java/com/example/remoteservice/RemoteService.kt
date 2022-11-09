package com.example.remoteservice

import android.app.Service
import android.content.Intent
import android.os.IBinder

class RemoteService : Service(){

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private val binder = object : IMyAidlInterface.Stub() {
        override fun calcBmi(height: Float, weight: Float) :Float{
//            var height_m = height/100f;
                return weight/((height/100f)*(height/100f))
//            return weight/((height_m)*(height_m))
//            return height_m
        }
    }
}