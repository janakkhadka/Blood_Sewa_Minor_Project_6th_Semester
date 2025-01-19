package com.example.donation.ViewModels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Thread.State

class EventsRepository {
    //created events ko lagi
    private val _events = MutableStateFlow<List<dummyEvent>>(emptyList())
    val events : StateFlow<List<dummyEvent>> get() = _events

    //urgent data ko lagi
    private val _urgentData = MutableStateFlow<List<dummyUrgentData>>(emptyList())
    val urgentData : StateFlow<List<dummyUrgentData>> get() = _urgentData


    fun addEvents(event: dummyEvent){
        _events.value += event
    }

    fun addUrgentData(urgentData: dummyUrgentData){
        _urgentData.value += urgentData
    }
}