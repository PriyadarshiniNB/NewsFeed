package me.priya.newsapp.ui.navigation

import android.R.attr.onClick
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import me.priya.newsapp.ui.offlinearticle.OfflineArticleScreenMain
import me.priya.newsapp.ui.screens.CountryAndLanguageListScreen.CountryScreen
import me.priya.newsapp.ui.screens.CountryAndLanguageListScreen.LanguageListScreen
import me.priya.newsapp.ui.screens.LanguageScreen.LanguageScreen
import me.priya.newsapp.ui.screens.MainScreen.MainScreen
import me.priya.newsapp.ui.screens.NewsBySourceScreen.NewsBySourceScreen
import me.priya.newsapp.ui.screens.NewsListScreen.NewsListScreen
import me.priya.newsapp.ui.screens.SearchScreen.SearchScreen
import me.priya.newsapp.ui.screens.SourceScreen.SourceScreen
import me.priya.newsapp.ui.screens.TopHeadlines.TopHeadLineScreen

@Composable
fun AppNavGraph(){

    var navController = rememberNavController()

    val context = LocalContext.current

    fun openCustomTab(url: String) {
        val intent = CustomTabsIntent.Builder().build()
        intent.launchUrl(context, Uri.parse(url))
    }

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main"){
            MainScreen { route ->
                navController.navigate(route)

            }
        }

        composable("countriesList") {
            CountryScreen {
                navController.navigate("news?country=$it")
            }
        }

        composable("languageList") {
            LanguageListScreen {
                navController.navigate("news?language=$it")
            }
        }

        composable("top_headlines") {
            TopHeadLineScreen(
                onArticleClick = { url ->
                    openCustomTab(url)
                }
            )
        }

        composable("sources") {
            SourceScreen(
                onSourceClick = { sourceId ->
                    navController.navigate("news_by_source/$sourceId")
                }
            )
        }

        composable(
            route = "news_by_source/{source}",
            arguments = listOf(
                navArgument("source") { type = NavType.StringType }
            )
        ) {
            NewsBySourceScreen(
                onArticleClick = { url -> openCustomTab(url) }
            )
        }

        composable("search") {
            SearchScreen(
                onClick = { url -> openCustomTab(url) }
            )
        }

//        composable("language") {
//            LanguageScreen(
//                onClick = { url -> openCustomTab(url) }
//            )
//        }

        composable("offline") {
            OfflineArticleScreenMain (
                onNewsClick = { url ->  openCustomTab(url)}
            )
        }

        composable(
            route = "news?country={country}&language={language}",
            arguments = listOf(
                navArgument("country") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument("language") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            NewsListScreen(
                onClick = { openCustomTab(it) }
            )
        }


    }
}

