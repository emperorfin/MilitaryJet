/**
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

package emperorfin.android.militaryjet.ui.utils

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.AuthenticationToggleMode
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.FALSE
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.TRUE
import emperorfin.android.militaryjet.ui.constants.StringConstants.THIS_STRING_MUST_BE_EMPTY
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_NEED_AN_ACCOUNT
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_ALREADY_HAVE_AN_ACCOUNT


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 30th March, 2023.
 */


class AuthenticationToggleModeTestUtil2(
    private val mContext: Context,
    private val mTargetContext: Context,
    private val composeTestRule: ComposeContentTestRule
) {

    fun setContentAsAuthenticationToggleModeAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        authenticationMode: AuthenticationMode,
        toggleAuthentication: () -> Unit = { }
    ) {

        setContentAsAuthenticationToggleMode(
            composeTestRule = composeTestRule,
            authenticationMode = authenticationMode,
            toggleAuthentication = toggleAuthentication
        )

        if (authenticationMode == SIGN_IN) {
            assertAuthenticationToggleModeAndTextExactlyNeedAnAccountIsDisplayed()
        } else if (authenticationMode == SIGN_UP) {
            assertAuthenticationToggleModeAndTextExactlyAlreadyHaveAnAccountIsDisplayed()
        }

    }

    private fun setContentAsAuthenticationToggleMode(
        composeTestRule: ComposeContentTestRule,
        authenticationMode: AuthenticationMode,
        toggleAuthentication: () -> Unit
    ) {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationToggleMode(
                    authenticationMode = authenticationMode,
                    toggleAuthentication = toggleAuthentication
                )
            }
        }

    }

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationToggleMode]
     * composable in the [ComposeContentTestRule.setContent] whenever the [AuthenticationMode] is
     * [SIGN_IN]
     */
    private fun assertAuthenticationToggleModeAndTextExactlyNeedAnAccountIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationToggleModeAndTextExactlyNeedAnAccount()
            .assertIsDisplayed()

    }

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationToggleMode]
     * composable in the [ComposeContentTestRule.setContent] whenever the [AuthenticationMode] is
     * [SIGN_UP]
     */
    private fun assertAuthenticationToggleModeAndTextExactlyAlreadyHaveAnAccountIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationToggleModeAndTextExactlyAlreadyHaveAnAccount()
            .assertIsDisplayed()

    }

    fun onNodeWithAuthenticationToggleModeAndTextExactlyNeedAnAccount(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationToggleModeAnd(
            otherMatcher = hasTextExactlyNeedAnAccount(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithAuthenticationToggleModeAndTextExactlyAlreadyHaveAnAccount(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationToggleModeAnd(
            otherMatcher = hasTextExactlyAlreadyHaveAnAccount(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationToggleModeAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagAuthenticationToggleMode().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun hasTestTagAuthenticationToggleMode(): SemanticsMatcher {

        return hasTestTagsAuthenticationToggleModeAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagsAuthenticationToggleModeAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON + otherTestTag
        )

    }

    // Before using a semantics matcher, check the implementation of the utility functions in this
    // section if it's already available to avoid duplication.
    // The function names make the check easier.

    private fun hasTextExactlyNeedAnAccount(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_NEED_AN_ACCOUNT),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAlreadyHaveAnAccount(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_ALREADY_HAVE_AN_ACCOUNT),
            includeEditableText = FALSE
        )

    }

}