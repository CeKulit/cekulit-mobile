package com.bangkit.cekulit.data.room.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
class HistoryEntity(
    @field:ColumnInfo(name = "idHistory")
    @field:PrimaryKey(autoGenerate = true)
    val idHistory: Int = 0,

    @field:ColumnInfo(name = "result")
    val result: String,

    @field:ColumnInfo(name = "image")
    val image: String,

    @field:ColumnInfo(name = "confidenceScore")
    val confidenceScore: Double,

    )