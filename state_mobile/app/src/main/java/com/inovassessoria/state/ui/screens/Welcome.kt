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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.inovassessoria.state.R

@Composable
fun WelcomeScreen(navController: NavHostController){
    val isDarkTheme = isSystemInDarkTheme()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val backgroundRes = if (isDarkTheme) {
            R.drawable.welcome_background_dark
        } else {
            R.drawable.welcome_background_light
        }

        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val welcomeLogo = if (isDarkTheme) {
                    R.drawable.welcome_logo_dark
                } else {
                    R.drawable.welcome_logo_light
                }

                Image(
                    painter = painterResource(id = welcomeLogo),
                    contentDescription = null,
                    modifier = Modifier
                        .width(48.dp)
                        .width(48.dp),
                    contentScale = ContentScale.Crop
                )
                Column {
                    Text(fontWeight = FontWeight.SemiBold,text = "State")
                    Text("seu guia de serviços")
                }
            }
            Button(
                onClick = {
                    navController.navigate("login")
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(top = 32.dp),
                shape = RoundedCornerShape(4.dp), // Define o border radius de 4dp
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDarkTheme) Color.White else Color(0xFF6714D6),
                    contentColor = if (isDarkTheme) Color.Black else Color.White
                ),
                elevation = ButtonDefaults.elevatedButtonElevation()
            ) {
                Text("Começar")
            }
        }
    }
}