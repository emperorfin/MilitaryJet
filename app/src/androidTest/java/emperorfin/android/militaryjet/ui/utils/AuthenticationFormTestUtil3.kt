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
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.AuthenticationForm
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_FORM_CONTENT_AREA
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.FALSE
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.TRUE
import emperorfin.android.militaryjet.ui.constants.NothingConstants.NULL
import emperorfin.android.militaryjet.ui.constants.StringConstants.THIS_STRING_MUST_BE_EMPTY
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_EIGHT_CHARACTERS
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_DIGIT
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_NEEDED
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_DIGIT_NEEDED
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_SATISFIED
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_AT_LEAST_ONE_DIGIT_SATISFIED


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 09th April, 2023.
 */


class AuthenticationFormTestUtil3(
    private val mContext: Context,
    private val mTargetContext: Context,
    private val composeTestRule: ComposeContentTestRule
) {

    val authenticationButtonTestUtil3 = AuthenticationButtonTestUtil3(
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

    fun setContentAsAuthenticationFormAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        authenticationMode: AuthenticationMode,
        email: String? = NULL,
        password: String? = NULL,
        passwordRequirements: List<PasswordRequirement> = emptyList(),
        enableAuthentication: Boolean
    ) {

        setContentAsAuthenticationForm(
            composeTestRule = composeTestRule,
            authenticationMode = authenticationMode,
            email = email,
            password = password,
            passwordRequirements = passwordRequirements,
            enableAuthentication = enableAuthentication
        )

        assertAuthenticationFormIsDisplayed(composeTestRule)

        if (authenticationMode == SIGN_IN) {
            authenticationTitleTestUtil3.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()
        } else if (authenticationMode == SIGN_UP) {
            authenticationTitleTestUtil3.assertAuthenticationTitleAndTextExactlySignUpForAnAccountIsDisplayed()
        }

    }

    private fun setContentAsAuthenticationForm(
        composeTestRule: ComposeContentTestRule,
        authenticationMode: AuthenticationMode,
        email: String?,
        password: String?,
        passwordRequirements: List<PasswordRequirement>,
        enableAuthentication: Boolean
    ) {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = authenticationMode,
                    onToggleMode = { },
                    email = email,
                    onEmailChanged = { },
                    password = password,
                    onPasswordChanged = { },
                    completedPasswordRequirements = passwordRequirements,
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

    }

    fun assertSignInModeIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            composeTestRule = composeTestRule,
            authenticationMode = SIGN_IN,
            enableAuthentication = FALSE
        )

        /**
         * No longer necessary since it's now part of the implementation of
         * [setContentAsAuthenticationFormAndAssertItIsDisplayed]
         */
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed(composeTestRule)

    }

    fun assertSignUpModeIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            composeTestRule = composeTestRule,
            authenticationMode = SIGN_UP,
            enableAuthentication = FALSE
        )

        /**
         * No longer necessary since it's now part of the implementation of
         * [setContentAsAuthenticationFormAndAssertItIsDisplayed]
         */
//        assertAuthenticationTitleAndTextExactlySignUpForAnAccountIsDisplayed(composeTestRule)

    }

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationForm]
     * composable in the [ComposeContentTestRule.setContent]
     */
    private fun assertAuthenticationFormIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationForm()
            .assertIsDisplayed()

    }

    // TODO: Remove this as it's no longer necessary.
//    private fun assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed(
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

    private fun onNodeWithAuthenticationForm(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        val thisStringCouldBeAnything = ""

        return onNodeWithAuthenticationFormAnd(
            otherMatcher = hasTextExactly(
                thisStringCouldBeAnything,
                includeEditableText = FALSE
            ).not(),
            useUnmergedTree = useUnmergedTree
        )

    }

    // TODO: Remove this as it's no longer necessary.
//    private fun onNodeWithAuthenticationTitleAndTextExactlySignInToYourAccount(
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
//    private fun onNodeWithAuthenticationTitleAndTextExactlySignUpForAnAccount(
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

    // ------- FOR ..._AnotherApproach()

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return passwordRequirementsTestUtil3.onNodeWithPasswordRequirementAtLeastEightCharactersNeededAnd(
//            otherMatcher = passwordRequirementsTestUtil3.hasTextExactlyAtLeastEightCharacters(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return passwordRequirementsTestUtil3.onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAnd(
//            otherMatcher = passwordRequirementsTestUtil3.hasTextExactlyAtLeastOneUppercaseLetter(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return passwordRequirementsTestUtil3.onNodeWithPasswordRequirementAtLeastOneDigitNeededAnd(
//            otherMatcher = passwordRequirementsTestUtil3.hasTextExactlyAtLeastOneDigit(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharacters(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return passwordRequirementsTestUtil3.onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAnd(
//            otherMatcher = passwordRequirementsTestUtil3.hasTextExactlyAtLeastEightCharacters(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAndTextExactlyAtLeastOneUppercaseLetter(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return passwordRequirementsTestUtil3.onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAnd(
//            otherMatcher = passwordRequirementsTestUtil3.hasTextExactlyAtLeastOneUppercaseLetter(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAndTextExactlyAtLeastOneDigit(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return passwordRequirementsTestUtil3.onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAnd(
//            otherMatcher = passwordRequirementsTestUtil3.hasTextExactlyAtLeastOneDigit(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // ------- /FOR ..._AnotherApproach()

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return passwordRequirementsTestUtil3.onNodeWithPasswordRequirementAtLeastEightCharactersAnd(
//            otherMatcher = passwordRequirementsTestUtil3.hasTextExactlyAtLeastEightCharactersNeeded(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return passwordRequirementsTestUtil3.onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAnd(
//            otherMatcher = passwordRequirementsTestUtil3.hasTextExactlyAtLeastOneUppercaseLetterNeeded(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return passwordRequirementsTestUtil3.onNodeWithPasswordRequirementAtLeastOneDigitAnd(
//            otherMatcher = passwordRequirementsTestUtil3.hasTextExactlyAtLeastOneDigitNeeded(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfied(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return passwordRequirementsTestUtil3.onNodeWithPasswordRequirementAtLeastEightCharactersAnd(
//            otherMatcher = passwordRequirementsTestUtil3.hasTextExactlyAtLeastEightCharactersSatisfied(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterSatisfied(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return passwordRequirementsTestUtil3.onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAnd(
//            otherMatcher = passwordRequirementsTestUtil3.hasTextExactlyAtLeastOneUppercaseLetterSatisfied(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    fun onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitSatisfied(
//        useUnmergedTree: Boolean = FALSE
//    ): SemanticsNodeInteraction {
//
//        return passwordRequirementsTestUtil3.onNodeWithPasswordRequirementAtLeastOneDigitAnd(
//            otherMatcher = passwordRequirementsTestUtil3.hasTextExactlyAtLeastOneDigitSatisfied(),
//            useUnmergedTree = useUnmergedTree
//        )
//
//    }

    // ------- FOR ..._AnotherApproach()

    // TODO: Remove this as it's no longer necessary.
//    private fun onNodeWithPasswordRequirementAtLeastEightCharactersNeededAnd(
//        composeTestRule: ComposeContentTestRule = this.composeTestRule,
//        useUnmergedTree: Boolean = FALSE,
//        otherMatcher: SemanticsMatcher
//    ): SemanticsNodeInteraction {
//
//        return composeTestRule
//            .onNode(
//                matcher = passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAndAtLeastEightCharactersNeeded().and(
//                    other = otherMatcher
//                ),
//                useUnmergedTree = useUnmergedTree
//            )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAnd(
//        composeTestRule: ComposeContentTestRule = this.composeTestRule,
//        useUnmergedTree: Boolean = FALSE,
//        otherMatcher: SemanticsMatcher
//    ): SemanticsNodeInteraction {
//
//        return composeTestRule
//            .onNode(
//                matcher = passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetterNeeded().and(
//                    other = otherMatcher
//                ),
//                useUnmergedTree = useUnmergedTree
//            )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun onNodeWithPasswordRequirementAtLeastOneDigitNeededAnd(
//        composeTestRule: ComposeContentTestRule = this.composeTestRule,
//        useUnmergedTree: Boolean = FALSE,
//        otherMatcher: SemanticsMatcher
//    ): SemanticsNodeInteraction {
//
//        return composeTestRule
//            .onNode(
//                matcher = passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAndAtLeastOneDigitNeeded().and(
//                    other = otherMatcher
//                ),
//                useUnmergedTree = useUnmergedTree
//            )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAnd(
//        composeTestRule: ComposeContentTestRule = this.composeTestRule,
//        useUnmergedTree: Boolean = FALSE,
//        otherMatcher: SemanticsMatcher
//    ): SemanticsNodeInteraction {
//
//        return composeTestRule
//            .onNode(
//                matcher = passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAndAtLeastEightCharactersSatisfied().and(
//                    other = otherMatcher
//                ),
//                useUnmergedTree = useUnmergedTree
//            )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAnd(
//        composeTestRule: ComposeContentTestRule = this.composeTestRule,
//        useUnmergedTree: Boolean = FALSE,
//        otherMatcher: SemanticsMatcher
//    ): SemanticsNodeInteraction {
//
//        return composeTestRule
//            .onNode(
//                matcher = passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetterSatisfied().and(
//                    other = otherMatcher
//                ),
//                useUnmergedTree = useUnmergedTree
//            )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAnd(
//        composeTestRule: ComposeContentTestRule = this.composeTestRule,
//        useUnmergedTree: Boolean = FALSE,
//        otherMatcher: SemanticsMatcher
//    ): SemanticsNodeInteraction {
//
//        return composeTestRule
//            .onNode(
//                matcher = passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAndAtLeastOneDigitSatisfied().and(
//                    other = otherMatcher
//                ),
//                useUnmergedTree = useUnmergedTree
//            )
//
//    }

    // ------- /FOR ..._AnotherApproach()

    private fun onNodeWithAuthenticationFormAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagAuthenticationForm().and(
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
//    private fun onNodeWithPasswordRequirementAtLeastEightCharactersAnd(
//        composeTestRule: ComposeContentTestRule = this.composeTestRule,
//        useUnmergedTree: Boolean = FALSE,
//        otherMatcher: SemanticsMatcher
//    ): SemanticsNodeInteraction {
//
//        return composeTestRule
//            .onNode(
//                matcher = passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAndAtLeastEightCharacters().and(
//                    other = otherMatcher
//                ),
//                useUnmergedTree = useUnmergedTree
//            )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAnd(
//        composeTestRule: ComposeContentTestRule = this.composeTestRule,
//        useUnmergedTree: Boolean = FALSE,
//        otherMatcher: SemanticsMatcher
//    ): SemanticsNodeInteraction {
//
//        return composeTestRule
//            .onNode(
//                matcher = passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetter().and(
//                    other = otherMatcher
//                ),
//                useUnmergedTree = useUnmergedTree
//            )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun onNodeWithPasswordRequirementAtLeastOneDigitAnd(
//        composeTestRule: ComposeContentTestRule = this.composeTestRule,
//        useUnmergedTree: Boolean = FALSE,
//        otherMatcher: SemanticsMatcher
//    ): SemanticsNodeInteraction {
//
//        return composeTestRule
//            .onNode(
//                matcher = passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAndAtLeastOneDigit().and(
//                    other = otherMatcher
//                ),
//                useUnmergedTree = useUnmergedTree
//            )
//
//    }

    private fun hasTestTagAuthenticationForm(): SemanticsMatcher {

        return hasTestTagsAuthenticationFormAnd(
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

    //For the sake of pattern, this function was created.
    private fun hasTestTagPasswordRequirement(): SemanticsMatcher {

        return passwordRequirementsTestUtil3
            .requirementTestUtil3
            .hasTestTagsPasswordRequirementAnd(
                otherTestTag = THIS_STRING_MUST_BE_EMPTY
            )

    }

    // ------- FOR ..._AnotherApproach()

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharactersNeeded():
//            SemanticsMatcher {
//
//        return passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAnd(
//            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED)
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetterNeeded():
//            SemanticsMatcher {
//
//        return passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAnd(
//            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_NEEDED)
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagsPasswordRequirementAndAtLeastOneDigitNeeded(): SemanticsMatcher {
//
//        return passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAnd(
//            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT_NEEDED)
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharactersSatisfied():
//            SemanticsMatcher {
//
//        return passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAnd(
//            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED)
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetterSatisfied():
//            SemanticsMatcher {
//
//        return passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAnd(
//            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_SATISFIED)
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagsPasswordRequirementAndAtLeastOneDigitSatisfied(): SemanticsMatcher {
//
//        return passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAnd(
//            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT_SATISFIED)
//        )
//
//    }

    // ------- /FOR ..._AnotherApproach()

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharacters(): SemanticsMatcher {
//
//        return passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAnd(
//            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS)
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetter(): SemanticsMatcher {
//
//        return passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAnd(
//            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER)
//        )
//
//    }

    // TODO: Remove this as it's no longer necessary.
//    private fun hasTestTagsPasswordRequirementAndAtLeastOneDigit(): SemanticsMatcher {
//
//        return passwordRequirementsTestUtil3.hasTestTagsPasswordRequirementAnd(
//            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT)
//        )
//
//    }

    private fun hasTestTagsAuthenticationFormAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_FORM_CONTENT_AREA + otherTestTag
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

    // TODO: Remove as it's no longer necessary.
//    private fun hasTestTagsAuthenticationToggleModeAnd(otherTestTag: String): SemanticsMatcher {
//
//        return hasTestTag(
//            testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON + otherTestTag
//        )
//
//    }

    // TODO: Remove as it's no longer necessary.
//    private fun hasTestTagsPasswordRequirementAnd(otherTestTag: String): SemanticsMatcher {
//
//        return hasTestTag(
//            testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT + otherTestTag
//        )
//
//    }

    // Before using a semantics matcher, check the implementation of the utility functions in this
    // section if it's already available to avoid duplication.
    // The function names make the check easier.

    // TODO: Remove as it's no longer necessary.
//    private fun hasTextExactlySignInToYourAccount(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_SIGN_IN_TO_YOUR_ACCOUNT),
//            includeEditableText = FALSE
//        )
//
//    }

    // TODO: Remove as it's no longer necessary.
//    private fun hasTextExactlySignUpForAnAccount(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_SIGN_UP_FOR_AN_ACCOUNT),
//            includeEditableText = FALSE
//        )
//
//    }

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
//    private fun hasTextExactlyAtLeastEightCharacters(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS),
//            includeEditableText = FALSE
//        )
//
//    }

    // TODO: Remove as it's no longer necessary.
//    private fun hasTextExactlyAtLeastOneUppercaseLetter(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER),
//            includeEditableText = FALSE
//        )
//
//    }

    // TODO: Remove as it's no longer necessary.
//    private fun hasTextExactlyAtLeastOneDigit(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT),
//            includeEditableText = FALSE
//        )
//
//    }

    // TODO: Remove as it's no longer necessary.
//    private fun hasTextExactlyAtLeastEightCharactersNeeded(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED),
//            includeEditableText = FALSE
//        )
//
//    }

    // TODO: Remove as it's no longer necessary.
//    private fun hasTextExactlyAtLeastOneUppercaseLetterNeeded(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_NEEDED),
//            includeEditableText = FALSE
//        )
//
//    }

    // TODO: Remove as it's no longer necessary.
//    private fun hasTextExactlyAtLeastOneDigitNeeded(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT_NEEDED),
//            includeEditableText = FALSE
//        )
//
//    }

    // TODO: Remove as it's no longer necessary.
//    private fun hasTextExactlyAtLeastEightCharactersSatisfied(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED),
//            includeEditableText = FALSE
//        )
//
//    }

    // TODO: Remove as it's no longer necessary.
//    private fun hasTextExactlyAtLeastOneUppercaseLetterSatisfied(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_SATISFIED),
//            includeEditableText = FALSE
//        )
//
//    }

    // TODO: Remove as it's no longer necessary.
//    private fun hasTextExactlyAtLeastOneDigitSatisfied(): SemanticsMatcher {
//
//        return hasTextExactly(
//            mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT_SATISFIED),
//            includeEditableText = FALSE
//        )
//
//    }

}