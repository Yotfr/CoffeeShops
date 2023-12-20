package ru.yotfr.sevenwindstestapp.data.tokenstorage

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.yotfr.sevenwindstestapp.domain.common.DataState
import ru.yotfr.sevenwindstestapp.domain.model.TokenModel
import ru.yotfr.sevenwindstestapp.domain.tokenstorage.TokenStorage
import javax.inject.Inject

class TokenStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : TokenStorage {
    override suspend fun updateToken(tokenModel: TokenModel): DataState<Unit> {
        return try {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.TOKEN] = tokenModel.token
            }
            DataState.Success(Unit)
        } catch (e: Exception) {
            Log.e("TOKEN_STORAGE", "Error inserting token, eMessage: ${e.message}")
            DataState.Error("Что-то пошло не так...")
        }
    }

    override suspend fun dropToken(): DataState<Unit> {
        return try {
            dataStore.edit { preferences ->
                preferences.remove(PreferencesKeys.TOKEN)
            }
            DataState.Success(Unit)
        } catch (e: Exception) {
            Log.e("TOKEN_STORAGE", "Error drop token, eMessage: ${e.message}")
            DataState.Error("Что-то пошло не так...")
        }
    }

    override suspend fun getToken(): Flow<DataState<TokenModel?>> {
        return try {
            val tokenState = dataStore.data
                .map { preferences ->
                    val token = preferences[PreferencesKeys.TOKEN]
                    DataState.Success(
                        data = token?.let {
                            TokenModel(it)
                        }
                    )
                }
            return tokenState
        } catch (e: Exception) {
            Log.e("TOKEN_STORAGE", "Error get token, eMessage: ${e.message}")
            flow { DataState.Error<DataState<TokenModel?>>("Что-то пошло не так...") }
        }
    }

    private object PreferencesKeys {
        val TOKEN = stringPreferencesKey("TOKEN")
    }
}