package com.example.mycozygarden.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val requirementType: String,
    val requirementValue: Int,
    var progress: Int = 0,
    var completed: Boolean = false,
    val rewardCoins: Int,
    val rewardGems: Int
)
