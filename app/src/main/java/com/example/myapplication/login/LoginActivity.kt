package com.example.myapplication.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.MainActivity
import com.example.myapplication.ui.theme.AppTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel = LoginViewModel(this)

        setContent {

            // Acceder al contexto actual
            val context = LocalContext.current

            // Observar los eventos de navegación
            LaunchedEffect(Unit) {

                // Observamos los eventos de navegación
                viewModel.navigationChannel.collect {
                    // Navegar a una nueva actividad cuando se recibe el evento
                    context.startActivity(Intent(context, MainActivity::class.java))
                    (context as Activity).finish()
                }
            }

            val errorMsgString: String by viewModel.errorMsg.observeAsState(initial = "")

            // Observar los eventos de  error
            LaunchedEffect(Unit) {
                viewModel.errorChannel.collect {
                    // Error message
                    Toast.makeText(context, errorMsgString, Toast.LENGTH_SHORT).show()
                }
            }

            AppTheme {
                when(viewModel.isRegister.observeAsState(initial = "").value) {
                    true -> RegisterView(viewModel)
                    false -> LoginView(viewModel)
                }
            }
        }

    }
}

@Composable
fun LoginView(viewModel: LoginViewModel) {
    // Acceder al contexto actual
    val context = LocalContext.current

    // Codigo de error y su string correspondiente

    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo header
            Spacer(modifier = Modifier.height(64.dp))
            Logo()
            Spacer(modifier = Modifier.height(25.dp))

            // Input fields
            EmailField(email) { viewModel.onLoginChanged(it, password) }
            PasswordField(password, "Password") { viewModel.onLoginChanged(email, it) }

            // Login button
            Spacer(modifier = Modifier.height(25.dp))
            ActionButton("Login", loginEnable) {
                viewModel.onLoginSelected()
            }
            Spacer(modifier = Modifier.height(25.dp))
            TextButton(onClick = { viewModel.toggleView()}
            ) {
                Text("or Register")
            }
        }
    }

}


@Composable
fun RegisterView(viewModel: LoginViewModel) {
    // Acceder al contexto actual
    val context = LocalContext.current

    // Codigo de error y su string correspondiente
    val errorMsgString = stringResource(R.string.signin_error)

    val name: String by viewModel.name.observeAsState(initial = "")
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val password2: String by viewModel.password2.observeAsState(initial = "")
    val registreEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo header
            Spacer(modifier = Modifier.height(64.dp))
            Logo()
            Spacer(modifier = Modifier.height(25.dp))

            // Input fields
            TextField(name) { viewModel.oRegisterChanged(it, email, password, password2) }
            EmailField(email) { viewModel.oRegisterChanged(name, it, password, password2) }
            PasswordField(password, "Password") { viewModel.oRegisterChanged(name, email, it, password2) }
            PasswordField(password2, "Repeat Password") { viewModel.oRegisterChanged(name, email, password, it) }

            // Login button
            Spacer(modifier = Modifier.height(25.dp))
            ActionButton("Register", registreEnable) {
                viewModel.onRegisterSelected()
            }
            Spacer(modifier = Modifier.height(25.dp))
            TextButton(onClick = { viewModel.toggleView()}) {
                Text("back to Login")
            }
        }
    }
}


@Composable
fun TextField(value: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text("Name") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        maxLines = 1,
        modifier = Modifier.width(270.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceDim,
            errorIndicatorColor = MaterialTheme.colorScheme.onError
        )
    )
}

@Composable
fun EmailField(email: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text("Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        modifier = Modifier.width(270.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceDim,
            errorIndicatorColor = MaterialTheme.colorScheme.onError
        )
    )
}

@Composable
fun PasswordField(password: String, label: String,  onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text("Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation() // Hace que el texto se oculte
        ,
        singleLine = true,
        maxLines = 1,
        modifier = Modifier.width(270.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceDim,
            errorIndicatorColor = MaterialTheme.colorScheme.onError
        )
    )
}

@Composable
fun ActionButton(text: String, loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = { onLoginSelected() },
        enabled = loginEnable,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .padding(top = 16.dp)
            .height(56.dp) // Hacer el botón más alto
            .width(270.dp), // Ajustar el botón para que ocupe todo el ancho
        colors = ButtonDefaults.buttonColors(
             containerColor = MaterialTheme.colorScheme.secondaryContainer // Usar el color secundario
            , contentColor = MaterialTheme.colorScheme.onSecondaryContainer // Texto contrastan
            , disabledContainerColor = MaterialTheme.colorScheme.surfaceDim // Usar el color secundario
            , disabledContentColor = MaterialTheme.colorScheme.onSurface // Texto contrastan
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 8.dp, // Definir la elevación del botón
            pressedElevation = 12.dp, // Definir la elevación cuando es presionado
            disabledElevation = 0.dp
        )
    ) {
        Text(
            text, // Cambia el texto si es necesario,
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
