package com.example.morello

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.morello.ui.create_balance_entry.CreateExpenseScreen
import com.example.morello.ui.create_balance_entry.CreateExpenseViewModel
import com.example.morello.ui.create_balance_entry.CreateIncomeScreen
import com.example.morello.ui.forgot_password.ForgotPasswordCodeValidationScreen
import com.example.morello.ui.forgot_password.ForgotPasswordEmailScreen
import com.example.morello.ui.login.LoginRoute
import com.example.morello.ui.login.LoginViewModel
import com.example.morello.ui.navigation.MorelloNavHost
import com.example.morello.ui.register.RegisterRoute
import com.example.morello.ui.register.RegisterViewModel
import com.example.morello.ui.theme.MorelloTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory
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
                    val viewModelProvider = ViewModelProvider(this)
                    MorelloNavHost(
                        navController = navController,
                        startDestination = "login",
                        viewModelProvider = viewModelProvider,
                    )
                }
            }
        }
    }
}
