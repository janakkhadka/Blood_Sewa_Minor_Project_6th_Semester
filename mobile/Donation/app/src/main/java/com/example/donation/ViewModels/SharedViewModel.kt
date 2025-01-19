package com.example.donation.ViewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel(private val repository: EventsRepository) : ViewModel(){
    val event : StateFlow<List<dummyEvent>> = repository.events
    val urgentData : StateFlow<List<dummyUrgentData>> = repository.urgentData

    fun addEvents(event : dummyEvent){
        repository.addEvents(event)
    }

    fun addUrgentData(data : dummyUrgentData){
        repository.addUrgentData(data)
    }
}