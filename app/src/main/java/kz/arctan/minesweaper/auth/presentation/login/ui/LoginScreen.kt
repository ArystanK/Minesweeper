package kz.arctan.minesweaper.auth.presentation.login.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kz.arctan.minesweaper.auth.presentation.login.LoginEvent
import kz.arctan.minesweaper.auth.presentation.login.LoginViewModel
import kz.arctan.minesweaper.auth.domain.util.LoginUiEvent
import kz.arctan.minesweaper.common.pesentation.BeautifulTextField

@Composable
@Preview
fun LoginScreen() {
    LoginView(viewModel = hiltViewModel())
}

@Composable
fun LoginView(viewModel: LoginViewModel) {
    var showPassword by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        BeautifulTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.email.value,
            onValueChange = { viewModel.onEvent(LoginEvent.EmailChange(it)) },
            placeholder = "Username",
            leadingIconData = Icons.Default.Person to "username"
        )
        BeautifulTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.password.value,
            onValueChange = { viewModel.onEvent(LoginEvent.PasswordChange(it)) },
            placeholder = "Password",
            leadingIconData = Icons.Default.Lock to "password",
            obscureText = !showPassword
        )
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            Text("Hide", color = if (!showPassword) Color.Blue else Color.Black)
            Switch(
                checked = showPassword,
                onCheckedChange = { showPassword = it }
            )
            Text("Show", color = if (showPassword) Color.Blue else Color.Black)
        }
//        Text(
//            modifier = Modifier.clickable(onClick = forgotPasswordButtonClick),
//            text = "Forgot password?",
//            style = TextStyle.Default.copy(color = Color.Blue)
//        )
        when (viewModel.uiEvent.collectAsState(initial = LoginUiEvent.LoginResultInitial).value) {
            LoginUiEvent.LoginResultLoading -> {
                CircularProgressIndicator()
            }
            LoginUiEvent.LoginResultInitial -> {
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    viewModel.onEvent(
                        LoginEvent.Login(
                            viewModel.email.value,
                            viewModel.password.value
                        )
                    )
                }) {
                    Text("Log In")
                }
            }
            is LoginUiEvent.Navigate -> {
                TODO("navigate")
            }
            is LoginUiEvent.ShowSnackBar -> {

            }
        }

        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            viewModel.onEvent(LoginEvent.SignUp)
        }) {
            Text("Sign Up")
        }
    }
}