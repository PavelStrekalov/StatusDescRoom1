package com.example.striker.statusdesc.View

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.striker.statusdesc.Model.User
import com.example.striker.statusdesc.R
import com.example.striker.statusdesc.ViewModel.DbUtils
import kotlinx.android.synthetic.main.fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainFragment:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val Tag = "MainFragment"
        var users: Array<User> ?= arrayOf(User(0,"","","",false))
        //RecycleInit
        val linearLayoutManager = LinearLayoutManager(
            context, // Context
            LinearLayout.VERTICAL, // Orientation
            false // Reverse layout
        )
       // val wait= activity?.findViewById<CircularProgressView>(R.id.progress_view)
         //wait?.visibility = View.VISIBLE
        val job: Job = GlobalScope.launch(Dispatchers.IO) {
            var db = DbUtils().dbInit(inflater.context,"UserDataBase")
            users = DbUtils().getAllUsers(db)
        }
        Handler().postDelayed({
            recycler_view.layoutManager = linearLayoutManager//linearLayoutManager
            if(users != null){
                recycler_view.adapter = RecyclerViewAdapter(users!!,activity!!)
                //wait?.visibility = View.GONE
            }
        },150)
        return inflater.inflate(R.layout.fragment,container, false)
    }

}