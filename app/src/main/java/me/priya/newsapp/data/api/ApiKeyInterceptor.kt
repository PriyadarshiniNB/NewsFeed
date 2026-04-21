package me.priya.newsapp.data.api


import me.priya.newsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        var newUrl = originalRequest.url.newBuilder()
            .addQueryParameter("apiKey", BuildConfig.NEWS_API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder().url(newUrl).build()

        return chain.proceed(newRequest)

    }

}