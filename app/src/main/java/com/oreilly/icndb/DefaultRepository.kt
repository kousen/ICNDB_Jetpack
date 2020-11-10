package com.oreilly.icndb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import java.net.URL
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultRepository(private val ioDispatcher: CoroutineDispatcher) : Repository {
    private val _cachedData = MutableLiveData("Old joke")
    override val cachedData: LiveData<String>
        get() = _cachedData

    override suspend fun fetchNewData(first: String, last: String) {
        withContext(Dispatchers.Main) {
            _cachedData.value = "Getting new joke..."
            _cachedData.value = getJokeOverNetwork(first, last)
        }
    }

    private suspend fun getJokeOverNetwork(first: String, last: String): String =
        withContext(ioDispatcher) {
            val queryString = if (first.isNotEmpty() || last.isNotEmpty()) {
                "&firstName=$first&lastName=$last"
            } else ""
            Gson().fromJson(
                URL("$BASE$queryString").readText(),
                JokeResponse::class.java
            ).value.joke
        }

    companion object {
        const val BASE = "http://api.icndb.com/jokes/random?limitTo=[nerdy]"
    }
}
