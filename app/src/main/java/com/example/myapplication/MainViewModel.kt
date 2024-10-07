package com.example.myapplication

import androidx.activity.ComponentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(mainActivity: ComponentActivity) : ViewModel() {

    sealed class NavigationTarget {
        object Main : NavigationTarget()
        object Login : NavigationTarget()
    }

    // Inicializar FirebaseApp
    private var firebaseApp: FirebaseApp?

    // Inicializar FirebaseAuth
    private var auth: FirebaseAuth

    // Para gestionar la navegación
    private val _navigationChannel = Channel<Unit>(Channel.BUFFERED)
    val navigationChannel = _navigationChannel.receiveAsFlow()

    private val _navigationTarget = MutableLiveData<NavigationTarget>(NavigationTarget.Main)
    val navigationTarget: LiveData<NavigationTarget> = _navigationTarget

    // Loggeado
    private val _loggedIn = MutableLiveData<Boolean>()
    val loggedIn: LiveData<Boolean> = _loggedIn

    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user: StateFlow<FirebaseUser?> = _user

    private fun setUser(user: FirebaseUser) {
        _user.value = user
        _loggedIn.value = true
    }

    fun logout() {
        _user.value = null
        _loggedIn.value = false
        auth.signOut()
        _navigationTarget.value = NavigationTarget.Login

        viewModelScope.launch {
            _navigationChannel.send(Unit)
        }
    }

    init {
        firebaseApp = FirebaseApp.initializeApp(mainActivity)
        auth = FirebaseAuth.getInstance()

        when (auth.currentUser) {
            null -> {
                _loggedIn.value = false
                logout()
            }

            else -> {
                setUser(auth.currentUser!!)
            }
        }
    }

}