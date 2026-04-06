package com.example.mycozygarden.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mycozygarden.data.model.Crop
import com.example.mycozygarden.data.model.CropType
import com.example.mycozygarden.data.model.GameState
import com.example.mycozygarden.data.model.GardenBed
import com.example.mycozygarden.data.repository.GameRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

class GameViewModel(private val repository: GameRepository) : ViewModel() {

    val gameState: LiveData<GameState> = repository.getGameState().asLiveData()
    val beds: LiveData<List<GardenBed>> = repository.getAllBeds().asLiveData()

    private var updateJob: Job? = null
    private var lastUpdateTime = System.currentTimeMillis()

    init {
        startUpdateLoop()
    }

    private fun startUpdateLoop() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            while (true) {
                val now = System.currentTimeMillis()
                val delta = (now - lastUpdateTime) / 1000f
                lastUpdateTime = now
                updateAllBeds(delta)
                delay(100)
            }
        }
    }

    private suspend fun updateAllBeds(deltaSec: Float) {
        val currentState = gameState.value ?: return
        val currentBeds = beds.value ?: return
        val hasAutoClicker = repository.getAllUpgrades().asLiveData().value?.any { it.effect == "auto_clicker" && it.owned } ?: false

        for (bed in currentBeds) {
            if (bed.cropType != null && bed.progress < 1f) {
                val crop = Crop.getByType(CropType.valueOf(bed.cropType!!))
                val baseGrowthRate = 1f / crop.baseGrowthTimeSec
                var growth = baseGrowthRate * deltaSec
                if (hasAutoClicker) growth += 0.005f * deltaSec
                val newProgress = min(bed.progress + growth, 1f)
                if (newProgress != bed.progress) {
                    bed.progress = newProgress
                    repository.updateBed(bed)
                }
            }
        }
    }

    fun onBedClick(bed: GardenBed) {
        viewModelScope.launch {
            val state = gameState.value ?: return@launch
            val hasTractor = repository.getAllUpgrades().asLiveData().value?.any { it.effect == "tractor" && it.owned } ?: false
            val clickPower = if (hasTractor) 0.03f else 0.02f
            if (bed.cropType != null && bed.progress < 1f) {
                val newProgress = min(bed.progress + clickPower, 1f)
                bed.progress = newProgress
                repository.updateBed(bed)
                state.totalClicks++
                repository.updateGameState(state)
            }
        }
    }

    fun onHarvest(bed: GardenBed) {
        viewModelScope.launch {
            val state = gameState.value ?: return@launch
            if (bed.cropType == null || bed.progress < 1f) return@launch

            val crop = Crop.getByType(CropType.valueOf(bed.cropType!!))
            val hasScarecrow = repository.getAllUpgrades().asLiveData().value?.any { it.effect == "scarecrow" && it.owned } ?: false
            val multiplier = if (hasScarecrow) 1.2f else 1f
            val reward = (crop.baseHarvestValue * multiplier * state.prestigeBonus).toLong()
            state.coins += reward
            state.totalHarvestedCoins += reward
            bed.cropType = null
            bed.progress = 0f
            repository.updateBed(bed)
            repository.updateGameState(state)

            checkAchievements(state)
        }
    }

    private suspend fun checkAchievements(state: GameState) {
        val achievements = repository.getAllAchievements().asLiveData().value ?: return
        for (ach in achievements) {
            if (!ach.completed) {
                when (ach.requirementType) {
                    "total_coins" -> if (state.totalHarvestedCoins >= ach.requirementValue) completeAchievement(ach, state)
                    "clicks" -> if (state.totalClicks >= ach.requirementValue) completeAchievement(ach, state)
                }
            }
        }
    }

    private suspend fun completeAchievement(ach: Achievement, state: GameState) {
        ach.completed = true
        state.coins += ach.rewardCoins
        state.gems += ach.rewardGems
        repository.updateAchievement(ach)
        repository.updateGameState(state)
    }

    override fun onCleared() {
        super.onCleared()
        updateJob?.cancel()
        viewModelScope.launch {
            val state = gameState.value ?: return@launch
            state.lastSaveTime = System.currentTimeMillis()
            repository.updateGameState(state)
        }
    }

    class GameViewModelFactory(private val repository: GameRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return GameViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
