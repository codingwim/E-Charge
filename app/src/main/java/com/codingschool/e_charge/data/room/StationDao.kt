package com.codingschool.e_charge.data.room

import androidx.room.*

@Dao
interface StationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStation(station: Station): Long

    @Query("SELECT * FROM station")
    fun getAllStations(): List<Station>

    @Query("SELECT * FROM station WHERE creator_user_id = :id")
    fun getAllStationsFromUserWithId(id: Int): List<Station>

    @Query("SELECT COUNT(*) FROM station WHERE street LIKE :street AND  number LIKE :number AND zip LIKE :zip AND town LIKE :town AND country LIKE :country")
    fun findExistingAddress(street: String, number: String, zip: String, town: String, country: String): Int

    @Transaction
    @Query("SELECT * from station")
    fun getStationsWithPlugs(): List<StationAndPlugs>

    @Transaction
    @Query("SELECT * from station WHERE creator_user_id = :id")
    fun getStationsWithPlugsForUserWithId(id: Int): List<StationAndPlugs>

    @Query("DELETE FROM station WHERE station_id = :id")
    fun deleteStationById(id: Int)

    @Query("DELETE FROM station WHERE creator_user_id = :id")
    fun deleteStationsForUserWithId(id: Int)
}