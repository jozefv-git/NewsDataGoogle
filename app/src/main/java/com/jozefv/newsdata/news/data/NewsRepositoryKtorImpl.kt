package com.jozefv.newsdata.news.data

import com.jozefv.newsdata.core.domain.ResultHandler
import com.jozefv.newsdata.news.data.dto.NewsDataDao
import com.jozefv.newsdata.news.data.dto.toNewsDataUi
import com.jozefv.newsdata.news.domain.NewsRepository
import com.jozefv.newsdata.news.domain.NewsDataUi
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.util.network.UnresolvedAddressException
import java.util.ArrayList

class NewsRepositoryKtorImpl(private val httpClient: HttpClient) : NewsRepository {
    private var allNews = mutableListOf<NewsDataDao>()

    // More about query check here: https://newsdata.io/documentation/#http-response-codes
    override suspend fun getNews(refreshNews: Boolean): ResultHandler<NewsDataUi,Int> {
        // try to get
        val response = try {
            // 10 results, language en and cz, contain image and try to return only non duplicate results
            httpClient.get("latest?size=10&language=en,cs&image=1&removeduplicate=1")
        } catch (e: UnresolvedAddressException) {
            // No internet or invalid url
            e.printStackTrace()
            return ResultHandler.Error(404)
        } catch (e: Exception) {
            e.printStackTrace()
            return ResultHandler.Error(0)
        }

        return when (response.status.value) {
            200 -> {
                // Success
                val newsData = response.body<NewsDataDao>()
                val validResults = newsData.results?.filter { it.description != null }
                val validData = newsData.copy(results = validResults?.let { ArrayList(it) })
                if (refreshNews) {
                    allNews.clear()
                    allNews.add(validData)
                } else {
                    allNews.add(validData)
                }
                // We have only one item in the list
                ResultHandler.Success(allNews.first().toNewsDataUi())
            }

            else -> {
                // Some error
                ResultHandler.Error(response.status.value)
            }
        }
    }

    override suspend fun getMoreNews(): ResultHandler<NewsDataUi,Int> {
        // If list is empty return error
        if (allNews.isEmpty()) return ResultHandler.Error(0)
        // Take always page from the latest fetch
        val latestPage = allNews.last().nextPage
        // Try to get
        val response = try {
            httpClient.get("latest?page=${latestPage}&size=10&language=en,cs&image=1&removeduplicate=1")
        } catch (e: UnresolvedAddressException) {
            e.printStackTrace()
            return ResultHandler.Error(404)
        } catch (e: Exception) {
            e.printStackTrace()
            return ResultHandler.Error(0)
        }
        return when (response.status.value) {
            200 -> {
                val newsData = response.body<NewsDataDao>()
                val validResults = newsData.results?.filter { it.description != null }
                val validData = newsData.copy(results = validResults?.let { ArrayList(it) })
                allNews.add(validData)
                val allData = allNews.map { it.toNewsDataUi() }.flatMap { it.results!! }
                ResultHandler.Success(NewsDataUi(allData))
            }

            else -> {
                ResultHandler.Error(response.status.value)
            }
        }
    }
}