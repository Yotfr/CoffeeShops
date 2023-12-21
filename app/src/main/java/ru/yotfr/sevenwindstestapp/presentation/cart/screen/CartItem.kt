package ru.yotfr.sevenwindstestapp.presentation.cart.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.yotfr.sevenwindstestapp.R
import ru.yotfr.sevenwindstestapp.presentation.theme.CoffeeTheme

@Composable
fun CartItem(
    name: String,
    price: String,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    count: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = CoffeeTheme.shape.smallRounded,
        color = CoffeeTheme.colors.secondary,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                    top = 14.dp,
                    bottom = 9.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = name,
                    style = CoffeeTheme.typography.labelBold,
                    color = CoffeeTheme.colors.lightPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = price,
                    style = CoffeeTheme.typography.captionRegular,
                    color = CoffeeTheme.colors.inactive
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = null,
                    modifier = Modifier.clickable { onDecrease() },
                    tint = CoffeeTheme.colors.lightPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = count,
                    style = CoffeeTheme.typography.increment,
                    color = CoffeeTheme.colors.lightPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = null,
                    modifier = Modifier.clickable { onIncrease() },
                    tint = CoffeeTheme.colors.lightPrimary
                )
            }
        }
    }
}