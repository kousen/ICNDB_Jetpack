package com.oreilly.icndb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import java.net.URL
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultDataSource(private val ioDispatcher: CoroutineDispatcher) : DataSource {
    private val _cachedData = MutableLiveData("Old joke")
    override val cachedData: LiveData<String>
        get() = _cachedData

    override suspend fun fetchNewData() {
        withContext(Dispatchers.Main) {
            _cachedData.value = "Getting new joke..."
            _cachedData.value = getJokeOverNetwork()
        }
    }

    private suspend fun getJokeOverNetwork(): String =
        withContext(ioDispatcher) {
            // delay(1000)
            Gson().fromJson(
                URL("http://api.icndb.com/jokes/random?limitTo=[nerdy]").readText(),
                JokeResponse::class.java
            ).value.joke
        }
}

interface DataSource {
    val cachedData: LiveData<String>
    suspend fun fetchNewData()
}

data class JokeValue(
    val id: Int,
    val joke: String,
    val categories: List<String>
)

data class JokeResponse(
    val type: String,
    val value: JokeValue
)
