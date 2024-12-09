package com.jozefv.newsdata.auth.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.jozefv.newsdata.R
import com.jozefv.newsdata.auth.presentation.components.CustomTextField
import com.jozefv.newsdata.core.presentation.ObserveAsEvents
import com.jozefv.newsdata.core.presentation.SpacerVerL
import com.jozefv.newsdata.core.presentation.SpacerVerM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    onSkip: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
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
    onAction: (LoginAction) -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp, vertical = 16.dp)
    ) {
        IconButton(modifier = Modifier.align(Alignment.TopEnd),
            onClick = { LoginAction.OnSkipClick }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = Icons.Default.Close.name
            )
        }
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "NewsData", style = MaterialTheme.typography.headlineLarge)
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
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            style = MaterialTheme.typography.bodySmall,
                            text = "Email or password is incorrect"
                        )
                    }
                }
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.notEmptyFields,
                    onClick = {
                        onAction(LoginAction.OnLoginClick)
                    }) {
                    Text(text = "Sign in")
                }
            }
            Text(text = "or")
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val googleIdOption = GetGoogleIdOption.Builder()
                        .setFilterByAuthorizedAccounts(false)
                        .setServerClientId("744000962425-r31icevllm2cn1udnh2lq384g0ese7t6.apps.googleusercontent.com")
                        .setAutoSelectEnabled(true)
                        .build()
                    val request = GetCredentialRequest.Builder()
                        .addCredentialOption(googleIdOption)
                        .build()
                    coroutineScope.launch(Dispatchers.IO) {
                        try {
                            val result = credentialManager.getCredential(
                                context = context,
                                request = request
                            )
                            onAction(LoginAction.OnLoginWithGoogleClick(result.credential))
                        } catch (e: GetCredentialException) {
                            Log.e("Credential error", e.message.orEmpty())
                            withContext(Dispatchers.Main.immediate) {
                                Toast.makeText(
                                    context,
                                    "There was an error: ${e.message.orEmpty()}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            ) {
                Text(text = "Sign in with Google")
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
