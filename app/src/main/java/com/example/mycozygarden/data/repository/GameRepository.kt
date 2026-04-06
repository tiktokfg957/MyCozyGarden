package com.example.mycozygarden.data.repository

import com.example.mycozygarden.data.database.AppDatabase
import com.example.mycozygarden.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GameRepository(private val db: AppDatabase) {

    fun getGameState(): Flow<GameState> = db.gameDao().getGameState()
    suspend fun updateGameState(state: GameState) = db.gameDao().updateGameState(state)

    fun getAllBeds(): Flow<List<GardenBed>> = db.gameDao().getAllBeds()
    suspend fun updateBed(bed: GardenBed) = db.gameDao().updateBed(bed)
    suspend fun insertBed(bed: GardenBed) = db.gameDao().insertBed(bed)

    fun getAllUpgrades(): Flow<List<Upgrade>> = db.gameDao().getAllUpgrades()
    suspend fun updateUpgrade(upgrade: Upgrade) = db.gameDao().updateUpgrade(upgrade)
    suspend fun insertUpgrade(upgrade: Upgrade) = db.gameDao().insertUpgrade(upgrade)

    fun getAllAchievements(): Flow<List<Achievement>> = db.gameDao().getAllAchievements()
    suspend fun updateAchievement(achievement: Achievement) = db.gameDao().updateAchievement(achievement)
    suspend fun insertAchievement(achievement: Achievement) = db.gameDao().insertAchievement(achievement)

    suspend fun initDatabase() {
        val state = db.gameDao().getGameState().first()
        if (state.id == 0) {
            db.gameDao().updateGameState(GameState())
        }
        val beds = db.gameDao().getAllBeds().first()
        if (beds.isEmpty()) {
            for (i in 0 until 12) {
                db.gameDao().insertBed(GardenBed(index = i, cropType = null, progress = 0f, lastUpdateTime = System.currentTimeMillis()))
            }
        }
        val upgrades = db.gameDao().getAllUpgrades().first()
        if (upgrades.isEmpty()) {
            val upgradeList = listOf(
                Upgrade(name = "Поливалка", description = "Автоматически поливает одну грядку", effect = "auto_clicker", effectValue = 0.005f, basePrice = 500),
                Upgrade(name = "Пугало", description = "+20% к стоимости урожая", effect = "scarecrow", effectValue = 0.2f, basePrice = 1000),
                Upgrade(name = "Курица-несушка", description = "+1 монета каждые 5 сек", effect = "chicken", effectValue = 0.2f, basePrice = 750),
                Upgrade(name = "Трактор", description = "Увеличивает силу клика", effect = "tractor", effectValue = 0.01f, basePrice = 1500),
                Upgrade(name = "Теплица", description = "Сажать любые культуры зимой", effect = "greenhouse", effectValue = 1f, basePrice = 2000)
            )
            upgradeList.forEach { db.gameDao().insertUpgrade(it) }
        }
        val achievements = db.gameDao().getAllAchievements().first()
        if (achievements.isEmpty()) {
            val achievementList = listOf(
                Achievement(name = "Первый урожай", description = "Собрать любую культуру", requirementType = "harvest", requirementValue = 1, rewardCoins = 50, rewardGems = 0),
                Achievement(name = "Картофелевод", description = "Собрать 100 картошек", requirementType = "crop_potato", requirementValue = 100, rewardCoins = 500, rewardGems = 0),
                Achievement(name = "Миллионер", description = "Накопить 1 000 000 монет", requirementType = "total_coins", requirementValue = 1_000_000, rewardCoins = 0, rewardGems = 10),
                Achievement(name = "Кликер", description = "Сделать 10 000 кликов", requirementType = "clicks", requirementValue = 10_000, rewardCoins = 0, rewardGems = 5)
            )
            achievementList.forEach { db.gameDao().insertAchievement(it) }
        }
    }
}
