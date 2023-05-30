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

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import emperorfin.android.components.R
import emperorfin.android.components.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.components.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.components.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.components.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


@Composable
fun AuthenticationButton(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    enableAuthentication: Boolean,
    onAuthenticate: () -> Unit
) {

    Button(
        modifier = modifier.testTag(TAG_AUTHENTICATION_AUTHENTICATE_BUTTON),
        onClick = {
            onAuthenticate()
        },
        enabled = enableAuthentication
    ) {
        Text(
            text = stringResource(
                id = if (authenticationMode == SIGN_IN) {
                    R.string.action_sign_in
                } else {
                    R.string.action_sign_up
                }
            )
        )
    }
    
}

@Composable
@Preview(showBackground = true)
private fun AuthenticationButtonPreview() {

    ComposeEmailAuthenticationTheme {
        AuthenticationButton(
            authenticationMode = SIGN_UP,
            enableAuthentication = TRUE,
            onAuthenticate = {}
        )
    }

}

private const val TRUE: Boolean = true