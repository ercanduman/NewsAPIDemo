package ercanduman.newsapidemo.data.network.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import ercanduman.newsapidemo.Constants
import kotlinx.parcelize.Parcelize

/**
 * Data class to hold and store article related data.
 *
 * "data" keyword make sure that equals, hashcode, copy and toString methods will be generated
 * under the hood. So no need to create these methods once again to compare objects.
 *
 * Annotating with @Entity means this class will be stored locally in room library and
 * a mapping SQLite table in the database will be created.
 *
 * Each entity must have at least 1 field annotated with PrimaryKey.
 */
@Entity
@Parcelize
data class Article(
    val author: String? = "",
    val content: String? = "",
    val description: String? = "",
    val publishedAt: String? = "",
    val source: Source? = Source("", ""),
    val title: String? = "",
    val urlToImage: String? = "",
    val isSaved: Boolean = false,
    @PrimaryKey val url: String = Constants.DEFAULT_URL
) : Parcelable