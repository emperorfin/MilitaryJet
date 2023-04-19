/*
 * Copyright 2023 Francis Nwokelo (emperorfin)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package emperorfin.android.militaryjet.ui.screens.authentication.uicomponents

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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import emperorfin.android.militaryjet.R
import emperorfin.android.militaryjet.ui.fortesting.iconContentDescription
import emperorfin.android.militaryjet.ui.fortesting.iconImageVector
import emperorfin.android.militaryjet.ui.fortesting.iconTintArgb
import emperorfin.android.militaryjet.ui.fortesting.textColorArgb
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT_ICON


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


// TODO: Rename this composable from Requirement to PasswordRequirement and this should reflect in
//  occurrences such as in utility function names such as setContentAsRequirement() (to
//  setContentAsPasswordRequirement()) in
//  app/src/androidTest/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/RequirementTest2.kt
//  Also, the following file name should changes accordingly:
//  app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
@Composable
fun Requirement(
    modifier: Modifier = Modifier,
    message: String,
    satisfied: Boolean
) {

    val iconImageVector: ImageVector = Icons.Default.Check
    val iconContentDescription: String = if (satisfied) {
        stringResource(
            id = R.string.content_description_icon_password_requirement_satisfied
        )
    } else {
        stringResource(
            id = R.string.content_description_icon_password_requirement_needed
        )
    }
    val tint: Color = if (satisfied) {
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
                testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT + message

                textColorArgb = tint.toArgb()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .testTag(tag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT_ICON)
                .size(
                    size = dimensionResource(id = R.dimen.size_12)
                )
                .semantics {
                    this.iconImageVector = iconImageVector
                    this.iconContentDescription = iconContentDescription
                    this.iconTintArgb = tint.toArgb()
                },
            imageVector = iconImageVector,
            contentDescription = iconContentDescription,
            tint = tint
        )

        Spacer(
            modifier = Modifier.width(
                width = dimensionResource(id = R.dimen.width_8)
            )
        )

        Text(
            modifier = Modifier
                .clearAndSetSemantics {  },
//                .testTag(tag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT + requirementStatus)
//                .semantics { textColorArgb = tint.toArgb() },
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
