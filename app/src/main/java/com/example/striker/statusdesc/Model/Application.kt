package com.example.striker.statusdesc.Model

import android.app.Application
import com.vk.sdk.VKSdk

class Application:Application() {
    override fun onCreate() {
        super.onCreate()
        VKSdk.initialize(applicationContext);
    }
}