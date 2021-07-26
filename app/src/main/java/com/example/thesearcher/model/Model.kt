package com.example.thesearcher.model

import java.io.Serializable

data class ImageInfo(
    val title: String,
    val thumbnailUrl: String,
    val originalUrl: String,
    val linkPageUrl: String
    ) : Serializable