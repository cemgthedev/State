package com.inovassessoria.state.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.inovassessoria.state.ui.viewmodels.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(navController: NavHostController, authViewModel: AuthViewModel = koinViewModel()) {
    val isDarkTheme = isSystemInDarkTheme()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authResult by authViewModel.authResult.observeAsState()
    val message by authViewModel.message.observeAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    val isFormValid = email.isNotBlank() && password.isNotBlank()

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        snackbarHost = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top=32.dp, start = 24.dp, end = 24.dp),
                contentAlignment = Alignment.TopCenter // Alinha o Snackbar no topo
            ) {
                SnackbarHost(snackbarHostState) { snackbarData ->
                    Snackbar(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = if (message?.type == "warning") Color.Red else Color.Blue,
                        contentColor = Color.White,
                        shape = RoundedCornerShape(8.dp),
                        action = {
                            snackbarData.visuals.actionLabel?.let { actionLabel ->
                                TextButton(onClick = { snackbarData.dismiss() }) {
                                    Text(actionLabel, color = Color.White)
                                }
                            }
                        }
                    ) {
                        Text(
                            text = snackbarData.visuals.message,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(fontWeight = FontWeight.SemiBold, text = "Login")
                    Text("Seja Bem-Vindo!")
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Campo de E-mail
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = it.isBlank()
                                        },
                        isError = emailError,
                        label = { Text("Email") },
                        placeholder = { Text("Informe seu email") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6714D6)
                        )
                    )
                    if (emailError) {
                        Text(
                            text = "O e-mail é obrigatório.",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.fillMaxWidth().padding(0.dp)
                        )
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Campo de Senha
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = it.isBlank()
                                        },
                        isError = passwordError,
                        label = { Text("Senha") },
                        placeholder = { Text("Informe sua senha") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF6714D6)
                        )
                    )
                    if (passwordError) {
                        Text(
                            text = "A senha é obrigatória.",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.fillMaxWidth().padding(0.dp)
                        )
                    }
                }

                // Botão de Entrar
                Button(
                    onClick = {
                        if (email.isBlank()) emailError = true
                        if (password.isBlank()) passwordError = true
                        if (isFormValid) authViewModel.login(email, password)
                    },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDarkTheme) Color.White else Color(0xFF6714D6),
                        contentColor = if (isDarkTheme) Color.Black else Color.White
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation()
                ) {
                    Text("Entrar")
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text("Ainda não possui uma conta ?")
                    Text(
                        text = "Cadastrar-se agora",
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable { navController.navigate("personalDataForm") }
                    )
                }
            }
        }

        LaunchedEffect(authResult) {
            authResult?.fold(
                onSuccess = {
                    navController.navigate("dashboard")
                },
                onFailure = {
                    snackbarHostState.showSnackbar(message?.text ?: "")
                    authViewModel.clear()
                }
            )
        }
    }
}