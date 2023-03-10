package emperorfin.android.militaryjet.ui.screens.authentication.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import emperorfin.android.militaryjet.R
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


@Composable
fun AuthenticationToggleMode(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    toggleAuthentication: () -> Unit
) {
    
    Surface(
        modifier = modifier.padding(
            top = dimensionResource(id = R.dimen.padding_top_16),
        ),
        shadowElevation = dimensionResource(id = R.dimen.surface_elevation_8)
    ) {
        TextButton(
            modifier = Modifier
                .testTag(TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON)
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(all = dimensionResource(id = R.dimen.padding_all_8)),
//            onClick = toggleAuthentication // OR
            onClick = {
                toggleAuthentication()
            }
        ) {
            Text(
                text = stringResource(
                    id = if (authenticationMode == SIGN_IN) {
                        R.string.action_need_account
                    } else {
                        R.string.action_already_have_account
                    }
                )
            )
        }
    }

}

@Composable
@Preview(showBackground = true)
private fun AuthenticationToggleModePreview() {

    ComposeEmailAuthenticationTheme {
        AuthenticationToggleMode(
            authenticationMode = SIGN_IN,
            toggleAuthentication = {}
        )
    }

}