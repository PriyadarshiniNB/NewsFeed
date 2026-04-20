package me.priya.newsapp.provider

import android.net.Uri
import androidx.core.net.toUri

//This is just creating a fixed address (like a URL) to access your app’s data.

object NewsContract {
    const val AUTHORITY = "me.priya.newsapp.provider" // domain name
    const val PATH_ARTICLES = "articles" // specify folder name
    val CONTENT_URI: Uri = "content://$AUTHORITY/$PATH_ARTICLES".toUri() // The full "Web Address" (URL) for your data.
}

