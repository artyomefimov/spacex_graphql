package com.example.rocketreserver.data.token

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class TokenStorageImpl(
    private val context: Context
): TokenStorage {

    private companion object {
        const val KEY_TOKEN = "TOKEN"
    }

    private val preferences: SharedPreferences by lazy {
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun getToken(): String {
        return preferences.getString(KEY_TOKEN, "").orEmpty()
    }

    override fun setToken(token: String) {
        preferences.edit {
            putString(KEY_TOKEN, token)
        }
    }
}