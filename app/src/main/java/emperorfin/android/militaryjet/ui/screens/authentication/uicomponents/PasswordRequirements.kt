package emperorfin.android.militaryjet.ui.screens.authentication.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENTS


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


@Composable
fun PasswordRequirements(
    modifier: Modifier = Modifier,
    satisfiedRequirements: List<PasswordRequirement>
) {
    
    Column(
        modifier = modifier.testTag(tag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENTS)
    ) {
        PasswordRequirement.values().forEach { requirement ->
            Requirement(
                message = stringResource(id = requirement.label),
                satisfied = satisfiedRequirements.contains(requirement)
            )
        }
    }
    
}

@Composable
@Preview(showBackground = TRUE)
private fun PasswordRequirementsPreview() {

    ComposeEmailAuthenticationTheme {
        PasswordRequirements(satisfiedRequirements = emptyList())
    }

}

private const val TRUE: Boolean = true