package com.jozefv.newsdata.core.presentation

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText {
    data class DynamicString(val value: String) : UiText

    class StringResource(
        @StringRes val id: Int
    ) : UiText

    @Composable
    fun asComposeString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(id = id)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id)
        }
    }
}