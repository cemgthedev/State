package com.inovassessoria.state.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.inovassessoria.state.models.TEnterprise
import com.inovassessoria.state.repositories.EnterpriseRepository
import com.inovassessoria.state.ui.viewmodels.AuthViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun DashboardScreen(navController: NavHostController, authViewModel: AuthViewModel = koinViewModel()){
    val isDarkTheme = isSystemInDarkTheme()

    val authResult by authViewModel.authResult.observeAsState()

    val enterpriseRepository = koinInject<EnterpriseRepository>()

    val scope = rememberCoroutineScope()
    val data = remember { mutableStateOf<Result<TEnterprise>?>(null) }

    LaunchedEffect(scope) {
        data.value = enterpriseRepository.search(authViewModel.getToken() ?: "")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 24.dp, bottom = 16.dp, end = 24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column {
            Text(fontWeight = FontWeight.SemiBold,text = "Dashboard")
            authResult?.fold(
                onSuccess = { data -> Text(text = "Dados: ${data}") },
                onFailure = { error -> Text(text = "Erro: ${error.message}") }
            ) ?: Text(text = "Carregando...")
        }
        Text(text = data.toString())
    }
}