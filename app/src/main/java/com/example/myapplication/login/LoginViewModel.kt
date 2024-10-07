package com.example.myapplication.login

import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(mainActivity: ComponentActivity) : ViewModel() {

    // Inicializar FirebaseApp
    private var firebaseApp: FirebaseApp?

    // Inicializar FirebaseAuth
    private var auth: FirebaseAuth

    // Creamos un `Channel` de tipo `Unit` para eventos de navegación
    private val _navigationChannel = Channel<Unit>(Channel.BUFFERED)
    val navigationChannel = _navigationChannel.receiveAsFlow()

    // Creamos un `Channel` de tipo `Unit` para eventos de error
    private val _errorChanner = Channel<Unit>(Channel.BUFFERED)
    val errorChannel = _errorChanner.receiveAsFlow()

    // Login component field states
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _password2 = MutableLiveData<String>()
    val password2: LiveData<String> = _password2

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isRegister = MutableLiveData<Boolean>()
    val isRegister: LiveData<Boolean> = _isRegister

    // Cargamos el susuario
    init {
        firebaseApp = FirebaseApp.initializeApp(mainActivity)
        auth = FirebaseAuth.getInstance()
        _isRegister.value = false

        auth.currentUser?.let {
            viewModelScope.launch {
                _navigationChannel.send(Unit)
            }
        }
    }

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidName(name: String): Boolean = name.length >= 5
    private fun isValidPassword(password: String): Boolean = password.length >= 6
    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    // Register View Funcitons
    fun toggleView() {
        _isRegister.value = true
    }

    fun oRegisterChanged(name: String, email: String, password: String, password2: String) {
        _name.value = name
        _email.value = email
        _password.value = password
        _password2.value = password2

        _loginEnable.value =
            isValidName(name)
                && isValidEmail(email)
                && isValidPassword(password)
                && isValidPassword(password2)
                && password == password2
    }

    // Login and register functions

    fun onLoginSelected() {
        auth.signInWithEmailAndPassword(_email.value!!, _password.value!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModelScope.launch {
                        _navigationChannel.send(Unit)
                    }
                } else {
                    viewModelScope.launch {
                        _errorChanner.send(Unit)
                    }
                }
            }
    }

    fun onRegisterSelected() {
        auth.createUserWithEmailAndPassword(_email.value!!, _password.value!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                } else {
                    viewModelScope.launch {
                        _errorChanner.send(Unit)
                    }
                }
            }
    }
}