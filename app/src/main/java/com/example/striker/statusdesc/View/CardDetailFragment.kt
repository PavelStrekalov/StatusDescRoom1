package com.example.striker.statusdesc.View

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.striker.statusdesc.Model.AppDatabase
import com.example.striker.statusdesc.Model.User
import com.example.striker.statusdesc.R
import com.example.striker.statusdesc.ViewModel.DbUtils
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CardDetailFragment:Fragment() {

    val TAG = "CardDetailFragment"
    lateinit var db   : AppDatabase
    lateinit var user : User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val job = GlobalScope.launch(Dispatchers.IO) {
            db = DbUtils().dbInit(inflater.context,"UserDataBase")
            user = DbUtils().getUserById(db,arguments?.getLong("idObj")!!)
        }

        return inflater.inflate(R.layout.card_detail_fragment,container, false)
    }

    override fun onStart() {
        super.onStart()

        var idObj = activity?.findViewById<TextView>(R.id.objId)
        var firstName = activity?.findViewById<TextView>(R.id.firstName)
        var lastName = activity?.findViewById<TextView>(R.id.lastName)
        var active = activity?.findViewById<CheckBox>(R.id.active)
        var avatarFull = activity?.findViewById<CircleImageView>(R.id.avatar_full)

        Handler().postDelayed({
            if(user != null){
                idObj?.text = user.id.toString()//parId
                firstName?.text = user.firstName.toString()
                lastName?.text = user.lastName.toString()
                active?.isChecked = user.active
                if(user.avatarFullUrl != ""){
                    Picasso.with(view?.context).load(user.avatarUrl).into(avatarFull)
                }
            }
        },150)
    }

}

