package ru.yotfr.sevenwindstestapp.presentation.locationmenu.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.yotfr.sevenwindstestapp.R
import ru.yotfr.sevenwindstestapp.presentation.theme.CoffeeTheme

@Composable
fun MenuItem(
    url: String,
    name: String,
    price: String,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    count: String
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = CoffeeTheme.shape.smallRounded,
        colors = CardDefaults.elevatedCardColors(
            containerColor = CoffeeTheme.colors.background
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        AsyncImage(
            model = url,
            contentDescription = null,
            modifier = Modifier.aspectRatio(1f),
            contentScale = ContentScale.FillHeight
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 11.dp, bottom = 11.dp, top = 10.dp, end = 5.dp)
        ) {
            Text(
                text = name,
                style = CoffeeTheme.typography.body,
                color = CoffeeTheme.colors.inactive,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(9.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = price,
                    style = CoffeeTheme.typography.captionBold,
                    color = CoffeeTheme.colors.lightPrimary,
                    maxLines = 1
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_minus),
                        contentDescription = null,
                        modifier = Modifier.clickable { onDecrease() },
                        tint = CoffeeTheme.colors.secondary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = count,
                        style = CoffeeTheme.typography.captionRegular,
                        color = CoffeeTheme.colors.lightPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = null,
                        modifier = Modifier.clickable { onIncrease() },
                        tint = CoffeeTheme.colors.secondary
                    )
                }
            }
        }
    }
}