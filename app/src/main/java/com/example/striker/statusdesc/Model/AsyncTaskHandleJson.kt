package com.example.striker.statusdesc.Model

import android.os.AsyncTask
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class AsyncTaskHandleJson: AsyncTask<String, String, String>() {
    override fun doInBackground(vararg url: String?): String {
        var text:String
        val connection = URL(url[0]).openConnection() as HttpURLConnection
        try{
            connection.connect()
            text = connection.inputStream.use{it.reader().use{reader -> reader.readText()}}
        }finally {
            connection.disconnect()
        }

        return text
    }

    override fun onPostExecute(result: String?){
        super.onPostExecute(result)
        handleJson(result)
    }
}

private fun handleJson(jsonString: String?):ArrayList<User> {
    val jsonArray = JSONArray(jsonString)
    val list = ArrayList<User>()
    var x = 0
    while(x<jsonArray.length()){
        val jsonObject = jsonArray.getJSONObject(x)

        list.add(
            User(
                jsonObject.getInt("id").toLong(),
                jsonObject.getString("nm"),
                jsonObject.getString("pp"),
                jsonObject.getString("tm"),
                true
            )
        )
        x++
    }
    return list
   /* //RecycleInit
    val linearLayoutManager = LinearLayoutManager(
        context, // Context
        LinearLayout.VERTICAL, // Orientation
        false // Reverse layout
    )
    ///
    Handler().postDelayed({
        recycler_view
        recycler_view.layoutManager = linearLayoutManager//linearLayoutManager
        if(list != null){
            recycler_view.adapter = RecyclerViewAdapter(list!!,activity!!)
            //wait?.visibility = View.GONE
        }
    },150)*/
}
