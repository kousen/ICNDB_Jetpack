package com.oreilly.icndb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitRepository(private val ioDispatcher: CoroutineDispatcher) : Repository {

    private val service = JokeService.create()

    private val _cachedData = MutableLiveData("Old joke")
    override val cachedData: LiveData<String>
        get() = _cachedData

    override suspend fun fetchNewData(first: String, last: String) {
        withContext(Dispatchers.Main) {
            _cachedData.value = "Getting new joke..."
            _cachedData.value = getJokeOverNetwork(first, last)
        }
    }

    private suspend fun getJokeOverNetwork(first: String, last: String) =
            service.getResponse(firstName = first, lastName = last).value.joke
}
