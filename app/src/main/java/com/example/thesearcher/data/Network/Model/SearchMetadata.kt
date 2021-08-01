package com.example.thesearcher.data.Network.Model

data class SearchMetadata(
    val created_at: String,
    val google_url: String,
    val id: String,
    val json_endpoint: String,
    val processed_at: String,
    val raw_html_file: String,
    val status: String,
    val total_time_taken: Double
)