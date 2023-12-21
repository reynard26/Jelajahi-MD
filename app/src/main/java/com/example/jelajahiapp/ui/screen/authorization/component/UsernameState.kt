package com.example.jelajahiapp.ui.screen.authorization.component

import com.example.jelajahiapp.ui.screen.authorization.TextFieldState

private fun isUsernameValid(username: String): Boolean {
    return username.length > 3
}

@Suppress("UNUSED_PARAMETER")
private fun usernameValidationError(username: String): String {
    return "Invalid name"
}

class UsernameState : TextFieldState(validator = ::isUsernameValid, errorFor = ::usernameValidationError)