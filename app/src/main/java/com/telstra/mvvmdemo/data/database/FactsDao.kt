package com.telstra.mvvmdemo.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.telstra.mvvmdemo.data.model.Facts

/**
 * The Data Access Object for the Facts class.
 * Defines methods for using the Facts class with Room.
 */
@Dao
interface FactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(facts: Facts): Long

    @Query("SELECT * FROM facts")
    fun getFacts(): LiveData<Facts>
}