package com.example.sampleapp.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sampleapp.ui.AppViewModel

@Composable
fun AuthScreen(
    viewModel: AppViewModel,
    onAuthenticated: (onboarded: Boolean) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .testTag("auth_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))
        Text(
            text = "Welcome to SampleApk",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Sign in or create an account to continue",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
        )

        TabRow(selectedTabIndex = selectedTab, modifier = Modifier.fillMaxWidth()) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Login") },
                modifier = Modifier.testTag("tab_login")
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Sign Up") },
                modifier = Modifier.testTag("tab_signup")
            )
        }

        Spacer(Modifier.height(24.dp))

        if (selectedTab == 0) {
            LoginForm(viewModel, onAuthenticated)
        } else {
            SignUpForm(viewModel, onAuthenticated)
        }
    }
}

@Composable
private fun LoginForm(viewModel: AppViewModel, onAuthenticated: (Boolean) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_login_email")
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_login_password")
        )
        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
        }
        Button(
            onClick = {
                error = validateLogin(email, password)
                if (error == null) {
                    viewModel.login(email.trim(), password) { result ->
                        result.onSuccess { onboarded -> onAuthenticated(onboarded) }
                            .onFailure { error = it.message }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("btn_login")
        ) {
            Text("Login")
        }
        Text(
            text = "Demo accounts (already set up):\n" +
                "• demo@example.com / demo1234\n" +
                "• jane@example.com / jane1234\n" +
                "Or create your own via the Sign Up tab.",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.testTag("login_hint")
        )
    }
}

@Composable
private fun SignUpForm(viewModel: AppViewModel, onAuthenticated: (Boolean) -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_signup_name")
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_signup_email")
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_signup_password")
        )
        OutlinedTextField(
            value = confirm,
            onValueChange = { confirm = it },
            label = { Text("Confirm Password") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_signup_confirm")
        )
        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
        }
        Button(
            onClick = {
                error = validateSignUp(name, email, password, confirm)
                if (error == null) {
                    viewModel.register(name.trim(), email.trim(), password) { result ->
                        result.onSuccess { onAuthenticated(false) }
                            .onFailure { error = it.message }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("btn_create_account")
        ) {
            Text("Create Account")
        }
    }
}

private fun validateLogin(email: String, password: String): String? = when {
    email.isBlank() -> "Email is required"
    !email.contains("@") -> "Enter a valid email"
    password.isBlank() -> "Password is required"
    password.length < 4 -> "Password must be at least 4 characters"
    else -> null
}

private fun validateSignUp(
    name: String,
    email: String,
    password: String,
    confirm: String
): String? = when {
    name.isBlank() -> "Full name is required"
    email.isBlank() -> "Email is required"
    !email.contains("@") -> "Enter a valid email"
    password.length < 4 -> "Password must be at least 4 characters"
    password != confirm -> "Passwords do not match"
    else -> null
}
