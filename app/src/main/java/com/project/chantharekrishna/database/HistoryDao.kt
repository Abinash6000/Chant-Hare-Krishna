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

    @Query("SELECT * FROM `HISTORY-TABLE` ORDER BY strftime('%Y-%m-%d', substr(`DATE`, 7, 4) || '-' || substr(`DATE`, 4, 2) || '-' || substr(`DATE`, 1, 2)) DESC")
    fun fetchAllDates(): Flow<List<HistoryEntity>>
}