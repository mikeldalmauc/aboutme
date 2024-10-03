package com.example.myapplication.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.gallery.sampleArtworks

class HomeViewModel : ViewModel() {

    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> = _count

    fun upCount() {
        _count.value = (_count.value ?: 0) + 1
    }

    fun downCount() {
        _count.value = (_count.value ?: 0) - 1
    }

}