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

package emperorfin.android.components.ui.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasAlertDialogConfirmButtonText
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasAlertDialogText
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasAlertDialogTitle
import emperorfin.android.components.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.components.ui.screens.authentication.uicomponents.AuthenticationErrorDialog
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_ERROR_DIALOG
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_ERROR_DIALOG_CONFIRM_BUTTON
import emperorfin.android.components.ui.constants.BooleanConstants.FALSE
import emperorfin.android.components.ui.constants.StringConstants.THIS_STRING_MUST_BE_EMPTY
import emperorfin.android.components.ui.constants.StringResourceConstants.STRING_RES_OK
import emperorfin.android.components.ui.constants.StringResourceConstants.STRING_RES_SOME_ERROR
import emperorfin.android.components.ui.constants.StringResourceConstants.STRING_RES_WHOOPS


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 26th March, 2023.
 */


class AuthenticationErrorDialogTestUtil2(
    private val mContext: Context,
    private val mTargetContext: Context,
    private val composeTestRule: ComposeContentTestRule
) {

    fun setContentAsAuthenticationErrorDialogAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        @StringRes error: Int,
        onDismissError: () -> Unit = { }
    ) {

        setContentAsAuthenticationErrorDialog(
            composeTestRule = composeTestRule,
            error = error,
            onDismissError = onDismissError
        )

        assertAuthenticationErrorDialogAndAlertDialogTitleWhoopsIsDisplayed()

    }

    private fun setContentAsAuthenticationErrorDialog(
        composeTestRule: ComposeContentTestRule,
        @StringRes error: Int,
        onDismissError: () -> Unit
    ) {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationErrorDialog(
                    error = error,
                    onDismissError = onDismissError
                )
            }
        }

    }

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationErrorDialog]
     * composable in the [ComposeContentTestRule.setContent].
     */
    private fun assertAuthenticationErrorDialogAndAlertDialogTitleWhoopsIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops()
            .assertIsDisplayed()

    }

    private fun onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationErrorDialogAnd(
            otherMatcher = hasAlertDialogTitleWhoops(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithAuthenticationErrorDialogAndAlertDialogTextSomeError(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationErrorDialogAnd(
            otherMatcher = hasAlertDialogTextSomeError(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithAuthenticationErrorDialogAndAlertDialogConfirmButtonTextOk(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationErrorDialogAnd(
            otherMatcher = hasAlertDialogConfirmButtonTextOk(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithAuthenticationErrorDialogConfirmButtonAndTextExactlyOk(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationErrorDialogConfirmButtonAnd(
            otherMatcher = hasTextExactlyOk(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationErrorDialogConfirmButtonAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagAuthenticationErrorDialogConfirmButton().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithAuthenticationErrorDialogAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagAuthenticationErrorDialog().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun hasTestTagAuthenticationErrorDialogConfirmButton(): SemanticsMatcher {

        return hasTestTagsAuthenticationErrorDialogConfirmButtonAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagAuthenticationErrorDialog(): SemanticsMatcher {

        return hasTestTagsAuthenticationErrorDialogAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagsAuthenticationErrorDialogConfirmButtonAnd(otherTestTag: String):
            SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_ERROR_DIALOG_CONFIRM_BUTTON + otherTestTag
        )

    }

    private fun hasTestTagsAuthenticationErrorDialogAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_ERROR_DIALOG + otherTestTag
        )

    }

    // Before using a semantics matcher, check the implementation of the utility functions in this
    // section if it's already available to avoid duplication.
    // The function names make the check easier.

    private fun hasAlertDialogTitleWhoops(): SemanticsMatcher {

        return hasAlertDialogTitle(
            alertDialogTitle = mContext.getString(STRING_RES_WHOOPS)
        )

    }

    private fun hasAlertDialogTextSomeError(): SemanticsMatcher {

        return hasAlertDialogText(
            alertDialogText = mContext.getString(STRING_RES_SOME_ERROR)
        )

    }

    private fun hasAlertDialogConfirmButtonTextOk(): SemanticsMatcher {

        return hasAlertDialogConfirmButtonText(
            alertDialogConfirmButtonText = mContext.getString(STRING_RES_OK)
        )

    }

    private fun hasTextExactlyOk(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_OK),
            includeEditableText = FALSE
        )

    }

}