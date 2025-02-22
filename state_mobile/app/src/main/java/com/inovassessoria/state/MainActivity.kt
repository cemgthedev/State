package com.inovassessoria.state

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.inovassessoria.state.ui.screens.DashboardScreen
import com.inovassessoria.state.ui.screens.LoginScreen
import com.inovassessoria.state.ui.screens.WelcomeScreen
import com.inovassessoria.state.ui.screens.register.user.LocationFormScreen
import com.inovassessoria.state.ui.screens.register.user.PersonalDataFormScreen
import com.inovassessoria.state.ui.theme.StateTheme
import com.inovassessoria.state.ui.viewmodels.AuthViewModel
import com.inovassessoria.state.ui.viewmodels.UserViewModel
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()
        enableEdgeToEdge()
        setContent {
            StateTheme {
                KoinAndroidContext {
                    val authViewModel = koinViewModel<AuthViewModel>()
                    val userViewModel = koinViewModel<UserViewModel>()
                    val navController = rememberNavController()

                    val authResult by authViewModel.authResult.observeAsState()

                    Scaffold(
                        topBar = {
                            if (authResult?.isSuccess == true) {
                                UserSessionHeader(navController = navController, authViewModel = authViewModel)
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "welcome",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("welcome") {
                                WelcomeScreen(navController = navController)
                            }
                            composable("login") {
                                LoginScreen(navController = navController, authViewModel = authViewModel)
                            }
                            composable("personalDataForm") {
                                PersonalDataFormScreen(navController = navController, userViewModel = userViewModel)
                            }
                            composable("locationForm") {
                                LocationFormScreen(navController = navController, userViewModel = userViewModel)
                            }
                            composable("dashboard") {
                                DashboardScreen(navController = navController, authViewModel = authViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserSessionHeader(navController: NavHostController, authViewModel: AuthViewModel = koinViewModel()) {
    val isOpen = remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = "Secretarias", fontWeight = FontWeight.SemiBold) },
        actions = {
            Box {
                IconButton(onClick = { isOpen.value = !isOpen.value }) {
                    Icon(Icons.Default.AccountCircle, contentDescription = "Usuário")
                }
                DropdownMenu(
                    expanded = isOpen.value,
                    onDismissRequest = { isOpen.value = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Logout") },
                        onClick = {
                            authViewModel.logout()
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true } // Limpa toda a stack de navegação
                            }
                        }
                    )
                }
            }
        }
    )
}