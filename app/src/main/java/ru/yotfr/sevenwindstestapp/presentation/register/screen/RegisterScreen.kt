package ru.yotfr.sevenwindstestapp.presentation.register.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.yotfr.sevenwindstestapp.R
import ru.yotfr.sevenwindstestapp.presentation.register.event.RegisterEvent
import ru.yotfr.sevenwindstestapp.presentation.register.viewmodel.RegisterViewModel
import ru.yotfr.sevenwindstestapp.ui.theme.CoffeeTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    vm: RegisterViewModel = hiltViewModel()
) {

    val state by vm.state.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.register),
                        style = CoffeeTheme.typography.labelBold,
                        color = CoffeeTheme.colors.lightPrimary
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = CoffeeTheme.colors.surface
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(190.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.email_hint),
                    style = CoffeeTheme.typography.body,
                    color = CoffeeTheme.colors.lightPrimary
                )
                Spacer(modifier = Modifier.height(7.5.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.email,
                    onValueChange = { newEmail ->
                        vm.onEvent(RegisterEvent.EmailChanged(newEmail))
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.email_hint),
                            style = CoffeeTheme.typography.labelRegular,
                            color = CoffeeTheme.colors.inactive
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = CoffeeTheme.colors.lightPrimary,
                        unfocusedBorderColor = CoffeeTheme.colors.lightPrimary,
                        cursorColor = CoffeeTheme.colors.lightPrimary
                    ),
                    shape = CoffeeTheme.shape.largeRounded
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.password),
                    style = CoffeeTheme.typography.body,
                    color = CoffeeTheme.colors.lightPrimary
                )
                Spacer(modifier = Modifier.height(7.5.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.password,
                    onValueChange = { newPassword ->
                        vm.onEvent(RegisterEvent.PasswordChanged(newPassword))
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = CoffeeTheme.colors.lightPrimary,
                        unfocusedBorderColor = CoffeeTheme.colors.lightPrimary,
                        cursorColor = CoffeeTheme.colors.lightPrimary
                    ),
                    shape = CoffeeTheme.shape.largeRounded,
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.repeat_password),
                    style = CoffeeTheme.typography.body,
                    color = CoffeeTheme.colors.lightPrimary
                )
                Spacer(modifier = Modifier.height(7.5.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.repeatPassword,
                    onValueChange = { repeatPassword ->
                        vm.onEvent(RegisterEvent.RepeatPasswordChanged(repeatPassword))
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = CoffeeTheme.colors.lightPrimary,
                        unfocusedBorderColor = CoffeeTheme.colors.lightPrimary,
                        cursorColor = CoffeeTheme.colors.lightPrimary
                    ),
                    shape = CoffeeTheme.shape.largeRounded,
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                modifier = Modifier.padding(vertical = 13.dp),
                onClick = {
                    vm.onEvent(RegisterEvent.RegisterClicked)
                },
                shape = CoffeeTheme.shape.largeRounded,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CoffeeTheme.colors.darkPrimary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.register),
                    style = CoffeeTheme.typography.labelBold,
                    color = CoffeeTheme.colors.onPrimary
                )
            }
        }
    }

}