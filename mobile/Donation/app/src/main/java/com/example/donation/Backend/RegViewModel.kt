package com.example.donation.backend

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RegViewModel : ViewModel() {
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
}
