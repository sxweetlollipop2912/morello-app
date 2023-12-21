package com.example.morello.ui.forgot_password

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.morello.ui.components.otp_field.OtpField


@Composable
fun ForgotPasswordCodeValidationScreen(
    email: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var code by remember {
        mutableStateOf("    ")
    }
    Scaffold {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            Text(text = "Code validation", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                textAlign = TextAlign.Center,
                text = buildAnnotatedString {
                    append("Please enter the 4-digit code sent to ")
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(email)
                    }
                }
            )
            Spacer(modifier = Modifier.padding(16.dp))
            OtpField(
                onTextChange = { newText ->
                    code = newText
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Button(
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Verify")
            }
        }
    }
}
