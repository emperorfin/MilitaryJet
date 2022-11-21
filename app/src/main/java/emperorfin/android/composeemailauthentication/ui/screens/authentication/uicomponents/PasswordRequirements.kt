package emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.PasswordRequirements


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


@Composable
fun PasswordRequirements(
    modifier: Modifier = Modifier,
    satisfiedRequirements: List<PasswordRequirements>
) {
    
    Column(modifier = modifier) {
        PasswordRequirements.values().forEach { requirement ->
            Requirement(
                message = stringResource(id = requirement.label),
                satisfied = satisfiedRequirements.contains(requirement)
            )
        }
    }
    
}