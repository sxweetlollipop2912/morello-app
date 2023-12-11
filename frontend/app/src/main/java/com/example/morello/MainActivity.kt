package com.example.morello

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.morello.ui.register.RegisterScreen
import com.example.morello.ui.register.RegisterUiState
import com.example.morello.ui.theme.MorelloTheme
import com.example.morello.ui.theme.login.LoginScreen
import com.example.morello.ui.theme.login.LoginUiState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MorelloTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var loginUiState by remember { mutableStateOf(LoginUiState.Empty) }
//                    var registerUiState by remember { mutableStateOf(RegisterUiState.Empty) }
//                    RegisterScreen(
//                        uiState = registerUiState,
//                        onEmailChanged = { registerUiState = registerUiState.copy(email = it) },
//                        onPasswordChanged = {
//                            registerUiState = registerUiState.copy(password = it)
//                        },
//                        onConfirmPasswordChanged = {
//                            registerUiState = registerUiState.copy(confirmPassword = it)
//                        },
//                        onRegisterButtonClicked = { /*TODO*/ },
//                        onShowPasswordChanged = {
//                            registerUiState = registerUiState.copy(showPassword = it)
//                        },
//                        onShowConfirmPasswordChanged = {
//                            registerUiState = registerUiState.copy(showConfirmPassword = it)
//                        },
//                        onNameChanged = { registerUiState = registerUiState.copy(name = it) },
//                        onBack = { /*TODO*/ })
                    LoginScreen(
                        uiState = loginUiState,
                        onEmailChanged = { loginUiState = loginUiState.copy(email = it) },
                        onPasswordChanged = {
                            loginUiState = loginUiState.copy(password = it)
                        },
                        onLoginButtonClicked = { /*TODO*/ },
                        onBack = { /*TODO*/ },
                        onForgotPasswordClicked = { /*TODO*/ },
                        onRegisterClicked = { /*TODO*/ },
                        onRememberMeChanged = { loginUiState = loginUiState.copy(rememberMe = it) },
                        onShowPasswordChanged = {
                            loginUiState = loginUiState.copy(showPassword = it)
                        },
                        onGoogleLoginClicked = { /*TODO*/ },
                        modifier = Modifier.padding(10.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MorelloTheme {
        Greeting("Android")
    }
}