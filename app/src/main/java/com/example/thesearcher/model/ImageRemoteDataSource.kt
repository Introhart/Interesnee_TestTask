package com.example.thesearcher.model

import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

interface OperationCallback<T> {
    fun onSuccess(data: MutableList<T>?)
    fun onError(error: String?)
}

// TODO :: REFACTOR ME
class ImageRemoteDataSource: ImageDataSource {

    private var paginationPage: Int = 0 // TODO :: WTF ?? Must be private ??
    private lateinit var searchRequest: String

    private val client = OkHttpClient()

    override fun retrieveImages(callback: OperationCallback<ImageInfo>, searchRequest: String){
        this.searchRequest = searchRequest
        paginationPage = 0

        val httpUrl: HttpUrl = HttpUrl.Builder() //TODO :: REFACTOR ME
            .scheme("https")
            .host("serpapi.com")
            .addPathSegment("search")
            .addQueryParameter("q", searchRequest)
            .addQueryParameter("tbm", "isch")
            .addQueryParameter("ijn", paginationPage.toString())
            .addQueryParameter("engine", "google")
            .addQueryParameter("api_key", "2a79c66278f7ee20959e979e87016451b2da276a79fbae3e1406fada5935b72a")
            .build()

        val request = Request.Builder()
            .url(httpUrl)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use{
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val stringRequestResult = response.body()!!.string()
                    val res = parseImageJson(stringRequestResult)
                    callback.onSuccess(res!!) // TODO :: HANDLE NULL EXCEPTION
                }
            }
        })
    }

    override fun retrieveNext(callback: OperationCallback<ImageInfo>) {
        paginationPage++

        val httpUrl: HttpUrl = HttpUrl.Builder() //TODO :: REFACTOR ME
            .scheme("https")
            .host("serpapi.com")
            .addPathSegment("search")
            .addQueryParameter("q", searchRequest)
            .addQueryParameter("tbm", "isch")
            .addQueryParameter("ijn", paginationPage.toString())
            .addQueryParameter("engine", "google")
            .addQueryParameter("api_key", "2a79c66278f7ee20959e979e87016451b2da276a79fbae3e1406fada5935b72a")
            .build()

        val request = Request.Builder()
            .url(httpUrl)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use{
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val stringRequestResult = response.body()!!.string()
                    val res = parseImageJson(stringRequestResult)
                    callback.onSuccess(res!!) // TODO :: HANDLE NULL EXCEPTION
                }
            }
        })
    }

    override fun retrievePrev(callback: OperationCallback<ImageInfo>) {
        if ( paginationPage > 0 )
            paginationPage--

        val httpUrl: HttpUrl = HttpUrl.Builder() //TODO :: REFACTOR ME
            .scheme("https")
            .host("serpapi.com")
            .addPathSegment("search")
            .addQueryParameter("q", searchRequest)
            .addQueryParameter("tbm", "isch")
            .addQueryParameter("ijn", paginationPage.toString())
            .addQueryParameter("engine", "google")
            .addQueryParameter("api_key", "2a79c66278f7ee20959e979e87016451b2da276a79fbae3e1406fada5935b72a")
            .build()

        val request = Request.Builder()
            .url(httpUrl)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use{
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val stringRequestResult = response.body()!!.string()
                    val res = parseImageJson(stringRequestResult)
                    callback.onSuccess(res!!) // TODO :: HANDLE NULL EXCEPTION
                }
            }
        })
    }

    private fun parseImageJson(sourceJsonString: String) : MutableList<ImageInfo>? {
        val newImagesList: MutableList<ImageInfo> = mutableListOf()
        val jsonObject: JSONObject = JSONObject(sourceJsonString)
        val imageResultsArray: JSONArray = jsonObject.getJSONArray("images_results")

        for (i in 0 until imageResultsArray.length()){
            newImagesList.add(
                ImageInfo(
                    imageResultsArray.getJSONObject(i).getString("title"),
                    imageResultsArray.getJSONObject(i).getString("thumbnail"),
                    imageResultsArray.getJSONObject(i).getString("original"),
                    imageResultsArray.getJSONObject(i).getString("link")
                )
            )
        }
        return newImagesList
    }
}