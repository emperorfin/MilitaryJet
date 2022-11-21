package emperorfin.android.composeemailauthentication.ui.screens.authentication

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import emperorfin.android.composeemailauthentication.ui.screens.authentication.stateholders.AuthenticationViewModel
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.AuthenticationContent
import emperorfin.android.composeemailauthentication.ui.res.theme.ComposeEmailAuthenticationTheme


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


@Composable
fun AuthenticationScreen() {
//    MaterialTheme {
//
//    }

    val viewModel: AuthenticationViewModel = viewModel()

    AuthenticationContent(
        modifier = Modifier.fillMaxWidth(),
        uiState = viewModel.uiState.collectAsState().value,
        handleEvent = viewModel::handleEvent
    )
}

@Composable
@Preview(showBackground = true)
private fun AuthenticationScreenPreview() {
    ComposeEmailAuthenticationTheme {
        AuthenticationScreen()
    }
}