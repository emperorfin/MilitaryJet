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

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import emperorfin.android.components.ui.fortesting.textFieldKeyboardOptionsImeAction
import emperorfin.android.components.ui.fortesting.textFieldKeyboardOptionsKeyboardType
import emperorfin.android.components.ui.fortesting.textFieldLeadingIconContentDescription
import emperorfin.android.components.ui.fortesting.textFieldLeadingIconImageVector
import emperorfin.android.components.ui.fortesting.textFieldSingleLine
import emperorfin.android.components.R
import emperorfin.android.components.ui.fortesting.*
import emperorfin.android.components.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_EMAIL


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    isTest: Boolean = FALSE,
    email: String?,
    onEmailChanged: (email: String) -> Unit,
    onNextClicked: () -> Unit
) {

    val emailOrEmptyString: String = email ?: ""

    val textFieldValue = TextFieldValue(
        text = emailOrEmptyString,
        selection = TextRange(index = email?.length ?: STRING_LENGTH_0)
    )

    val leadingIconImageVector: ImageVector = Icons.Default.Email
    val leadingIconContentDescription: String = stringResource(
        id = R.string.content_description_email_input_leading_icon
    )

    val singleLine: Boolean = TRUE

    val keyboardOptionsKeyboardType: KeyboardType = KeyboardType.Email
    val keyboardOptionsImeAction: ImeAction = ImeAction.Next

    val modifier2: Modifier = modifier
        .testTag(TAG_AUTHENTICATION_INPUT_EMAIL)
        // For testing
        .semantics {
//            this.textFieldLeadingIconImageVector = leadingIconImageVector // Or
            textFieldLeadingIconImageVector = leadingIconImageVector

            textFieldLeadingIconContentDescription = leadingIconContentDescription

            textFieldKeyboardOptionsKeyboardType = keyboardOptionsKeyboardType

            textFieldKeyboardOptionsImeAction = keyboardOptionsImeAction

            textFieldSingleLine = singleLine
        }

    val label: @Composable () -> Unit = {
        Text(
            text = stringResource(id = R.string.label_email)
        )
    }

    val leadingIcon: @Composable () -> Unit = {
        Icon(
//                imageVector = Icons.Default.Email,
            imageVector = leadingIconImageVector,
            contentDescription = leadingIconContentDescription
        )
    }

    val keyboardOptions = KeyboardOptions(
        keyboardType = keyboardOptionsKeyboardType,
        imeAction = keyboardOptionsImeAction,
    )

    val keyboardActions = KeyboardActions(
        onNext = {
            onNextClicked()
        }
    )

    if (!isTest) {
        TextField(
            modifier = modifier2,
            value = emailOrEmptyString,
            onValueChange = { emailAddress ->
                onEmailChanged(emailAddress)
            },
            label = label,
            singleLine = singleLine,
            leadingIcon = leadingIcon,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
    } else {
        TextField(
            modifier = modifier2,
            value = textFieldValue,
            onValueChange = { emailTextFieldValue ->
                onEmailChanged(emailTextFieldValue.text)
            },
            label = label,
            singleLine = singleLine,
            leadingIcon = leadingIcon,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
    }

}

@Composable
@Preview(showBackground = true)
private fun EmailInputPreview() {
    ComposeEmailAuthenticationTheme {
        EmailInput(
            email = "email",
            onEmailChanged = {},
            onNextClicked = {}
        )
    }
}

private const val FALSE: Boolean = false
private const val TRUE: Boolean = true

private const val STRING_LENGTH_0: Int = 0

