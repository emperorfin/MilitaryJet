package emperorfin.android.militaryjet.ui.screens.authentication.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import emperorfin.android.militaryjet.R
import emperorfin.android.militaryjet.ui.fortesting.*
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    // Test (that should pass) fails while the TextField's visualTransformation property is set to
    // PasswordVisualTransformation(). This was included so that the value of the TextField's
    // visualTransformation could be changed to VisualTransformation.None during testing.
    passwordVisualTransformation: VisualTransformation = PasswordVisualTransformation(),
    password: String?,
    onPasswordChanged: (password: String) -> Unit,
    onDoneClicked: () -> Unit
) {

    var isPasswordHidden by remember { mutableStateOf(value = TRUE) }

    val leadingIconImageVector: ImageVector = Icons.Default.Lock
    val leadingIconContentDescription: String = stringResource(
        id = R.string.content_description_password_input_leading_icon
    )

    val trailingIconClickLabel: String = if (isPasswordHidden) {
        stringResource(id = R.string.content_description_show_password)
    } else stringResource(id = R.string.content_description_hide_password)
    val trailingIconImageVector: ImageVector = if (isPasswordHidden) {
        Icons.Default.Visibility
    } else Icons.Default.VisibilityOff
    val trailingIconContentDescription: String = if (isPasswordHidden) {
        stringResource(
            id = R.string.content_description_password_input_trailing_icon_password_hidden
        )
    } else {
        stringResource(
            id = R.string.content_description_password_input_trailing_icon_password_shown
        )
    }

    val singleLine: Boolean = TRUE

    val keyboardOptionsKeyboardType: KeyboardType = KeyboardType.Password
    val keyboardOptionsImeAction: ImeAction = ImeAction.Done

    val visualTransformation: VisualTransformation = if (isPasswordHidden) {
        passwordVisualTransformation
    } else VisualTransformation.None

    val softKeyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    TextField(
        modifier = modifier
            .testTag(TAG_AUTHENTICATION_INPUT_PASSWORD)
            // For testing
            .semantics {
                textFieldLeadingIconImageVector = leadingIconImageVector
                textFieldLeadingIconContentDescription = leadingIconContentDescription

                textFieldTrailingIconClickLabel = trailingIconClickLabel
                textFieldTrailingIconImageVector = trailingIconImageVector
                textFieldTrailingIconContentDescription = trailingIconContentDescription

                textFieldKeyboardOptionsKeyboardType = keyboardOptionsKeyboardType
                textFieldKeyboardOptionsImeAction = keyboardOptionsImeAction

                textFieldSingleLine = singleLine

                textFieldVisualTransformation = visualTransformation
            },
        value = password ?: "",
        onValueChange = {
            onPasswordChanged(it)
        },
        singleLine = singleLine,
        label = {
            Text(
                text = stringResource(id = R.string.label_password)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIconImageVector,
                contentDescription = leadingIconContentDescription
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .testTag(tag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON)
                    // If you want to make the password_Toggled_Reflects_State() test in
                    // app/src/androidTest/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/PasswordInputTest.kt
                    // to pass, this testTag would have to be uncommented while commenting out
                    // testTag on this Modifier.
                    // This is another approach of testing if especially if you don't want to
                    // include test code in the actual code or if you don't want the test to
                    // how the actual code is written.
                    // For example, due to testing, this PasswordInput composable was refactored to
                    // include the passwordVisualTransformation parameter with a default value.
//                    .testTag(
//                        tag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON_PASSWORD_HIDDEN +
//                                isPasswordHidden
//                    )
                    .clickable(
                    onClickLabel = trailingIconClickLabel
                ) {
                    isPasswordHidden = !isPasswordHidden
                },
                imageVector = trailingIconImageVector,
                contentDescription = trailingIconContentDescription
            )
        },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardOptionsKeyboardType,
            imeAction = keyboardOptionsImeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {

                // This may not be necessary as the keyboard closes after recomposition.
                softKeyboardController?.hide()

                onDoneClicked()
            }
        )
    )

}

@Composable
@Preview(showBackground = true)
private fun PasswordInputPreview() {
    ComposeEmailAuthenticationTheme {
        PasswordInput(
            password = "password",
            onPasswordChanged = {},
            onDoneClicked = {}
        )
    }
}

private const val TRUE: Boolean = true
