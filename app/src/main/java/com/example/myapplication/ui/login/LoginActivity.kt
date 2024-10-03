package com.example.myapplication.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.ui.theme.AppTheme
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                LoginView(LoginViewModel())
            }
        }
    }
}

@Composable
fun LoginView(viewModel: LoginViewModel) {
    val context = LocalContext.current

    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {

        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo header
                Spacer(modifier = Modifier.height(64.dp))
                Logo()
                Spacer(modifier = Modifier.height(40.dp))

                // Input fields
                EmailField(email) {viewModel.onLoginChanged(it, password)}
                PasswordField(password) {viewModel.onLoginChanged(email, it)}

                // Login button
                Spacer(modifier = Modifier.height(25.dp))
                    LoginButton(loginEnable) {
                        coroutineScope.launch {
                            viewModel.onLoginSelected()
                        }
                        // Navegar a MainActivity después del login
                        context.startActivity(Intent(context, MainActivity::class.java))
                        (context as Activity).finish()
                    }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(email: String, onTextFieldChanged: (String) -> Unit){
    OutlinedTextField(
        value = email
        , onValueChange = { onTextFieldChanged(it) }
        , label = { Text("Email") }
        , keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        , singleLine = true
        , maxLines = 1
        , modifier = Modifier.width(270.dp)
        , colors =  TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceDim,
            errorIndicatorColor = MaterialTheme.colorScheme.onError
        )
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit){
    OutlinedTextField(
        value = password
        , onValueChange = { onTextFieldChanged(it) }
        , label = { Text("Password") }
        , keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        , visualTransformation = PasswordVisualTransformation() // Hace que el texto se oculte
        , singleLine = true
        , maxLines = 1
        , modifier = Modifier.width(270.dp)
        , colors =  TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceDim,
            errorIndicatorColor = MaterialTheme.colorScheme.onError
        )
    )
}

@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick =  { onLoginSelected() },
        enabled = loginEnable,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .padding(top = 16.dp)
            .height(56.dp) // Hacer el botón más alto
            .width(270.dp), // Ajustar el botón para que ocupe todo el ancho
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer // Usar el color secundario
            , contentColor = MaterialTheme.colorScheme.onSecondaryContainer // Texto contrastante
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 8.dp, // Definir la elevación del botón
            pressedElevation = 12.dp, // Definir la elevación cuando es presionado
            disabledElevation = 0.dp
        )
    ) {
        Text(
            "Login", // Cambia el texto si es necesario,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.headlineLarge, // Usar estilo de texto del tema
            color = MaterialTheme.colorScheme.onSecondary // Usar color de texto del tema
        )
    }

}

@Composable
fun Logo() {
    Box(
        modifier = Modifier
            .size(220.dp)
            .clip(CircleShape)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.favicon_mikel),
            contentDescription = "Personal Profile Picture",
            colorFilter = ColorFilter.lighting(Color.White, Color.White)
        )
    }
}