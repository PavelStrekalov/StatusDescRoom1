package com.example.striker.statusdesc.View

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.striker.statusdesc.Model.AppDatabase
import com.example.striker.statusdesc.Model.User
import com.example.striker.statusdesc.R
import com.example.striker.statusdesc.ViewModel.DbUtils
import com.github.rahatarmanahmed.cpv.CircularProgressView
import kotlinx.android.synthetic.main.fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainFragment:Fragment() {
    var users: Array<User>? = null
    var time: Long ?= null
    lateinit var db: AppDatabase
    lateinit var linearLayoutManager:LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        time = 2500
        db = DbUtils().dbInit(inflater.context, "UserDataBase")

        linearLayoutManager = LinearLayoutManager(
            context, // Context
            LinearLayout.VERTICAL, // Orientation
            false // Reverse layout
        )

        val url = "http://mysafeinfo.com/api/data?list=presidents&format=json"
        //AsyncTaskHandleJson().execute(url)
        GlobalScope.launch(Dispatchers.IO) {

            if (db.UsersDao().count() == 0) {
                var jsonString = DbUtils().dataLoadWithConnect(url)
                users = DbUtils().handleJson(jsonString)
                DbUtils().dataInsert(db, users!!)
            }
            users = DbUtils().getAllUsers(db)
        }

        return inflater.inflate(R.layout.fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        val wait = activity?.findViewById<CircularProgressView>(R.id.progress_view)
        if (wait?.visibility==View.GONE) time=500

        while(1==1) {
            if (users != null && recycler_view != null) {
                recycler_view.layoutManager = linearLayoutManager//linearLayoutManager
                recycler_view.adapter = RecyclerViewAdapter(users!!, activity!!)
                wait?.visibility = View.GONE
                break
            }
        }
    }
}