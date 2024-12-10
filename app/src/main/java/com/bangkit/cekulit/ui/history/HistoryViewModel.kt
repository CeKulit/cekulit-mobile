package com.bangkit.cekulit.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.cekulit.data.HistoryRepository
import com.bangkit.cekulit.data.room.history.HistoryEntity
import kotlinx.coroutines.launch

class HistoryViewModel(private val historyRepository: HistoryRepository) : ViewModel() {
    fun getListHistory() = historyRepository.getListHistory()

    fun setListHistory(histories: List<HistoryEntity>) {
        viewModelScope.launch {
            historyRepository.setListHistory(histories)
        }
    }
}