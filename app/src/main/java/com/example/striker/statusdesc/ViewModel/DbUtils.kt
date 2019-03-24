package com.example.striker.statusdesc.ViewModel

import android.arch.persistence.room.Room
import android.content.Context
import com.example.striker.statusdesc.Model.AppDatabase
import com.example.striker.statusdesc.Model.User
import org.json.JSONArray
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

    fun handleJson(jsonString:String?): Array<User>? {
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
                    jsonObject.getString("nm"),
                    jsonObject.getString("pp"),
                    jsonObject.getString("tm"),
                    active
                )
            )
            x++
        }
        return list.toTypedArray()
    }

    fun getAll(db:AppDatabase): Array<User> {
        return db.UsersDao().getAll()
    }
}