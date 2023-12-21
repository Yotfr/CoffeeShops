package ru.yotfr.sevenwindstestapp.presentation.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import ru.yotfr.sevenwindstestapp.presentation.theme.CoffeeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    label: String,
    password: String,
    onPasswordChanged: (String) -> Unit,
    isError: Boolean,
    errorMessage: String,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            style = CoffeeTheme.typography.body,
            color = if (isError) CoffeeTheme.colors.error
            else CoffeeTheme.colors.lightPrimary
        )
        Spacer(modifier = Modifier.height(7.5.dp))
        val interactionSource = remember { MutableInteractionSource() }
        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = onPasswordChanged,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        ) { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = password,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(
                            text = errorMessage,
                            style = CoffeeTheme.typography.body,
                            color = CoffeeTheme.colors.error
                        )
                    }
                },
                container = {
                    TextFieldDefaults.OutlinedBorderContainerBox(
                        enabled = true,
                        isError = isError,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = CoffeeTheme.colors.lightPrimary,
                            unfocusedBorderColor = CoffeeTheme.colors.lightPrimary,
                            cursorColor = CoffeeTheme.colors.lightPrimary,
                            errorBorderColor = CoffeeTheme.colors.error
                        ),
                        shape = CoffeeTheme.shape.largeRounded,
                        focusedBorderThickness = 2.dp,
                        unfocusedBorderThickness = 2.dp,
                        interactionSource = interactionSource
                    )
                },
                interactionSource = interactionSource
            )
        }
    }
}