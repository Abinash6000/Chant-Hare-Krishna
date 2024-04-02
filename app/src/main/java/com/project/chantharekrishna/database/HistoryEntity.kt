package com.project.chantharekrishna

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history-table")
data class HistoryEntity(
    @PrimaryKey
    val date: String,
    val malaCount: Int,
    val mantraCount: Int
)
