package com.example.jelajahiapp.ui.screen.authorization.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.jelajahiapp.R
import com.example.jelajahiapp.ui.screen.authorization.TextFieldState
import com.example.jelajahiapp.ui.theme.fonts
import com.example.jelajahiapp.ui.theme.green87
import com.example.jelajahiapp.ui.theme.grey40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Username(
    usernameState: TextFieldState,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        value = usernameState.text,
        onValueChange = {
            usernameState.text = it
            usernameState.enableShowErrors()
        },
        label = {
            Text(
                text = stringResource(id = R.string.name),
                fontFamily = fonts
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                usernameState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    usernameState.enableShowErrors()
                }
            },
        isError = usernameState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        ),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = green87,
            unfocusedBorderColor = grey40
        ),
        supportingText = {usernameState.getError()?.let { error -> TextFieldError(textError = error) }}
    )

}