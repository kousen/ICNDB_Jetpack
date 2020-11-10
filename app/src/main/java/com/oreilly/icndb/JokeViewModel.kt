package com.oreilly.icndb

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JokeViewModel(private val repository: Repository) : ViewModel() {
    val first = MutableLiveData<String>("")

    val last = MutableLiveData("")

    val cachedValue = repository.cachedData

    fun onRefresh() {
        viewModelScope.launch {
            repository.fetchNewData(first.value ?: "Jean-Luc", last.value ?: "Picard")
        }
    }
}

object LiveDataVMFactory : ViewModelProvider.Factory {
    private val dataSource = DefaultRepository(Dispatchers.IO)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return JokeViewModel(dataSource) as T
    }
}
