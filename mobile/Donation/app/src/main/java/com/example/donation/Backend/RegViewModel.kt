package com.example.donation.backend

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donation.backend.login.LoginRequest
import com.example.donation.datastore.DataStoreManager
import kotlinx.coroutines.launch

class RegViewModel(private val dataStore : DataStoreManager) : ViewModel() {
    private val responseMessage = mutableStateOf("")


    fun registerUser(
        username: String,
        phoneNumber: String,
        bloodGroup: String,
        district: String,
        province: String,
        email: String,
        password: String,
        onResult: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val request = Registration(username, phoneNumber, bloodGroup, district, province, email, password)
                val response = UserRegistration.authService.registerUser(request)

                if (response.isSuccessful) {
                    val message = response.body()?.message ?: "Registration Successful!"
                    responseMessage.value = message
                    onResult(message)
                } else {
                    val error = response.errorBody()?.string() ?: "Unknown Error"
                    onResult("Error: $error")
                }
            } catch (e: Exception) {
                onResult("Registration Failed: ${e.message}")
            }
        }
    }

    fun loginUser(email: String,password : String,onResult: (String) -> Unit){
        viewModelScope.launch {
            try{
                val request = LoginRequest(email = email,password = password)
                val response = UserRegistration.authService.loginUser(request)
                if(response.isSuccessful){
                    val loginResponse = response.body()
                    val accessToken = loginResponse?.token?.access ?: ""
                    val refresh_token = loginResponse?.token?.refresh?: ""

                    SaveAccessToken(accessToken)
                    SaveRefreshToken(refresh_token)
                }else{
                    val error = response.errorBody()?.string() ?: "Unknown Error"
                    onResult("Login Failed: $error")
                }

            }catch(e: Exception){
                onResult("Login Failed: ${e.message}")
            }
        }
    }
    private  fun SaveAccessToken(token : String){
        viewModelScope.launch {
            dataStore.saveAccessToken(token)
        }

    }

    private fun SaveRefreshToken(refreshToken : String){
        viewModelScope.launch {
            dataStore.SaveRefreshToken(refreshToken)
        }
    }
}


