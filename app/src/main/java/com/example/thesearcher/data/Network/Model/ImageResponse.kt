package com.example.thesearcher.data.Network.Model

data class ImageResponse(
    val images_results: List<ImagesResult>,
    val search_information: SearchInformation,
    val search_metadata: SearchMetadata,
    val search_parameters: SearchParameters,
    val suggested_searches: List<SuggestedSearche>
)