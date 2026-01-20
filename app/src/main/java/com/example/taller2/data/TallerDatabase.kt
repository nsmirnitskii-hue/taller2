package com.example.taller2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Coche::class],
    version = 1,
    exportSchema = false
)
abstract class TallerDatabase : RoomDatabase() {

    abstract fun cocheDao(): CocheDao

    companion object {
        @Volatile
        private var INSTANCE: TallerDatabase? = null

        fun getDatabase(context: Context): TallerDatabase {
            //context.deleteDatabase("taller_database")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TallerDatabase::class.java,
                    "taller_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}