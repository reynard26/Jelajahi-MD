package com.example.jelajahiapp.ui.screen.authorization

private fun isPasswordValid(password: String): Boolean {
    return password.length > 3
}

@Suppress("UNUSED_PARAMETER")
private fun passwordValidationError(password: String): String {
    return "Invalid password"
}

class PasswordState : TextFieldState(validator = ::isPasswordValid, errorFor = ::passwordValidationError)