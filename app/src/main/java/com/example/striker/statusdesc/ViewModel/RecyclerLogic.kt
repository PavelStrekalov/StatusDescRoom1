package com.example.striker.statusdesc.ViewModel

import android.view.View
import com.example.striker.statusdesc.Model.User

class RecyclerLogic {

    fun isUserVisible(user: User): Int {
        var active = user.active
        if(active) {
            return View.VISIBLE
        }else{
            return View.GONE
        }
    }
}