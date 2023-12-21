package ru.yotfr.sevenwindstestapp.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yotfr.sevenwindstestapp.presentation.theme.CoffeeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeAppBar(
    title: String
) {
    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                    style = CoffeeTheme.typography.labelBold,
                    color = CoffeeTheme.colors.lightPrimary
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = CoffeeTheme.colors.surface
            )
        )
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.5.dp,
            color = CoffeeTheme.colors.gray
        )
    }
}