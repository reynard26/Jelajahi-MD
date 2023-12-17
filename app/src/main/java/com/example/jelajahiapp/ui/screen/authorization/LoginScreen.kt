package com.example.jelajahiapp.ui.screen.authorization

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.jelajahiapp.R
import com.example.jelajahiapp.data.Result
import com.example.jelajahiapp.data.ViewModelFactory
import com.example.jelajahiapp.data.response.ResponseLogin
import com.example.jelajahiapp.navigation.Screen
import com.example.jelajahiapp.ui.screen.authorization.component.Email
import com.example.jelajahiapp.ui.screen.authorization.component.EmailState
import com.example.jelajahiapp.ui.screen.authorization.component.Password
import com.example.jelajahiapp.ui.screen.authorization.viewmodel.UserViewModel
import com.example.jelajahiapp.ui.screen.home.HomeViewModel
import com.example.jelajahiapp.ui.theme.JelajahiAppTheme
import com.example.jelajahiapp.ui.theme.black100
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.green40
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
                title = {
                    Image(painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(0.dp,25.dp,0.dp,0.dp)
                    )
                },
                actions = {
                    TextButton(
                        onClick = {
                            // Navigate to the Register screen when the icon is clicked
                            navController.navigate(Screen.Register.route)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.register),
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
            Spacer(modifier = Modifier.height(20.dp))
            LoginContent(
                navController = navController,
                onSignInSubmitted = onSignInSubmitted,
                emailState = emailState,
                passwordState = passwordState
            )
        }
    }
}

@Composable
fun LoginContent(
    navController: NavHostController,
    onSignInSubmitted: (String, String) -> Unit,
    emailState: EmailState,
    passwordState: PasswordState,
    homeviewModel: HomeViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current)),
    viewModel: UserViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    var isLoginButtonClickedState by remember { mutableStateOf(false) }
    val loginState by viewModel.loginState.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        homeviewModel.fetchToken()
    }

    val tokenState by homeviewModel.tokenFlow.collectAsState()

    LaunchedEffect(tokenState) {
        if (tokenState != null && tokenState!!.isNotBlank()) {
            navController.navigate(Screen.Home.route)
        }
    }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
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
                    fontSize = 26.sp
                )

                Text(
                    text = stringResource(id = R.string.good_to),
                    fontFamily = fonts,
                    color = grey40,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                val focusRequester = remember { FocusRequester() }

                // Use the provided emailState instead of creating a new one
                Email(emailState, onImeAction = { focusRequester.requestFocus() })

                Spacer(modifier = Modifier.height(8.dp))

                val onSubmit = {
                    isLoginButtonClickedState = true
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
                        disabledContainerColor = green40,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    enabled = emailState.isValid && passwordState.isValid
                ) {
                    when {
                        isLoginButtonClickedState -> {
                            when (loginState) {
                                is Result.Loading -> {
                                    // Loading state UI
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(30.dp)
                                            .padding(4.dp),
                                        color = white100
                                    )
                                }

                                is Result.Success -> {
                                    val data = (loginState as? Result.Success<ResponseLogin>)?.data
                                    data?.let {
                                        val token = data.loginResult?.token
                                        val userId = data.loginResult?.userId
                                        if (!token.isNullOrBlank() && userId != null) {
                                            Toast.makeText(context, "${data.message}", Toast.LENGTH_LONG).show()
                                            isLoginButtonClickedState = false
                                            navController.navigate(Screen.Home.route)
                                            viewModel.saveToken(token, userId)
                                        } else {
                                            // Handle the case where token or userId is null or blank
                                        }
                                    }
                                }

                                is Result.Error -> {
                                    val errorState = loginState as? Result.Error
                                    val serverMsg = errorState?.message ?: "An unknown error occurred" // Default message if 'message' is null
                                    Toast.makeText(context, serverMsg, Toast.LENGTH_LONG).show()
                                    isLoginButtonClickedState = false
                                }

                                else -> {}
                            }
                        }

                        else -> {
                            Text(
                                color = white100,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                text = stringResource(id = R.string.login)
                            )
                        }
                    }
                }
            }
        }
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