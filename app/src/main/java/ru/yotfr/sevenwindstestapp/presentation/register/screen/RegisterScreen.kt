package ru.yotfr.sevenwindstestapp.presentation.register.screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.yotfr.sevenwindstestapp.R
import ru.yotfr.sevenwindstestapp.presentation.common.CoffeeAppBar
import ru.yotfr.sevenwindstestapp.presentation.common.CoffeeButton
import ru.yotfr.sevenwindstestapp.presentation.common.EmailField
import ru.yotfr.sevenwindstestapp.presentation.common.PasswordField
import ru.yotfr.sevenwindstestapp.presentation.register.event.RegisterEvent
import ru.yotfr.sevenwindstestapp.presentation.register.event.RegisterOneTimeEvent
import ru.yotfr.sevenwindstestapp.presentation.register.viewmodel.RegisterViewModel
import ru.yotfr.sevenwindstestapp.presentation.theme.CoffeeTheme
import ru.yotfr.sevenwindstestapp.presentation.utils.observeWithLifecycle
import ru.yotfr.sevenwindstestapp.presentation.utils.rememberImeState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    vm: RegisterViewModel = hiltViewModel(),
    navigateAuthorizeScreen: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }

    val state by vm.state.collectAsState()
    vm.event.observeWithLifecycle { event ->
        when (event) {
            is RegisterOneTimeEvent.ShowErrorSnackbar -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        event.message ?: ContextCompat.getString(
                            context,
                            R.string.something_went_wrong
                        )
                    )
                }
            }

            RegisterOneTimeEvent.SuccessfullyRegistered -> {
                navigateAuthorizeScreen()
            }
        }
    }


    Scaffold(
        topBar = {
            CoffeeAppBar(
                title = stringResource(id = R.string.register)
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .padding(it)
                .padding(horizontal = 18.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(190.dp))
            EmailField(
                modifier = Modifier.fillMaxWidth(),
                isError = state.isEmailEmptyError,
                email = state.email,
                onEmailChanged = { newEmail ->
                    vm.onEvent(
                        RegisterEvent.EmailChanged(
                            newEmail = newEmail
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            PasswordField(
                label = stringResource(id = R.string.password),
                password = state.password,
                onPasswordChanged = { newPassword ->
                    vm.onEvent(
                        RegisterEvent.PasswordChanged(
                            newPassword = newPassword
                        )
                    )
                },
                isError = state.isPasswordEmptyError || state.isPasswordsNotMatchError,
                errorMessage = if (state.isPasswordEmptyError) stringResource(id = R.string.field_empty)
                else stringResource(id = R.string.passwords_not_match),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            PasswordField(
                label = stringResource(id = R.string.repeat_password),
                password = state.repeatPassword,
                onPasswordChanged = { repeatPassword ->
                    vm.onEvent(RegisterEvent.RepeatPasswordChanged(repeatPassword))
                },
                isError = state.isPasswordsNotMatchError,
                errorMessage = stringResource(id = R.string.passwords_not_match),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))
            CoffeeButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                onClick = {
                    vm.onEvent(RegisterEvent.RegisterClicked)
                },
                text = stringResource(id = R.string.register)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.already_registered),
                    style = CoffeeTheme.typography.captionRegular,
                    color = CoffeeTheme.colors.inactive
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(
                    onClick = {
                        navigateAuthorizeScreen()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.enter),
                        style = CoffeeTheme.typography.captionBold,
                        color = CoffeeTheme.colors.lightPrimary
                    )
                }
            }
        }
    }

}