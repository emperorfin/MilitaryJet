/*
 * Copyright 2023 Francis Nwokelo (emperorfin)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package emperorfin.android.militaryjet.ui.screens.authentication.stateholders

import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewmodel.compose.
import androidx.lifecycle.viewModelScope
import emperorfin.android.militaryjet.R
import emperorfin.android.militaryjet.ui.screens.authentication.events.AuthenticationEvent
import emperorfin.android.militaryjet.ui.screens.authentication.events.AuthenticationEvent.AuthenticationToggleModeEvent
import emperorfin.android.militaryjet.ui.screens.authentication.events.AuthenticationEvent.EmailChangedEvent
import emperorfin.android.militaryjet.ui.screens.authentication.events.AuthenticationEvent.PasswordChangedEvent
import emperorfin.android.militaryjet.ui.screens.authentication.events.AuthenticationEvent.AuthenticateEvent
import emperorfin.android.militaryjet.ui.screens.authentication.events.AuthenticationEvent.ErrorDismissedEvent
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.EIGHT_CHARACTERS
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.CAPITAL_LETTER
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.NUMBER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


class AuthenticationViewModel : ViewModel() {

    // ---------------------------------- CONSTANT DECLARATIONS -----------------------------------
    companion object {

        // BOOLEAN
        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        // STRING

        // ARRAY

        // BYTE
        private const val PASSWORD_REQUIREMENT_MORE_THAN_SEVEN_CHARS: Byte = 7

        // SHORT

        // INT

        // LONG
        private const val DELAY_TIME_MILLIS_2000L: Long = 2000L

        // FLOAT

        // DOUBLE

        // CHARACTER

    }

    // NULL
    private val NULL = null

    // --------------------------------- /CONSTANT DECLARATIONS -----------------------------------


    // ---------------------------------- INSTANCE DECLARATIONS -----------------------------------
    val uiState = MutableStateFlow(AuthenticationUiState())
    // --------------------------------- /INSTANCE DECLARATIONS -----------------------------------


    // ---------------------------------- FUNCTION DECLARATIONS -----------------------------------
    fun handleEvent(authenticationEvent: AuthenticationEvent) {

        when (authenticationEvent) {
            is AuthenticationToggleModeEvent -> {
                toggleAuthenticationMode()
            }
            is EmailChangedEvent -> {
                updateEmail(authenticationEvent.emailAddress)
            }
            is PasswordChangedEvent -> {
                updatePassword(authenticationEvent.password)
            }
            is AuthenticateEvent -> {
                authenticate()
            }
            is ErrorDismissedEvent -> {
                dismissError()
            }
        }

    }

    private fun toggleAuthenticationMode() {

        val authenticationMode = uiState.value.authenticationMode

        val newAuthenticationMode = if (authenticationMode == SIGN_IN) {
            SIGN_UP
        } else {
            SIGN_IN
        }

        uiState.value = uiState.value.copy(
            authenticationMode = newAuthenticationMode
        )

    }

    private fun updateEmail(email: String) {

        uiState.value = uiState.value.copy(
            email = email
        )

    }

    private fun updatePassword(password: String) {

        val requirements = mutableListOf<PasswordRequirement>()

        if (password.length > PASSWORD_REQUIREMENT_MORE_THAN_SEVEN_CHARS) {
            requirements.add(EIGHT_CHARACTERS)
        }

        if (password.any { it.isUpperCase() }) {
            requirements.add(CAPITAL_LETTER)
        }

        if (password.any { it.isDigit() }) {
            requirements.add(NUMBER)
        }

        uiState.value = uiState.value.copy(
            password = password,
            passwordRequirements = requirements.toList()
        )
    }

    private fun authenticate() {

        uiState.value = uiState.value.copy(
            isLoading = TRUE
        )

        viewModelScope.launch(Dispatchers.IO) {
            // Simulates network request
            delay(timeMillis = DELAY_TIME_MILLIS_2000L)
//            delay(timeMillis = 1L)

            withContext(Dispatchers.Main) {
                uiState.value = uiState.value.copy(
                    isLoading = FALSE,
                    error = R.string.error_message_something_went_wrong
                )
            }
        }

    }

    private fun dismissError() {

        uiState.value = uiState.value.copy(
            error = NULL
        )

    }

    // --------------------------------- /FUNCTION DECLARATIONS -----------------------------------

}