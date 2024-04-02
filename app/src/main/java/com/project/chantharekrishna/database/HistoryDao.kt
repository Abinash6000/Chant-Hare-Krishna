package com.project.chantharekrishna

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(entity: HistoryEntity)

    @Query("SELECT * FROM `HISTORY-TABLE` ORDER BY `DATE` DESC")
    fun fetchAllDates(): Flow<List<HistoryEntity>>
}