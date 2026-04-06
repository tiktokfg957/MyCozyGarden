package com.example.mycozygarden.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "garden_beds")
data class GardenBed(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val index: Int,
    var cropType: String?,
    var progress: Float,
    var lastUpdateTime: Long
)
