package com.devmark.devmark.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
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

    private companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        private val CURRENT_WORKSPACE = intPreferencesKey("current_workspace")
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

    override suspend fun setCurrentWorkspace(workspaceId: Int) {
        LoggerUtils.debug("setCurrentWorkspace 호출")
        userDataStorePreferences.edit { preferences ->
            preferences[CURRENT_WORKSPACE] = workspaceId
        }
    }

    override suspend fun getCurrentWorkspace(): Result<Int> {
        LoggerUtils.debug("getCurrentWorkspace 호출")
        return try {
            val currentWorkspaceId = userDataStorePreferences.data.map { preferences ->
                preferences[CURRENT_WORKSPACE] ?: -1
            }.first()
            Result.success(currentWorkspaceId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}