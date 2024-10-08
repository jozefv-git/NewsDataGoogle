package com.jozefv.newsdata.core.data

import android.content.SharedPreferences
import com.jozefv.newsdata.core.domain.AuthUser
import com.jozefv.newsdata.core.domain.SessionStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EncryptedSharedPrefSessionStorage(private val sharedPreferences: SharedPreferences) :
    SessionStorage {
    override suspend fun getUser(): AuthUser? {
        return withContext(Dispatchers.IO) {
            val jsonUser = sharedPreferences.getString(SHARED_USER_KEY, null)
            jsonUser?.let {
                Json.decodeFromString<AuthUserSerialized>(it).toAuthUser()
            }
        }
    }

    override suspend fun setUser(user: AuthUser?) {
        withContext(Dispatchers.IO) {
            if (user == null) {
                sharedPreferences
                    .edit()
                    .remove(SHARED_USER_KEY)
                    .commit()
                return@withContext
            }
            val jsonUser = Json.encodeToString(user.toAuthSerialized())
            sharedPreferences
                .edit()
                .putString(SHARED_USER_KEY, jsonUser)
                .commit()
        }
    }

    companion object {
        private const val SHARED_USER_KEY = "SHARED_USER_KEY"
    }
}