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
import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.*
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasAlertDialogTitle
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasCircularProgressIndicatorColorArgb
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.screens.authentication.stateholders.AuthenticationUiState
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.AuthenticationContent
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_CONTENT
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATION_TITLE
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_ERROR_DIALOG
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PROGRESS
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_EIGHT_CHARACTERS
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_DIGIT
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_NEEDED
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_DIGIT_NEEDED
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_SATISFIED
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_DIGIT_SATISFIED
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_WHOOPS
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_NEED_AN_ACCOUNT
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_ALREADY_HAVE_AN_ACCOUNT
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_SIGN_IN_TO_YOUR_ACCOUNT
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_SIGN_UP_FOR_AN_ACCOUNT
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.TRUE
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.FALSE
import emperorfin.android.militaryjet.ui.constants.NothingConstants.NULL
import emperorfin.android.militaryjet.ui.constants.StringConstants.THIS_STRING_MUST_BE_EMPTY
import emperorfin.android.militaryjet.ui.constants.ColorArgbConstants.COLOR_ARGB_CIRCULAR_PROGRESS_INDICATOR_PRESET_COLOR


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 09th April, 2023.
 */


class AuthenticationContentTestUtil3(
    private val mContext: Context,
    private val mTargetContext: Context,
    private val composeTestRule: ComposeContentTestRule
) {

    private val authenticationButtonTestUtil3 = AuthenticationButtonTestUtil3(
        mContext = mContext,
        mTargetContext = mTargetContext,
        composeTestRule = composeTestRule
    )

    private val authenticationErrorDialogTestUtil3 = AuthenticationErrorDialogTestUtil3(
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
            assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()
        } else if (authenticationMode == SIGN_UP) {
            assertAuthenticationTitleAndTextExactlySignUpForAnAccountIsDisplayed()
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

    fun assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationTitleAndTextExactlySignInToYourAccount()
            .assertIsDisplayed()

    }

    fun assertAuthenticationTitleAndTextExactlySignUpForAnAccountIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationTitleAndTextExactlySignUpForAnAccount()
            .assertIsDisplayed()

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

    fun onNodeWithAuthenticationButtonAndTextExactlySignIn(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationButtonAnd(
            otherMatcher = authenticationButtonTestUtil3.hasTextExactlySignIn(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithAuthenticationButtonAndTextExactlySignUp(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationButtonAnd(
            otherMatcher = authenticationButtonTestUtil3.hasTextExactlySignUp(),
            useUnmergedTree = useUnmergedTree
        )

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

    fun onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationErrorDialogAnd(
            otherMatcher = authenticationErrorDialogTestUtil3.hasAlertDialogTitleWhoops(),
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

    private fun hasTestTagAuthenticationContent(): SemanticsMatcher {

        return hasTestTagsAuthenticationContentAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagAuthenticationTitle(): SemanticsMatcher {

        return hasTestTagsAuthenticationTitleAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagAuthenticationButton(): SemanticsMatcher {

        return hasTestTagsAuthenticationButtonAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagAuthenticationToggleMode(): SemanticsMatcher {

        return hasTestTagsAuthenticationToggleModeAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagAuthenticationErrorDialog(): SemanticsMatcher {

        return hasTestTagsAuthenticationErrorDialogAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagCircularProgressIndicator(): SemanticsMatcher {

        return hasTestTagsCircularProgressIndicatorAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    // ------- FOR ..._AnotherApproach()

    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharactersNeeded():
            SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetterNeeded():
            SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_NEEDED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneDigitNeeded(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT_NEEDED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharactersSatisfied():
            SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetterSatisfied():
            SemanticsMatcher {

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

    private fun hasTestTagsAuthenticationContentAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_CONTENT + otherTestTag
        )

    }

    private fun hasTestTagsAuthenticationTitleAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE + otherTestTag
        )

    }

    private fun hasTestTagsAuthenticationButtonAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON + otherTestTag
        )

    }

    private fun hasTestTagsAuthenticationToggleModeAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON + otherTestTag
        )

    }

    private fun hasTestTagsAuthenticationErrorDialogAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_ERROR_DIALOG + otherTestTag
        )

    }

    private fun hasTestTagsCircularProgressIndicatorAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_PROGRESS + otherTestTag
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

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTextExactlySignIn(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_SIGN_IN),
//            includeEditableText = FALSE
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTextExactlySignUp(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_SIGN_UP),
//            includeEditableText = FALSE
//        )
//
//    }

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

    // TODO: Remove this as it's no longer necessary.
//    private fun hasAlertDialogTitleWhoops(): SemanticsMatcher {
//
//        return hasAlertDialogTitle(
//            alertDialogTitle = mContext.getString(STRING_RES_WHOOPS)
//        )
//
//    }

    private fun hasCircularProgressIndicatorColorArgbPresetColor(): SemanticsMatcher {

        return hasCircularProgressIndicatorColorArgb(
            circularProgressIndicatorColorInArgb = COLOR_ARGB_CIRCULAR_PROGRESS_INDICATOR_PRESET_COLOR
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

}