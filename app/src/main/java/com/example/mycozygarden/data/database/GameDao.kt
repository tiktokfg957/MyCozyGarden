package com.example.mycozygarden.data.database

import androidx.room.*
import com.example.mycozygarden.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM game_state WHERE id = 1")
    fun getGameState(): Flow<GameState>

    @Update
    suspend fun updateGameState(state: GameState)

    @Query("SELECT * FROM garden_beds ORDER BY index ASC")
    fun getAllBeds(): Flow<List<GardenBed>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBed(bed: GardenBed)

    @Update
    suspend fun updateBed(bed: GardenBed)

    @Query("SELECT * FROM upgrades")
    fun getAllUpgrades(): Flow<List<Upgrade>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpgrade(upgrade: Upgrade)

    @Update
    suspend fun updateUpgrade(upgrade: Upgrade)

    @Query("SELECT * FROM achievements")
    fun getAllAchievements(): Flow<List<Achievement>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievement(achievement: Achievement)

    @Update
    suspend fun updateAchievement(achievement: Achievement)
}
