package com.example.donation.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.donation.backend.UserRegistration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    companion object {
        private val Context.dataStore by preferencesDataStore(name = "user_preferences")
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val LOGIN_STATUS = booleanPreferencesKey("loginStatus")
        val USER_NAME = stringPreferencesKey("user_name")
    }

    //status checking of user
    val getStatus : Flow<Boolean?> = context.dataStore.data
        .map { preferences ->
            preferences[LOGIN_STATUS]?:false
        }
    suspend fun saveStatus(value : Boolean){
        context.dataStore.edit { preferences->
            preferences[LOGIN_STATUS] = value
        }
    }

    //get saved access token

    val getAccessToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[ACCESS_TOKEN] ?: ""
        }

    //save access token
    suspend fun saveAccessToken(token : String){
        context.dataStore.edit {preferences ->
            preferences[ACCESS_TOKEN] = token

        }
    }

    //get refresh token
    val getRefreshToken : Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[REFRESH_TOKEN]?: ""

        }

    //save refresh token
    suspend fun SaveRefreshToken(refresh : String){
        context.dataStore.edit { preferences->
            preferences[REFRESH_TOKEN] =refresh


        }
    }


    //user information from UserDetails
    val getUserName : Flow<String?> = context.dataStore.data
        .map { pref ->
            pref[USER_NAME]?: ""


        }
    suspend fun SaveUserName(name : String){
        context.dataStore.edit { pref ->
            pref[USER_NAME] = name


        }
    }

}
