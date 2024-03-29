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
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import emperorfin.android.components.ui.screens.authentication.uicomponents.AuthenticationButton
import emperorfin.android.components.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.components.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.components.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.components.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
import emperorfin.android.components.ui.constants.BooleanConstants.FALSE
import emperorfin.android.components.ui.constants.StringConstants.THIS_STRING_MUST_BE_EMPTY
import emperorfin.android.components.ui.constants.StringResourceConstants.STRING_RES_SIGN_IN
import emperorfin.android.components.ui.constants.StringResourceConstants.STRING_RES_SIGN_UP


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Wednesday 22nd March, 2023.
 */


class AuthenticationButtonTestUtil2(
    private val mContext: Context,
    private val mTargetContext: Context,
    private val composeTestRule: ComposeContentTestRule
) {

    fun setContentAsAuthenticationButtonAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        authenticationMode: AuthenticationMode,
        enableAuthentication: Boolean,
        onAuthenticate: () -> Unit = { }
    ) {

        setContentAsAuthenticationButton(
            composeTestRule = composeTestRule,
            authenticationMode = authenticationMode,
            enableAuthentication = enableAuthentication,
            onAuthenticate = onAuthenticate
        )

        if (authenticationMode == SIGN_IN) {
            assertAuthenticationButtonAndTextExactlySignInIsDisplayed()
        } else if (authenticationMode == SIGN_UP) {
            assertAuthenticationButtonAndTextExactlySignUpIsDisplayed()
        }

    }

    private fun setContentAsAuthenticationButton(
        composeTestRule: ComposeContentTestRule,
        authenticationMode: AuthenticationMode,
        enableAuthentication: Boolean,
        onAuthenticate: () -> Unit
    ) {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = authenticationMode,
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = onAuthenticate
                )
            }
        }

    }

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationButton]
     * composable in the [ComposeContentTestRule.setContent] whenever the [AuthenticationMode] is
     * [SIGN_IN]
     */
    private fun assertAuthenticationButtonAndTextExactlySignInIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .apply {
                assertIsDisplayed()

                assertTextEqualsSignIn(this)
            }

    }

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationButton]
     * composable in the [ComposeContentTestRule.setContent] whenever the [AuthenticationMode] is
     * [SIGN_UP]
     */
    private fun assertAuthenticationButtonAndTextExactlySignUpIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertTextEqualsSignUp(this)
            }

    }

    fun onNodeWithAuthenticationButtonAndTextExactlySignIn(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationButtonAnd(
            otherMatcher = hasTextExactlySignIn(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithAuthenticationButtonAndTextExactlySignUp(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationButtonAnd(
            otherMatcher = hasTextExactlySignUp(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationButtonAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagAuthenticationButton().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun hasTestTagAuthenticationButton(): SemanticsMatcher {

        return hasTestTagsAuthenticationButtonAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagsAuthenticationButtonAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON + otherTestTag
        )

    }

    // Before using a semantics matcher, check the implementation of the utility functions in this
    // section if it's already available to avoid duplication.
    // The function names make the check easier.

    private fun hasTextExactlySignUp(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_SIGN_UP),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlySignIn(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_SIGN_IN),
            includeEditableText = FALSE
        )

    }

    private fun assertTextEqualsSignUp(
        semanticsNodeInteraction: SemanticsNodeInteraction
    ): SemanticsNodeInteraction {

        return semanticsNodeInteraction.assertTextEquals(
            mContext.getString(STRING_RES_SIGN_UP),
            includeEditableText = FALSE
        )

    }

    private fun assertTextEqualsSignIn(
        semanticsNodeInteraction: SemanticsNodeInteraction
    ): SemanticsNodeInteraction {

        return semanticsNodeInteraction.assertTextEquals(
            mContext.getString(STRING_RES_SIGN_IN),
            includeEditableText = FALSE
        )

    }

}