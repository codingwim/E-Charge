package com.codingschool.e_charge.data.room

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    @Query("SELECT * FROM user")
    fun getAllUsers(): List<User>

    @Query("SELECT COUNT(*) FROM user WHERE email = :email")
    fun existsUserWithEmail(email: String): Int

    @Query("SELECT * FROM user WHERE user_id = :id LIMIT 1")
    fun getUserById(id: Int): User

    @Transaction
    @Query("SELECT * FROM user WHERE user_id = :id")
    fun getUserWithStationsByUserId(id: Int): UserAndStations

    @Transaction
    @Query("SELECT * FROM user")
    fun getUsersWithStations(): List<UserAndStations>

    @Query("DELETE FROM user WHERE user_id = :id")
    fun deleteUserById(id: Int)
}