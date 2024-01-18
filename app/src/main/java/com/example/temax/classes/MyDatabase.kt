package com.example.temax.classes

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.temax.services.ApartementDao
import com.example.temax.services.HouseDao
import com.example.temax.services.RoomDao


@Database(entities = [HouseEntity::class, ApartementEntity::class, RoomEntity::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun houseDao(): HouseDao

    abstract  fun apartementDao(): ApartementDao

    abstract fun roomDao(): RoomDao

    companion object {
        @Volatile
        private var instance: MyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            Log.d("MyDatabase", "Creating or getting database instance")
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,MyDatabase::class.java, "temax.db"
            ).build()
    }
}
