package com.example.jelajahiapp.ui.screen.authorization

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.jelajahiapp.R
import com.example.jelajahiapp.data.ViewModelFactory
import com.example.jelajahiapp.ui.screen.authorization.component.ButtonGoogle
import com.example.jelajahiapp.ui.screen.authorization.component.Email
import com.example.jelajahiapp.ui.screen.authorization.component.EmailState
import com.example.jelajahiapp.ui.screen.authorization.component.Password
import com.example.jelajahiapp.ui.screen.authorization.component.Username
import com.example.jelajahiapp.ui.screen.authorization.component.UsernameState
import com.example.jelajahiapp.ui.screen.authorization.viewmodel.UserViewModel
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.green40
import com.example.jelajahiapp.ui.theme.grey40
import com.example.jelajahiapp.ui.theme.purple100
import com.example.jelajahiapp.ui.theme.white100

@Composable
fun SignupScreen(
    navController: NavHostController,
    viewModel: UserViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val onSignUpSubmitted: (String,String, String) -> Unit = { username, email, password ->
        viewModel.signup(username, email, password)
    }

    SignupContent(
        onSignUpSubmitted = onSignUpSubmitted,
    )

}

@Composable
fun SignupContent(
    onSignUpSubmitted: (usernameState: String, emailState: String, passwordState: String) -> Unit,
    viewModel: UserViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current)),
) {
    val signupState by viewModel.defaultState.collectAsState()
    var isSignupButtonClicked by remember { mutableStateOf(false) }
    val isSignupButtonClickedState = rememberUpdatedState(isSignupButtonClicked)
    val context = LocalContext.current

    val usernameState = remember { UsernameState() }
    val emailState = remember { EmailState() }
    val focusRequester = remember { FocusRequester() }
    val confirmationPasswordFocusRequest = remember { FocusRequester() }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)) {
        Column(
            modifier = Modifier,
        ) {
            Text(
                text = stringResource(id = R.string.getting_started),
                fontFamily = fonts,
                fontWeight = FontWeight.ExtraBold,
                color = purple100,
                fontSize = 27.sp
            )

            Text(
                text = stringResource(id = R.string.account_continue),
                fontFamily = fonts,
                color = grey40,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Username(usernameState,onImeAction = { focusRequester.requestFocus() } )

            Spacer(modifier = Modifier.height(8.dp))

            Email(emailState, onImeAction = { focusRequester.requestFocus() })

            Spacer(modifier = Modifier.height(8.dp))
            val passwordState = remember { PasswordState() }
            Password(
                label = stringResource(id = R.string.password),
                passwordState = passwordState,
                imeAction = ImeAction.Next,
                onImeAction = { confirmationPasswordFocusRequest.requestFocus() },
                modifier = Modifier.focusRequester(focusRequester)
            )

            Spacer(modifier = Modifier.height(8.dp))
            val confirmPasswordState =
                remember { ConfirmPasswordState(passwordState = passwordState) }
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
                    isSignupButtonClicked = true
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
                    isSignupButtonClickedState.value -> {
                        when (signupState) {
                            is com.example.jelajahiapp.data.Result.Loading -> {
                                // Loading state UI
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .padding(4.dp),
                                    color = white100
                                )
                            }
                            is com.example.jelajahiapp.data.Result.Success -> {
                                // Success state UI
                                Toast.makeText(context, "${(signupState as com.example.jelajahiapp.data.Result.Success).data.message}", Toast.LENGTH_LONG).show()
                                isSignupButtonClicked = false
                            }
                            is com.example.jelajahiapp.data.Result.Error -> {
                                // Error state UI
                                Toast.makeText(context, "Signup failed: ${(signupState as com.example.jelajahiapp.data.Result.Error).error}", Toast.LENGTH_LONG).show()
                                isSignupButtonClicked = false
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
                text = stringResource(id = R.string.sign_up_google),
                loadingText = stringResource(id = R.string.loading_up_google),
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {

            }
        }
    }
}


//@Preview(name = "Sign in light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Preview(name = "Sign in dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun SignupPreview() {
//    JelajahiAppTheme {
//        SignupScreen(
//
//        )
//    }
//}