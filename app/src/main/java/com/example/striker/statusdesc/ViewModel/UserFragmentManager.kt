package com.example.striker.statusdesc.ViewModel

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.example.striker.statusdesc.R

class UserFragmentManager {
    lateinit private var transaction: FragmentTransaction

    fun init(fragmentManager: FragmentManager, containerId:Int, fragment:Fragment){
        transaction = fragmentManager.beginTransaction()
        transaction.add(containerId,fragment)
        transaction.commit()
    }

    fun replaceFragment(fragmentManager: FragmentManager, containerId:Int, fragment:Fragment){
        transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
        transaction.replace(containerId,fragment)

        transaction.addToBackStack(null)
        transaction.commit()
    }
}