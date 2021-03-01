package ercanduman.newsapidemo.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ercanduman.newsapidemo.data.network.model.Article

/**
 * Data Access object that stores all SqLite queries for database interactions.
 *
 * At compile time, Room will generate an implementation of this class when it is referenced
 * by a Database.
 *
 * @author ercan
 * @since  3/1/21
 */
@Dao
interface ArticleDao {

    /**
     * Inserts parameter object into database.
     *
     * In case of conflicts, the new old one will be replaced with new instance.
     *
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    /**
     * Deletes its parameter from the database.
     */
    @Delete
    suspend fun delete(article: Article)
}