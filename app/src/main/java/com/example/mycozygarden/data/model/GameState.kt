package com.example.mycozygarden.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_state")
data class GameState(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    var coins: Long = 0,
    var gems: Int = 0,
    var totalHarvestedCoins: Long = 0,
    var totalClicks: Int = 0,
    var clickPower: Float = 0.02f,
    var hasScarecrow: Boolean = false,
    var hasChicken: Boolean = false,
    var hasTractor: Boolean = false,
    var hasGreenhouse: Boolean = false,
    var prestigeBonus: Float = 1.0f,
    var lastSaveTime: Long = System.currentTimeMillis()
)
