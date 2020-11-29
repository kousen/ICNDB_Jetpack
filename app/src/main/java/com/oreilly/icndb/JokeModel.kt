package com.oreilly.icndb

data class JokeValue(
    val id: Int,
    val joke: String,
    val categories: List<String>
)

data class JokeResponse(
    val type: String,
    val value: JokeValue
)
