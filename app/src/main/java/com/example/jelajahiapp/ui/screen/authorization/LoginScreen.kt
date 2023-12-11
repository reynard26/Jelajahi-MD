package com.example.jelajahiapp.ui.screen.authorization

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.jelajahiapp.R
import com.example.jelajahiapp.data.ViewModelFactory
import com.example.jelajahiapp.navigation.Screen
import com.example.jelajahiapp.ui.screen.authorization.component.Email
import com.example.jelajahiapp.ui.screen.authorization.component.EmailState
import com.example.jelajahiapp.ui.screen.authorization.component.Password
import com.example.jelajahiapp.ui.screen.authorization.viewmodel.UserViewModel
import com.example.jelajahiapp.ui.theme.JelajahiAppTheme
import com.example.jelajahiapp.ui.theme.black100
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.grey40
import com.example.jelajahiapp.ui.theme.purple100
import com.example.jelajahiapp.ui.theme.white100

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    TextButton(
                        onClick = {
                            // Navigate to the Register screen when the icon is clicked
                            navController.navigate(Screen.Register.route)
                        }
                    ) {
                        Text(
                            text = "Register",
                            maxLines = 1,
                            fontSize = 16.sp,
                            color = black100,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LoginContent(
                onSignInSubmitted = onSignInSubmitted,
                emailState = emailState,
                passwordState = passwordState
            )
        }
    }
}

@Composable
fun LoginContent(
    onSignInSubmitted: (String, String) -> Unit,
    emailState: EmailState,
    passwordState: PasswordState,
    viewModel: UserViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    var isLoginButtonClicked by remember { mutableStateOf(false) }
    val isLoginButtonClickedState = rememberUpdatedState(isLoginButtonClicked)
    val loginState by viewModel.loginState.collectAsState()
    val context = LocalContext.current

    if (isLoginButtonClickedState.value) {
        when (loginState) {
            is com.example.jelajahiapp.data.Result.Loading -> {
                // Loading state UI
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .alpha(0.5f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(60.dp)
                            .padding(16.dp)
                    )
                }
            }
            is com.example.jelajahiapp.data.Result.Success -> {
                // Success state UI
                Log.d("LoginContent", "Login successful")
                Text(text = "Login: ${(loginState as com.example.jelajahiapp.data.Result.Success)}")
                // navController.navigate("next_screen_route")
            }
            is com.example.jelajahiapp.data.Result.Error -> {
                Toast.makeText(context, "Login failed: ${(loginState as com.example.jelajahiapp.data.Result.Error).error}", Toast.LENGTH_LONG).show()
                isLoginButtonClicked = false
            }
            else -> {
                // Handle any other unexpected cases
                Text(text = "Unexpected login state")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier,
        ) {
            // Your existing UI code
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
                isLoginButtonClicked = true
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
        //            Text(
//                text = stringResource(id = R.string.or),
//                fontFamily = fonts,
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            ButtonGoogle(
//                text = stringResource(id = R.string.sign_in_google),
//                loadingText = stringResource(id = R.string.loading_in_google),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp)
//            ) {
//                // Handle Google sign-in click
//            }
        }
    }
}


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