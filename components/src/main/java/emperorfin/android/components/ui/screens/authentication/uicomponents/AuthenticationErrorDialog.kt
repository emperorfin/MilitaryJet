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

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import emperorfin.android.components.R
import emperorfin.android.components.ui.fortesting.alertDialogConfirmButtonText
import emperorfin.android.components.ui.fortesting.alertDialogText
import emperorfin.android.components.ui.fortesting.alertDialogTitle
import emperorfin.android.components.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_ERROR_DIALOG
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_ERROR_DIALOG_CONFIRM_BUTTON


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


@Composable
fun AuthenticationErrorDialog(
    modifier: Modifier = Modifier,
    @StringRes error: Int,
    onDismissError: () -> Unit
) {

    val alertDialogTitle: String = stringResource(id = R.string.error_title)
    val alertDialogText: String = stringResource(id = error)
    val alertDialogConfirmButtonText: String = stringResource(id = R.string.error_action_ok)

    AlertDialog(
        modifier = modifier
            .testTag(tag = TAG_AUTHENTICATION_ERROR_DIALOG)
            .semantics {
                this.alertDialogTitle = alertDialogTitle
                this.alertDialogText = alertDialogText
                this.alertDialogConfirmButtonText = alertDialogConfirmButtonText
            },
        onDismissRequest = {
            onDismissError()
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                TextButton(
                    onClick = { onDismissError() },
                    modifier = Modifier.testTag(tag = TAG_AUTHENTICATION_ERROR_DIALOG_CONFIRM_BUTTON)
                ) {
                    Text(
                        text = alertDialogConfirmButtonText
                    )
                }
            }
        },
        title = {
            Text(
                text = alertDialogTitle,
                fontSize = FONT_SIZE_SP_18
            )
        },
        text = {
            Text(
                text = alertDialogText
            )
        }
    )

}

@Composable
@Preview(showBackground = true)
private fun AuthenticationErrorDialogPreview() {

    ComposeEmailAuthenticationTheme {
        AuthenticationErrorDialog(
            error = R.string.preview_message,
            onDismissError = {}
        )
    }

}

private val FONT_SIZE_SP_18: TextUnit = 18.sp