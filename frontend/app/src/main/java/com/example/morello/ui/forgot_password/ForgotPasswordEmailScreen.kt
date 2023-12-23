package com.example.morello.ui.forgot_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.morello.ui.components.MorelloTopBar

@Composable
fun ForgotPasswordEmailScreen(
    email: String,
    onEmailChanged: (String) -> Unit,
    onEmailSent: () -> Unit,
    onLoginClicked: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MorelloTopBar(head = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Logo",
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = "Morello",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }, tail = {
                Row {
                    Text(
                        text = "Joined us before?",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    ClickableText(
                        text = AnnotatedString("Log in"),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        onLoginClicked()
                    }
                }
            })
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            Text(text = "Forgot Password", style = MaterialTheme.typography.headlineMedium)
            Text(
                text = "Don't worry, we got you covered! Enter the email address you used to register and we will send you a link to reset your password.",
                style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(10.dp))
            OutlinedTextField(
                value = email,
                label = { Text(text = "Email") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "EmailLogo",
                        modifier = Modifier.size(24.dp)
                    )
                },
                onValueChange = onEmailChanged,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = onEmailSent,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Submit")
            }
        }
    }
}