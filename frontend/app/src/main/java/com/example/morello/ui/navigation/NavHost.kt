package com.example.morello.ui.navigation

import AuthorizedHomeRoute
import CreateExpenseRoute
import CreateGroupRoute
import CreateIncomeRoute
import ForgotPasswordCodeRoute
import ForgotPasswordRoute
import GroupOwnerHomeRoute
import HomeRoute
import LoginRoute
import OwnerGroupHomeRoute
import RegisterRoute
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.morello.ui.authorized_home.AuthorizedHomeRoute
import com.example.morello.ui.create_balance_entry.CreateExpenseRoute
import com.example.morello.ui.create_balance_entry.CreateExpenseViewModel
import com.example.morello.ui.create_group.CreateGroupRoute
import com.example.morello.ui.forgot_password.ForgotPasswordCodeValidationScreen
import com.example.morello.ui.forgot_password.ForgotPasswordEmailScreen
import com.example.morello.ui.login.LoginRoute
import com.example.morello.ui.owner_group.OwnerGroupRoute
import com.example.morello.ui.register.RegisterRoute

fun NavGraphBuilder.ownerGroupHomeGraph(
    navController: NavHostController,
) {
    val graphRoute = OwnerGroupHomeRoute

    navigation(
        route = graphRoute.routeWithArgs,
        startDestination = GroupOwnerHomeRoute.routeWithArgs,
        arguments = graphRoute.args
    ) {
        composable(GroupOwnerHomeRoute.routeWithArgs) {
            OwnerGroupRoute(
                viewModel = hiltViewModel(),
                onToNewIncomeEntry = {
                    navController.navigate(CreateIncomeRoute.base)
                },
                onToNewExpenseEntry = {
                    navController.navigate(CreateExpenseRoute.base)
                },
                onToBalanceEntryList = {},
                onToCollectSessionList = {},
                onBack = {
                    navController.popBackStack()
                })
        }
        composable(
            CreateExpenseRoute.routeWithArgs
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(graphRoute.routeWithArgs)
            }
                val groupId = parentEntry.arguments?.getInt(graphRoute.groupId)!!
            CreateExpenseRoute(
                groupId = groupId,
                viewModel = hiltViewModel(),
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(CreateIncomeRoute.routeWithArgs) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(graphRoute.routeWithArgs)
            }
            val groupId = parentEntry.arguments?.getInt(graphRoute.groupId)!!
            // TODO: Replace with CreateIncomeRoute
            CreateExpenseRoute(
                groupId = groupId,
                viewModel = hiltViewModel(),
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

fun NavGraphBuilder.authorizedHomeGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = HomeRoute.routeWithArgs,
        route = AuthorizedHomeRoute.routeWithArgs
    ) {
        ownerGroupHomeGraph(
            navController = navController,
        )
        composable(HomeRoute.routeWithArgs) {
            AuthorizedHomeRoute(
                viewModel = hiltViewModel(),
                onCreateNewGroup = {
                    navController.navigate(CreateGroupRoute.base)
                },
                navigateToGroup = { groupId ->
                    navController.navigate("${OwnerGroupHomeRoute.base}/$groupId")
                }
            )
        }
        composable(CreateGroupRoute.routeWithArgs) {
            CreateGroupRoute(
                viewModel = hiltViewModel(),
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
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        authorizedHomeGraph(
            navController = navController,
        )
        composable(CreateGroupRoute.routeWithArgs) {
            CreateGroupRoute(
                viewModel = hiltViewModel(),
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(LoginRoute.routeWithArgs) {
            LoginRoute(
                viewModel = hiltViewModel(),
                switchToSignIn = {
                    navController.navigate(RegisterRoute.base)
                },
                switchToForgotPassword = {
                    navController.navigate(ForgotPasswordRoute.base)
                },
                onGoogleLoginRequest = {
                    navController.navigate(LoginRoute.base)
                },
                onFacebookLoginRequest = {
                    navController.navigate(LoginRoute.base)
                },
                onLoginSuccess = {
                    navController.navigate(HomeRoute.base)
                },
                modifier = Modifier.padding(10.dp),
            )
        }
        composable(RegisterRoute.routeWithArgs) {
            RegisterRoute(
                viewModel = hiltViewModel(),
                switchToLogin = {
                    navController.navigate(LoginRoute.base)
                },
                modifier = Modifier.padding(10.dp),
            )
        }
        composable(ForgotPasswordRoute.routeWithArgs) {
            ForgotPasswordEmailScreen(
                email = "",
                onEmailChanged = {},
                onEmailSent = {
                    navController.navigate(ForgotPasswordCodeRoute.base)
                },
                onLoginClicked = { /*TODO*/ },
                onBack = { },
                modifier = Modifier.padding(10.dp),
            )
        }
        composable(ForgotPasswordCodeRoute.routeWithArgs) {
            ForgotPasswordCodeValidationScreen(
                email = "ltp@gmail.com",
                onBack = { /*TODO*/ })
        }
    }
}