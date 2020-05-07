package com.telstra.telstramvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.telstra.telstramvvm.data.model.Facts

/**
 * SQL query
 */
@Dao
interface FactsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(facts: Facts): Long

    @Query("SELECT * FROM facts")
    fun getFacts(): LiveData<Facts>

}