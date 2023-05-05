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
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasCircularProgressIndicatorColorArgb
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.AuthenticationScreen
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PROGRESS
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_SCREEN
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_PASSWORD
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_EIGHT_CHARACTERS
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_DIGIT
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_NEEDED
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_DIGIT_NEEDED
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_SATISFIED
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_DIGIT_SATISFIED
import emperorfin.android.militaryjet.ui.constants.ColorArgbConstants.COLOR_ARGB_CIRCULAR_PROGRESS_INDICATOR_PRESET_COLOR
import emperorfin.android.militaryjet.ui.constants.StringConstants.THIS_STRING_MUST_BE_EMPTY
import emperorfin.android.militaryjet.ui.constants.StringConstants.THIS_STRING_COULD_BE_ANYTHING
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.FALSE
import emperorfin.android.militaryjet.ui.constants.NothingConstants.NULL


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 09th April, 2023.
 */


class AuthenticationScreenTestUtil3(
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

    val authenticationTitleTestUtil3 = AuthenticationTitleTestUtil3(
        mContext = mContext,
        mTargetContext = mTargetContext,
        composeTestRule = composeTestRule
    )

    val authenticationToggleModeTestUtil3 = AuthenticationToggleModeTestUtil3(
        mContext = mContext,
        mTargetContext = mTargetContext,
        composeTestRule = composeTestRule
    )

    val emailInputTestUtil3 = EmailInputTestUtil3(
        mContext = mContext,
        mTargetContext = mTargetContext,
        composeTestRule = composeTestRule
    )

    val passwordInputTestUtil3 = PasswordInputTestUtil3(
        mContext = mContext,
        mTargetContext = mTargetContext,
        composeTestRule = composeTestRule
    )

    val passwordRequirementsTestUtil3 = PasswordRequirementsTestUtil3(
        mContext = mContext,
        mTargetContext = mTargetContext,
        composeTestRule = composeTestRule
    )

    /**
     * @param isSignInMode This is nullable should there's a case where [assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed]
     * or [navigateFromSignInToSignUpModesAndConfirmTitles] doesn't have to be run. But such case
     * should be rare.
     */
    fun setContentAsAuthenticationScreenAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        keyboardHelper: KeyboardHelper? = NULL,
        isSignInMode: Boolean?
    ) {

        setContentAsAuthenticationScreen(
            composeTestRule = composeTestRule,
            keyboardHelper = keyboardHelper
        )

        assertAuthenticationScreenIsDisplayed(composeTestRule)

        isSignInMode?.let {
            if (it) {
                authenticationTitleTestUtil3.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed(
                    composeTestRule = composeTestRule
                )
            } else {
                navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule = composeTestRule)
            }
        }

    }

    private fun setContentAsAuthenticationScreen(
        composeTestRule: ComposeContentTestRule, keyboardHelper: KeyboardHelper?
    ) {

        composeTestRule.setContent {
            keyboardHelper?.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
            }
        }

    }

    private fun navigateFromSignInToSignUpModesAndConfirmTitles(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        authenticationTitleTestUtil3.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed(composeTestRule)

        authenticationToggleModeTestUtil3.onNodeWithAuthenticationToggleModeAndTextExactlyNeedAnAccount()
            .performClick()

        assertAuthenticationScreenIsDisplayed(composeTestRule)

        authenticationTitleTestUtil3.assertAuthenticationTitleAndTextExactlySignUpForAnAccountIsDisplayed(composeTestRule)

    }

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationScreen]
     * composable in the [ComposeContentTestRule.setContent]
     */
    private fun assertAuthenticationScreenIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationScreen()
            .assertIsDisplayed()

    }

    fun onNodeWithAuthenticationScreen(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationScreenAnd(
            otherMatcher = hasTextExactly(
                THIS_STRING_COULD_BE_ANYTHING,
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

    fun onNodeWithPasswordInputTrailingIcon(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return passwordInputTestUtil3.onNodeWithPasswordInputTrailingIconAnd(
            otherMatcher = hasTextExactly(
                THIS_STRING_COULD_BE_ANYTHING,
                includeEditableText = FALSE
            ).not(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationScreenAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagAuthenticationScreen().and(
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

    private fun hasTestTagAuthenticationScreen(): SemanticsMatcher {

        return hasTestTagsAuthenticationScreenAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagCircularProgressIndicator(): SemanticsMatcher {

        return hasTestTagsCircularProgressIndicatorAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagsAuthenticationScreenAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_SCREEN + otherTestTag
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
            circularProgressIndicatorColorInArgb =
            COLOR_ARGB_CIRCULAR_PROGRESS_INDICATOR_PRESET_COLOR
        )

    }

    fun hasTestTagCircularProgressIndicatorAndHasCircularProgressIndicatorColorArgbPresetColor():
            SemanticsMatcher {

        return hasTestTagCircularProgressIndicator().and(
            other = hasCircularProgressIndicatorColorArgbPresetColor()
        )

    }

    fun hasTestTagAuthenticationErrorDialogAndHasAlertDialogTitleWhoops(): SemanticsMatcher {

        return authenticationErrorDialogTestUtil3.hasTestTagAuthenticationErrorDialog().and(
            other = authenticationErrorDialogTestUtil3.hasAlertDialogTitleWhoops()
        )

    }

    fun hasTestTagAuthenticationTitleAndHasTextExactlySignInToYourAccount(): SemanticsMatcher {

        return authenticationTitleTestUtil3.hasTestTagAuthenticationTitle().and(
            other = authenticationTitleTestUtil3.hasTextExactlySignInToYourAccount()
        )

    }

    fun hasTestTagAuthenticationTitleAndHasTextExactlySignUpForAnAccount(): SemanticsMatcher {

        return authenticationTitleTestUtil3.hasTestTagAuthenticationTitle().and(
            other = authenticationTitleTestUtil3.hasTextExactlySignUpForAnAccount()
        )

    }

}