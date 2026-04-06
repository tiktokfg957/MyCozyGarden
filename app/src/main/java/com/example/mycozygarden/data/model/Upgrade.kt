package com.example.mycozygarden.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "upgrades")
data class Upgrade(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val effect: String,
    val effectValue: Float,
    val basePrice: Int,
    var owned: Boolean = false
)
