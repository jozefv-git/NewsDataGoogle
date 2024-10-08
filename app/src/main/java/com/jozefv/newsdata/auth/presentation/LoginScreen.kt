package com.jozefv.newsdata.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jozefv.newsdata.R
import com.jozefv.newsdata.auth.presentation.components.CustomTextField
import com.jozefv.newsdata.core.presentation.ObserveAsEvents
import com.jozefv.newsdata.core.presentation.SpacerHorM
import com.jozefv.newsdata.core.presentation.SpacerVerL
import com.jozefv.newsdata.core.presentation.SpacerVerM
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    onSkip: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {

    ObserveAsEvents(flow = viewModel.eventChannel) { loginEvent ->
        if (loginEvent is LoginEvent.LoginSuccess) {
            onLoginSuccess()
        }
    }

    LoginScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is LoginAction.OnSkipClick -> onSkip()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
            Text(text = "NewsData", style = MaterialTheme.typography.headlineLarge)
        }
        SpacerVerL()
        CustomTextField(
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            labelText = "Your email",
            //supportingText = "email@email.com",
            textFieldValue = state.email,
            onValueChange = {
                onAction(LoginAction.EmailInput(it))
            }
        )
        SpacerVerM()
        CustomTextField(
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            labelText = "Your password",
            textFieldValue = state.password,
            isPasswordVisible = state.isPasswordVisible,
            trailingIcon = if (state.isPasswordVisible) {
                ImageVector.vectorResource(R.drawable.baseline_visibility_24)
            } else {
                ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24)
            },
            onTrailingIconClick = {
                onAction(LoginAction.OnPasswordVisibilityClick)
            },
            onValueChange = {
                onAction(LoginAction.PasswordInput(it))
            }
        )
        SpacerVerM()
        state.canLogin?.let { canLogin ->
            if (!canLogin) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                    Text(
                        style = MaterialTheme.typography.bodySmall,
                        text = "Email or password is incorrect"
                    )
                }
            }
        }
        SpacerVerL()
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(onClick = {
                onAction(LoginAction.OnSkipClick)
            }) {
                Text(text = "Skip")
            }
            SpacerHorM()
            Button(
                enabled = state.notEmptyFields,
                onClick = {
                    onAction(LoginAction.OnLoginClick)
                }) {
                Text(text = "Login")
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
// Add theme here
    MaterialTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {}
        )
    }
}
