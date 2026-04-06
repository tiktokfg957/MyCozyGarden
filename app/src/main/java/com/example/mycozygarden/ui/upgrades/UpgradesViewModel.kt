package com.example.mycozygarden.ui.upgrades

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mycozygarden.data.model.GameState
import com.example.mycozygarden.data.model.Upgrade
import com.example.mycozygarden.data.repository.GameRepository
import kotlinx.coroutines.launch

class UpgradesViewModel(private val repository: GameRepository) : ViewModel() {

    val upgrades: LiveData<List<Upgrade>> = repository.getAllUpgrades().asLiveData()
    val gameState: LiveData<GameState> = repository.getGameState().asLiveData()

    suspend fun buyUpgrade(upgrade: Upgrade): Boolean {
        val state = gameState.value ?: return false
        if (state.coins >= upgrade.basePrice && !upgrade.owned) {
            state.coins -= upgrade.basePrice
            upgrade.owned = true
            repository.updateGameState(state)
            repository.updateUpgrade(upgrade)
            return true
        }
        return false
    }

    class UpgradesViewModelFactory(private val repository: GameRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UpgradesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UpgradesViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
