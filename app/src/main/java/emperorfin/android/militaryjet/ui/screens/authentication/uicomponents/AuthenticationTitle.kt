/**
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

package emperorfin.android.militaryjet.ui.screens.authentication.uicomponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import emperorfin.android.militaryjet.R
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATION_TITLE


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


@Composable
fun AuthenticationTitle(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode
) {

    Text(
        modifier = Modifier
            .testTag(tag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE),
        text = stringResource(
            id = if (authenticationMode == SIGN_IN) {
                R.string.label_sign_in_to_account
            } else {
                R.string.label_sign_up_for_account
            }
        ), 
        fontSize = FONT_SIZE_SP_24,
        fontWeight = FontWeight.Black
    )

}

@Composable
@Preview(showBackground = true)
private fun AuthenticationTitlePreview() {
    ComposeEmailAuthenticationTheme {
        AuthenticationTitle(
            authenticationMode = SIGN_IN
        )
    }
}

private val FONT_SIZE_SP_24: TextUnit = 24.sp