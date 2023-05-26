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

package emperorfin.android.components.ui.screens.authentication.uicomponents

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.components.test.R
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasAlertDialogTitle
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasCircularProgressIndicatorColorArgb
import emperorfin.android.components.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.components.ui.screens.authentication.AuthenticationScreen
import emperorfin.android.components.ui.screens.authentication.AuthenticationScreenTest2
import emperorfin.android.components.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.components.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.components.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.components.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.components.ui.screens.authentication.enums.PasswordRequirement.EIGHT_CHARACTERS
import emperorfin.android.components.ui.screens.authentication.enums.PasswordRequirement.CAPITAL_LETTER
import emperorfin.android.components.ui.screens.authentication.enums.PasswordRequirement.NUMBER
import emperorfin.android.components.ui.screens.authentication.stateholders.AuthenticationUiState
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_CONTENT
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATION_TITLE
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_ERROR_DIALOG
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PROGRESS
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 19th January, 2023.
 */


/**
 * The following classes are revisions of this class:
 * [AuthenticationContentTest3]
 * [AuthenticationContentTest4]
 * [AuthenticationContentTest5]
 *
 * The tests in this class are a subset of the ones in the [AuthenticationScreenTest2] class but with
 * the tests in this class focused on testing the [AuthenticationContent] composable instead of
 * [AuthenticationScreen] composable.
 *
 * Also, the other tests in the [AuthenticationScreenTest2] class that are excluded in this class are
 * the ones that includes things like performing click and text input operations. Such tests shouldn't
 * be included in a non-screen composable test class as, in my opinion, it's not a good compose UI
 * test practice.
 *
 * Important:
 *
 * - Try not to run all the test cases by running this test class as some tests might fail. If you do
 * and some tests fail, try running them one after the other. If a test fails, check the test
 * function's KDoc/comment (if any) on possible solution to make the test pass when it should.
 * - If you try to run a test and it fails, check the test function's KDoc/comment (if any) on
 * possible solution to make the test pass when it should.
 * - Test cases with "_AnotherApproachX" (where X may or may not contain a number) suffixed in their
 * function names might fail. A little changes would need to be made for them to pass. Kindly take a
 * look at the function's KDoc/comment on how to make the test pass when it should.
 *
 * Notes:
 *
 * - If you ever need to pass a resource (e.g. a string resource) into a composable during testing,
 * be sure to use the one from the main source set and the R must be the one from
 * [emperorfin.android.components.R] and not
 * [emperorfin.android.components.test.R].
 * - Every other thing during testing that involves the use of a resource (e.g. a string resource)
 * such as performing matches or assertions, be sure to use the resource from the androidTest source
 * set (which you should've provided a copy and always in sync with the one from the main source set).
 * And the R must be the one from [emperorfin.android.components.test.R] instead of
 * [emperorfin.android.components.R].
 *
 * Be sure to have Configured res srcDirs for androidTest sourceSet in app/build.gradle file.
 * See the following:
 * - https://stackoverflow.com/questions/36955608/espresso-how-to-use-r-string-resources-of-androidtest-folder
 * - https://stackoverflow.com/questions/26663539/configuring-res-srcdirs-for-androidtest-sourceset
 */
class AuthenticationContentTest2 {

    private companion object {

        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        private val NULL = null

        private const val THIS_STRING_MUST_BE_EMPTY: String = ""

        private const val INPUT_CONTENT_EMAIL: String = "contact@email.com"
        private const val INPUT_CONTENT_PASSWORD: String = "passworD1"
        private const val INPUT_CONTENT_PASSWORD_PASSWORD: String = "password"
        private const val INPUT_CONTENT_PASSWORD_PASS: String = "Pass"
        private const val INPUT_CONTENT_PASSWORD_1PASS: String = "1pass"

        private val MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE =
            emperorfin.android.components.R.string.test_error_message

        /**
         * To easily find out the ARGB of a color to be used as the expected value during assertion,
         * there are two approaches to do this:
         *
         * - you can either call toArgb() on the color object and then logcat the returned value or
         * - you can just use any random assertion expected value, run the test case (which should
         * fail) and then get and use (as the correct assertion expected value) the assertion actual
         * value from the failed test error message (simplest approach).
         */
        private const val COLOR_ARGB_CIRCULAR_PROGRESS_INDICATOR_PRESET_COLOR: Int = -11576430

        @StringRes
        private val STRING_RES_AT_LEAST_EIGHT_CHARACTERS: Int = R.string.password_requirement_characters
        @StringRes
        private val STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER: Int = R.string.password_requirement_capital
        @StringRes
        private val STRING_RES_AT_LEAST_ONE_DIGIT: Int = R.string.password_requirement_digit
        @StringRes
        private val STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED: Int = R.string.test_password_requirement_needed_characters
        @StringRes
        private val STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_NEEDED: Int = R.string.test_password_requirement_needed_capital
        @StringRes
        private val STRING_RES_AT_LEAST_ONE_DIGIT_NEEDED: Int = R.string.test_password_requirement_needed_digit
        @StringRes
        private val STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED: Int = R.string.test_password_requirement_satisfied_characters
        @StringRes
        private val STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_SATISFIED: Int = R.string.test_password_requirement_satisfied_capital
        @StringRes
        private val STRING_RES_AT_LEAST_ONE_DIGIT_SATISFIED: Int = R.string.test_password_requirement_satisfied_digit
        @StringRes
        private val STRING_RES_WHOOPS: Int = R.string.error_title
        @StringRes
        private val STRING_RES_NEED_AN_ACCOUNT: Int = R.string.action_need_account
        @StringRes
        private val STRING_RES_ALREADY_HAVE_AN_ACCOUNT: Int = R.string.action_already_have_account
        @StringRes
        private val STRING_RES_SIGN_IN: Int = R.string.action_sign_in
        @StringRes
        private val STRING_RES_SIGN_UP: Int = R.string.action_sign_up
        @StringRes
        private val STRING_RES_SIGN_IN_TO_YOUR_ACCOUNT: Int = R.string.label_sign_in_to_account
        @StringRes
        private val STRING_RES_SIGN_UP_FOR_AN_ACCOUNT: Int = R.string.label_sign_up_for_account

    }

    /**
     * Use this when resources are coming from the main source set, whether directly
     * (e.g. R.string.sample_text) or indirectly (e.g. [EIGHT_CHARACTERS.label]
     * which is directly using a string resource).
     *
     * To actually reference the resource, you use
     * [emperorfin.android.components.R] and not
     * [emperorfin.android.components.test.R]
     *
     * So let's say if you want to reference a string resource, that string resource should come
     * from app/src/main/res/values/strings.xml XML resource file which must be, as you may have
     * noticed, from the main source set.
     */
    private lateinit var mTargetContext: Context

    /**
     * Use this when resources are coming from the androidTest or test source set. In this case, the
     * resources should come from androidTest (not test) source set.
     *
     * To actually reference the resource, you use
     * [emperorfin.android.components.test.R] and not
     * [emperorfin.android.components.R]
     *
     * So let's say if you want to reference a string resource, that string resource should come
     * from app/src/androidTest/res/values/strings.xml XML resource file which must be, as you may
     * have noticed, from the androidTest source set. And always update this file with the changes
     * made to the app/src/main/res/values/strings.xml XML resource file from the main source set.
     * And of course, you may/should re-run existing test(s) to be sure they don't fail as a result
     * of the synchronization.
     *
     * Be sure to have Configured res srcDirs for androidTest sourceSet in app/build.gradle file.
     * See the following:
     * - https://stackoverflow.com/questions/36955608/espresso-how-to-use-r-string-resources-of-androidtest-folder
     * - https://stackoverflow.com/questions/26663539/configuring-res-srcdirs-for-androidtest-sourceset
     */
    private lateinit var mContext: Context

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    private fun setContentAsAuthenticationContentAndAssertItIsDisplayed(
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

    private fun setContentAsAuthenticationContent(
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

    private fun assertSignInModeIsDisplayed(
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

    private fun assertSignUpModeIsDisplayed(
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
    private fun assertAuthenticationContentIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationContent()
            .assertIsDisplayed()

    }

    private fun assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationTitleAndTextExactlySignInToYourAccount()
            .assertIsDisplayed()

    }

    private fun assertAuthenticationTitleAndTextExactlySignUpForAnAccountIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationTitleAndTextExactlySignUpForAnAccount()
            .assertIsDisplayed()

    }

    // ------- FOR ..._AnotherApproach()

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersNeededAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharacters(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAnd(
            otherMatcher = hasTextExactlyAtLeastOneUppercaseLetter(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneDigitNeededAnd(
            otherMatcher = hasTextExactlyAtLeastOneDigit(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharacters(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharacters(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAndTextExactlyAtLeastOneUppercaseLetter(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAnd(
            otherMatcher = hasTextExactlyAtLeastOneUppercaseLetter(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAndTextExactlyAtLeastOneDigit(
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

    private fun onNodeWithAuthenticationButtonAndTextExactlySignIn(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationButtonAnd(
            otherMatcher = hasTextExactlySignIn(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationButtonAndTextExactlySignUp(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationButtonAnd(
            otherMatcher = hasTextExactlySignUp(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationToggleModeAndTextExactlyNeedAnAccount(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationToggleModeAnd(
            otherMatcher = hasTextExactlyNeedAnAccount(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationToggleModeAndTextExactlyAlreadyHaveAnAccount(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationToggleModeAnd(
            otherMatcher = hasTextExactlyAlreadyHaveAnAccount(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationErrorDialogAnd(
            otherMatcher = hasAlertDialogTitleWhoops(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithCircularProgressIndicatorAnd(
            otherMatcher = hasCircularProgressIndicatorColorArgbPresetColor(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharactersNeeded(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAnd(
            otherMatcher = hasTextExactlyAtLeastOneUppercaseLetterNeeded(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneDigitAnd(
            otherMatcher = hasTextExactlyAtLeastOneDigitNeeded(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfied(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharactersSatisfied(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterSatisfied(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAnd(
            otherMatcher = hasTextExactlyAtLeastOneUppercaseLetterSatisfied(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitSatisfied(
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

    private fun hasTextExactlySignIn(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_SIGN_IN),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlySignUp(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_SIGN_UP),
            includeEditableText = FALSE
        )

    }

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

    private fun hasAlertDialogTitleWhoops(): SemanticsMatcher {

        return hasAlertDialogTitle(
            alertDialogTitle = mContext.getString(STRING_RES_WHOOPS)
        )

    }

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

    @Before
    fun setUpContexts() {

        // See field's KDoc for more info.
        mTargetContext = InstrumentationRegistry.getInstrumentation().targetContext
//        mTargetContext = ApplicationProvider.getApplicationContext<Context>() // Haven't tested but might work.
        // See field's KDoc for more info.
        mContext = InstrumentationRegistry.getInstrumentation().context

    }

    @Test
    fun sign_In_Mode_Displayed() {

        assertSignInModeIsDisplayed()

    }

    @Test
    fun sign_Up_Mode_Displayed() {

        assertSignUpModeIsDisplayed()

    }

    @Test
    fun sign_In_Title_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(authenticationMode = SIGN_IN)

        /**
         * No longer necessary since it's now part of the implementation of
         * [setContentAsAuthenticationContentAndAssertItIsDisplayed]
         */
//        assertSignInTitleIsDisplayed()

    }

    @Test
    fun sign_Up_Title_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(authenticationMode = SIGN_UP)

        /**
         * No longer necessary since it's now part of the implementation of
         * [setContentAsAuthenticationContentAndAssertItIsDisplayed]
         */
//        assertSignUpTitleIsDisplayed()

    }

    @Test
    fun sign_In_Disabled_Button_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(authenticationMode = SIGN_IN)

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Disabled_Button_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(authenticationMode = SIGN_UP)

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_In_Enabled_Button_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD
        )

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .apply {
                assertIsDisplayed()

                assertIsEnabled()
            }

    }

    @Test
    fun sign_Up_Enabled_Button_Displayed() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(
            EIGHT_CHARACTERS,
            CAPITAL_LETTER,
            NUMBER
        )

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements
        )

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertIsEnabled()
            }

    }

    @Test
    fun sign_In_Button_Disabled_When_Email_Input_Has_No_Content() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            password = INPUT_CONTENT_PASSWORD
        )

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_In_Button_Disabled_When_Password_Input_Has_No_Content() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            email = INPUT_CONTENT_EMAIL
        )

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Button_Disabled_When_Email_Input_Has_No_Content() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(
            EIGHT_CHARACTERS,
            CAPITAL_LETTER,
            NUMBER
        )

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            password = INPUT_CONTENT_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements
        )

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Button_Disabled_When_Password_Input_Has_No_Content() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL
        )

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun need_Account_Button_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN
        )

        onNodeWithAuthenticationToggleModeAndTextExactlyNeedAnAccount()
            .assertIsDisplayed()

    }

    @Test
    fun already_Have_Account_Button_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP
        )

        onNodeWithAuthenticationToggleModeAndTextExactlyAlreadyHaveAnAccount()
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Error_Alert_Dialog_Not_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN
        )

        onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops()
            .assertDoesNotExist()

    }

    @Test
    fun sign_In_Error_Alert_Dialog_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            error = MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE
        )

        onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops()
            .assertIsDisplayed()

    }

    // Goes straight to the "Sign Up" screen without having to navigate from the "Sign In" screen.
    @Test
    fun sign_Up_Error_Alert_Dialog_Not_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP
        )

        onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops()
            .assertDoesNotExist()

    }

    // Goes straight to the "Sign Up" screen without having to navigate from the "Sign In" screen.
    @Test
    fun sign_Up_Error_Alert_Dialog_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            error = MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE
        )

        onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops()
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Progress_Indicator_Not_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            email = INPUT_CONTENT_EMAIL, // Optional but recommended
            password = INPUT_CONTENT_PASSWORD // Optional but recommended
        )

        onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
            .assertDoesNotExist()

    }

    @Test
    fun sign_Up_Progress_Indicator_Not_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL, // Optional but recommended
            password = INPUT_CONTENT_PASSWORD // Optional but recommended
        )

        onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
            .assertDoesNotExist()

    }

    /**
     * Trying to confirm whether it's Sign In mode would fail the test since the loading
     * indicator displays immediately on top of the screen making this node to appear as if it's
     * not displayed([SemanticsNodeInteraction.assertIsNotDisplayed]) or
     * existed([SemanticsNodeInteraction.assertDoesNotExist]).
     *
     * Since this is not a screen test but an individual component test, it's fine to make the
     * assumption that this is a Sign In mode. And this function [sign_In_Mode_Displayed] takes care
     * of testing if a screen is in Sign In mode.
     */
    @Test
    fun sign_In_Progress_Indicator_Displayed() {

        /**
         * This would fail the test when it should pass since the function
         * [setContentAsAuthenticationContentAndAssertItIsDisplayed] calls [assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed].
         * See this test case's KDoc.
         */
//        setContentAsAuthenticationContentAndAssertItIsDisplayed(
//            authenticationMode = SIGN_IN,
//            isLoading = TRUE
//        )

        setContentAsAuthenticationContent(
            composeTestRule = composeTestRule,
            authenticationMode = SIGN_IN,
            isLoading = TRUE,
            email = NULL,
            password = NULL,
            passwordRequirements = emptyList(),
            error = NULL
        )

        assertAuthenticationContentIsDisplayed()

        onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
            .assertIsDisplayed()

    }

    /**
     * Trying to confirm whether it's Sign Up mode would fail the test since the loading
     * indicator displays immediately on top of the screen making this node to appear as if it's
     * not displayed([SemanticsNodeInteraction.assertIsNotDisplayed]) or
     * existed([SemanticsNodeInteraction.assertDoesNotExist]).
     *
     * Since this is not a screen test but an individual component test, it's fine to make the
     * assumption that this is a Sign Up mode. And this function [sign_Up_Mode_Displayed] takes care
     * of testing if a screen is in Sign Up mode.
     */
    @Test
    fun sign_Up_Progress_Indicator_Displayed() {

        /**
         * This would fail the test when it should pass since the function
         * [setContentAsAuthenticationContentAndAssertItIsDisplayed] calls [assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed].
         * See this test case's KDoc.
         */
//        setContentAsAuthenticationContentAndAssertItIsDisplayed(
//            authenticationMode = SIGN_UP,
//            isLoading = TRUE
//        )

        setContentAsAuthenticationContent(
            composeTestRule = composeTestRule,
            authenticationMode = SIGN_UP,
            isLoading = TRUE,
            email = NULL,
            password = NULL,
            passwordRequirements = emptyList(),
            error = NULL
        )

        assertAuthenticationContentIsDisplayed()

        onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_No_Password_Requirement_Satisfied() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/components/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/components/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_No_Password_Requirement_Satisfied_AnotherApproach() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_Eight_Characters_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(EIGHT_CHARACTERS)

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfied(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/components/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/components/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_Only_At_Least_Eight_Characters_Satisfied_AnotherApproach() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(EIGHT_CHARACTERS)

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharacters(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_One_Uppercase_Letter_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(CAPITAL_LETTER)

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_PASS,
            passwordRequirements = satisfiedPasswordRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterSatisfied(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/components/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/components/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_Only_At_Least_One_Uppercase_Letter_Satisfied_AnotherApproach() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(CAPITAL_LETTER)

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_PASS,
            passwordRequirements = satisfiedPasswordRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAndTextExactlyAtLeastOneUppercaseLetter(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_One_Digit_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(NUMBER)

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_1PASS,
            passwordRequirements = satisfiedPasswordRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitSatisfied(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/components/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/components/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_Only_At_Least_One_Digit_Satisfied_AnotherApproach() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(NUMBER)

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_1PASS,
            passwordRequirements = satisfiedPasswordRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAndTextExactlyAtLeastOneDigit(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_All_Password_Requirements_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(
            EIGHT_CHARACTERS,
            CAPITAL_LETTER,
            NUMBER
        )

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfied(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterSatisfied(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitSatisfied(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/components/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/components/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_All_Password_Requirements_Satisfied_AnotherApproach() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(
            EIGHT_CHARACTERS,
            CAPITAL_LETTER,
            NUMBER
        )

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharacters(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAndTextExactlyAtLeastOneUppercaseLetter(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAndTextExactlyAtLeastOneDigit(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }
    
}