package com.example.myapplication.ui.componentes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContadorViewModel : ViewModel() {

    // LiveData para el contador
    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> = _count

    fun upCount() {
        _count.value = (_count.value ?: 0) + 1
    }

    fun downCount() {
        _count.value = (_count.value ?: 0) - 1
    }

    init {
        _count.value = 0
    }
}