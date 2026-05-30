package com.example.sampleapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sampleapp.ui.AppViewModel
import com.example.sampleapp.ui.screens.auth.AuthScreen
import com.example.sampleapp.ui.screens.dashboard.DashboardScreen
import com.example.sampleapp.ui.screens.setup.SetupScreen
import com.example.sampleapp.ui.screens.splash.SplashScreen
import com.example.sampleapp.ui.screens.terms.TermsScreen

@Composable
fun AppNavHost(appViewModel: AppViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            SplashScreen(
                onFinished = {
                    navController.navigate(Routes.AUTH) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.AUTH) {
            AuthScreen(
                viewModel = appViewModel,
                onAuthenticated = { onboarded ->
                    if (onboarded) {
                        // Returning / demo accounts already accepted terms and set up.
                        navController.navigate(Routes.DASHBOARD) {
                            popUpTo(Routes.AUTH) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Routes.TERMS)
                    }
                }
            )
        }

        composable(Routes.TERMS) {
            TermsScreen(
                viewModel = appViewModel,
                onAccepted = {
                    navController.navigate(Routes.SETUP) {
                        popUpTo(Routes.TERMS) { inclusive = true }
                    }
                },
                onDeclined = {
                    appViewModel.logout()
                    navController.navigate(Routes.AUTH) {
                        popUpTo(Routes.AUTH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.SETUP) {
            SetupScreen(
                viewModel = appViewModel,
                onContinue = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.SETUP) { inclusive = true }
                    }
                },
                onSkip = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.SETUP) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.DASHBOARD) {
            DashboardScreen(
                viewModel = appViewModel,
                onLogout = {
                    appViewModel.logout()
                    navController.navigate(Routes.AUTH) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
