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

package emperorfin.android.militaryjet.ui.utils

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.AuthenticationTitle
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATION_TITLE
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.FALSE
import emperorfin.android.militaryjet.ui.constants.StringConstants.THIS_STRING_MUST_BE_EMPTY
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_SIGN_IN_TO_YOUR_ACCOUNT
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_SIGN_UP_FOR_AN_ACCOUNT


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 30th March, 2023.
 */


class AuthenticationTitleTestUtil2(
    private val mContext: Context,
    private val mTargetContext: Context,
    private val composeTestRule: ComposeContentTestRule
) {

    fun setContentAsAuthenticationTitleAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        authenticationMode: AuthenticationMode
    ) {

        setContentAsAuthenticationTitle(
            composeTestRule = composeTestRule,
            authenticationMode = authenticationMode
        )

        if (authenticationMode == SIGN_IN) {
            assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()
        } else if (authenticationMode == SIGN_UP) {
            assertAuthenticationTitleAndTextExactlySignUpForAnAccountIsDisplayed()
        }

    }

    private fun setContentAsAuthenticationTitle(
        composeTestRule: ComposeContentTestRule,
        authenticationMode: AuthenticationMode
    ) {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationTitle(
                    authenticationMode = authenticationMode
                )
            }
        }

    }

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationTitle]
     * composable in the [ComposeContentTestRule.setContent] whenever the [AuthenticationMode] is
     * [SIGN_IN]
     */
    private fun assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationTitleAndTextExactlySignInToYourAccount()
            .assertIsDisplayed()

    }

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationTitle]
     * composable in the [ComposeContentTestRule.setContent] whenever the [AuthenticationMode] is
     * [SIGN_UP]
     */
    private fun assertAuthenticationTitleAndTextExactlySignUpForAnAccountIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationTitleAndTextExactlySignUpForAnAccount()
            .assertIsDisplayed()

    }

    private fun onNodeWithAuthenticationTitleAndTextExactlySignInToYourAccount(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationTitleAnd(
            otherMatcher = hasTextExactlySignInToYourAccount(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationTitleAndTextExactlySignUpForAnAccount(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationTitleAnd(
            otherMatcher = hasTextExactlySignUpForAnAccount(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationTitleAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagAuthenticationTitle().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun hasTestTagAuthenticationTitle(): SemanticsMatcher {

        return hasTestTagsAuthenticationTitleAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagsAuthenticationTitleAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE + otherTestTag
        )

    }

    // Before using a semantics matcher, check the implementation of the utility functions in this
    // section if it's already available to avoid duplication.
    // The function names make the check easier.

    private fun hasTextExactlySignInToYourAccount(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_SIGN_IN_TO_YOUR_ACCOUNT),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlySignUpForAnAccount(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_SIGN_UP_FOR_AN_ACCOUNT),
            includeEditableText = FALSE
        )

    }

}