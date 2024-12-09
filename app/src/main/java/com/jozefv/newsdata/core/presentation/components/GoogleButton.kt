package com.jozefv.newsdata.core.presentation.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.jozefv.newsdata.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    buttonText: String = "Sign in with Google",
    enabled: Boolean = true,
    onAction: (Credential) -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)
    Button(
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        onClick = {
            // https://developer.android.com/identity/sign-in/credential-manager-siwg
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(BuildConfig.GOOGLE_CLOUD_WEB_SERVER_CLIENT_ID)
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
                    onAction(result.credential)
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
        Text(text = buttonText)
    }
}

@Preview
@Composable
private fun PreviewGoogleButton() {
    MaterialTheme {
        GoogleButton {

        }
    }
}

