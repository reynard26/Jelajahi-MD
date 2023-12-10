package com.example.jelajahiapp.ui.screen.authorization

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.jelajahiapp.R
import com.example.jelajahiapp.data.ViewModelFactory
import com.example.jelajahiapp.data.response.ResponseLogin
import com.example.jelajahiapp.ui.screen.authorization.component.ButtonGoogle
import com.example.jelajahiapp.ui.screen.authorization.component.Email
import com.example.jelajahiapp.ui.screen.authorization.component.EmailState
import com.example.jelajahiapp.ui.screen.authorization.component.Password
import com.example.jelajahiapp.ui.screen.authorization.viewmodel.UserViewModel
import com.example.jelajahiapp.ui.theme.JelajahiAppTheme
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.grey40
import com.example.jelajahiapp.ui.theme.purple100
import com.example.jelajahiapp.ui.theme.white100

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: UserViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val emailState = remember { EmailState() }
    val passwordState = remember { PasswordState() }

    val onSignInSubmitted: (String, String) -> Unit = { email, password ->
        viewModel.login(email, password)
    }

    val loginState by viewModel.loginState.collectAsState()

    LoginContent(
        onSignInSubmitted = onSignInSubmitted,
        emailState = emailState,
        passwordState = passwordState,
        loginState = loginState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    onSignInSubmitted: (String, String) -> Unit,
    emailState: EmailState,
    passwordState: PasswordState,
    loginState: com.example.jelajahiapp.data.Result<ResponseLogin>
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Login",
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

                // Use the provided emailState instead of creating a new one
                Email(emailState, onImeAction = { focusRequester.requestFocus() })

                Spacer(modifier = Modifier.height(8.dp))

                val onSubmit = {
                    Log.d("LoginContent", "Login button clicked")
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
                        disabledContentColor = white100,
                        contentColor = white100,
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
                    modifier = Modifier.fillMaxWidth()
                )

                ButtonGoogle(
                    text = stringResource(id = R.string.sign_in_google),
                    loadingText = stringResource(id = R.string.loading_in_google),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    // Handle Google sign-in click
                }

                // Observe login state
                when (loginState) {
                    is com.example.jelajahiapp.data.Result.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally)
                            .fillMaxSize())
                    }
                    is com.example.jelajahiapp.data.Result.Success -> {
                        Log.d("LoginContent", "Login successful")
                        Text(text = "Login:  ${(loginState as com.example.jelajahiapp.data.Result.Success)}")
                        // Handle success, for example, navigate to the next screen
                        // navController.navigate("next_screen_route")
                    }
                    is com.example.jelajahiapp.data.Result.Error -> {
                        // Handle error, show an error message, etc.
                        Text(text = "Login failed: ${(loginState as com.example.jelajahiapp.data.Result.Error).error}")
                    }
                    else -> {
                        // Handle any other unexpected cases
                        Text(text = "Unexpected login state")
                    }
                }
            }
        }
    }
}

//@Composable
//fun ErrorSnackbar(
//    snackbarHostState: SnackbarHostState,
//    modifier: Modifier = Modifier,
//    onDismiss: () -> Unit = { }
//) {
//    SnackbarHost(
//        hostState = snackbarHostState,
//        snackbar = { data ->
//            Snackbar(
//                modifier = Modifier.padding(16.dp),
//                content = {
//                    Text(
//                        text = data.visuals.message,
//                        style = MaterialTheme.typography.bodyMedium,
//                    )
//                },
//                action = {
//                    data.visuals.actionLabel?.let {
//                        TextButton(onClick = onDismiss) {
//                            Text(
//                                text = stringResource(id = R.string.dismiss),
//                                color = MaterialTheme.colorScheme.inversePrimary
//                            )
//                        }
//                    }
//                }
//            )
//        },
//        modifier = modifier
//            .fillMaxWidth()
//            .wrapContentHeight(Alignment.Bottom)
//    )
//}

@Preview(name = "Sign in light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Sign in dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginPreview() {
    JelajahiAppTheme {
//        LoginScreen(
//            email = null,
//            onSignInSubmitted = { _, _ -> },
//            onSignInAsGuest = {},
//            onNavUp = {},
//        )
    }
}