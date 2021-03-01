package ercanduman.newsapidemo.data.db.dao

import androidx.room.*
import ercanduman.newsapidemo.data.network.model.Article
import kotlinx.coroutines.flow.Flow

/**
 * Data Access object that stores all SqLite queries for database interactions.
 *
 * At compile time, Room will generate an implementation of this class when it is referenced
 * by Database.
 *
 *
 * For retrieving data, Kotlin Flow API is used.
 *
 * Flow: An asynchronous data stream that sequentially emits values and completes
 * normally or with an exception. Execution of the flow is also called collecting the data
 * which is always performed in a suspending manner without actual blocking.
 *
 * Flow API in Kotlin is a better way to handle the stream of data asynchronously
 * that executes sequentially.
 *
 *   " Key Difference between LiveData & Kotlin Flow
 *   LiveData is used to observe data without having any hazel to handle lifecycle problems.
 *   Whereas Kotlin flow is used for continuous data integration and it also simplified the asynchronous programming.
 *   Take Room Library as an example. First, it used LiveData to transmit data from the database to UI.
 *   It solved most of the existing problems. But when there are any future changes in the database,
 *   LiveData is helpless in this situation. After a while, the room used Kotlin flow to solve this problem.
 *   With Flow as return-type, room created a new possibility of seamless data integration across
 *   the app between database and UI without writing any extra code.
 *
 *   For more details: https://medium.com/android-dev-hacks/exploring-livedata-and-kotlin-flow-7c8d8e706324 "
 *
 * Take away, as it is recommended to keep flow in the repository level, and make the LiveData
 * a bridge between the UI and the repository, in this app Flow will be used for interactions with Room, and
 * LiveData (which is lifecycle aware) will be used inside ViewModel and UI related components for
 * observing data changes.
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

    /**
     * Retrieves all locally saved articles and returns flow of list of them.
     */
    @Query("SELECT * FROM article")
    fun getSavedArticles(): Flow<List<Article>>
}