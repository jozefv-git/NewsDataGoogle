package com.jozefv.newsdata.auth.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
    trailingIcon: ImageVector? = null,
    labelText: String = "",
    supportingText: String = "",
    textFieldValue: String,
    singleLine: Boolean = true,
    isPasswordVisible: Boolean = true,
    onTrailingIconClick: () -> Unit = {},
    onValueChange: (String) -> Unit
) {
    // Automatically re-assign state when config change - we don't need to persist this
    var isFocused by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                isFocused = it.isFocused
            },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions,
        shape = MaterialTheme.shapes.small,
        value = textFieldValue,
        singleLine = singleLine,

        label = {
            Text(text = labelText)
        },
        supportingText = {
            Text(text = supportingText)
        },
        trailingIcon = {
            if (isFocused) {
                trailingIcon?.let {
                    IconButton(
                        onClick = {
                            onTrailingIconClick()
                        }
                    ) {
                        Icon(
                            imageVector = it,
                            contentDescription = it.name
                        )
                    }
                }
            }
        },
        onValueChange = {
            onValueChange(it)
        }
    )
}

@Preview
@Composable
private fun CustomTextFieldPreview() {
    CustomTextField(
        textFieldValue = "This is example text",
        trailingIcon = Icons.Default.Clear,
        onTrailingIconClick = {},
        onValueChange = {}
    )
}