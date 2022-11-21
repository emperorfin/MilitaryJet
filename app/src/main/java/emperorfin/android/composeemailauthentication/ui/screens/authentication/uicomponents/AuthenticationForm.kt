package emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import emperorfin.android.composeemailauthentication.R
import emperorfin.android.composeemailauthentication.ui.res.fractionResource
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.PasswordRequirements
import emperorfin.android.composeemailauthentication.ui.res.theme.ComposeEmailAuthenticationTheme


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


@Composable
fun AuthenticationForm(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    onToggleMode: () -> Unit,
    email: String?,
    onEmailChanged: (email: String) -> Unit,
    password: String?,
    onPasswordChanged: (password: String) -> Unit,
    completedPasswordRequirements: List<PasswordRequirements>,
    enableAuthentication: Boolean,
    onAuthenticate: () -> Unit
) {

    val passwordInputFocusRequester = FocusRequester()

    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier.height(
                height = dimensionResource(id = R.dimen.height_32_spacer)
            )
        )

        AuthenticationTitle(
            modifier = Modifier.fillMaxWidth(), authenticationMode = authenticationMode
        )

        Spacer(
            modifier = Modifier.height(
                height = dimensionResource(id = R.dimen.height_40_spacer)
            )
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_horizontal_32)),
            elevation = cardElevation(
                defaultElevation = dimensionResource(id = R.dimen.card_elevation_4)
            )
        ) {
            Column(
                modifier = Modifier.padding(
                    all = dimensionResource(id = R.dimen.padding_all_16)
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EmailInput(
                    modifier = Modifier.fillMaxWidth(),
                    email = email ?: "",
                    onEmailChanged = onEmailChanged,
                    onNextClicked = {
                        passwordInputFocusRequester.requestFocus()
                    }
                )

                Spacer(
                    modifier = Modifier.height(
                        height = dimensionResource(id = R.dimen.height_16_spacer)
                    )
                )

                PasswordInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordInputFocusRequester),
                    password = password ?: "",
                    onPasswordChanged = onPasswordChanged,
                    onDoneClicked = onAuthenticate
                )

                Spacer(
                    modifier = Modifier.height(
                        height = dimensionResource(id = R.dimen.height_12_spacer)
                    )
                )

                AnimatedVisibility(
                    visible = authenticationMode == SIGN_UP
                ) {
                    PasswordRequirements(
                        satisfiedRequirements = completedPasswordRequirements
                    )
                }

                Spacer(
                    modifier = Modifier.height(
                        height = dimensionResource(id = R.dimen.height_12_spacer)
                    )
                )

                AuthenticationButton(
                    enableAuthentication = enableAuthentication,
                    authenticationMode = authenticationMode,
                    onAuthenticate = onAuthenticate
                )
            }
        }

        Spacer(
            modifier = Modifier.weight(
//                        weight = 1f
                weight = fractionResource(id = R.fraction.weight_1)
            )
        )

        AuthenticationToggleMode(
            modifier = Modifier.fillMaxWidth(),
            authenticationMode = authenticationMode,
//                    toggleAuthentication = onToggleMode // OR
            toggleAuthentication = {
                onToggleMode()
            }
        )
    }

}

@Composable
@Preview(showBackground = true)
private fun AuthenticationFormPreview() {
    ComposeEmailAuthenticationTheme {
        AuthenticationForm(
            authenticationMode = SIGN_IN,
            onToggleMode = {},
            email = NULL,
            onEmailChanged = {},
            password = "password",
            onPasswordChanged = {},
            completedPasswordRequirements = emptyList(),
            enableAuthentication = TRUE,
            onAuthenticate = {}

        )
    }
}

private const val TRUE: Boolean = true

private val NULL = null