package com.inovassessoria.state.ui.screens.register.user

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.inovassessoria.state.ui.viewmodels.UserViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PersonalDataFormScreen(navController: NavHostController, userViewModel: UserViewModel = koinViewModel()) {
    val isDarkTheme = isSystemInDarkTheme()

    val snackbarHostState = remember { SnackbarHostState() }

    // Observando os estados dos LiveData
    var avatarUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            avatarUri = it
            userViewModel.setAvatarUrl(it.toString()) // Atualiza o ViewModel
        }
    }
    val name by userViewModel.name.observeAsState("")
    val document by userViewModel.document.observeAsState("")
    val phone by userViewModel.phone.observeAsState("")
    val email by userViewModel.email.observeAsState("")
    val password by userViewModel.password.observeAsState("")

    var nameError by remember { mutableStateOf(false) }
    var documentError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    val isFormValid = name.isNotBlank() && document.isNotBlank() && email.isNotBlank() && password.isNotBlank()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                text = "Dados Pessoais"
            )

            // Avatar com opção de clicar para selecionar imagem
            /*
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .clickable { imagePickerLauncher.launch("image/*") }
            ) {
                if (avatarUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(avatarUri),
                        contentDescription = "Avatar",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Ícone de usuário",
                        modifier = Modifier.size(116.dp)
                    )
                }
            }

            // Botão para adicionar ou remover imagem
            Button(
                onClick = {
                    if (avatarUri == null) {
                        imagePickerLauncher.launch("image/*")
                    } else {
                        avatarUri = null
                        userViewModel.setAvatarUrl("") // Remove a URL do ViewModel
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (avatarUri == null) Color(0xFF6714D6) else Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text(if (avatarUri == null) "Adicionar imagem" else "Remover imagem")
            }
            */
             */
             */

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Campo de Nome
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        userViewModel.setName(it)
                        nameError = it.isBlank()
                    },
                    label = { Text("Nome") },
                    placeholder = { Text("Informe seu nome") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF6714D6)
                    )
                )
                if (nameError) {
                    Text(
                        text = "O nome é obrigatório.",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.fillMaxWidth().padding(0.dp)
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Campo de Documento
                OutlinedTextField(
                    value = document,
                    onValueChange = userViewModel::setDocument,
                    label = { Text("CPF/CNPJ") },
                    placeholder = { Text("Informe seu cpf/cnpj") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF6714D6)
                    )
                )
                if (documentError) {
                    Text(
                        text = "O documento é obrigatório.",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.fillMaxWidth().padding(0.dp)
                    )
                }
            }

            // Campo de Telefone
            OutlinedTextField(
                value = phone,
                onValueChange = userViewModel::setPhone,
                label = { Text("Telefone") },
                placeholder = { Text("Informe seu número de telefone") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6714D6)
                )
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Campo de E-mail
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        userViewModel.setEmail(it)
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
                        userViewModel.setPassword(it)
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Botão de Cancelar
                Button(
                    onClick = { navController.popBackStack() },
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation()
                ) {
                    Text("Cancelar")
                }
                // Botão de Próximo
                Button(
                    onClick = {
                        if (name.isBlank()) nameError = true
                        if (document.isBlank()) documentError = true
                        if (email.isBlank()) emailError = true
                        if (password.isBlank()) passwordError = true
                        if (isFormValid) navController.navigate("locationForm")
                    },
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDarkTheme) Color.White else Color(0xFF6714D6),
                        contentColor = if (isDarkTheme) Color.Black else Color.White
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation()
                ) {
                    Text("Próximo")
                }
            }
        }
    }
}