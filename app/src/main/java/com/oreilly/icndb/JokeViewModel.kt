package com.oreilly.icndb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JokeViewModel(private val dataSource: DataSource) : ViewModel() {
    val cachedValue = dataSource.cachedData

    fun onRefresh() {
        viewModelScope.launch {
            dataSource.fetchNewData()
        }
    }
}

object LiveDataVMFactory : ViewModelProvider.Factory {
    private val dataSource = DefaultDataSource(Dispatchers.IO)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return JokeViewModel(dataSource) as T
    }
}
