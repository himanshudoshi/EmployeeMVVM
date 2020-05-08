package com.telstra.mvvmdemo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.telstra.mvvmdemo.data.model.Facts
import com.telstra.mvvmdemo.utils.Converters

/**
 * Room Database creation and singleton for Facts Class
 */
@Database(entities = [Facts::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FactsDatabase : RoomDatabase() {

    /** Connects the database to the DAO. */
    abstract fun getFactsDao(): FactsDao

    /** Define a companion object, this allows us to add functions on the FactsDatabase class. */
    companion object {

        @Volatile
        private var instance: FactsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: buildDatabase(
                        context
                    ).also {
                        instance = it
                    }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FactsDatabase::class.java,
                "Facts.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}