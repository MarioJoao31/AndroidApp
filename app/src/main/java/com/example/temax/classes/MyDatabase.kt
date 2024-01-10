package com.example.temax.classes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.temax.services.HouseDao


@Database(entities = [HouseEntity::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun houseDao(): HouseDao

    companion object {
        @Volatile
        private var instance: MyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,MyDatabase::class.java, "temax.db"
            ).build()
    }
}
