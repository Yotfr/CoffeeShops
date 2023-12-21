package ru.yotfr.sevenwindstestapp.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yotfr.sevenwindstestapp.presentation.theme.CoffeeTheme

@Composable
fun CoffeeButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = CoffeeTheme.shape.largeRounded,
        colors = ButtonDefaults.buttonColors(
            containerColor = CoffeeTheme.colors.darkPrimary
        )
    ) {
        Text(
            modifier = Modifier.padding(vertical = 7.dp),
            text = text,
            style = CoffeeTheme.typography.labelBold,
            color = CoffeeTheme.colors.onPrimary
        )
    }
}