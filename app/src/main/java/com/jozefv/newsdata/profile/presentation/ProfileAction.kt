package com.jozefv.newsdata.profile.presentation

import androidx.credentials.Credential

interface ProfileAction {
    data class OnLogin(val credential: Credential) : ProfileAction
    data object OnLogout : ProfileAction
}