package com.example.striker.statusdesc.View

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.example.striker.statusdesc.R
import com.example.striker.statusdesc.ViewModel.UserFragmentManager
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.util.VKUtil

class MainActivity : AppCompatActivity()  {

    lateinit private var manager: FragmentManager
    lateinit private var mainFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fingerprints = VKUtil.getCertificateFingerprint(this, this.packageName)
        VKSdk.login(this, VKScope.FRIENDS)

        setContentView(R.layout.activity_main)

        //Log.e(TAG,"fingerprints ${fingerprints[0]}")
        manager = supportFragmentManager
        mainFragment = MainFragment()

        UserFragmentManager().init(manager, R.id.container,mainFragment)
    }

    /*fun loginVK(requestCode: Int, resultCode: Int,  data: Intent?){
        if(VKSdk.onActivityResult(requestCode,resultCode,data, object: VKCallback<VKAccessToken>{
                override fun onResult(res: VKAccessToken?) {

                }

                override fun onError(error: VKError?) {
                    var c = "DS"
                }
            })
           ){}
    }*/
}

