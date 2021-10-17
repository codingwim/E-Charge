package com.codingschool.e_charge.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlugDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPlug(plug: Plug)

    @Query("SELECT * FROM plug")
    fun getAllPlugs(): List<Plug>

    @Query("SELECT * FROM plug WHERE plug_station_id = :id")
    fun getAllPlugsAtFromStationWithId(id: Int): List<Plug>

    @Query("SELECT * FROM plug WHERE plug_type_id = :id")
    fun getAllPlugsFromTypeWithId(id: Int): List<Plug>

    @Query("SELECT COUNT(*) FROM plug WHERE plug_type_id = :type_id AND plug_station_id = :station_id")
    fun countPlugsFromTypeWithIdForStationWithId(type_id: Int, station_id: Int): Int

    @Query("SELECT * FROM plug WHERE plug_id = :id LIMIT 1")
    fun getPlugById(id: Int): Plug

    @Query("DELETE FROM plug WHERE plug_id = :id")
    fun deletePlugById(id: Int)
}