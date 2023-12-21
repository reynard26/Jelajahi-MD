package com.example.jelajahiapp.ui.screen.authorization

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.jelajahiapp.R
import com.example.jelajahiapp.data.Result
import com.example.jelajahiapp.data.ViewModelFactory
import com.example.jelajahiapp.navigation.Screen
import com.example.jelajahiapp.ui.screen.authorization.component.Email
import com.example.jelajahiapp.ui.screen.authorization.component.EmailState
import com.example.jelajahiapp.ui.screen.authorization.component.Password
import com.example.jelajahiapp.ui.screen.authorization.component.Username
import com.example.jelajahiapp.ui.screen.authorization.component.UsernameState
import com.example.jelajahiapp.ui.screen.authorization.viewmodel.UserViewModel
import com.example.jelajahiapp.ui.theme.black100
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.green40
import com.example.jelajahiapp.ui.theme.grey40
import com.example.jelajahiapp.ui.theme.purple100
import com.example.jelajahiapp.ui.theme.white100

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    navController: NavHostController,
    viewModel: UserViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val onSignUpSubmitted: (String,String, String) -> Unit = { username, email, password ->
        viewModel.signup(username, email, password)
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
                            navController.navigate(Screen.Login.route)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.login),
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
            SignupContent(
                onSignUpSubmitted = onSignUpSubmitted,
                navController = navController
            )
        }
    }
}
@Composable
fun SignupContent(
    navController: NavHostController,
    onSignUpSubmitted: (usernameState: String, emailState: String, passwordState: String) -> Unit,
    viewModel: UserViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current)),
) {
    var isSignupButtonClickedState by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val usernameState = remember { UsernameState() }
    val emailState = remember { EmailState() }
    val focusRequester = remember { FocusRequester() }
    val passwordState = remember { PasswordState() }
    val confirmationPasswordFocusRequest = remember { FocusRequester() }
    val confirmPasswordState =
        remember { ConfirmPasswordState(passwordState = passwordState) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)) {
        Column(
            modifier = Modifier,
        ) {
            Text(
                text = stringResource(id = R.string.getting_started),
                fontFamily = fonts,
                fontWeight = FontWeight.ExtraBold,
                color = purple100,
                fontSize = 26.sp
            )

            Text(
                text = stringResource(id = R.string.account_continue),
                fontFamily = fonts,
                color = grey40,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Username(usernameState,onImeAction = { focusRequester.requestFocus() } )

            Spacer(modifier = Modifier.height(8.dp))

            Email(emailState, onImeAction = { focusRequester.requestFocus() })

            Spacer(modifier = Modifier.height(8.dp))

            Password(
                label = stringResource(id = R.string.password),
                passwordState = passwordState,
                imeAction = ImeAction.Next,
                onImeAction = { confirmationPasswordFocusRequest.requestFocus() },
                modifier = Modifier.focusRequester(focusRequester)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Password(
                label = stringResource(id = R.string.confirm_password),
                passwordState = confirmPasswordState,
                modifier = Modifier.focusRequester(confirmationPasswordFocusRequest)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = green40,
                    disabledContentColor = white100,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                onClick = {
                    isSignupButtonClickedState = true
                    onSignUpSubmitted(
                        usernameState.text,
                        emailState.text,
                        passwordState.text
                    )
                },
                enabled = emailState.isValid &&
                        passwordState.isValid && confirmPasswordState.isValid
            ) {
                when {
                    isSignupButtonClickedState -> {
                        when (val signupState = viewModel.defaultState.collectAsState().value) {
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
                                Toast.makeText(context, R.string.account_created, Toast.LENGTH_LONG).show()
                                isSignupButtonClickedState = false
                                navController.navigate(Screen.Login.route)
                            }

                            is Result.Error -> {
                                val errorState = signupState as? Result.Error
                                val serverMsg = errorState?.message ?: "An unknown error occurred" // Default message if 'message' is null
                                Toast.makeText(context, serverMsg, Toast.LENGTH_LONG).show()
                                isSignupButtonClickedState = false
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
                            text = stringResource(id = R.string.signup)
                        )
                    }
                }
            }
        }
    }
}
