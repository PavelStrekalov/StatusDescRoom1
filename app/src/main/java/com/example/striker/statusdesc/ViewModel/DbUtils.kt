package com.example.striker.statusdesc.ViewModel

import android.arch.persistence.room.Room
import android.content.Context
import com.example.striker.statusdesc.Model.AppDatabase
import com.example.striker.statusdesc.Model.User

class DbUtils() {
        fun dbInit(context: Context, dbName: String): Array<User> {
            val db = Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()
            dataInsert(db)
            var usersDao = getAll(db)
            return usersDao
        }

    fun dataInsert(db:AppDatabase): AppDatabase {
        db.UsersDao().insert(User(1, "John", "travolta1", "USA",false))
        db.UsersDao().insert(User(2, "John", "travolta2", "Russia",true))
        db.UsersDao().insert(User(3, "John", "travolta3", "Canada",true))
        db.UsersDao().insert(User(4, "John", "travolta4", "Serbiya",false))
        db.UsersDao().insert(User(5, "John", "travolta5", "Finland",true))
        db.UsersDao().insert(User(6, "John", "travolta6", "Ukraine",false))
        db.UsersDao().insert(User(7, "John", "travolta7", "Lipetck",true))
        db.UsersDao().insert(User(8, "John", "travolta8", "Moscow",true))
        return db
    }

    fun getAll(db:AppDatabase): Array<User> {
        return db.UsersDao().getAll()
    }
}