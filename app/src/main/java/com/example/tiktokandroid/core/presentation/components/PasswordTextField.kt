package com.example.tiktokandroid.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiktokandroid.auth.data.model.PasswordStrength
import com.example.tiktokandroid.auth.data.model.PasswordValidator

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    hint: String = "Password",
    showPasswordLevels : Boolean = true,
    onTextChange: (String) -> Unit = {},
    onValidityChange: (isValid: Boolean, strength: PasswordStrength) -> Unit = { _, _ -> }
) {
    var password by rememberSaveable { mutableStateOf("") }
    var visible by rememberSaveable { mutableStateOf(false) }

    // compute once per recomposition
    val isValid = remember(password) { PasswordValidator.isPasswordValid(password) }
    val strength = remember(password) { PasswordValidator.passwordStrength(password) }

    // notify parent when validity or strength change
    LaunchedEffect(isValid, strength) {
        onValidityChange(isValid, strength)
    }

    Column(modifier = modifier) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {
                password = it
                onTextChange(it)
            },
            placeholder = { Text(hint) },
            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Row {
                    if (password.isNotEmpty()) {
                        IconButton(onClick = { password = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                    IconButton(onClick = { visible = !visible }) {
                        Icon(
                            imageVector = if (visible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (visible) "Hide" else "Show"
                        )
                    }
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5),
                disabledContainerColor = Color(0xFFF5F5F5),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))


        if(showPasswordLevels) {
            // Strength row (visual)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Password strength: ${strength.name}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.width(8.dp))
                val color = when (strength) {
                    PasswordStrength.Weak -> Color.Red
                    PasswordStrength.Medium -> Color(0xFFFFA000) // amber
                    PasswordStrength.Strong -> Color(0xFF4CAF50)
                }
                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(80.dp)
                        .background(color = color, shape = RoundedCornerShape(4.dp))
                )
            }

            // Recommended checklist
            Column(modifier = Modifier.padding(top = 8.dp)) {
                ChecklistItem("At least ${8} characters", PasswordValidator.isMinLength(password))
                ChecklistItem(
                    "Contains lower & upper case",
                    PasswordValidator.hasLower(password) && PasswordValidator.hasUpper(password)
                )
                ChecklistItem("Contains a number", PasswordValidator.hasDigit(password))
                ChecklistItem(
                    "Contains a special character",
                    PasswordValidator.hasSpecial(password)
                )
            }
        }
    }
}

@Composable
private fun ChecklistItem(text: String, ok: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = if (ok) Icons.Default.Check else Icons.Default.Close,
            contentDescription = null,
            tint = if (ok) Color(0xFF4CAF50) else Color.Gray,
            modifier = Modifier.size(16.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodySmall, color = if (ok) Color.Black else Color.Gray)
    }
}


@Preview
@Composable
private fun PasswordTextFieldPreview() {

    PasswordTextField()
}