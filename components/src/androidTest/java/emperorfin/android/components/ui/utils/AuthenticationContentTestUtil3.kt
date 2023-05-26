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
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.*
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasCircularProgressIndicatorColorArgb
import emperorfin.android.components.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.components.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.components.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.components.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.components.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.components.ui.screens.authentication.stateholders.AuthenticationUiState
import emperorfin.android.components.ui.screens.authentication.uicomponents.AuthenticationContent
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_CONTENT
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PROGRESS
import emperorfin.android.components.ui.constants.BooleanConstants.FALSE
import emperorfin.android.components.ui.constants.NothingConstants.NULL
import emperorfin.android.components.ui.constants.StringConstants.THIS_STRING_MUST_BE_EMPTY
import emperorfin.android.components.ui.constants.ColorArgbConstants.COLOR_ARGB_CIRCULAR_PROGRESS_INDICATOR_PRESET_COLOR


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 09th April, 2023.
 */


class AuthenticationContentTestUtil3(
    private val mContext: Context,
    private val mTargetContext: Context,
    private val composeTestRule: ComposeContentTestRule
) {

    val authenticationButtonTestUtil3 = AuthenticationButtonTestUtil3(
        mContext = mContext,
        mTargetContext = mTargetContext,
        composeTestRule = composeTestRule
    )

    val authenticationErrorDialogTestUtil3 = AuthenticationErrorDialogTestUtil3(
        mContext = mContext,
        mTargetContext = mTargetContext,
        composeTestRule = composeTestRule
    )

    private val authenticationTitleTestUtil3 = AuthenticationTitleTestUtil3(
        mContext = mContext,
        mTargetContext = mTargetContext,
        composeTestRule = composeTestRule
    )

    val authenticationToggleModeTestUtil3 = AuthenticationToggleModeTestUtil3(
        mContext = mContext,
        mTargetContext = mTargetContext,
        composeTestRule = composeTestRule
    )

    val passwordRequirementsTestUtil3 = PasswordRequirementsTestUtil3(
        mContext = mContext,
        mTargetContext = mTargetContext,
        composeTestRule = composeTestRule
    )

    fun setContentAsAuthenticationContentAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        authenticationMode: AuthenticationMode,
        email: String? = NULL,
        password: String? = NULL,
        passwordRequirements: List<PasswordRequirement> = emptyList(),
        isLoading: Boolean = FALSE,
        @StringRes error: Int? = NULL
    ) {

        setContentAsAuthenticationContent(
            composeTestRule = composeTestRule,
            authenticationMode = authenticationMode,
            email = email,
            password = password,
            passwordRequirements = passwordRequirements,
            isLoading = isLoading,
            error = error

        )

        assertAuthenticationContentIsDisplayed(composeTestRule)

        if (authenticationMode == SIGN_IN) {
            authenticationTitleTestUtil3.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()
        } else if (authenticationMode == SIGN_UP) {
            authenticationTitleTestUtil3.assertAuthenticationTitleAndTextExactlySignUpForAnAccountIsDisplayed()
        }

    }

    fun setContentAsAuthenticationContent(
        composeTestRule: ComposeContentTestRule,
        authenticationMode: AuthenticationMode,
        email: String?,
        password: String?,
        passwordRequirements: List<PasswordRequirement>,
        isLoading: Boolean,
        @StringRes error: Int?
    ) {

        val uiState = AuthenticationUiState(
            authenticationMode = authenticationMode,
            email = email,
            password = password,
            passwordRequirements = passwordRequirements,
            isLoading = isLoading,
            error = error
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = { }
                )
            }
        }

    }

    fun assertSignInModeIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            composeTestRule = composeTestRule,
            authenticationMode = SIGN_IN
        )

        /**
         * No longer necessary since it's now part of the implementation of
         * [setContentAsAuthenticationContentAndAssertItIsDisplayed]
         */
//        assertSignInTitleIsDisplayed(composeTestRule)

    }

    fun assertSignUpModeIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            composeTestRule = composeTestRule,
            authenticationMode = SIGN_UP
        )

        /**
         * No longer necessary since it's now part of the implementation of
         * [setContentAsAuthenticationContentAndAssertItIsDisplayed]
         */
//        assertSignUpTitleIsDisplayed(composeTestRule)

    }

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationContent]
     * composable in the [ComposeContentTestRule.setContent]
     */
    fun assertAuthenticationContentIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationContent()
            .assertIsDisplayed()

    }

    private fun onNodeWithAuthenticationContent(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        val thisTextCouldBeAnything = ""

        return onNodeWithAuthenticationContentAnd(
            otherMatcher = hasTextExactly(
                thisTextCouldBeAnything,
                includeEditableText = FALSE
            ).not(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithCircularProgressIndicatorAnd(
            otherMatcher = hasCircularProgressIndicatorColorArgbPresetColor(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationContentAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagAuthenticationContent().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithCircularProgressIndicatorAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagCircularProgressIndicator().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun hasTestTagAuthenticationContent(): SemanticsMatcher {

        return hasTestTagsAuthenticationContentAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagCircularProgressIndicator(): SemanticsMatcher {

        return hasTestTagsCircularProgressIndicatorAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagsAuthenticationContentAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_CONTENT + otherTestTag
        )

    }

    private fun hasTestTagsCircularProgressIndicatorAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_PROGRESS + otherTestTag
        )

    }

    // Before using a semantics matcher, check the implementation of the utility functions in this
    // section if it's already available to avoid duplication.
    // The function names make the check easier.

    private fun hasCircularProgressIndicatorColorArgbPresetColor(): SemanticsMatcher {

        return hasCircularProgressIndicatorColorArgb(
            circularProgressIndicatorColorInArgb = COLOR_ARGB_CIRCULAR_PROGRESS_INDICATOR_PRESET_COLOR
        )

    }

}