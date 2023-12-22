package com.example.jelajahiapp.ui.screen.community

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.jelajahiapp.ui.screen.authorization.TextFieldState

open class TextFieldStateCommunity(
    private val validator: (String) -> Boolean = { true },
    initialText: String = "",
) {
    var text: String by mutableStateOf("")


}

private fun isDataValid(data: String): Boolean {
    return data.length > 3
}


class dataCommunityState(initialText: String = "") : TextFieldState(initialText = initialText,validator = ::isDataValid)