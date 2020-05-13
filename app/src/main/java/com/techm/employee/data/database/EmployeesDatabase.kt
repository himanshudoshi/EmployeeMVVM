package com.techm.employee.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.techm.employee.data.model.Employees
import com.techm.employee.data.model.EmployeesDetails
import com.techm.employee.utils.Converters

/**
 * Room Database creation and singleton for Employees Class
 */
@Database(entities = [Employees::class],
    version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class EmployeesDatabase : RoomDatabase() {

    /** Connects the database to the DAO. */
    abstract fun getEmployeesDao(): EmployeesDao

    /** Define a companion object, this allows us to add functions on the Employees Database class. */
    companion object {

        @Volatile
        private var instance: EmployeesDatabase? = null
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
                EmployeesDatabase::class.java,
                "Employees.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}