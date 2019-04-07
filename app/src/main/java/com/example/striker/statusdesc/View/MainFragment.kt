package com.example.striker.statusdesc.View

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.striker.statusdesc.Model.AppDatabase
import com.example.striker.statusdesc.Model.User
import com.example.striker.statusdesc.R
import com.example.striker.statusdesc.ViewModel.DbUtils
import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKApi
import com.vk.sdk.api.VKApiConst
import com.vk.sdk.api.VKError
import com.vk.sdk.api.VKParameters
import kotlinx.android.synthetic.main.fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainFragment:Fragment() {
    var users: Array<User>? = null
    lateinit var db: AppDatabase
    lateinit var linearLayoutManager:LinearLayoutManager
    private val TAG:String = "FD"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        db = DbUtils().dbInit(inflater.context, "UserDataBase")

        linearLayoutManager = LinearLayoutManager(
            context, // Context
            LinearLayout.VERTICAL, // Orientation
            false // Reverse layout
        )

         //val url = "http://mysafeinfo.com/api/data?list=presidents&format=json"
        var request =  VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "id, first_name, last_name, online, photo_50"))
        //AsyncTaskHandleJson().execute(url)
        GlobalScope.launch(Dispatchers.IO) {

            //if (db.UsersDao().count() == 0) {
                //var jsonString = DbUtils().dataLoadWithConnect(url)
                //users = DbUtils().handleJson(jsonString)

                DbUtils().dataLoadWithConnectForVK(request, db)
            //while(1==1) {
            users = DbUtils().getAllUsers(db)
            //}
                //DbUtils().dataInsert(db, users!!)
          //  }

        }


        return inflater.inflate(R.layout.fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        val wait = activity?.findViewById<CircularProgressView>(R.id.progress_view)
        while(1==1) {
            //Log.e("STRIKER!!!!!", (users!![0]==null).toString())
           // Log.e("STRIKER!!!!!BOOOL", (users!![0]!=null).toString())
           // Log.e("STRIKER!!!!!BOOOL11", (recycler_view.adapter!=null).toString())
            if (users != null) {
                Log.e("STRIKERFName",users!![0].firstName.toString())
                Log.e("STRIKERURL",users!![0].avatarUrl.toString())
                recycler_view.layoutManager = linearLayoutManager//linearLayoutManager
                recycler_view.adapter = RecyclerViewAdapter(users!!, activity!!)
                wait?.visibility = View.GONE
            }
             if(recycler_view!=null)
                break
        }

    }

    fun loginVk(requestCode:Int, resultCode: Int, data: Intent?):Boolean{
        if(!VKSdk.onActivityResult(requestCode, resultCode, data,object: VKCallback<VKAccessToken>{
                override fun onResult(res: VKAccessToken?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onError(error: VKError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
        })){return false}
        return true
    }
}