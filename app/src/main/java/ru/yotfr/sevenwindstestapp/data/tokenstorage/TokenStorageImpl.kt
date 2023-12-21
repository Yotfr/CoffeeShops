package ru.yotfr.sevenwindstestapp.data.tokenstorage

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.yotfr.sevenwindstestapp.domain.model.DataState
import ru.yotfr.sevenwindstestapp.domain.model.ErrorCause
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
            DataState.Error(
                null,
                ErrorCause.Unknown
            )
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
            DataState.Error(
                null,
                ErrorCause.Unknown
            )
        }
    }

    override suspend fun getToken(): DataState<TokenModel?> {
        return try {
            val token = dataStore.data
                .map { preferences ->
                    preferences[PreferencesKeys.TOKEN]
                }.first()
            return DataState.Success(
                data = token?.let {
                    TokenModel(
                        token = it
                    )
                }
            )
        } catch (e: Exception) {
            Log.e("TOKEN_STORAGE", "Error get token, eMessage: ${e.message}")
            DataState.Error(
                null,
                ErrorCause.Unknown
            )
        }
    }

    private object PreferencesKeys {
        val TOKEN = stringPreferencesKey("TOKEN")
    }
}