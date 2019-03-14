package com.example.striker.statusdesc.View

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.striker.statusdesc.R


class CardDetailFragment:Fragment() {

    val TAG = "CardDetailFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.card_detail_fragment,container, false)
    }

    override fun onStart() {
        super.onStart()
        var idObj = activity!!.findViewById<TextView>(R.id.objId)
        var firstName = activity!!.findViewById<TextView>(R.id.firstName)
        var lastName = activity!!.findViewById<TextView>(R.id.lastName)
        var location = activity!!.findViewById<TextView>(R.id.location)
        var active = activity!!.findViewById<CheckBox>(R.id.active)
        var parId:String = arguments?.getString("idObj") ?: "default"
        var parFirstName:String = arguments?.getString("firstName") ?: "default"
        var parLastName:String = arguments?.getString("lastName") ?: "default"
        var parLocation:String = arguments?.getString("location") ?: "default"
        var parActive:Boolean = arguments?.getBoolean("active") ?: false
        idObj.text = parId
        firstName.text = parFirstName
        lastName.text = parLastName
        location.text = parLocation
        active.isChecked = parActive
    }
}

