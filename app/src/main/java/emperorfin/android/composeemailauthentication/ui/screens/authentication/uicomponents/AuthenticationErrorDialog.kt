package emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import emperorfin.android.composeemailauthentication.R
import emperorfin.android.composeemailauthentication.ui.res.theme.ComposeEmailAuthenticationTheme


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

    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onDismissError()
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                TextButton(onClick = { onDismissError() }) {
                    Text(
                        text = stringResource(id = R.string.error_action)
                    )
                }
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.error_title),
                fontSize = FONT_SIZE_SP_18
            )
        },
        text = {
            Text(
                text = stringResource(id = error)
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