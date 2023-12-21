package ru.yotfr.sevenwindstestapp.presentation.authorize.screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import ru.yotfr.sevenwindstestapp.presentation.authorize.event.AuthorizeEvent
import ru.yotfr.sevenwindstestapp.presentation.authorize.event.AuthorizeOneTimeEvent
import ru.yotfr.sevenwindstestapp.presentation.authorize.viewmodel.AuthorizeViewModel
import ru.yotfr.sevenwindstestapp.presentation.common.CoffeeAppBar
import ru.yotfr.sevenwindstestapp.presentation.common.CoffeeButton
import ru.yotfr.sevenwindstestapp.presentation.common.EmailField
import ru.yotfr.sevenwindstestapp.presentation.common.PasswordField
import ru.yotfr.sevenwindstestapp.presentation.utils.observeWithLifecycle
import ru.yotfr.sevenwindstestapp.presentation.utils.rememberImeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorizeScreen(
    vm: AuthorizeViewModel = hiltViewModel(),
    navigateRegisterScreen: () -> Unit,
    navigateMainGraph: () -> Unit
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
        when(event) {
            is AuthorizeOneTimeEvent.ShowErrorSnackbar -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        event.message ?: ContextCompat.getString(
                            context,
                            R.string.something_went_wrong
                        )
                    )
                }
            }
            AuthorizeOneTimeEvent.SuccessfullyAuthorized -> {
                navigateMainGraph()
            }
        }
    }

    Scaffold(
        topBar = {
            CoffeeAppBar(
                title = stringResource(id = R.string.authorize),
                isTopLevel = false,
                onNavigationItemClick = navigateRegisterScreen
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
                isError = false,
                email = state.email,
                onEmailChanged = { newEmail ->
                    vm.onEvent(AuthorizeEvent.EmailChanged(newEmail))
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            PasswordField(
                label = stringResource(id = R.string.password),
                password = state.password,
                onPasswordChanged = { newPassword ->
                    vm.onEvent(AuthorizeEvent.PasswordChanged(newPassword))
                },
                isError = false,
                errorMessage = "",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))
            CoffeeButton(
                onClick = {
                    vm.onEvent(AuthorizeEvent.AuthorizeClicked)
                },
                text = stringResource(id = R.string.authorize),
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
            )
        }
    }
}