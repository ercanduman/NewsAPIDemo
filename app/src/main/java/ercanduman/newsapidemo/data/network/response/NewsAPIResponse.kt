package ercanduman.newsapidemo.data.network.response

import com.google.gson.annotations.SerializedName
import ercanduman.newsapidemo.data.network.model.Article

/**
 * Stores all JSON content retrieved from API call. GSON library is responsible to parse JSON and
 * set to class members.
 *
 * This class contains list of Article objects, status of network call and total result count
 * that returned from api.
 */
data class NewsAPIResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)