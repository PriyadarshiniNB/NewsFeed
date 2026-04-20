package me.priya.newsapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import me.priya.newsapp.data.local.AppDatabase

class NewsContentProvider : ContentProvider() {

    private lateinit var database: AppDatabase

    override fun onCreate(): Boolean {
        val providerContext = context ?: return false

        // Initialize the database using your companion object
        // This ensures Hilt and the Provider use the same instance
        database = AppDatabase.getInstance(providerContext)

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return database.articleDao().getArticlesCursor()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null // optional
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0 // optional
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0 // optional
    }

    //This tells Android what kind of data is being returned.   cursor.dir means giving a directory
    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.dir/vnd.${NewsContract.AUTHORITY}.${NewsContract.PATH_ARTICLES}"
    }
}