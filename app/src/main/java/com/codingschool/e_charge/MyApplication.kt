package com.codingschool.e_charge

import android.app.Application
import androidx.room.Room
import com.codingschool.e_charge.data.room.AppDataBase

class MyApplication : Application() {
    lateinit var database: AppDataBase

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        database = Room
            .databaseBuilder(
                this,
                AppDataBase::class.java,
                "app-db"
            )
            // REMOVE ON PRODUCTION VERSION
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    companion object {
        private var instance: MyApplication? = null
    }
}