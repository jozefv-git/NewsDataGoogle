package com.jozefv.newsdata.news.presentation.mappers

import com.jozefv.newsdata.news.domain.NewsDataUi
import com.jozefv.newsdata.news.domain.ResultUi

private fun ResultUi.toResultUiParcelize(): ResultUiParcelize {
    return ResultUiParcelize(
        title = title,
        imageUrl = imageUrl,
        description = description,
        publishedDate = publishedDate,
        sourceUrl = sourceUrl,
        articleLink = articleLink,
        sourceName = sourceName
    )
}
// We need this conversion for adaptive layout detail list navigation
// It needs to be parcelable to support saving and restoring the selected list item
// https://developer.android.com/develop/ui/compose/layouts/adaptive/list-detail
// !!! If we don't use parcelable - There may be crash !!!
// Mapping will be done in VM - so we don't break our layer pattern
fun NewsDataUi.toNewsDataUiParcelize(): NewsDataUiParcelize {
    return NewsDataUiParcelize(
        results = results?.map { it.toResultUiParcelize() }
    )
}