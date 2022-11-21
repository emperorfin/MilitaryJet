package emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
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
fun Requirement(
    modifier: Modifier = Modifier,
    message: String,
    satisfied: Boolean
) {

    val tint: Color = if (satisfied) {
//        MaterialTheme.colorScheme.onSurface
        MaterialTheme.colorScheme.primary
    } else MaterialTheme.colorScheme.onSurface.copy(alpha = COLOR_ALPHA)

    val requirementStatus: String = if (satisfied) {
        stringResource(id = R.string.password_requirement_satisfied, message)
    } else {
        stringResource(id = R.string.password_requirement_needed, message)
    }

    Row(
        modifier = Modifier
            .padding(
                all = dimensionResource(id = R.dimen.padding_all_6)
            )
            .semantics(mergeDescendants = TRUE) {
//            this.text = AnnotatedString(requirementStatus) // Or
                text = AnnotatedString(requirementStatus)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(
                size = dimensionResource(id = R.dimen.size_12)
            ),
            imageVector = Icons.Default.Check,
            contentDescription = NULL,
            tint = tint
        )

        Spacer(
            modifier = Modifier.width(
                width = dimensionResource(id = R.dimen.width_8)
            )
        )

        Text(
            modifier = Modifier.clearAndSetSemantics {  },
            text = message,
            fontSize = FONT_SIZE_SP_12,
            color = tint
        )
    }

}

@Composable
@Preview(showBackground = TRUE)
private fun RequirementPreview() {
    ComposeEmailAuthenticationTheme {
        Requirement(
            modifier = Modifier,
            message = "message",
            satisfied = TRUE
        )
    }
}

private const val TRUE: Boolean = true

private const val COLOR_ALPHA: Float = 0.4f

private val FONT_SIZE_SP_12: TextUnit = 12.sp

private val NULL = null