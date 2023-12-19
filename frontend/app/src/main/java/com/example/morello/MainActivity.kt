package com.example.morello

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.morello.ui.login.LoginRoute
import com.example.morello.ui.register.RegisterRoute
import com.example.morello.ui.register.RegisterViewModel
import com.example.morello.ui.theme.MorelloTheme
import com.example.morello.ui.theme.login.LoginViewModel

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
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "register",
                    ) {
                        composable("login") {
                            val viewModel: LoginViewModel by viewModels { LoginViewModel.Factory }
                            LoginRoute(
                                viewModel = viewModel,
                                switchToSignIn = {
                                    navController.navigate("register")
                                },
                                switchToForgotPassword = {
                                    navController.navigate("forgotPassword")
                                },
                                onGoogleLoginRequest = {
                                    navController.navigate("login")
                                },
                                onFacebookLoginRequest = {
                                    navController.navigate("login")
                                },
                                modifier = Modifier.padding(10.dp),
                            )
                        }
                        composable("register") {
                            val viewModel: RegisterViewModel by viewModels()
                            RegisterRoute(
                                viewModel = viewModel,
                                switchToLogin = {
                                    navController.navigate("login")
                                },
                                modifier = Modifier.padding(10.dp),
                            )
                        }
                    }
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