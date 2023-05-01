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

    // TODO: Remove this as it's no longer necessary.
//    fun assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed(
//        composeTestRule: ComposeContentTestRule = this.composeTestRule
//    ) {
//
//        authenticationTitleTestUtil3.onNodeWithAuthenticationTitleAndTextExactlySignInToYourAccount()
//            .assertIsDisplayed()
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun assertAuthenticationTitleAndTextExactlySignUpForAnAccountIsDisplayed(
//        composeTestRule: ComposeContentTestRule = this.composeTestRule
//    ) {
//
//        authenticationTitleTestUtil3.onNodeWithAuthenticationTitleAndTextExactlySignUpForAnAccount()
//            .assertIsDisplayed()
//
//    }

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

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithAuthenticationTitleAndTextExactlySignInToYourAccount(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return authenticationTitleTestUtil3.onNodeWithAuthenticationTitleAnd(
//            otherMatcher = authenticationTitleTestUtil3.hasTextExactlySignInToYourAccount(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithAuthenticationTitleAndTextExactlySignUpForAnAccount(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return authenticationTitleTestUtil3.onNodeWithAuthenticationTitleAnd(
//            otherMatcher = authenticationTitleTestUtil3.hasTextExactlySignUpForAnAccount(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithAuthenticationButtonAndTextExactlySignIn(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return authenticationButtonTestUtil3.onNodeWithAuthenticationButtonAnd(
//            otherMatcher = authenticationButtonTestUtil3.hasTextExactlySignIn(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithAuthenticationButtonAndTextExactlySignUp(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return authenticationButtonTestUtil3.onNodeWithAuthenticationButtonAnd(
//            otherMatcher = authenticationButtonTestUtil3.hasTextExactlySignUp(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithAuthenticationToggleModeAndTextExactlyNeedAnAccount(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return authenticationToggleModeTestUtil3.onNodeWithAuthenticationToggleModeAnd(
//            otherMatcher = authenticationToggleModeTestUtil3.hasTextExactlyNeedAnAccount(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithAuthenticationToggleModeAndTextExactlyAlreadyHaveAnAccount(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return authenticationToggleModeTestUtil3.onNodeWithAuthenticationToggleModeAnd(
//            otherMatcher = authenticationToggleModeTestUtil3.hasTextExactlyAlreadyHaveAnAccount(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithEmailInputAndTextExactlyEmailAddress(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        // This one works.
////        return composeTestRule
////            .onNodeWithTag(
////                testTag = TAG_AUTHENTICATION_INPUT_EMAIL,
////                useUnmergedTree = useUnmergedTree
////            )
//
//        // This is recommended.
//        return emailInputTestUtil3.onNodeWithEmailInputAnd(
//            otherMatcher = emailInputTestUtil3.hasTextExactlyEmailAddress(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    fun onNodeWithPasswordInputAndTextExactlyPassword(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        // This one works.
//        return composeTestRule
//            .onNodeWithTag(
//                testTag = TAG_AUTHENTICATION_INPUT_PASSWORD,
//                useUnmergedTree = useUnmergedTree
//            )

        // This is recommended.
        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextExactlyPassword(),
            useUnmergedTree = useUnmergedTree
        )

    }

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return authenticationErrorDialogTestUtil3.onNodeWithAuthenticationErrorDialogAnd(
//            otherMatcher = authenticationErrorDialogTestUtil3.hasAlertDialogTitleWhoops(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

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

        return onNodeWithPasswordInputTrailingIconAnd(
            otherMatcher = hasTextExactly(
                THIS_STRING_COULD_BE_ANYTHING,
                includeEditableText = FALSE
            ).not(),
            useUnmergedTree = useUnmergedTree
        )

    }

    // ------- FOR ..._AnotherApproach()

    fun onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersNeededAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharacters(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAnd(
            otherMatcher = hasTextExactlyAtLeastOneUppercaseLetter(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneDigitNeededAnd(
            otherMatcher = hasTextExactlyAtLeastOneDigit(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharacters(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharacters(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAndTextExactlyAtLeastOneUppercaseLetter(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAnd(
            otherMatcher = hasTextExactlyAtLeastOneUppercaseLetter(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAndTextExactlyAtLeastOneDigit(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAnd(
            otherMatcher = hasTextExactlyAtLeastOneDigit(),
            useUnmergedTree = useUnmergedTree
        )

    }

    // ------- /FOR ..._AnotherApproach()

    fun onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharactersNeeded(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAnd(
            otherMatcher = hasTextExactlyAtLeastOneUppercaseLetterNeeded(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneDigitAnd(
            otherMatcher = hasTextExactlyAtLeastOneDigitNeeded(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfied(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharactersSatisfied(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterSatisfied(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAnd(
            otherMatcher = hasTextExactlyAtLeastOneUppercaseLetterSatisfied(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitSatisfied(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneDigitAnd(
            otherMatcher = hasTextExactlyAtLeastOneDigitSatisfied(),
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

    // TODO: Remove this as it's no longer necessary.
//    private fun onNodeWithAuthenticationTitleAnd(
//        composeTestRule: ComposeContentTestRule = this.composeTestRule,
//        useUnmergedTree: Boolean = FALSE,
//        otherMatcher: SemanticsMatcher
//    ): SemanticsNodeInteraction {
//
//        return composeTestRule
//            .onNode(
//                matcher = authenticationTitleTestUtil3.hasTestTagAuthenticationTitle().and(
//                    other = otherMatcher
//                ),
//                useUnmergedTree = useUnmergedTree
//            )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun onNodeWithAuthenticationButtonAnd(
//        composeTestRule: ComposeContentTestRule = this.composeTestRule,
//        useUnmergedTree: Boolean = FALSE,
//        otherMatcher: SemanticsMatcher
//    ): SemanticsNodeInteraction {
//
//        return composeTestRule
//            .onNode(
//                matcher = authenticationButtonTestUtil3.hasTestTagAuthenticationButton().and(
//                    other = otherMatcher
//                ),
//                useUnmergedTree = useUnmergedTree
//            )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun onNodeWithAuthenticationToggleModeAnd(
//        composeTestRule: ComposeContentTestRule = this.composeTestRule,
//        useUnmergedTree: Boolean = FALSE,
//        otherMatcher: SemanticsMatcher
//    ): SemanticsNodeInteraction {
//
//        return composeTestRule
//            .onNode(
//                matcher = authenticationToggleModeTestUtil3.hasTestTagAuthenticationToggleMode().and(
//                    other = otherMatcher
//                ),
//                useUnmergedTree = useUnmergedTree
//            )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun onNodeWithEmailInputAnd(
//        composeTestRule: ComposeContentTestRule = this.composeTestRule,
//        useUnmergedTree: Boolean = FALSE,
//        otherMatcher: SemanticsMatcher
//    ): SemanticsNodeInteraction {
//
//        return composeTestRule
//            .onNode(
//                matcher = emailInputTestUtil3.hasTestTagEmailInput().and(
//                    other = otherMatcher
//                ),
//                useUnmergedTree = useUnmergedTree
//            )
//
//    }

    private fun onNodeWithPasswordInputAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagPasswordInput().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    // TODO: Remove this as it's no longer necessary.
//    private fun onNodeWithAuthenticationErrorDialogAnd(
//        composeTestRule: ComposeContentTestRule = this.composeTestRule,
//        useUnmergedTree: Boolean = FALSE,
//        otherMatcher: SemanticsMatcher
//    ): SemanticsNodeInteraction {
//
//        return composeTestRule
//            .onNode(
//                matcher = authenticationErrorDialogTestUtil3.hasTestTagAuthenticationErrorDialog().and(
//                    other = otherMatcher
//                ),
//                useUnmergedTree = useUnmergedTree
//            )
//
//    }

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

    private fun onNodeWithPasswordInputTrailingIconAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagPasswordInputTrailingIcon().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    // ------- FOR ..._AnotherApproach()

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersNeededAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastEightCharactersNeeded().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetterNeeded().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitNeededAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastOneDigitNeeded().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastEightCharactersSatisfied().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetterSatisfied().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastOneDigitSatisfied().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    // ------- /FOR ..._AnotherApproach()

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastEightCharacters().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetter().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastOneDigit().and(
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

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagAuthenticationTitle(): SemanticsMatcher {
//
//        return authenticationTitleTestUtil3.hasTestTagsAuthenticationTitleAnd(
//            otherTestTag = THIS_STRING_MUST_BE_EMPTY
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagAuthenticationButton(): SemanticsMatcher {
//
//        return authenticationButtonTestUtil3.hasTestTagsAuthenticationButtonAnd(
//            otherTestTag = THIS_STRING_MUST_BE_EMPTY
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagAuthenticationToggleMode(): SemanticsMatcher {
//
//        return authenticationToggleModeTestUtil3.hasTestTagsAuthenticationToggleModeAnd(
//            otherTestTag = THIS_STRING_MUST_BE_EMPTY
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagEmailInput(): SemanticsMatcher {
//
//        return emailInputTestUtil3.hasTestTagsEmailInputAnd(
//            otherTestTag = THIS_STRING_MUST_BE_EMPTY
//        )
//
//    }

    private fun hasTestTagPasswordInput(): SemanticsMatcher {

        return hasTestTagsPasswordInputAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagAuthenticationErrorDialog(): SemanticsMatcher {
//
//        return authenticationErrorDialogTestUtil3.hasTestTagsAuthenticationErrorDialogAnd(
//            otherTestTag = THIS_STRING_MUST_BE_EMPTY
//        )
//
//    }

    private fun hasTestTagCircularProgressIndicator(): SemanticsMatcher {

        return hasTestTagsCircularProgressIndicatorAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagPasswordInputTrailingIcon(): SemanticsMatcher {

        return hasTestTagsPasswordInputTrailingIconAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    // ------- FOR ..._AnotherApproach()

    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharactersNeeded(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetterNeeded(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_NEEDED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneDigitNeeded(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT_NEEDED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharactersSatisfied(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetterSatisfied(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_SATISFIED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneDigitSatisfied(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT_SATISFIED)
        )

    }

    // ------- /FOR ..._AnotherApproach()

    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharacters(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetter(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneDigit(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT)
        )

    }

    private fun hasTestTagsAuthenticationScreenAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_SCREEN + otherTestTag
        )

    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagsAuthenticationTitleAnd(otherTestTag: String): SemanticsMatcher {
//
//        return hasTestTag(
//            testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE + otherTestTag
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagsAuthenticationButtonAnd(otherTestTag: String): SemanticsMatcher {
//
//        return hasTestTag(
//            testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON + otherTestTag
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagsAuthenticationToggleModeAnd(otherTestTag: String): SemanticsMatcher {
//
//        return hasTestTag(
//            testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON + otherTestTag
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagsEmailInputAnd(otherTestTag: String): SemanticsMatcher {
//
//        return hasTestTag(
//            testTag = TAG_AUTHENTICATION_INPUT_EMAIL + otherTestTag
//        )
//
//    }

    private fun hasTestTagsPasswordInputAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_INPUT_PASSWORD + otherTestTag
        )

    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagsAuthenticationErrorDialogAnd(otherTestTag: String): SemanticsMatcher {
//
//        return hasTestTag(
//            testTag = TAG_AUTHENTICATION_ERROR_DIALOG + otherTestTag
//        )
//
//    }

    private fun hasTestTagsCircularProgressIndicatorAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_PROGRESS + otherTestTag
        )

    }

    private fun hasTestTagsPasswordInputTrailingIconAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON + otherTestTag
        )

    }

    private fun hasTestTagsPasswordRequirementAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT + otherTestTag
        )

    }

    // Before using a semantics matcher, check the implementation of the utility functions in this
    // section if it's already available to avoid duplication.
    // The function names make the check easier.

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTextExactlySignInToYourAccount(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_SIGN_IN_TO_YOUR_ACCOUNT),
//            includeEditableText = FALSE
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTextExactlySignUpForAnAccount(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_SIGN_UP_FOR_AN_ACCOUNT),
//            includeEditableText = FALSE
//        )
//
//    }

    // TODO: Remove as it's no longer necessary.
//    private fun hasTextExactlyEmailAddress(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_EMAIL_ADDRESS),
//            includeEditableText = FALSE
//        )
//
//    }

    private fun hasTextExactlyPassword(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_PASSWORD),
            includeEditableText = FALSE
        )

    }

    // TODO: Remove as it's no longer necessary.
//    private fun hasTextExactlySignIn(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_SIGN_IN),
//            includeEditableText = FALSE
//        )
//
//    }

    // TODO: Remove as it's no longer necessary.
//    private fun hasTextExactlySignUp(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_SIGN_UP),
//            includeEditableText = FALSE
//        )
//
//    }

    // TODO: Remove as it's no longer necessary.
//    private fun hasTextExactlyNeedAnAccount(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_NEED_AN_ACCOUNT),
//            includeEditableText = FALSE
//        )
//
//    }

    // TODO: Remove as it's no longer necessary.
//    private fun hasTextExactlyAlreadyHaveAnAccount(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_ALREADY_HAVE_AN_ACCOUNT),
//            includeEditableText = FALSE
//        )
//
//    }

    // TODO: Remove as it's no longer necessary.
//    private fun hasAlertDialogTitleWhoops(): SemanticsMatcher {
//
//        return hasAlertDialogTitle(
//            alertDialogTitle = mContext.getString(STRING_RES_WHOOPS)
//        )
//
//    }

    private fun hasTextExactlyAtLeastEightCharacters(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastOneUppercaseLetter(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastOneDigit(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastEightCharactersNeeded(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastOneUppercaseLetterNeeded(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_NEEDED),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastOneDigitNeeded(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT_NEEDED),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastEightCharactersSatisfied(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastOneUppercaseLetterSatisfied(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_SATISFIED),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastOneDigitSatisfied(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT_SATISFIED),
            includeEditableText = FALSE
        )

    }

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