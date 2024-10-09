package com.jozefv.newsdata.news.data

import com.jozefv.newsdata.news.data.dto.NewsDataDao
import com.jozefv.newsdata.news.domain.NewsDataUi
import com.jozefv.newsdata.news.domain.ResultUi

fun NewsDataDao.toNewsDataUi(): NewsDataUi {
    return NewsDataUi(
        results = results?.map {
            ResultUi(
                title = it.title ?: "Title wasn't provided",
                description = it.description ?: "Description wasn't provided",
                imageUrl = it.image_url,
                sourceName = it.source_name ?: "Unknown",
                sourceUrl = it.source_url ?: "Source wasn't provided",
                publishedDate = it.pubDate?.let { timeConverter(it) }
                    ?: "Publish date wasn't provided",
                articleLink = it.link!! // Link cannot be null as long as article exists
            )
        }
    )
}