package ercanduman.newsapidemo.data.network.model

/**
 * Data class to hold and store article related data.
 *
 * "data" keyword make sure that equals, hashcode, copy and toString methods will be generated under
 * the hood. So no need to create these methods once again to compare objects.
 */
data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)