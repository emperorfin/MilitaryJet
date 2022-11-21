package emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import emperorfin.android.composeemailauthentication.R
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.composeemailauthentication.ui.res.theme.ComposeEmailAuthenticationTheme


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
        modifier = modifier,
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
            enableAuthentication = TRUE
            ,
            onAuthenticate = {}
        )
    }

}

private const val TRUE: Boolean = true