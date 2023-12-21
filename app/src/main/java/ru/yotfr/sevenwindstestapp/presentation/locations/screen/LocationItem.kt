package ru.yotfr.sevenwindstestapp.presentation.locations.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yotfr.sevenwindstestapp.presentation.theme.CoffeeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationItem(
    name: String,
    distance: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = CoffeeTheme.shape.smallRounded,
        color = CoffeeTheme.colors.secondary,
        shadowElevation = 2.dp,
        tonalElevation = 0.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                    top = 14.dp,
                    bottom = 9.dp
                )
        ) {
            Text(
                text = name,
                style = CoffeeTheme.typography.labelBold,
                color = CoffeeTheme.colors.darkPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = distance,
                style = CoffeeTheme.typography.captionRegular,
                color = CoffeeTheme.colors.inactive
            )
        }
    }
}