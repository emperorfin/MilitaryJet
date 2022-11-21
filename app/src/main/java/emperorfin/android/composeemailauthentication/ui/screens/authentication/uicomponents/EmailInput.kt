package emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import emperorfin.android.composeemailauthentication.R
import emperorfin.android.composeemailauthentication.ui.res.theme.ComposeEmailAuthenticationTheme


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    email: String?,
    onEmailChanged: (email: String) -> Unit,
    onNextClicked: () -> Unit
) {
    
    TextField(
        modifier = modifier,
        value = email ?: "",
        onValueChange = { emailAddress ->
            onEmailChanged(emailAddress)
        },
        label = {
            Text(
                text = stringResource(id = R.string.label_email)
            )
        },
        singleLine = TRUE,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = NULL
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                onNextClicked()
            }
        )
    )
    
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

private const val TRUE: Boolean = true

private val NULL = null

