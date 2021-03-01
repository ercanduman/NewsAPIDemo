package ercanduman.newsapidemo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ercanduman.newsapidemo.Constants
import ercanduman.newsapidemo.data.db.dao.ArticleDao
import ercanduman.newsapidemo.data.network.model.Article

/**
 *
 * Inherits from RoomDatabase and creates a persistence database and its associated SqLite tables
 * for storing and retrieving all items locally.
 *
 * As it is mentioned in room documentation, this class should be an abstract class and extend RoomDatabase.
 *
 * Annotating with @Database marks a class as a RoomDatabase.
 * Room verifies all of queries in Dao classes while the application is being compiled so that
 * if there is a problem in one of the queries, it will be notified instantly.
 *
 * Notice: Singleton functionality and creation of AppDatabase instance will be handled by Dagger-Hilt library
 *
 * @author ercan
 * @since  3/1/21
 */
@Database(entities = [Article::class], version = Constants.DATABASE_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): ArticleDao
}