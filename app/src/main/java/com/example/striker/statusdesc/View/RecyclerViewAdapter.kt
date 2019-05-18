package com.example.striker.statusdesc.View

import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.striker.statusdesc.Model.User
import com.example.striker.statusdesc.R
import com.example.striker.statusdesc.ViewModel.RecyclerLogic
import com.example.striker.statusdesc.ViewModel.UserFragmentManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_view.view.*


class RecyclerViewAdapter(val usersSource: Array<User>, val curActivity:FragmentActivity) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    var users:ArrayList<User> = ArrayList()
    var usersSource_New:ArrayList<User> = ArrayList()

    lateinit private var transaction: FragmentTransaction

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        usersSource_New.clear()
        usersSource_New.addAll(usersSource)
        filter("")
        //notifyDataSetChanged()

        val v: View = LayoutInflater.from(p0?.context).inflate(R.layout.custom_view,p0,false)
        // Return the view holder
        return ViewHolder(v)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        if(p1>=users.size) return
        // Display the current user full name and location in view holder custom view
        if(users.get(p1).avatarUrl!=""){
            Picasso.with(p0.itemView.context).load(users.get(p1).avatarUrl).into(p0?.avatar)
        }
        p0?.name?.text = users.get(p1).firstName + " " + users.get(p1).lastName
        p0.checkbox_on.visibility = RecyclerLogic().isUserVisible(users.get(p1))
        if(p0.checkbox_on.visibility == View.GONE)  p0.checkbox_off.visibility = View.VISIBLE

        p0.itemView.setOnClickListener(View.OnClickListener { v ->
            var manager = curActivity.supportFragmentManager
            var cardDetailFragment = CardDetailFragment()
            UserFragmentManager().replaceFragment(manager, R.id.container, cardDetailFragment, users.get(p1).id)
        })
    }

    fun filter(query : String) {
        users.clear()
        usersSource_New!!.forEach {
            var fullName = it.firstName+" "+it.lastName
            if(fullName.contains(query,true)) users.add(it)// || it.firstName!!.contains(query,true) || it.lastName!!.contains(query,true)
        }
    }

    override fun getItemCount(): Int {
        return usersSource.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name = itemView.tv_name
        val checkbox_on = itemView.checkbox_on
        val checkbox_off = itemView.checkbox_off
        val avatar = itemView.avatar
    }

// This two methods useful for avoiding duplicate item
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}