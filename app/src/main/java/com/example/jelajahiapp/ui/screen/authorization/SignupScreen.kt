package com.example.jelajahiapp.ui.screen.authorization

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jelajahiapp.R
import com.example.jelajahiapp.ui.screen.authorization.component.ButtonGoogle
import com.example.jelajahiapp.ui.screen.authorization.component.Email
import com.example.jelajahiapp.ui.screen.authorization.component.Username
import com.example.jelajahiapp.ui.screen.authorization.component.EmailState
import com.example.jelajahiapp.ui.screen.authorization.component.EmailStateSaver
import com.example.jelajahiapp.ui.screen.authorization.component.Password
import com.example.jelajahiapp.ui.screen.authorization.component.UsernameState
import com.example.jelajahiapp.ui.theme.JelajahiAppTheme
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.green40
import com.example.jelajahiapp.ui.theme.green87
import com.example.jelajahiapp.ui.theme.grey40
import com.example.jelajahiapp.ui.theme.purple100
import com.example.jelajahiapp.ui.theme.white100

@Composable
fun SignupScreen(
//    email: String?,
//    onSignInSubmitted: (email: String, password: String) -> Unit,
//    onSignInAsGuest: () -> Unit,
//    onNavUp: () -> Unit,
) {

}

@Composable
fun SignupContent(
    email: String?,
    onSignInSubmitted: (username:String, email: String, password: String) -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)) {
        Column(
            modifier = Modifier,
        ) {
            Text(text = stringResource(id = R.string.getting_started),
                fontFamily = fonts,
                fontWeight = FontWeight.ExtraBold,
                color = purple100,
                fontSize = 28.sp
            )

            Text(text = stringResource(id = R.string.account_continue),
                fontFamily = fonts,
                color = grey40,
                fontSize = 17.sp)

            Spacer(modifier = Modifier.height(4.dp))

            val focusRequester = remember { FocusRequester() }

            val usernameState = remember { UsernameState()}
            Username(usernameState, onImeAction = { focusRequester.requestFocus() })

            Spacer(modifier = Modifier.height(10.dp))
            val emailState by rememberSaveable(stateSaver = EmailStateSaver) {
                mutableStateOf(EmailState(email))
            }
            Email(emailState, onImeAction = { focusRequester.requestFocus() })

            Spacer(modifier = Modifier.height(14.dp))

            val passwordState = remember { PasswordState() }

            val onSubmit = {
                if (usernameState.isValid && emailState.isValid && passwordState.isValid) {
                    onSignInSubmitted(usernameState.text,emailState.text, passwordState.text)
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
                    .padding(vertical = 16.dp),
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
                text = stringResource(id = R.string.sign_up_google),
                loadingText = stringResource(id = R.string.loading_up_google),
                modifier = Modifier .fillMaxWidth()) {

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
fun SignupPreview() {
    JelajahiAppTheme {
        SignupScreen(
//            email = null,
//            onSignInSubmitted = { _, _ -> },
//            onSignInAsGuest = {},
//            onNavUp = {},
        )
    }
}