package com.example.striker.statusdesc.Model

import android.arch.persistence.room.*

@Entity
class User(
    id: Long,
    firstName: String,
    lastName: String,
    avatarUrl: String,
    avatarFullUrl: String,
    active: Boolean) {
    @PrimaryKey()
    var id: Long = id
    var firstName: String ?= firstName
    var lastName:String ?= lastName
    var avatarUrl : String ?= avatarUrl
    var avatarFullUrl : String ?= avatarFullUrl
    var active = active
}

@Dao
interface UsersDao {

    @Query(value = "SELECT * FROM User")
    fun getAll(): Array<User>

    @Query(value = "SELECT * FROM User WHERE id = :id")
    fun getById(id: Long):User

    @Query(value = "DELETE FROM User WHERE id is not null")
    fun dropDb()

    @Query(value = "SELECT * FROM User WHERE firstName like :substring OR lastName like :substring")
    fun filterDb(substring: String): Array<User>

    @Query(value="SELECT COUNT(*) FROM User")
    fun count():Int

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