package com.example.striker.statusdesc.View

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.example.striker.statusdesc.R
import com.example.striker.statusdesc.ViewModel.UserFragmentManager

class MainActivity : AppCompatActivity()  {

    lateinit private var manager: FragmentManager
    lateinit private var mainFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = supportFragmentManager
        mainFragment = MainFragment()

        UserFragmentManager().init(manager, R.id.container,mainFragment)
    }
}

