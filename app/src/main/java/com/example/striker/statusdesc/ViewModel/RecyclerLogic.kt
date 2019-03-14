package com.example.striker.statusdesc.ViewModel

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.striker.statusdesc.Model.User
import com.example.striker.statusdesc.R
import com.example.striker.statusdesc.View.CardDetailFragment

class RecyclerLogic {

    fun isUserVisible(user: User): Int {
        var active = user.active
        if(active) {
            return View.VISIBLE
        }else{
            return View.GONE
        }
    }

    fun onClickCard(view: View,activity: AppCompatActivity){
        lateinit var manager: FragmentManager
        lateinit var cardDetailFragment: Fragment
        lateinit var transaction: FragmentTransaction

        manager = activity.supportFragmentManager

        cardDetailFragment = CardDetailFragment()

        transaction = manager.beginTransaction()

        var Bundle = Bundle()
        Bundle.putString("idObj","01")
        Bundle.putString("firstName","Ivan")
        Bundle.putString("lastName","Sidorov")
        Bundle.putString("location","Moscow")
        Bundle.putBoolean("active",true)
        cardDetailFragment.arguments=Bundle
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
        transaction.replace(R.id.container,cardDetailFragment)

        transaction.addToBackStack(null)
        transaction.commit()
    }
}