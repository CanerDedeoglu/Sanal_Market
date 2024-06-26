package com.canerdedeoglu.afinal

import LoginViewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.navigation.NavController
import androidx.compose.ui.text.input.PasswordVisualTransformation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel(), navController: NavController) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var errorUsername by rememberSaveable { mutableStateOf<String?>(null) }
    var errorPassword by rememberSaveable { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val loginState by viewModel.loginState.observeAsState()

    // Handle login state changes
    loginState?.let { success ->
        if (success) {
            Toast.makeText(context, "Giriş başarılı!", Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.actionToAnasayfa)
        } else {
            Toast.makeText(context, "Giriş başarısız!", Toast.LENGTH_SHORT).show()
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.login)),
        content = {
            val orangeColor = colorResource(id = R.color.button_text)

            val (imageIcon, titleUsername, errorUsernameRef, titlePassword, errorPasswordRef, btnGiris) = createRefs()

            // Kullanıcı adı üstüne resim eklemek için Image komponentini kullanabiliriz
            Image(
                painter = painterResource(id = R.drawable.login__con), // Login.jpg resminin resource ID'si
                contentDescription = null, // Erişilebilirlik için content description
                modifier = Modifier
                    .constrainAs(imageIcon) {
                        top.linkTo(parent.top, margin = 300.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
                    .size(100.dp) // Resmin boyutunu ayarlayabilirsiniz
            )

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", fontStyle = FontStyle.Italic) },
                modifier = Modifier
                    .constrainAs(titleUsername) {
                        top.linkTo(imageIcon.bottom, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = orangeColor,
                    unfocusedIndicatorColor = orangeColor
                )
            )

            errorUsername?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .constrainAs(errorUsernameRef) {
                            top.linkTo(titleUsername.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                )
            }

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", fontStyle = FontStyle.Italic) },
                modifier = Modifier
                    .constrainAs(titlePassword) {
                        top.linkTo(if (errorUsername != null) errorUsernameRef.bottom else titleUsername.bottom, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = orangeColor,
                    unfocusedIndicatorColor = orangeColor
                ),
                visualTransformation = PasswordVisualTransformation() // This line hides the password
            )

            errorPassword?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .constrainAs(errorPasswordRef) {
                            top.linkTo(titlePassword.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                )
            }

            Button(
                onClick = {
                    errorUsername = if (username.trim().isEmpty()) "Kullanıcı adı boş olamaz" else null
                    errorPassword = if (password.trim().isEmpty()) "Şifre boş olamaz" else null

                    if (errorUsername == null && errorPassword == null) {
                        viewModel.login(username, password)
                    }
                },
                modifier = Modifier
                    .constrainAs(btnGiris) {
                        top.linkTo(if (errorPassword != null) errorPasswordRef.bottom else titlePassword.bottom, margin = 24.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2D2D2D)
                )
            ) {
                Text("Giriş", fontSize = 16.sp, color = Color.White)
            }
        }
    )
}
