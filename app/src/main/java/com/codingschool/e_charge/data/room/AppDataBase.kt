package com.codingschool.e_charge.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, Station::class, Plug::class, Type::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun stationDao(): StationDao
    abstract fun plugDao(): PlugDao
    abstract fun typeDao(): TypeDao
}