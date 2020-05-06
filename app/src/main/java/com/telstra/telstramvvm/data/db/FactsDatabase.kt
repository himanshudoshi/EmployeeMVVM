package com.telstra.telstramvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.telstra.telstramvvm.data.model.Facts
import com.telstra.telstramvvm.utils.Converters

@Database(

    entities = [Facts::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FactsDatabase : RoomDatabase() {

    abstract fun getFactsDao(): FactsDao

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