package com.example.morello.ui.navigation

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.morello.MorelloApp
import com.example.morello.ui.create_balance_entry.CreateExpenseRoute
import com.example.morello.ui.create_balance_entry.CreateExpenseScreen
import com.example.morello.ui.create_balance_entry.CreateExpenseViewModel
import com.example.morello.ui.forgot_password.ForgotPasswordCodeValidationScreen
import com.example.morello.ui.forgot_password.ForgotPasswordEmailScreen
import com.example.morello.ui.login.LoginRoute
import com.example.morello.ui.login.LoginViewModel
import com.example.morello.ui.owner_group.OwnerGroupRoute
import com.example.morello.ui.owner_group.OwnerGroupViewModel
import com.example.morello.ui.register.RegisterRoute
import com.example.morello.ui.register.RegisterViewModel
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory
import dagger.hilt.android.internal.lifecycle.HiltViewModelMap

fun NavGraphBuilder.ownerGroupHomeGraph(
    groupId: Int,
    viewModelProvider: ViewModelProvider,
    navController: NavHostController,
) {
    navigation(
        startDestination = "groupOwnerHome",
        route = "home",
    ) {
        composable("groupOwnerHome") {
            OwnerGroupRoute(
                groupId = groupId,
                viewModel = viewModelProvider[OwnerGroupViewModel::class.java],
                onAddNewIncomeEntry = {
                    navController.navigate("createBalanceEntry/income")
                },
                onAddNewExpenseEntry = {
                    navController.navigate("createBalanceEntry/expense")
                },
                onAddNewMember = {
                    navController.navigate("addMember")
                },
                onBack = {
                    navController.popBackStack()
                })
        }
        composable("createBalanceEntry/expense") {
            CreateExpenseRoute(
                groupId = groupId,
                viewModel = viewModelProvider[CreateExpenseViewModel::class.java],
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("createBalanceEntry/income") {
            CreateExpenseRoute(
                groupId = groupId,
                viewModel = viewModelProvider[CreateExpenseViewModel::class.java],
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}


@Composable
fun MorelloNavHost(
    navController: NavHostController,
    startDestination: String,
    viewModelProvider: ViewModelProvider,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable("login") {
            LoginRoute(
                viewModel = viewModelProvider[LoginViewModel::class.java],
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
            RegisterRoute(
                viewModel = viewModelProvider[RegisterViewModel::class.java],
                switchToLogin = {
                    navController.navigate("login")
                },
                modifier = Modifier.padding(10.dp),
            )
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
        ownerGroupHomeGraph(
            groupId = 1,
            viewModelProvider = viewModelProvider,
            navController = navController,
        )
    }
}