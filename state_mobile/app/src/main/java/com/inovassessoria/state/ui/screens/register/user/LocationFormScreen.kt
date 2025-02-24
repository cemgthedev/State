package com.inovassessoria.state.ui.screens.register.user

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.inovassessoria.state.models.TEnterprise
import com.inovassessoria.state.repositories.EnterpriseRepository
import com.inovassessoria.state.services.EnterprisesResponse
import com.inovassessoria.state.ui.viewmodels.AuthViewModel
import com.inovassessoria.state.ui.viewmodels.UserViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun LocationFormScreen(navController: NavHostController, authViewModel: AuthViewModel = koinViewModel(), userViewModel: UserViewModel = koinViewModel()) {
    val isDarkTheme = isSystemInDarkTheme()

    // Observando os estados dos LiveData
    val authResult by authViewModel.authResult.observeAsState()
    val city by userViewModel.city.observeAsState("")
    val neighborhood by userViewModel.neighborhood.observeAsState("")
    val street by userViewModel.street.observeAsState("")
    val number by userViewModel.number.observeAsState("")
    val cep by userViewModel.cep.observeAsState("")
    val complement by userViewModel.complement.observeAsState("")

    val enterpriseRepository = koinInject<EnterpriseRepository>()

    val scope = rememberCoroutineScope()
    var data = remember { mutableStateOf<Result<EnterprisesResponse>?>(null) }
    var enterprises by remember { mutableStateOf<List<TEnterprise>>(emptyList()) }

    LaunchedEffect(scope) {
        data.value = enterpriseRepository.search()
        enterprises = data.value?.getOrNull()?.items ?: emptyList()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top=24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                text = "Dados de Localização"
            )

            // Campo de Cidade
            OutlinedTextField(
                value = city,
                onValueChange = userViewModel::setCity,
                label = { Text("Cidade") },
                placeholder = { Text("Informe sua cidade") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6714D6)
                )
            )

            var expanded by remember { mutableStateOf(false) }
            var selectedText by remember { mutableStateOf("Selecione o município") }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { expanded = true },
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = selectedText, modifier = Modifier.padding(top=8.dp, bottom=8.dp))
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.fillMaxWidth(0.8f)) {
                    DropdownMenuItem(
                        text = { Text(text = "Nenhum") },
                        onClick = {
                            selectedText = "Nenhum"
                            expanded = false
                            userViewModel.setEnterpriseId(null)
                        }
                    )
                    enterprises.forEach { enterprise ->
                        DropdownMenuItem(
                            text = { Text(text = enterprise.name) },
                            onClick = {
                                selectedText = enterprise.name
                                expanded = false
                                userViewModel.setEnterpriseId(enterprise.id)
                            }
                        )
                    }
                }
            }

            // Campo de Bairro
            OutlinedTextField(
                value = neighborhood,
                onValueChange = userViewModel::setNeighborhood,
                label = { Text("Bairro") },
                placeholder = { Text("Informe seu bairro") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6714D6)
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ){
                // Campo de Rua
                OutlinedTextField(
                    value = street,
                    onValueChange = userViewModel::setStreet,
                    label = { Text("Rua") },
                    placeholder = { Text("Informe sua rua") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF6714D6)
                    )
                )

                // Campo de Número da Rua
                OutlinedTextField(
                    value = number,
                    onValueChange = userViewModel::setNumber,
                    label = { Text("Número") },
                    placeholder = { Text("N. da rua") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.widthIn(min=100.dp, max=100.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF6714D6)
                    )
                )
            }

            // Campo de CEP
            OutlinedTextField(
                value = cep,
                onValueChange = userViewModel::setCep,
                label = { Text("CEP") },
                placeholder = { Text("Informe seu CEP") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6714D6)
                )
            )

            // Campo de Complemento
            OutlinedTextField(
                value = complement,
                onValueChange = userViewModel::setComplement,
                label = { Text("Complemento") },
                placeholder = { Text("Informe um Complemento") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6714D6)
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ){
                // Botão de Voltar
                Button(
                    onClick = { navController.popBackStack() },
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDarkTheme) Color.White else Color(0xFF6714D6),
                        contentColor = if (isDarkTheme) Color.Black else Color.White
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation()
                ) {
                    Text("Voltar")
                }
                // Botão de Cadastro
                Button(
                    onClick = {
                        userViewModel.userRegister()
                        authViewModel.login(userViewModel.getEmail(), userViewModel.getPassword())
                              },
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation()
                ) {
                    Text("Cadastrar")
                }
            }
        }
    }

    LaunchedEffect(authResult) {
        authResult?.fold(
            onSuccess = {
                navController.navigate("dashboard")
            },
            onFailure = {
                navController.navigate("login")
                authViewModel.clear()
            }
        )
    }
}