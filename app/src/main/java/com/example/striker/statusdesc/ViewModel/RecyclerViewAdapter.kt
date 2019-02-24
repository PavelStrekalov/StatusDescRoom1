package com.example.striker.statusdesc.ViewModel

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.striker.statusdesc.Model.User
import com.example.striker.statusdesc.R
import kotlinx.android.synthetic.main.custom_view.view.*

class RecyclerViewAdapter(val users: Array<User>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v: View = LayoutInflater.from(p0?.context)
            .inflate(R.layout.custom_view,p0,false)

        // Return the view holder
        return ViewHolder(v)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        // Display the current user full name and location in view holder custom view
        p0?.name?.text = users.get(p1).firstName + " " + users.get(p1).lastName
        p0?.location?.text = users.get(p1).location
        var active = users.get(p1).active
        if(active) {
            p0.checkbox_on.visibility = View.VISIBLE
        }else{
            p0.checkbox_off.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name = itemView.tv_name
        val location = itemView.tv_location
        val checkbox_on = itemView.checkbox_on
        val checkbox_off = itemView.checkbox_off
    }

// This two methods useful for avoiding duplicate item
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}