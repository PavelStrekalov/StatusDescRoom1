package com.example.striker.statusdesc.ViewModel

import android.arch.persistence.room.Room
import android.content.Context
import com.example.striker.statusdesc.Model.AppDatabase
import com.example.striker.statusdesc.Model.User
import com.google.gson.JsonParser
import com.vk.sdk.api.VKError
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.VKResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

class DbUtils() {
    fun dbInit(context: Context, dbName: String): AppDatabase {
        val db = Room.databaseBuilder(context, AppDatabase::class.java, dbName).fallbackToDestructiveMigrationFrom().build()
        //dataInsert(db)
        return db
    }

    fun getAllUsers(db: AppDatabase): Array<User> {
        var usersDao = getAll(db)
        return usersDao
    }

    fun getUserById(db: AppDatabase, id: Long):User{
        var user = db.UsersDao().getById(id = id)
        return user
    }

    fun dataInsert(db:AppDatabase, data:Array<User>): AppDatabase {
        var x = 0
        while (x < data.size){
            db.UsersDao().insert(data[x])
            x++
        }
        return db
    }

    fun dataLoadWithConnect(url:String): String {
        var text:String
        val connection = URL(url).openConnection() as HttpURLConnection
        try{
            connection.connect()
            text = connection.inputStream.use{it.reader().use{reader -> reader.readText()}}
        }finally {
            connection.disconnect()
        }

        return text
    }

    fun dataLoadWithConnectForVK(request: VKRequest,db: AppDatabase) {
        var list: ArrayList<User> = ArrayList()
        request.executeWithListener(object : VKRequest.VKRequestListener(){
            override fun onComplete(response: VKResponse) {
                super.onComplete(response)
                val jsonParser = JsonParser()
                val parserJson = jsonParser.parse(response.json.toString()).asJsonObject
                parserJson.get("response").asJsonObject.getAsJsonArray("items").forEach {
                    list.add(
                        User(
                            id = it.asJsonObject.get("id").asLong,
                            firstName = it.asJsonObject.get("first_name").asString,
                            lastName = it.asJsonObject.get("last_name").asString,
                            location = /*it.asJsonObject.get("site").asString*/"DS",
                            avatarUrl= it.asJsonObject.get("photo_50").asString,
                            active = it.asJsonObject.get("online").asInt==1
                        )
                    )
                }

                //Log.e("STRIKER11111",list!!.toTypedArray()[0].firstName.toString())
                GlobalScope.launch(Dispatchers.IO) {
                    dropDb(db)
                    dataInsert(db, list.toTypedArray()!!)
                }
            }


            override fun onError(error: VKError?) {
                super.onError(error)
            }

            override fun onProgress(progressType: VKRequest.VKProgressType?, bytesLoaded: Long, bytesTotal: Long) {
                super.onProgress(progressType, bytesLoaded, bytesTotal)
            }
        })
    }

   /*fun handleJson(jsonString:String?): Array<User>? {
        val jsonArray = JSONArray(jsonString)
        val list = ArrayList<User>()
        var x = 0
        var active = false
        var objCount = jsonArray.length()
        while(x<objCount){
            val jsonObject = jsonArray.getJSONObject(x)
            if(x==objCount-1)active=true
            list.add(
                User(
                    jsonObject.getInt("id").toLong(),
                    jsonObject.getString("nm"),//"first_name"
                    jsonObject.getString("pp"),//"last_name"
                    jsonObject.getString("tm"),//"tm"
                    active//jsonObject.getBoolean("active")
                )
            )
            x++
        }
        return list.toTypedArray()
    }

    fun handleJsonForVk(jsonString:String?): Array<User>? {
        val jsonArray = JSONArray(jsonString)
        val list = ArrayList<User>()
        var x = 0
        var active = false
        var objCount = jsonArray.length()
        while(x<objCount){
            val jsonObject = jsonArray.getJSONObject(x)
            if(x==objCount-1)active=true
            list.add(
                User(
                    jsonObject.getInt("id").toLong(),
                    jsonObject.getString("nm"),//"first_name"
                    jsonObject.getString("pp"),//"last_name"
                    jsonObject.getString("tm"),//"tm"
                    active//jsonObject.getBoolean("active")
                )
            )
            x++
        }
        return list.toTypedArray()
    }*/

    fun getAll(db:AppDatabase): Array<User> {
        return db.UsersDao().getAll()
    }

    fun dropDb(db: AppDatabase) {
        return db.UsersDao().dropDb()
    }
}