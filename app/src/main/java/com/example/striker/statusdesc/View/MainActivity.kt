package com.example.striker.statusdesc.View

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.striker.statusdesc.R
import com.example.striker.statusdesc.ViewModel.UserFragmentManager

class MainActivity : AppCompatActivity()  {

    lateinit private var manager:FragmentManager
    lateinit private var mainFragment:Fragment
    lateinit private var cardDetailFragment: Fragment
    lateinit private var transaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = supportFragmentManager
        mainFragment = MainFragment()
        cardDetailFragment = CardDetailFragment()

        UserFragmentManager().init(manager, R.id.container,mainFragment)
    }

    public fun onClickCard(view: View){
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

