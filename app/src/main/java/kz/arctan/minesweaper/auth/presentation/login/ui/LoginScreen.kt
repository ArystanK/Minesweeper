package kz.arctan.minesweaper.auth.presentation.login.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collect
import kz.arctan.minesweaper.auth.presentation.login.LoginEvent
import kz.arctan.minesweaper.auth.presentation.login.LoginUiEvent
import kz.arctan.minesweaper.auth.presentation.login.LoginViewModel
import kz.arctan.minesweaper.common.domain.util.Routes
import kz.arctan.minesweaper.common.pesentation.BeautifulTextField
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel,
    onNavigate: (LoginUiEvent.Navigate) -> Unit
) {
    var loading by remember { mutableStateOf(false) }
    var showSnackBar by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                LoginUiEvent.LoginResultLoading -> {
                    loading = true
                    showSnackBar = false
                }
                is LoginUiEvent.Navigate -> navController.navigate(it.route)
                is LoginUiEvent.ShowSnackBar -> {
                    showSnackBar = true
                    loading = false
                    errorMessage = it.message
                }
            }
        }
    }
    if (loading) {
        CircularProgressIndicator()
    }
    if (showSnackBar) {
        Snackbar {
            Text(errorMessage)
        }
    }
    LoginView(
        email = viewModel.email.value,
        onEmailChange = { viewModel.onEvent(LoginEvent.EmailChange(it)) },
        onLogInClick = {
            viewModel.onEvent(
                LoginEvent.Login(
                    viewModel.email.value,
                    viewModel.password.value
                )
            )
        },
        onSignUpClick = { viewModel.onEvent(LoginEvent.SignUp) },
        password = viewModel.password.value,
        onPasswordChange = { viewModel.onEvent(LoginEvent.PasswordChange(it)) },
        onPlayClick = { navController.navigate(Routes.CHOOSE_GAME_MODE_SCREEN) }
    )
}

@Composable
fun LoginView(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLogInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onPlayClick: () -> Unit
) {
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
            value = email,
            onValueChange = onEmailChange,
            placeholder = "Username",
            leadingIconData = Icons.Default.Person to "username"
        )
        BeautifulTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = onPasswordChange,
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
        Button(modifier = Modifier.fillMaxWidth(), onClick = onLogInClick) {
            Text("Log In")
        }
//        LaunchedEffect(key1 = true) {
//            viewModel.uiEvent.collect { event: LoginUiEvent ->
//                when (event) {
//                    LoginUiEvent.LoginResultLoading -> {
////                        CircularProgressIndicator()
//                    }
//                    LoginUiEvent.LoginResultInitial -> {
////                        Button(modifier = Modifier.fillMaxWidth(), onClick = {
////                            viewModel.onEvent(
////                                LoginEvent.Login(
////                                    viewModel.email.value,
////                                    viewModel.password.value
////                                )
////                            )
////                        }) {
////                            Text("Log In")
////                        }
//                    }
//                    is LoginUiEvent.Navigate -> {
////                        onNavigate(viewModel.uiEvent)
//                    }
//                    is LoginUiEvent.ShowSnackBar -> {
//
//                    }
//                }
//            }
//        }
        Button(modifier = Modifier.fillMaxWidth(), onClick = onSignUpClick) {
            Text("Sign Up")
        }
        Button(onClick = onPlayClick) {
            Text("Play without registration")
        }
    }
}

@Preview
@Composable
fun LoginViewPreview() {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    LoginView(
        email = email,
        onEmailChange = { email = it },
        password = password,
        onPasswordChange = { password = it },
        onLogInClick = { },
        onSignUpClick = { },
        onPlayClick = { }
    )
}