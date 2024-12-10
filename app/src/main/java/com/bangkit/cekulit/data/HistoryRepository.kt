package com.bangkit.cekulit.data

import androidx.lifecycle.LiveData
import com.bangkit.cekulit.data.room.history.HistoryDAO
import com.bangkit.cekulit.data.room.history.HistoryEntity

class HistoryRepository(
    private val historyDao: HistoryDAO
) {

    fun getListHistory(): LiveData<List<HistoryEntity>>{
        return historyDao.getListHistory()
    }

   suspend fun setListHistory(history: List<HistoryEntity>){
        return historyDao.insertHistory(history)
    }

    companion object {
        @Volatile
        private var instance: HistoryRepository? = null
        fun getInstance(
            historyDao: HistoryDAO,
        ): HistoryRepository =
            instance ?: synchronized(this) {
                instance ?: HistoryRepository(historyDao)
            }.also { instance = it }
    }
}