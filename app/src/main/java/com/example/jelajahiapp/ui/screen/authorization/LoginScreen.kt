package com.example.jelajahiapp.ui.screen.authorization

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jelajahiapp.R
import com.example.jelajahiapp.ui.screen.authorization.component.ButtonGoogle
import com.example.jelajahiapp.ui.screen.authorization.component.Email
import com.example.jelajahiapp.ui.screen.authorization.component.EmailState
import com.example.jelajahiapp.ui.screen.authorization.component.EmailStateSaver
import com.example.jelajahiapp.ui.screen.authorization.component.Password
import com.example.jelajahiapp.ui.screen.authorization.PasswordState
import com.example.jelajahiapp.ui.theme.JelajahiAppTheme
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.green40
import com.example.jelajahiapp.ui.theme.green87
import com.example.jelajahiapp.ui.theme.grey40
import com.example.jelajahiapp.ui.theme.purple100
import com.example.jelajahiapp.ui.theme.white100

@Composable
fun LoginScreen(
//    email: String?,
//    onSignInSubmitted: (email: String, password: String) -> Unit,
//    onSignInAsGuest: () -> Unit,
//    onNavUp: () -> Unit,
) {

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    email: String?,
    onSignInSubmitted: (email: String, password: String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Register",
                        maxLines = 1,
                        fontSize = 15.sp,
                    )
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(14.dp)
        ) {
            Column(
                modifier = Modifier,
            ) {
                Text(
                    text = stringResource(id = R.string.lets_something),
                    fontFamily = fonts,
                    fontWeight = FontWeight.ExtraBold,
                    color = purple100,
                    fontSize = 27.sp
                )

                Text(
                    text = stringResource(id = R.string.good_to),
                    fontFamily = fonts,
                    color = grey40,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                val focusRequester = remember { FocusRequester() }
                val emailState by rememberSaveable(stateSaver = EmailStateSaver) {
                    mutableStateOf(EmailState(email))
                }
                Email(emailState, onImeAction = { focusRequester.requestFocus() })

                Spacer(modifier = Modifier.height(14.dp))

                val passwordState = remember { PasswordState() }

                val onSubmit = {
                    if (emailState.isValid && passwordState.isValid) {
                        onSignInSubmitted(emailState.text, passwordState.text)
                    }
                }
                Password(
                    label = stringResource(id = R.string.password),
                    passwordState = passwordState,
                    modifier = Modifier.focusRequester(focusRequester),
                    onImeAction = { onSubmit() }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { onSubmit() },
                    colors = ButtonDefaults.buttonColors(
                        disabledContainerColor = green40,
                        disabledContentColor = white100,
                        contentColor = white100,
                        containerColor = green87// Set the color for the disabled state
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    enabled = emailState.isValid && passwordState.isValid
                ) {
                    Text(
                        color = white100,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        text = stringResource(id = R.string.login)
                    )
                }
                Text(
                    text = stringResource(id = R.string.or),
                    fontFamily = fonts,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                ButtonGoogle(
                    text = stringResource(id = R.string.sign_in_google),
                    loadingText = stringResource(id = R.string.loading_in_google),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {

                }
            }
        }
    }
}

@Composable
fun ErrorSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = { }
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                content = {
                    Text(
                        text = data.visuals.message,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                action = {
                    data.visuals.actionLabel?.let {
                        TextButton(onClick = onDismiss) {
                            Text(
                                text = stringResource(id = R.string.dismiss),
                                color = MaterialTheme.colorScheme.inversePrimary
                            )
                        }
                    }
                }
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Bottom)
    )
}

@Preview(name = "Sign in light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Sign in dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginPreview() {
    JelajahiAppTheme {
        LoginScreen(
//            email = null,
//            onSignInSubmitted = { _, _ -> },
//            onSignInAsGuest = {},
//            onNavUp = {},
        )
    }
}