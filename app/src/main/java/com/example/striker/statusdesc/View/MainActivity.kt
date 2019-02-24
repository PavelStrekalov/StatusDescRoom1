package com.example.striker.statusdesc.View

import android.arch.persistence.room.Room
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.example.striker.statusdesc.Model.AppDatabase
import com.example.striker.statusdesc.Model.User
import com.example.striker.statusdesc.R
import com.example.striker.statusdesc.ViewModel.RecyclerViewAdapter
import com.github.rahatarmanahmed.cpv.CircularProgressView
import kotlinx.android.synthetic.main.fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        var users: Array<User> ?= arrayOf(User(0,"","","",false))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//DataLoad
            val job: Job = GlobalScope.launch(Dispatchers.IO) {
                val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database-name").build()
                db.UsersDao().insert(User(1, "John", "travolta1", "USA",false))
                db.UsersDao().insert(User(2, "John", "travolta2", "Russia",true))
                db.UsersDao().insert(User(3, "John", "travolta3", "Canada",true))
                db.UsersDao().insert(User(4, "John", "travolta4", "Serbiya",false))
                db.UsersDao().insert(User(5, "John", "travolta5", "Finland",true))
                db.UsersDao().insert(User(6, "John", "travolta6", "Ukraine",false))
                db.UsersDao().insert(User(7, "John", "travolta7", "Lipetck",true))
                db.UsersDao().insert(User(8, "John", "travolta8", "Moscow",true))
                users = db.UsersDao().getAll()//.getAll()//.getAll()//?: newUser(1,"John","travolta","Moscow")
            }

//RecycleInit
        val linearLayoutManager = LinearLayoutManager(
            this, // Context
            LinearLayout.VERTICAL, // Orientation
            false // Reverse layout
        )
        var wait = findViewById<CircularProgressView>(R.id.progress_view)
        wait.visibility = View.VISIBLE
        Handler().postDelayed({
            recycler_view.layoutManager = linearLayoutManager//linearLayoutManager
            if(users != null){
                recycler_view.adapter = RecyclerViewAdapter(users!!)
                wait.visibility = View.GONE
            }
        },1000)

    }
}
