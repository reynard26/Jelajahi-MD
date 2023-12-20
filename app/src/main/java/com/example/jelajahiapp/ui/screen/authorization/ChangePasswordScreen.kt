package com.example.jelajahiapp.ui.screen.authorization

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.jelajahiapp.data.response.ResponseUser
import com.example.jelajahiapp.navigation.Screen
import com.example.jelajahiapp.ui.screen.authorization.component.Password
import com.example.jelajahiapp.ui.screen.authorization.viewmodel.UserViewModel
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.green40
import com.example.jelajahiapp.ui.theme.purple100
import com.example.jelajahiapp.ui.theme.white100

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    navController: NavHostController,
    onBackClick: () -> Unit,
    viewModel: UserViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val onSignInSubmitted: (String, String, String) -> Unit = { email, currentPassword, newPassword ->
        viewModel.changePassword(email, currentPassword, newPassword)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { onBackClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {},
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ChangePasswordContent(
                navController = navController,
                onSignInSubmitted = onSignInSubmitted,
            )
        }
    }
}

@Composable
fun ChangePasswordContent(
    navController: NavHostController,
    onSignInSubmitted: (String, String, String) -> Unit,
    viewModel: UserViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    var isChangeButtonClickedState by remember { mutableStateOf(false) }
    val currentPassword = remember { PasswordState() }
    val newPassword = remember { PasswordState() }
    val confirmationPasswordFocusRequest = remember { FocusRequester() }
    val confirmPasswordState =
        remember { ConfirmPasswordState(passwordState = newPassword) }

    LaunchedEffect(Unit) {
        viewModel.fetchEmail()
    }

    val emailState by viewModel.emailFlow.collectAsState()

    LaunchedEffect(emailState) {
        if (emailState != null && emailState!!.isNotBlank()) {
            Log.d("emailnyaada", emailState!!)
        }
    }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier,
        ) {
            Text(
                text = stringResource(id = R.string.change_password),
                fontFamily = fonts,
                fontWeight = FontWeight.ExtraBold,
                color = purple100,
                fontSize = 26.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            val focusRequester = remember { FocusRequester() }

            Spacer(modifier = Modifier.height(8.dp))

            val onSubmit = {
                isChangeButtonClickedState = true
                if ( currentPassword.isValid && newPassword.isValid) {
                    emailState?.let { onSignInSubmitted(it, currentPassword.text, newPassword.text) }
                }
            }

            Password(
                label = stringResource(id = R.string.current_pasword),
                passwordState = currentPassword,
                modifier = Modifier.focusRequester(focusRequester),
                onImeAction = { onSubmit() }
            )

            Password(
                label = stringResource(id = R.string.new_password),
                passwordState = newPassword,
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
                enabled =currentPassword.isValid && newPassword.isValid && confirmPasswordState.isValid
            ) {
                when {
                    isChangeButtonClickedState -> {
                        when (val changepasswordState = viewModel.defaultState.collectAsState().value) {
                            is Result.Loading -> {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .padding(4.dp),
                                    color = white100
                                )
                            }

                            is Result.Success<*> -> {
                                val data = (changepasswordState as? Result.Success<ResponseUser>)?.data
                                data?.let {
                                    Toast.makeText(context, "${data.messages}", Toast.LENGTH_LONG).show()
                                    isChangeButtonClickedState = false
                                    viewModel.setLogout {
                                        navController.navigate(Screen.Login.route) {
                                            popUpTo(Screen.Login.route) {
                                                inclusive = true
                                            }
                                        }
                                    }
                                    Toast.makeText(context, R.string.re_login, Toast.LENGTH_LONG).show()
                                }
                            }

                            is Result.Error -> {
                                val errorState = changepasswordState as? Result.Error
                                val serverMsg = errorState?.message ?: "An unknown error occurred"
                                Toast.makeText(context, serverMsg, Toast.LENGTH_LONG).show()
                                isChangeButtonClickedState = false
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
                            text = stringResource(id = R.string.save)
                        )
                    }
                }
            }
        }
    }
}