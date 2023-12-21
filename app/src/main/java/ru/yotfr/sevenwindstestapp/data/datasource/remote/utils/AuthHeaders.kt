package ru.yotfr.sevenwindstestapp.data.datasource.remote.utils

class AuthHeaders : HashMap<String,String>()
class AuthHeaderProvider {
    fun getAuthHeaders(accessToken: String): AuthHeaders =
        AuthHeaders().apply {
            put(AUTH_HEADER, getBearer(accessToken))
        }

    companion object {
        private const val AUTH_HEADER = "AUTHORIZATION"
        private fun getBearer(accessToken: String) = "Bearer $accessToken"
    }
}