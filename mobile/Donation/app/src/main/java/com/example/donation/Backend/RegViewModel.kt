package com.example.donation.Backend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RegViewModel : ViewModel(){
    fun registerUser(email:String,username : String, password : String,OnResult : (String)->Unit){
        viewModelScope.launch {
            try{
                val response = UserRegistration.authService.registerUser(Registration(username,email,password))
                if(response.success){
                    OnResult("Success : ${response.message}")
                }else{
                    OnResult("Error : ${response.message}")
                }
            }catch (e: Exception){
                OnResult("exception : ${e.message}")
            }
        }
    }


}