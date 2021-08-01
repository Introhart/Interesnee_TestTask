package com.example.thesearcher.data.Network.Model

data class SearchParameters(
    val device: String,
    val engine: String,
    val google_domain: String,
    val ijn: String,
    val q: String,
    val tbm: String
)