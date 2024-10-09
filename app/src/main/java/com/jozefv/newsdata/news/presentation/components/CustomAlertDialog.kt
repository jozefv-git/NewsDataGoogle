package com.jozefv.newsdata.news.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jozefv.newsdata.R

@Composable
fun CustomAlertDialog(
    modifier: Modifier = Modifier,
    dialogTitle: String,
    dialogText: String,
    dismissButtonText: String = "No",
    confirmButtonText: String = "Yes",
    dialogIcon: @Composable () -> Unit = {},
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        title = {
            Text(style = MaterialTheme.typography.bodyLarge, text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        icon = {
            dialogIcon()
        },
        onDismissRequest = { onDismiss() },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = dismissButtonText)
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
            }) {
                Text(text = confirmButtonText)
            }
        }
    )
}

@Preview
@Composable
private fun CustomAlertDialogPreview(){
    CustomAlertDialog(
        dialogTitle = "Logout",
        dialogText = "Are you sure you want to log out?",
        dialogIcon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_logout_24),
                contentDescription = "Log out"
            )
        },
        onDismiss = { },
        onConfirm = {})
}