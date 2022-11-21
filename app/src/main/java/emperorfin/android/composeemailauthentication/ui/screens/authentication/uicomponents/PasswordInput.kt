package emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import emperorfin.android.composeemailauthentication.R
import emperorfin.android.composeemailauthentication.ui.res.theme.ComposeEmailAuthenticationTheme


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInput(
    modifier: Modifier = Modifier,
    password: String?,
    onPasswordChanged: (password: String) -> Unit,
    onDoneClicked: () -> Unit
) {

    var isPasswordHidden by remember { mutableStateOf(value = TRUE) }

    TextField(
        modifier = modifier,
        value = password ?: "",
        onValueChange = {
            onPasswordChanged(it)
        },
        singleLine = TRUE,
        label = {
            Text(
                text = stringResource(id = R.string.label_password)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = NULL
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable(
                    onClickLabel = if (isPasswordHidden) {
                        stringResource(id = R.string.content_description_show_password)
                    } else stringResource(id = R.string.content_description_hide_password)
                ) {
                    isPasswordHidden = !isPasswordHidden
                },
                imageVector = if (isPasswordHidden) {
                    Icons.Default.Visibility
                } else Icons.Default.VisibilityOff,
                contentDescription = NULL
            )
        },
        visualTransformation = if (isPasswordHidden) {
            PasswordVisualTransformation()
        } else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
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

private val NULL = null
