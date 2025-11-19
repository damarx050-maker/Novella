package com.novella.app.ui

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.novella.app.R
import com.novella.app.theme.NovellaTheme
import com.novella.app.utils.LocalLanguageCode
import com.novella.app.utils.LocalizationUtils
import com.novella.app.viewmodel.AuthViewModel
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import com.novella.app.ui.auth.LoginScreen
import com.novella.app.ui.onboarding.OnboardingScreen
import com.novella.app.ui.home.HomeScreen
import com.novella.app.ui.details.DetailsScreen
import com.novella.app.ui.reader.ReaderScreen
import com.novella.app.ui.library.LibraryScreen
import com.novella.app.ui.search.SearchScreen
import com.novella.app.ui.subscription.SubscriptionScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovellaApp() {
    val navController = rememberNavController()
    val (lang, setLang) = remember { mutableStateOf(LocalizationUtils.EN) }
    val isArabic = lang == LocalizationUtils.AR
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("novella_prefs", 0) }
    val startDestination = remember {
        val loggedIn = prefs.getBoolean("isLoggedIn", false)
        val onboardingShown = prefs.getBoolean("onboarding_shown", false)
        when {
            !loggedIn -> "login"
            !onboardingShown -> "onboarding"
            else -> "home"
        }
    }

    NovellaTheme(isArabic = isArabic) {
        CompositionLocalProvider(LocalLanguageCode provides lang, LocalLayoutDirection provides if (isArabic) LayoutDirection.Rtl else LayoutDirection.Ltr) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = stringResource(R.string.app_name)) },
                        actions = {
                            IconButton(onClick = { setLang(if (isArabic) LocalizationUtils.EN else LocalizationUtils.AR) }) {
                                Text(if (isArabic) "EN" else "AR")
                            }
                        }
                    )
                }
            ) { padding ->
                NavHost(navController = navController, startDestination = startDestination) {
                    composable("login") { LoginScreen(onLoggedIn = {
                        val shouldShowOnboarding = !prefs.getBoolean("onboarding_shown", false)
                        if (shouldShowOnboarding) {
                            navController.navigate("onboarding") { popUpTo("login") { inclusive = true } }
                        } else {
                            navController.navigate("home") { popUpTo("login") { inclusive = true } }
                        }
                    }) }
                    composable("onboarding") { OnboardingScreen(onDone = {
                        prefs.edit().putBoolean("onboarding_shown", true).apply()
                        navController.navigate("home") { popUpTo("onboarding") { inclusive = true } }
                    }) }
                    composable("home") { HomeScreen(onOpenDetails = { id -> navController.navigate("details/$id") }, onOpenSearch = { navController.navigate("search") }) }
                    composable(
                        route = "details/{novelId}",
                        arguments = listOf(navArgument("novelId") { type = NavType.StringType })
                    ) { backStack ->
                        val id = backStack.arguments?.getString("novelId").orEmpty()
                        DetailsScreen(
                            novelId = id,
                            onRead = { navController.navigate("reader/$id") },
                            onOpenSubscription = { navController.navigate("subscription") }
                        )
                    }
                    composable(
                        route = "reader/{novelId}",
                        arguments = listOf(navArgument("novelId") { type = NavType.StringType })
                    ) { backStack ->
                        val id = backStack.arguments?.getString("novelId").orEmpty()
                        ReaderScreen(novelId = id)
                    }
                    composable("library") { LibraryScreen() }
                    composable("search") { SearchScreen(onOpenDetails = { id -> navController.navigate("details/$id") }) }
                    composable("subscription") { SubscriptionScreen() }
                }
            }
        }
    }
}
