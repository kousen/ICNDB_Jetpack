package com.oreilly.icndb

import androidx.lifecycle.LiveData

interface Repository {
    val cachedData: LiveData<String>
    suspend fun fetchNewData(first: String, last: String)
}
