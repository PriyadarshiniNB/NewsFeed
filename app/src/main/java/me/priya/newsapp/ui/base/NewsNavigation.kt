package me.priya.newsapp.ui.base

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.priya.newsapp.ui.offlinearticle.OfflineArticleRoute

sealed class Route(val name: String) {
    object OfflineArticle : Route("offlinearticle")
}

@Composable
fun NewsNavHost() {

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Route.OfflineArticle.name
    ) {
        composable(route = Route.OfflineArticle.name) {
            OfflineArticleRoute(onNewsClick = {
                openCustomChromeTab(context, it)
            })
        }
    }

}

fun openCustomChromeTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}



