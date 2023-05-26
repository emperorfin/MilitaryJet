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

package emperorfin.android.components.ui.screens.authentication.uicomponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import emperorfin.android.components.ui.fortesting.circularProgressIndicatorColorArgb
import emperorfin.android.components.ui.screens.authentication.stateholders.AuthenticationUiState
import emperorfin.android.components.ui.screens.authentication.events.AuthenticationEvent
import emperorfin.android.components.ui.screens.authentication.events.AuthenticationEvent.EmailChangedEvent
import emperorfin.android.components.ui.screens.authentication.events.AuthenticationEvent.PasswordChangedEvent
import emperorfin.android.components.ui.screens.authentication.events.AuthenticationEvent.AuthenticateEvent
import emperorfin.android.components.ui.screens.authentication.events.AuthenticationEvent.ErrorDismissedEvent
import emperorfin.android.components.ui.screens.authentication.events.AuthenticationEvent.AuthenticationToggleModeEvent
import emperorfin.android.components.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PROGRESS
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_CONTENT


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


@Composable
fun AuthenticationContent(
    modifier: Modifier = Modifier,
    uiState: AuthenticationUiState,
    handleEvent: (event: AuthenticationEvent) -> Unit
) {

    Box(
        modifier = modifier
            .testTag(tag = TAG_AUTHENTICATION_CONTENT)
            /*.semantics(mergeDescendants = true){}*/,
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading) {
            val defaultPresetColor: Color = ProgressIndicatorDefaults.circularColor

            CircularProgressIndicator(
                modifier = Modifier
                    .testTag(tag = TAG_AUTHENTICATION_PROGRESS)
                    .semantics{
                        circularProgressIndicatorColorArgb = defaultPresetColor.toArgb()
                    },
                // This is optional if just for testing purposes with the preset color.
                color = defaultPresetColor
            )
        } else {
            AuthenticationForm(
                modifier = Modifier.fillMaxSize(),
                authenticationMode = uiState.authenticationMode,
                onToggleMode = {
                    handleEvent(
                        AuthenticationToggleModeEvent
                    )
                },
                email = uiState.email,
                onEmailChanged = { emailAddress ->
                    handleEvent(
                        EmailChangedEvent(emailAddress)
                    )
                },
                password = uiState.password,
                onPasswordChanged = { password ->
                    handleEvent(
                        PasswordChangedEvent(password)
                    )
                },
                completedPasswordRequirements = uiState.passwordRequirements,
                enableAuthentication = uiState.checkFormValidity(),
                onAuthenticate = {
                    handleEvent(
                        AuthenticateEvent
                    )
                }
            )

            uiState.error?.let {
                AuthenticationErrorDialog(
                    error = it,
                    onDismissError = {
                        handleEvent(ErrorDismissedEvent)
                    }
                )
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
private fun AuthenticationContentPreview() {
    ComposeEmailAuthenticationTheme {
        AuthenticationContent(uiState = AuthenticationUiState(), handleEvent = {})
    }
}
