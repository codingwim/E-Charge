package com.codingschool.e_charge.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TypeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertType(type: Type)

    @Query("SELECT * FROM type")
    fun getAllTypes(): List<Type>

    @Query("SELECT * FROM type WHERE type_id = :id LIMIT 1")
    fun getTypeById(id: Int): Type

    @Query("SELECT type_id FROM type WHERE name LIKE :name LIMIT 1")
    fun getIdByName(name: String): Int

    @Query("DELETE FROM type WHERE type_id = :id")
    fun deleteTypeById(id: Int)

    @Query("DELETE FROM type")
    fun deleteAllTypes()
}