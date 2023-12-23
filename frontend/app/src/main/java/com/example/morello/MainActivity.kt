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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.morello.ui.create_balance_entry.CreateExpenseScreen
import com.example.morello.ui.create_balance_entry.CreateIncomeScreen
import com.example.morello.ui.forgot_password.ForgotPasswordCodeValidationScreen
import com.example.morello.ui.forgot_password.ForgotPasswordEmailScreen
import com.example.morello.ui.login.LoginRoute
import com.example.morello.ui.login.LoginViewModel
import com.example.morello.ui.register.RegisterRoute
import com.example.morello.ui.register.RegisterViewModel
import com.example.morello.ui.theme.MorelloTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
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
                        startDestination = "login",
                    ) {
                        composable("login") {
                            val viewModel: LoginViewModel by viewModels()
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
                                onLoginSuccess = {
                                    navController.navigate("home")
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
                        composable("home") {
                            Text(text = "Home")
                        }
                        composable("forgotPassword") {
                            ForgotPasswordEmailScreen(
                                email = "",
                                onEmailChanged = {},
                                onEmailSent = {
                                    navController.navigate("forgotPassword/code")
                                },
                                onLoginClicked = { /*TODO*/ },
                                onBack = { },
                                modifier = Modifier.padding(10.dp),
                            )
                        }
                        composable("forgotPassword/code") {
                            ForgotPasswordCodeValidationScreen(
                                email = "ltp@gmail.com",
                                onBack = { /*TODO*/ })
                        }
                        composable("createBalanceEntry/expense") {
                            var dateTime by remember { mutableStateOf(LocalDateTime.now()) }
                            var amount by remember { mutableStateOf(100) }
                            var name by remember { mutableStateOf("") }
                            CreateExpenseScreen(
                                amount = amount,
                                balanceAfter = 1000 - amount,
                                name = name,
                                description = "Nothing",
                                dateTime = dateTime,
                                onAmountChanged = { amount = it },
                                onNameChanged = { name = it },
                                onDescriptionChanged = {},
                                onDateTimeChanged = { dateTime = it },
                                onCreate = {},
                                onBack = {},
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        composable("createBalanceEntry/income") {
                            var dateTime by remember { mutableStateOf(LocalDateTime.now()) }
                            var amount by remember { mutableStateOf(100) }
                            var name by remember { mutableStateOf("") }
                            CreateIncomeScreen(
                                amount = amount,
                                balanceAfter = 1000 - amount,
                                name = name,
                                description = "Nothing",
                                dateTime = dateTime,
                                onBalanceChanged = { amount = it },
                                onNameChanged = { name = it },
                                onDescriptionChanged = {},
                                onDateTimeChanged = { dateTime = it },
                                onCreate = {},
                                onBack = {},
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
