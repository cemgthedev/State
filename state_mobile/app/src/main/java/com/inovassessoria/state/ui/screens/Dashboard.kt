package com.inovassessoria.state.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.inovassessoria.state.R
import com.inovassessoria.state.models.TInstitution
import com.inovassessoria.state.repositories.InstitutionRepository
import com.inovassessoria.state.services.InstitutionResponse
import com.inovassessoria.state.ui.viewmodels.AuthViewModel
import org.koin.compose.koinInject

@Composable
fun DashboardScreen(navController: NavHostController, authViewModel: AuthViewModel){
    val institutionRepository = koinInject<InstitutionRepository>()

    val scope = rememberCoroutineScope()
    var data = remember { mutableStateOf<Result<InstitutionResponse>?>(null) }
    var institutions by remember { mutableStateOf<List<TInstitution>>(emptyList()) }

    LaunchedEffect(scope) {
        data.value = institutionRepository.search(authViewModel.getToken() ?: "")
        institutions = data.value?.getOrNull()?.items ?: emptyList()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 24.dp, bottom = 16.dp, end = 24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column {
            if (institutions.isEmpty()) {
                Text("Nenhuma secretaria encontrada")
            } else {
                institutions.forEach { institution ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(2.dp, Color(0xFF6714D6))
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.domain),
                                contentDescription = "Institution Avatar",
                                modifier = Modifier.size(40.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    text = institution.setting?.name ?: institution.name,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = ("Telefone: " + institution.setting?.phoneWsp)
                                        ?: ("Telefone: " + institution.phone),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}