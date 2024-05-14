package com.devmark.devmark.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.devmark.devmark.data.utils.LoggerUtils
import com.devmark.devmark.presentation.base.GlobalApplication.Companion.app
import com.devmark.devmark.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "com.devmark.devmark.user_preferences"
)

class UserPreferencesRepositoryImpl: UserPreferencesRepository {
    private val userDataStorePreferences: DataStore<Preferences> = app.applicationContext.userDataStore
    private val tag = "UserPreferences"

    private companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }

    override suspend fun clearData(): Result<Boolean> {
        LoggerUtils.debug("clearData 호출")
        return try {
            userDataStorePreferences.edit { preferences ->
                preferences.clear()
            }
            Result.success(true)
        } catch (e: Exception){
            Result.success(false)
        }
    }

    override suspend fun setAccessToken(accessToken: String) {
        LoggerUtils.debug("setAccessToken 호출")
        userDataStorePreferences.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
        }
    }

    override suspend fun getAccessToken(): Result<String> {
        LoggerUtils.debug("getAccessToken 호출")
        return try {
            val accessToken = userDataStorePreferences.data.map { preferences ->
                preferences[ACCESS_TOKEN_KEY] ?: ""
            }.first()
            Result.success(accessToken)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        LoggerUtils.debug("setRefreshToken 호출")
        userDataStorePreferences.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    override suspend fun getRefreshToken(): Result<String> {
        LoggerUtils.debug("getRefreshToken 호출")
        return try {
            val refreshToken = userDataStorePreferences.data.map { preferences ->
                preferences[REFRESH_TOKEN_KEY] ?: ""
            }.first()
            Result.success(refreshToken)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}