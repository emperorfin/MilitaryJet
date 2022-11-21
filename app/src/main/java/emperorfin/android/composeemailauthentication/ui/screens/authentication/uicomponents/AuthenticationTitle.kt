package emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import emperorfin.android.composeemailauthentication.R
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.composeemailauthentication.ui.res.theme.ComposeEmailAuthenticationTheme


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