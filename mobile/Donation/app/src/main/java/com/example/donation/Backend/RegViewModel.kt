package com.example.donation.backend

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.donation.backend.login.LoginRequest
import com.example.donation.backend.registration.Registration
import com.example.donation.backend.searchDonor.SearchDonor
import com.example.donation.datastore.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegViewModel(private val dataStore : DataStoreManager) : ViewModel() {
    private val responseMessage = mutableStateOf("")
    private val _donors = MutableStateFlow<List<SearchDonor>>(emptyList())
    val donors: StateFlow<List<SearchDonor>> = _donors


    fun registerUser(
        name: String,
        phone_number: String,
        blood_group: String,
        district: String,
        province: String,
        email: String,
        password: String,
        DOB : String,
        gender : String,
        onResult: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val request = Registration(name, phone_number , blood_group, district, province, email, password,DOB,gender)
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
                    val accessToken = loginResponse?.access_token ?: ""
                    val refresh_token = loginResponse?.refresh_token?: ""

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

    init {
    Searchdonors()
    }

    private fun Searchdonors(){
        viewModelScope.launch {
            try{
                val bearerToken ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzQ1OTExODQxLCJpYXQiOjE3MzcyNzE4NDEsImp0aSI6Ijk0Mzc4OTQ0NzFmOTQzMmY5MDY0NTAxMTcyMGI1YWQ3IiwidXNlcl9pZCI6Mn0.vEpTb-RklySyDYTszxHxzNjaNMY1DAkvLOtZVI7JwL8"

                val response =UserRegistration.authService.searchUser("Bearer $bearerToken")
                Log.d("FetchDonors", "Response: $response")

            }catch (e:Exception){
                e.printStackTrace()
                Log.e("FetchCriminals", "Error: ${e.message}")

            }

        }


    }
}


