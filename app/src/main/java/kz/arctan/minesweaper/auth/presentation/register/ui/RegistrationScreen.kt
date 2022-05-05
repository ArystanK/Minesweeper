package kz.arctan.minesweaper.auth.presentation.register.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kz.arctan.minesweaper.auth.presentation.register.RegistrationViewModel
import kz.arctan.minesweaper.common.pesentation.BeautifulTextField

@Composable
@Preview
fun RegistrationScreenPreview() {
    var username by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    RegistrationView(
        username = username,
        onUsernameChange = { username = it },
        firstName = firstName,
        onFirstNameChange = { firstName = it },
        lastName = lastName,
        onLastNameChange = { lastName = it },
        onNextButtonClick = {},
        onBackButtonClick = {}
    )
}

@Composable
fun RegistrationScreen(registrationViewModel: RegistrationViewModel, navController: NavController) {
    RegistrationView(
        username = registrationViewModel.email.value,
        onUsernameChange = { registrationViewModel.email.value = it },
        firstName = registrationViewModel.firstName.value,
        onFirstNameChange = { registrationViewModel.firstName.value = it },
        lastName = registrationViewModel.lastName.value,
        onLastNameChange = { registrationViewModel.lastName.value = it },
        onNextButtonClick = { /*TODO*/ },
        onBackButtonClick = { navController.popBackStack() }
    )
}

@Composable
fun RegistrationView(
    username: String,
    onUsernameChange: (String) -> Unit,
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    onNextButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar {
            IconButton(onClick = onBackButtonClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "back")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text("Registration", fontSize = 20.sp)
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            BeautifulTextField(
                modifier = Modifier.fillMaxWidth(),
                value = username,
                onValueChange = onUsernameChange,
                placeholder = "Email",
            )
            BeautifulTextField(
                modifier = Modifier.fillMaxWidth(),
                value = firstName,
                onValueChange = onFirstNameChange,
                placeholder = "First Name",
            )
            BeautifulTextField(
                modifier = Modifier.fillMaxWidth(),
                value = lastName,
                onValueChange = onLastNameChange,
                placeholder = "Last Name",
            )
            Button(modifier = Modifier.fillMaxWidth(), onClick = onNextButtonClick) {
                Text("Next")
            }
        }
    }
}