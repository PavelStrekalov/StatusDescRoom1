package com.example.striker.statusdesc.Model

import android.arch.persistence.room.*

@Entity
public class User(id:Long,firstName:String,lastName:String,location:String,active:Boolean) {
    @PrimaryKey()
    var id: Long = id
    var firstName: String? = firstName
    var lastName:String?=lastName
    var location:String?=location
    var active = active
}

@Dao
interface UsersDao {

    @Query(value = "SELECT * FROM User")
    fun getAll(): Array<User>

    @Query("SELECT * FROM User WHERE id = :id")
    fun getById(id: Int): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newuser: User)

    @Update
     fun update(newuser: User)

    @Delete
     fun delete(newuser: User)
}

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun UsersDao(): UsersDao
}