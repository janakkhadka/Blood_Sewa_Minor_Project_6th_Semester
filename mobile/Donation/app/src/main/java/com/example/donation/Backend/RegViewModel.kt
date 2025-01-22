package com.example.donation.backend

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.donation.DataClasses.SeeBloodRequest
import com.example.donation.backend.login.LoginRequest
import com.example.donation.backend.registration.Registration
import com.example.donation.backend.searchDonor.SearchDonor
import com.example.donation.datastore.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegViewModel(private val dataStore : DataStoreManager) : ViewModel() {
    private val responseMessage = mutableStateOf("")

    //searching donors
    private val _donors = MutableStateFlow<List<SearchDonor>>(emptyList())
    val donors: StateFlow<List<SearchDonor>> = _donors

    //to see blood request
    private val _request = MutableStateFlow<List<SeeBloodRequest>>(emptyList())
    val requests : StateFlow<List<SeeBloodRequest>> = _request

    val bearerToken ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzQ1OTExODQxLCJpYXQiOjE3MzcyNzE4NDEsImp0aSI6Ijk0Mzc4OTQ0NzFmOTQzMmY5MDY0NTAxMTcyMGI1YWQ3IiwidXNlcl9pZCI6Mn0.vEpTb-RklySyDYTszxHxzNjaNMY1DAkvLOtZVI7JwL8"



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

    private fun Searchdonors() {
        viewModelScope.launch {
            try {
                val response = UserRegistration.authService.searchUser("Bearer $bearerToken")
                if (response.isNotEmpty()) {
                    _donors.value = response
                } else {
                    Log.e("SearchDonors", "Empty donor list")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("fetchDonors", "Error: ${e.message}")
            }
        }
    }

    private fun SeeRequests() {
        viewModelScope.launch {
            try {
                val response = UserRegistration.authService.seeBloodRequest("Bearer $bearerToken")
                if (response.isNotEmpty()) {
                    _request.value = response
                } else {
                    Log.e("SeeRequests", "Empty blood request list")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("fetchRequests", "Error: ${e.message}")
            }
        }
    }

}


