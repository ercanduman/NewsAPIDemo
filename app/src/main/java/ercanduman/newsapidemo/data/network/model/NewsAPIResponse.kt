package ercanduman.newsapidemo.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Stores all JSON content retrieved from API call. GSON library is responsible to parse JSON and
 * set to class members.
 */
data class NewsAPIResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)