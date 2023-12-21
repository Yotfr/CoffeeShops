package ru.yotfr.sevenwindstestapp.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.yotfr.sevenwindstestapp.R
import ru.yotfr.sevenwindstestapp.presentation.theme.CoffeeTheme

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider()
                Text(
                    text = if(isPermanentlyDeclined) {
                        stringResource(id = R.string.grant_permission)
                    } else {
                        stringResource(id = R.string.OK)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) {
                                onGoToAppSettingsClick()
                            } else {
                                onOkClick()
                            }
                        }
                        .padding(16.dp),
                    style = CoffeeTheme.typography.labelBold,
                    color = CoffeeTheme.colors.darkPrimary,
                    textAlign = TextAlign.Center
                )
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.permission_required),
                style = CoffeeTheme.typography.labelBold,
                color = CoffeeTheme.colors.darkPrimary
            )
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(
                    isPermanentlyDeclined = isPermanentlyDeclined
                ),
                style = CoffeeTheme.typography.labelRegular,
                color = CoffeeTheme.colors.lightPrimary
            )
        },
        modifier = modifier
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class LocationPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined) {
            "Кажется, вы навсегда отклонили разрешение на отслеживание местоположения. " +
                    "Вы можете перейти в настройки приложения, чтобы предоставить его."
        } else {
            "Этому приложению необходим доступ к местоположению, " +
                    "чтобы вы смогли увидеть кофейни поблизости на карте."
        }
    }
}