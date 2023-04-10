package emperorfin.android.militaryjet.ui.screens.authentication

import android.content.Context
import android.view.KeyEvent.*
import androidx.annotation.StringRes
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import emperorfin.android.militaryjet.test.R
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasAlertDialogTitle
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasCircularProgressIndicatorColorArgb
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.extensions.waitUntilDoesNotExist
import emperorfin.android.militaryjet.ui.extensions.waitUntilExists
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATION_TITLE
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_EMAIL
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_ERROR_DIALOG
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PROGRESS
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_SCREEN
import emperorfin.android.militaryjet.ui.utils.KeyboardHelper
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 06th January, 2023.
 */


/**
 * The following classes are revisions of this class:
 * [AuthenticationScreenTest3]
 * [AuthenticationScreenTest4]
 * [AuthenticationScreenTest5]
 *
 * Important:
 *
 * - Try not to run all the test cases by running this test class as some tests might fail. If you
 * do and some tests fail, try re-running those failed tests one after the other. If a test fails,
 * check the test function's KDoc/comment (if any) on possible solution to make the test pass when
 * it should.
 * - If you try to run a test and it fails, check the test function's KDoc/comment (if any) on
 * possible solution to make the test pass when it should.
 * - Test cases with "_AnotherApproach" suffixed in their function names might fail. A little
 * changes would need to be made for them to pass. Kindly take a look at the function's KDoc/comment
 * on how to make the test pass when it should.
 *
 * Useful Docs:
 *
 * - Compose [ComposeContentTestRule.waitUntil] test API as an alternative to Espresso's Idling
 * Resources ( https://medium.com/androiddevelopers/alternatives-to-idling-resources-in-compose-tests-8ae71f9fc473 ).
 *
 * - [Compose testing, matchers and more!]( https://developer.android.com/jetpack/compose/testing ).
 *
 * - [Compose testing cheatsheet]( https://developer.android.com/jetpack/compose/testing-cheatsheet )
 *
 * Notes:
 *
 * - If you ever need to pass a resource (e.g. a string resource) into a composable during testing,
 * be sure to use the one from the main source set and the R must be the one from
 * [emperorfin.android.militaryjet.R] and not
 * [emperorfin.android.militaryjet.test.R].
 * - Every other thing during testing that involves the use of a resource (e.g. a string resource)
 * such as performing matches or assertions, be sure to use the resource from the androidTest source
 * set (which you should've provided a copy and always in sync with the one from the main source set).
 * And the R must be the one from [emperorfin.android.militaryjet.test.R] instead of
 * [emperorfin.android.militaryjet.R].
 *
 * Be sure to have configured res srcDirs for androidTest sourceSet in app/build.gradle file.
 * See the following:
 * - https://stackoverflow.com/questions/36955608/espresso-how-to-use-r-string-resources-of-androidtest-folder
 * - https://stackoverflow.com/questions/26663539/configuring-res-srcdirs-for-androidtest-sourceset
 */
class AuthenticationScreenTest2 {

    companion object {

        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        private val NULL = null

        private const val THIS_STRING_MUST_BE_EMPTY: String = ""
        private const val THIS_STRING_COULD_BE_ANYTHING: String = ""

        private const val INPUT_CONTENT_EMAIL: String = "contact@email.com"
        private const val INPUT_CONTENT_PASSWORD: String = "passworD1"
        private const val INPUT_CONTENT_PASSWORD_PASSWORD: String = "password"
        private const val INPUT_CONTENT_PASSWORD_PASS: String = "Pass"
        private const val INPUT_CONTENT_PASSWORD_1PASS: String = "1pass"

        @StringRes
        private const val MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE: Int =
            emperorfin.android.militaryjet.R.string.test_error_message

        @StringRes
        private const val STRING_RES_SIGN_IN_TO_YOUR_ACCOUNT: Int = R.string.label_sign_in_to_account
        @StringRes
        private const val STRING_RES_SIGN_UP_FOR_AN_ACCOUNT: Int = R.string.label_sign_up_for_account
        @StringRes
        private const val STRING_RES_EMAIL_ADDRESS: Int = R.string.label_email
        @StringRes
        private const val STRING_RES_PASSWORD: Int = R.string.label_password
        @StringRes
        private const val STRING_RES_NEED_AN_ACCOUNT: Int = R.string.action_need_account
        @StringRes
        private const val STRING_RES_ALREADY_HAVE_AN_ACCOUNT: Int = R.string.action_already_have_account
        @StringRes
        private const val STRING_RES_SIGN_IN: Int = R.string.action_sign_in
        @StringRes
        private const val STRING_RES_SIGN_UP: Int = R.string.action_sign_up

        @StringRes
        private const val STRING_RES_AT_LEAST_EIGHT_CHARACTERS: Int = R.string.password_requirement_characters
        @StringRes
        private const val STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER: Int = R.string.password_requirement_capital
        @StringRes
        private const val STRING_RES_AT_LEAST_ONE_DIGIT: Int = R.string.password_requirement_digit
        @StringRes
        private const val STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED: Int = R.string.test_password_requirement_needed_characters
        @StringRes
        private const val STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_NEEDED: Int = R.string.test_password_requirement_needed_capital
        @StringRes
        private const val STRING_RES_AT_LEAST_ONE_DIGIT_NEEDED: Int = R.string.test_password_requirement_needed_digit
        @StringRes
        private const val STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED: Int = R.string.test_password_requirement_satisfied_characters
        @StringRes
        private const val STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_SATISFIED: Int = R.string.test_password_requirement_satisfied_capital
        @StringRes
        private const val STRING_RES_AT_LEAST_ONE_DIGIT_SATISFIED: Int = R.string.test_password_requirement_satisfied_digit

        @StringRes
        private const val STRING_RES_WHOOPS: Int = R.string.error_title

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

        /**
         * Since the delay time millis in the authenticate() function in app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/stateholders/AuthenticationViewModel.kt
         * is 2000L, the [ComposeContentTestRule.waitUntil] test API timeoutMillis should be
         * something greater than 2_000L such as 2_010L or 2_500L instead of the default 1_000L
         * value.
         *
         * For scenarios where the amount of time an operation such as network call is unknown, you
         * should keep increasing the timeoutMillis of the [ComposeContentTestRule.waitUntil] until
         * the test doesn't throw any error or exception related to timeout exception.
         */
        private const val TIMEOUT_MILLIS_2500L: Long = 2_500L

    }

    /**
     * Use this when resources are coming from the main source set, whether directly
     * (e.g. R.string.sample_text) or indirectly (e.g. [PasswordRequirement.EIGHT_CHARACTERS.label]
     * which is directly using a string resource).
     *
     * To actually reference the resource, you use
     * [emperorfin.android.militaryjet.R] and not
     * [emperorfin.android.militaryjet.test.R]
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
     * [emperorfin.android.militaryjet.test.R] and not
     * [emperorfin.android.militaryjet.R]
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

    private val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

    // This is no longer necessary and should be removed.
    private fun setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        keyboardHelper: KeyboardHelper
    ) {

        setContentAsAuthenticationScreenWithKeyboardHelperInit(composeTestRule, keyboardHelper)

        assertAuthenticationScreenIsDisplayed(composeTestRule)

    }

    // Should be removed.
//    private fun setContentAsAuthenticationScreenAndAssertItIsDisplayed(
//        composeTestRule: ComposeContentTestRule
//    ) {
//
//        setContentAsAuthenticationScreen(composeTestRule)
//
//        assertAuthenticationScreenIsDisplayed(composeTestRule)
//
//    }

    /**
     * @param isSignInMode This is nullable should there's a case where [assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed]
     * or [navigateFromSignInToSignUpModesAndConfirmTitles] doesn't have to be run. But such case
     * should be rare.
     */
    private fun setContentAsAuthenticationScreenAndAssertItIsDisplayed(
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
                assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed(
                    composeTestRule = composeTestRule
                )
            } else {
                navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule = composeTestRule)
            }
        }

    }

    // This is no longer necessary and should be removed.
    private fun setContentAsAuthenticationScreenWithKeyboardHelperInit(
        composeTestRule: ComposeContentTestRule, keyboardHelper: KeyboardHelper
    ) {

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
            }
        }

    }

    // Should be removed.
//    private fun setContentAsAuthenticationScreen(composeTestRule: ComposeContentTestRule) {
//
//        composeTestRule.setContent {
//            ComposeEmailAuthenticationTheme {
//                AuthenticationScreen()
//            }
//        }
//
//    }

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

        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed(composeTestRule)

        onNodeWithAuthenticationToggleModeAndTextExactlyNeedAnAccount()
            .performClick()

        assertAuthenticationScreenIsDisplayed(composeTestRule)

        assertAuthenticationTitleAndTextExactlySignUpForAnAccountIsDisplayed(composeTestRule)

    }

    // This is no longer necessary and should be removed.
    private fun assertSignInModeIsDisplayed(composeTestRule: ComposeContentTestRule) {

        assertAuthenticationScreenIsDisplayed(composeTestRule)

        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed(composeTestRule)

    }

    // This is no longer necessary and should be removed.
    private fun assertSignUpModeIsDisplayed(composeTestRule: ComposeContentTestRule) {

        assertAuthenticationScreenIsDisplayed(composeTestRule)

        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

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

    private fun onNodeWithAuthenticationScreen(
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

    private fun onNodeWithEmailInputAndTextExactlyEmailAddress(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        // This one works.
//        return composeTestRule
//            .onNodeWithTag(
//                testTag = TAG_AUTHENTICATION_INPUT_EMAIL,
//                useUnmergedTree = useUnmergedTree
//            )

        // This is recommended.
        return onNodeWithEmailInputAnd(
            otherMatcher = hasTextExactlyEmailAddress(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputAndTextExactlyPassword(
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

    private fun onNodeWithPasswordInputTrailingIcon(
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

    private fun onNodeWithEmailInputAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagEmailInput().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

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

    private fun hasTestTagEmailInput(): SemanticsMatcher {

        return hasTestTagsEmailInputAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagPasswordInput(): SemanticsMatcher {

        return hasTestTagsPasswordInputAnd(
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

    private fun hasTestTagsEmailInputAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_INPUT_EMAIL + otherTestTag
        )

    }

    private fun hasTestTagsPasswordInputAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_INPUT_PASSWORD + otherTestTag
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

    private fun hasTextExactlyEmailAddress(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_EMAIL_ADDRESS),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyPassword(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_PASSWORD),
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

    private fun hasTestTagCircularProgressIndicatorAndHasCircularProgressIndicatorColorArgbPresetColor():
            SemanticsMatcher {

        return hasTestTagCircularProgressIndicator().and(
            other = hasCircularProgressIndicatorColorArgbPresetColor()
        )

    }

    private fun hasTestTagAuthenticationErrorDialogAndHasAlertDialogTitleWhoops(): SemanticsMatcher {

        return hasTestTagAuthenticationErrorDialog().and(
            other = hasAlertDialogTitleWhoops()
        )

    }

    private fun hasTestTagAuthenticationTitleAndHasTextExactlySignInToYourAccount(): SemanticsMatcher {

        return hasTestTagAuthenticationTitle().and(
            other = hasTextExactlySignInToYourAccount()
        )

    }

    private fun hasTestTagAuthenticationTitleAndHasTextExactlySignUpForAnAccount(): SemanticsMatcher {

        return hasTestTagAuthenticationTitle().and(
            other = hasTextExactlySignUpForAnAccount()
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
    fun sign_In_Mode_Displayed_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertSignInModeIsDisplayed(composeTestRule)
        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed(composeTestRule)

    }

    @Test
    fun sign_Up_Mode_Displayed_After_Toggled() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        assertSignUpModeIsDisplayed(composeTestRule)
        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

    }

    @Test
    fun sign_In_Title_Displayed_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed(composeTestRule)

    }

    @Test
    fun sign_Up_Title_Displayed_After_Toggled() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

    }

    @Test
    fun sign_In_Button_Displayed_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Button_Displayed_After_Toggle() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .assertIsDisplayed()

    }

    @Test
    fun need_Account_Displayed_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithAuthenticationToggleModeAndTextExactlyNeedAnAccount()
            .assertIsDisplayed()

    }

    @Test
    fun already_Have_Account_Displayed_After_Toggle() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithAuthenticationToggleModeAndTextExactlyAlreadyHaveAnAccount()
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Button_Disabled_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_In_Button_Enabled_With_Valid_Form_Content() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .assertIsDisplayed()
            .assertIsEnabled()

    }

    @Test
    fun sign_In_Button_Disabled_When_Email_Input_Has_No_Content() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .assertIsDisplayed()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_In_Button_Disabled_When_Password_Input_Has_No_Content() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .assertIsDisplayed()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Enabled_With_Valid_Form_Content() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .assertIsDisplayed()
            .assertIsEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_When_Email_Input_Has_No_Content() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .assertIsDisplayed()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_When_Password_Input_Has_No_Content() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .assertIsDisplayed()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_In_Error_Alert_Dialog_Not_Displayed_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops()
            .assertDoesNotExist()

    }

    @Test
    fun sign_Up_Error_Alert_Dialog_Not_Displayed_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops()
            .assertDoesNotExist()

    }

    /**
     * NOTE:
     *
     * For this test case to pass when it should, whatever time delay that's used in the
     * authenticate() function in app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/stateholders/AuthenticationViewModel.kt,
     * the time delay used in this test case should be more than it.
     *
     * E.g., if 2000L milliseconds is used in the ViewModel, its recommended to wait for at least
     * 2500L milliseconds in the test case.
     *
     * Compose waitUntil test API as an alternative to Espresso's Idling Resources
     * ( https://medium.com/androiddevelopers/alternatives-to-idling-resources-in-compose-tests-8ae71f9fc473 ).
     *
     * Since a Repository (which a ViewModel would depend on for network or disk I/O call) or a
     * ViewModel (for time consuming task) takes some amount of time to fetch data or compute an
     * operation, the Compose waitUntil() test API shouldn't be used for this kind of scenario. Use
     * test doubles ( https://developer.android.com/training/testing/fundamentals/test-doubles )
     * instead.
     *
     * Assuming you want to test a part of the app the involves a network operation, the Repository
     * should be injectable in the ViewModel so that a fake Repository can be injected into the
     * ViewModel during testing in order to simulate the network operation.
     *
     * If you load data in a background thread, the test framework might execute the next operation
     * too soon, making your test fail. The worst situation is when this happens only a small
     * percentage of the time, making the test flaky.
     *
     * So this is where test doubles is really necessary as loading data is usually fast, especially
     * when using fake data, so why waste time with idling resources. If you don’t want to modify
     * your code under test to expose when it’s busy, one option is to waitUntil() a certain
     * condition is met, instead of waiting for an arbitrary amount of time.
     *
     * But this should be used as a last resort when installing an Idling Resource is not practical
     * or you don’t want to modify your production code. Using it before every test statement should
     * be considered a smell, as it pollutes the test code unnecessarily, making it harder to
     * maintain.
     *
     * But wait..., when you have hundreds or thousands of UI tests, you want tests to be as fast as
     * possible. Also, sometimes emulators or devices misbehave and jank, making that operation take
     * a bit longer than than the operation, breaking your build. When you have hundreds of tests
     * this becomes a huge issue.
     *
     * When should you use waitUntil() then? A good use case for it is loading data from an
     * observable (with LiveData, Kotlin Flow or RxJava). When your UI needs to receive multiple
     * updates before you consider it idle, you might want to simplify synchronization using
     * waitUntil().
     *
     * There is an open issue ( https://issuetracker.google.com/226934294 ) to introduce a more
     * sophisticated version of this helper in the future but for now, happy… wait for it… testing!
     *
     * For more info, see https://medium.com/androiddevelopers/alternatives-to-idling-resources-in-compose-tests-8ae71f9fc473
     */
    @Test
    fun sign_In_Error_Alert_Dialog_Displayed_After_Error() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .performClick()

        composeTestRule.waitUntilExists(
            matcher = hasTestTagAuthenticationErrorDialogAndHasAlertDialogTitleWhoops(),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            useUnmergedTree = TRUE // Optional.
        )

        onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    /**
     * NOTE:
     *
     * For this test case to pass when it should, whatever time delay that's used in the
     * authenticate() function in app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/stateholders/AuthenticationViewModel.kt,
     * the time delay used in this test case should be more than it.
     *
     * E.g., if 2000L milliseconds is used in the ViewModel, its recommended to wait for at least
     * 2500L milliseconds in the test case.
     *
     * Compose waitUntil test API as an alternative to Espresso's Idling Resources
     * ( https://medium.com/androiddevelopers/alternatives-to-idling-resources-in-compose-tests-8ae71f9fc473 ).
     *
     * Since a Repository (which a ViewModel would depend on for network or disk I/O call) or a
     * ViewModel (for time consuming task) takes some amount of time to fetch data or compute an
     * operation, the Compose waitUntil() test API shouldn't be used for this kind of scenario. Use
     * test doubles ( https://developer.android.com/training/testing/fundamentals/test-doubles )
     * instead.
     *
     * Assuming you want to test a part of the app the involves a network operation, the Repository
     * should be injectable in the ViewModel so that a fake Repository can be injected into the
     * ViewModel during testing in order to simulate the network operation.
     *
     * If you load data in a background thread, the test framework might execute the next operation
     * too soon, making your test fail. The worst situation is when this happens only a small
     * percentage of the time, making the test flaky.
     *
     * So this is where test doubles is really necessary as loading data is usually fast, especially
     * when using fake data, so why waste time with idling resources. If you don’t want to modify
     * your code under test to expose when it’s busy, one option is to waitUntil() a certain
     * condition is met, instead of waiting for an arbitrary amount of time.
     *
     * But this should be used as a last resort when installing an Idling Resource is not practical
     * or you don’t want to modify your production code. Using it before every test statement should
     * be considered a smell, as it pollutes the test code unnecessarily, making it harder to
     * maintain.
     *
     * But wait..., when you have hundreds or thousands of UI tests, you want tests to be as fast as
     * possible. Also, sometimes emulators or devices misbehave and jank, making that operation take
     * a bit longer than than the operation, breaking your build. When you have hundreds of tests
     * this becomes a huge issue.
     *
     * When should you use waitUntil() then? A good use case for it is loading data from an
     * observable (with LiveData, Kotlin Flow or RxJava). When your UI needs to receive multiple
     * updates before you consider it idle, you might want to simplify synchronization using
     * waitUntil().
     *
     * There is an open issue ( https://issuetracker.google.com/226934294 ) to introduce a more
     * sophisticated version of this helper in the future but for now, happy… wait for it… testing!
     *
     * For more info, see https://medium.com/androiddevelopers/alternatives-to-idling-resources-in-compose-tests-8ae71f9fc473
     */
    @Test
    fun sign_Up_Error_Alert_Dialog_Displayed_After_Error() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .performClick()

        composeTestRule.waitUntilExists(
            matcher = hasTestTagAuthenticationErrorDialogAndHasAlertDialogTitleWhoops(),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            useUnmergedTree = TRUE // Optional.
        )

        onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Progress_Indicator_Not_Displayed_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
            .assertDoesNotExist()

    }

    @Test
    fun sign_Up_Progress_Indicator_Not_Displayed_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
            .assertDoesNotExist()

    }

    /**
     * NOTE:
     *
     * Reducing the time delay to, e.g., 1 millisecond in the authenticate() function in
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/stateholders/AuthenticationViewModel.kt
     * will cause the test to fail with the following message:
     *
     * "java.lang.AssertionError: Failed to perform isDisplayed check. Reason: Expected exactly '1'
     * node but could not find any node that satisfies: (TestTag = 'ui.screens.authentication.uicomponents.authentication-progress')"
     *
     * This is because the length of time for the CircularProgressIndicator with the testTag
     * "ui.screens.authentication.uicomponents.authentication-progress" to remain on the screen
     * before it disappears is 1ms. 1 millisecond 0.001 second which is an ultra-short time.
     *
     * For a production app, if the time delay is so short for the CircularProgressIndicator to be
     * noticeable then a simulated delay would have to be injected for the test to pass.
     */
    @Test
    fun sign_In_Progress_Indicator_Displayed_While_Loading() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .performClick()

        onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
            .assertIsDisplayed()

    }

    /**
     * NOTE:
     *
     * Reducing the time delay to, e.g., 1 millisecond in the authenticate() function in
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/stateholders/AuthenticationViewModel.kt
     * will cause the test to fail with the following message:
     *
     * "java.lang.AssertionError: Failed to perform isDisplayed check. Reason: Expected exactly '1'
     * node but could not find any node that satisfies: (TestTag = 'ui.screens.authentication.uicomponents.authentication-progress')"
     *
     * This is because the length of time for the CircularProgressIndicator with the testTag
     * "ui.screens.authentication.uicomponents.authentication-progress" to remain on the screen
     * before it disappears is 1ms. 1 millisecond 0.001 second which is an ultra-short time.
     *
     * For a production app, if the time delay is so short for the CircularProgressIndicator to be
     * noticeable then a simulated delay would have to be injected for the test to pass.
     */
    @Test
    fun sign_Up_Progress_Indicator_Displayed_While_Loading() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .performClick()

        onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
            .assertIsDisplayed()

    }

    /**
     * NOTE:
     *
     * For this test case to pass when it should, whatever time delay that's used in the
     * authenticate() function in app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/stateholders/AuthenticationViewModel.kt,
     * the time delay used in this test case should be more than it.
     *
     * E.g., if 2000L milliseconds is used in the ViewModel, its recommended to wait for at least
     * 2500L milliseconds in the test case.
     *
     * Compose waitUntil test API as an alternative to Espresso's Idling Resources
     * ( https://medium.com/androiddevelopers/alternatives-to-idling-resources-in-compose-tests-8ae71f9fc473 ).
     *
     * Since a Repository (which a ViewModel would depend on for network or disk I/O call) or a
     * ViewModel (for time consuming task) takes some amount of time to fetch data or compute an
     * operation, the Compose waitUntil() test API shouldn't be used for this kind of scenario. Use
     * test doubles ( https://developer.android.com/training/testing/fundamentals/test-doubles )
     * instead.
     *
     * Assuming you want to test a part of the app the involves a network operation, the Repository
     * should be injectable in the ViewModel so that a fake Repository can be injected into the
     * ViewModel during testing in order to simulate the network operation.
     *
     * If you load data in a background thread, the test framework might execute the next operation
     * too soon, making your test fail. The worst situation is when this happens only a small
     * percentage of the time, making the test flaky.
     *
     * So this is where test doubles is really necessary as loading data is usually fast, especially
     * when using fake data, so why waste time with idling resources. If you don’t want to modify
     * your code under test to expose when it’s busy, one option is to waitUntil() a certain
     * condition is met, instead of waiting for an arbitrary amount of time.
     *
     * But this should be used as a last resort when installing an Idling Resource is not practical
     * or you don’t want to modify your production code. Using it before every test statement should
     * be considered a smell, as it pollutes the test code unnecessarily, making it harder to
     * maintain.
     *
     * But wait..., when you have hundreds or thousands of UI tests, you want tests to be as fast as
     * possible. Also, sometimes emulators or devices misbehave and jank, making that operation take
     * a bit longer than than the operation, breaking your build. When you have hundreds of tests
     * this becomes a huge issue.
     *
     * When should you use waitUntil() then? A good use case for it is loading data from an
     * observable (with LiveData, Kotlin Flow or RxJava). When your UI needs to receive multiple
     * updates before you consider it idle, you might want to simplify synchronization using
     * waitUntil().
     *
     * There is an open issue ( https://issuetracker.google.com/226934294 ) to introduce a more
     * sophisticated version of this helper in the future but for now, happy… wait for it… testing!
     *
     * For more info, see https://medium.com/androiddevelopers/alternatives-to-idling-resources-in-compose-tests-8ae71f9fc473
     */
    @Test
    fun sign_In_Progress_Indicator_Not_Displayed_After_Loading() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .performClick()

        onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        composeTestRule.waitUntilDoesNotExist(
            matcher = hasTestTagCircularProgressIndicatorAndHasCircularProgressIndicatorColorArgbPresetColor(),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            useUnmergedTree = TRUE // Optional.
        )

        onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor(
            useUnmergedTree = TRUE // Optional
        )
            .assertDoesNotExist()

    }

    /**
     * NOTE:
     *
     * For this test case to pass when it should, whatever time delay that's used in the
     * authenticate() function in app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/stateholders/AuthenticationViewModel.kt,
     * the time delay used in this test case should be more than it.
     *
     * E.g., if 2000L milliseconds is used in the ViewModel, its recommended to wait for at least
     * 2500L milliseconds in the test case.
     *
     * Compose waitUntil test API as an alternative to Espresso's Idling Resources
     * ( https://medium.com/androiddevelopers/alternatives-to-idling-resources-in-compose-tests-8ae71f9fc473 ).
     *
     * Since a Repository (which a ViewModel would depend on for network or disk I/O call) or a
     * ViewModel (for time consuming task) takes some amount of time to fetch data or compute an
     * operation, the Compose waitUntil() test API shouldn't be used for this kind of scenario. Use
     * test doubles ( https://developer.android.com/training/testing/fundamentals/test-doubles )
     * instead.
     *
     * Assuming you want to test a part of the app the involves a network operation, the Repository
     * should be injectable in the ViewModel so that a fake Repository can be injected into the
     * ViewModel during testing in order to simulate the network operation.
     *
     * If you load data in a background thread, the test framework might execute the next operation
     * too soon, making your test fail. The worst situation is when this happens only a small
     * percentage of the time, making the test flaky.
     *
     * So this is where test doubles is really necessary as loading data is usually fast, especially
     * when using fake data, so why waste time with idling resources. If you don’t want to modify
     * your code under test to expose when it’s busy, one option is to waitUntil() a certain
     * condition is met, instead of waiting for an arbitrary amount of time.
     *
     * But this should be used as a last resort when installing an Idling Resource is not practical
     * or you don’t want to modify your production code. Using it before every test statement should
     * be considered a smell, as it pollutes the test code unnecessarily, making it harder to
     * maintain.
     *
     * But wait..., when you have hundreds or thousands of UI tests, you want tests to be as fast as
     * possible. Also, sometimes emulators or devices misbehave and jank, making that operation take
     * a bit longer than than the operation, breaking your build. When you have hundreds of tests
     * this becomes a huge issue.
     *
     * When should you use waitUntil() then? A good use case for it is loading data from an
     * observable (with LiveData, Kotlin Flow or RxJava). When your UI needs to receive multiple
     * updates before you consider it idle, you might want to simplify synchronization using
     * waitUntil().
     *
     * There is an open issue ( https://issuetracker.google.com/226934294 ) to introduce a more
     * sophisticated version of this helper in the future but for now, happy… wait for it… testing!
     *
     * For more info, see https://medium.com/androiddevelopers/alternatives-to-idling-resources-in-compose-tests-8ae71f9fc473
     */
    @Test
    fun sign_Up_Progress_Indicator_Not_Displayed_After_Loading() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .performClick()

        onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        composeTestRule.waitUntilDoesNotExist(
            matcher = hasTestTagCircularProgressIndicatorAndHasCircularProgressIndicatorColorArgbPresetColor(),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            useUnmergedTree = TRUE // Optional.
        )

        onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor(
            useUnmergedTree = TRUE // Optional
        )
            .assertDoesNotExist()

    }

    /**
     * NOTE:
     *
     * For this test case to pass when it should, whatever time delay that's used in the
     * authenticate() function in app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/stateholders/AuthenticationViewModel.kt,
     * the time delay used in this test case should be more than it.
     *
     * E.g., if 2000L milliseconds is used in the ViewModel, its recommended to wait for at least
     * 2500L milliseconds in the test case.
     *
     * Compose waitUntil test API as an alternative to Espresso's Idling Resources
     * ( https://medium.com/androiddevelopers/alternatives-to-idling-resources-in-compose-tests-8ae71f9fc473 ).
     *
     * Since a Repository (which a ViewModel would depend on for network or disk I/O call) or a
     * ViewModel (for time consuming task) takes some amount of time to fetch data or compute an
     * operation, the Compose waitUntil() test API shouldn't be used for this kind of scenario. Use
     * test doubles ( https://developer.android.com/training/testing/fundamentals/test-doubles )
     * instead.
     *
     * Assuming you want to test a part of the app the involves a network operation, the Repository
     * should be injectable in the ViewModel so that a fake Repository can be injected into the
     * ViewModel during testing in order to simulate the network operation.
     *
     * If you load data in a background thread, the test framework might execute the next operation
     * too soon, making your test fail. The worst situation is when this happens only a small
     * percentage of the time, making the test flaky.
     *
     * So this is where test doubles is really necessary as loading data is usually fast, especially
     * when using fake data, so why waste time with idling resources. If you don’t want to modify
     * your code under test to expose when it’s busy, one option is to waitUntil() a certain
     * condition is met, instead of waiting for an arbitrary amount of time.
     *
     * But this should be used as a last resort when installing an Idling Resource is not practical
     * or you don’t want to modify your production code. Using it before every test statement should
     * be considered a smell, as it pollutes the test code unnecessarily, making it harder to
     * maintain.
     *
     * But wait..., when you have hundreds or thousands of UI tests, you want tests to be as fast as
     * possible. Also, sometimes emulators or devices misbehave and jank, making that operation take
     * a bit longer than than the operation, breaking your build. When you have hundreds of tests
     * this becomes a huge issue.
     *
     * When should you use waitUntil() then? A good use case for it is loading data from an
     * observable (with LiveData, Kotlin Flow or RxJava). When your UI needs to receive multiple
     * updates before you consider it idle, you might want to simplify synchronization using
     * waitUntil().
     *
     * There is an open issue ( https://issuetracker.google.com/226934294 ) to introduce a more
     * sophisticated version of this helper in the future but for now, happy… wait for it… testing!
     *
     * For more info, see https://medium.com/androiddevelopers/alternatives-to-idling-resources-in-compose-tests-8ae71f9fc473
     */
    @Test
    fun sign_In_Ui_Components_Displayed_After_Loading() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .performClick()

        composeTestRule.waitUntilExists(
            matcher = hasTestTagAuthenticationTitleAndHasTextExactlySignInToYourAccount(), // hasTestTagAuthenticationScreen()
            timeoutMillis = TIMEOUT_MILLIS_2500L
        )

        onNodeWithAuthenticationScreen()
            .assertExists()

        /**
         * This would fail the test if [hasTestTagAuthenticationScreen] is used in the above
         * [ComposeContentTestRule.waitUntilExists] function (NB: this is an extension function).
         */
        onNodeWithAuthenticationTitleAndTextExactlySignInToYourAccount()
            .assertExists()

    }

    /**
     * NOTE:
     *
     * For this test case to pass when it should, whatever time delay that's used in the
     * authenticate() function in app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/stateholders/AuthenticationViewModel.kt,
     * the time delay used in this test case should be more than it.
     *
     * E.g., if 2000L milliseconds is used in the ViewModel, its recommended to wait for at least
     * 2500L milliseconds in the test case.
     *
     * Compose waitUntil test API as an alternative to Espresso's Idling Resources
     * ( https://medium.com/androiddevelopers/alternatives-to-idling-resources-in-compose-tests-8ae71f9fc473 ).
     *
     * Since a Repository (which a ViewModel would depend on for network or disk I/O call) or a
     * ViewModel (for time consuming task) takes some amount of time to fetch data or compute an
     * operation, the Compose waitUntil() test API shouldn't be used for this kind of scenario. Use
     * test doubles ( https://developer.android.com/training/testing/fundamentals/test-doubles )
     * instead.
     *
     * Assuming you want to test a part of the app the involves a network operation, the Repository
     * should be injectable in the ViewModel so that a fake Repository can be injected into the
     * ViewModel during testing in order to simulate the network operation.
     *
     * If you load data in a background thread, the test framework might execute the next operation
     * too soon, making your test fail. The worst situation is when this happens only a small
     * percentage of the time, making the test flaky.
     *
     * So this is where test doubles is really necessary as loading data is usually fast, especially
     * when using fake data, so why waste time with idling resources. If you don’t want to modify
     * your code under test to expose when it’s busy, one option is to waitUntil() a certain
     * condition is met, instead of waiting for an arbitrary amount of time.
     *
     * But this should be used as a last resort when installing an Idling Resource is not practical
     * or you don’t want to modify your production code. Using it before every test statement should
     * be considered a smell, as it pollutes the test code unnecessarily, making it harder to
     * maintain.
     *
     * But wait..., when you have hundreds or thousands of UI tests, you want tests to be as fast as
     * possible. Also, sometimes emulators or devices misbehave and jank, making that operation take
     * a bit longer than than the operation, breaking your build. When you have hundreds of tests
     * this becomes a huge issue.
     *
     * When should you use waitUntil() then? A good use case for it is loading data from an
     * observable (with LiveData, Kotlin Flow or RxJava). When your UI needs to receive multiple
     * updates before you consider it idle, you might want to simplify synchronization using
     * waitUntil().
     *
     * There is an open issue ( https://issuetracker.google.com/226934294 ) to introduce a more
     * sophisticated version of this helper in the future but for now, happy… wait for it… testing!
     *
     * For more info, see https://medium.com/androiddevelopers/alternatives-to-idling-resources-in-compose-tests-8ae71f9fc473
     */
    @Test
    fun sign_Up_Ui_Components_Displayed_After_Loading() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .performClick()

        composeTestRule
            .waitUntilExists(
                matcher = hasTestTagAuthenticationTitleAndHasTextExactlySignUpForAnAccount(), // hasTestTagAuthenticationScreen()
                timeoutMillis = TIMEOUT_MILLIS_2500L
            )

        onNodeWithAuthenticationScreen()
            .assertExists()

        /**
         * This would fail the test if [hasTestTagAuthenticationScreen] is used in the above
         * [ComposeContentTestRule.waitUntilExists] function (NB: this is an extension function).
         */
        onNodeWithAuthenticationTitleAndTextExactlySignUpForAnAccount()
            .assertExists()

    }

    //....|

    @Test
    fun sign_In_Email_Text_Field_Is_Focused_When_Clicked() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .apply {
                performClick()

                assertIsFocused()
            }

    }

    @Test
    fun sign_In_Password_Text_Field_Is_Focused_When_Clicked() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .apply {
                performClick()

                assertIsFocused()
            }

    }

    @Test
    fun sign_Up_Email_Text_Field_Is_Focused_When_Clicked() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .apply {
                performClick()

                assertIsFocused()
            }

    }

    @Test
    fun sign_Up_Password_Text_Field_Is_Focused_When_Clicked() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .apply {
                performClick()

                assertIsFocused()
            }

    }

    //....|

    @Test
    fun sign_In_Password_Text_Field_Is_Focused_When_Next_Ime_Action_Clicked_From_Email_Text_Field() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsFocused()

    }

    @Test
    fun sign_Up_Password_Text_Field_Is_Focused_When_Next_Ime_Action_Clicked_From_Email_Text_Field() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsFocused()

    }

    @Test
    fun sign_In_Progress_Indicator_Displays_When_Done_Ime_Action_Clicked_From_Password_Text_Field() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        onNodeWithPasswordInputAndTextExactlyPassword()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_PASSWORD
                )

                performImeAction()
            }

        onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Progress_Indicator_Displays_When_Done_Ime_Action_Clicked_From_Password_Text_Field() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        onNodeWithPasswordInputAndTextExactlyPassword()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_PASSWORD
                )

                performImeAction()
            }

        onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_No_Password_Requirement_Satisfied_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

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
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_No_Password_Requirement_Satisfied_By_Default_AnotherApproach() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

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

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASSWORD
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
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_Only_At_Least_Eight_Characters_Satisfied_AnotherApproach() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASSWORD
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

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASS
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
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_Only_At_Least_One_Uppercase_Letter_Satisfied_AnotherApproach() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASS
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

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_1PASS
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
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_Only_At_Least_One_Digit_Satisfied_AnotherApproach() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_1PASS
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

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
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
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_All_Password_Requirements_Satisfied_AnotherApproach() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
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

    @Test
    fun sign_In_Perform_Key_Press_On_Text_Fields() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .apply {
                performClick()

//                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_SHIFT_LEFT)))
//                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_SHIFT_RIGHT)))
//                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_CAPS_LOCK)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_C)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_O)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_N)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_T)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_A)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_C)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_T)))

                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_AT)))

                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_E)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_M)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_A)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_I)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_L)))

                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_PERIOD)))

                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_C)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_O)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_M)))

                assertTextContains(value = INPUT_CONTENT_EMAIL)
            }

        // Just to change the VisualTransformation of the PasswordInput so the test could pass when
        // it should.
        onNodeWithPasswordInputTrailingIcon()
            .performClick()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .apply {
                performClick()

                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_P)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_A)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_S)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_S)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_W)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_O)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_R)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_D)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_1)))

                assertTextContains(value = INPUT_CONTENT_PASSWORD.lowercase())
            }

    }

    // If this test case fails, try to re-run it for a second time (which should pass).
    @Test
    fun sign_Up_Perform_Key_Press_On_Text_Fields() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .apply {
                performClick()

//                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_SHIFT_LEFT)))
//                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_SHIFT_RIGHT)))
//                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_CAPS_LOCK)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_C)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_O)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_N)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_T)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_A)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_C)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_T)))

                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_AT)))

                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_E)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_M)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_A)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_I)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_L)))

                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_PERIOD)))

                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_C)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_O)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_M)))

                assertTextContains(value = INPUT_CONTENT_EMAIL)
            }

        // Just to change the VisualTransformation of the PasswordInput so the test could pass when
        // it should.
        onNodeWithPasswordInputTrailingIcon()
            .performClick()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .apply {
                performClick()

                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_P)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_A)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_S)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_S)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_W)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_O)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_R)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_D)))
                performKeyPress(KeyEvent(NativeKeyEvent(ACTION_DOWN, KEYCODE_1)))

                assertTextContains(value = INPUT_CONTENT_PASSWORD.lowercase())
            }

    }

    @Test
    fun sign_In_To_Sign_Up_And_Back_To_Sign_In() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        // This should have been used but navigation has been between modes (SignIn and SignUp) but
        // inside of a single AuthenticationScreen screen (i.e. route/destination).
//        Espresso.pressBack()

        onNodeWithAuthenticationToggleModeAndTextExactlyAlreadyHaveAnAccount()
            .performClick()

        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

    }

    @Test
    fun sign_In_No_Text_Field_Showing_Soft_Keyboard_By_Default() {

        // Since this has been replaced, it should be removed.
//        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(
            keyboardHelper = keyboardHelper, isSignInMode = TRUE
        )

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_In_Email_Input_Shows_Soft_Keyboard_When_Clicked() {

        // Since this has been replaced, it should be removed.
//        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(
            keyboardHelper = keyboardHelper, isSignInMode = TRUE
        )

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_In_Password_Input_Shows_Soft_Keyboard_When_Clicked() {

        // Since this has been replaced, it should be removed.
//        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(
            keyboardHelper = keyboardHelper, isSignInMode = TRUE
        )

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_In_Email_Input_Soft_Keyboard_Manually_Closes_After_Shown() {

        // Since this has been replaced, it should be removed.
//        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(
            keyboardHelper = keyboardHelper, isSignInMode = TRUE
        )

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        // Before this point of trying to close the soft keyboard, the keyboard must have been shown
        // since a click was performed on the TextField which automatically shows the keyboard.
        // It suffices to say that hideKeyboard() rather than hideKeyboardIfShown() (even though
        // with it the test would pass when it should but it doesn't properly test that the keyboard
        // was actually shown before trying to close it) should be called on the KeyboardHelper
        // object.
        keyboardHelper.hideKeyboard()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_In_Password_Input_Soft_Keyboard_Manually_Closes_After_Shown() {

        // Since this has been replaced, it should be removed.
//        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(
            keyboardHelper = keyboardHelper, isSignInMode = TRUE
        )

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        // Before this point of trying to close the soft keyboard, the keyboard must have been shown
        // since a click was performed on the TextField which automatically shows the keyboard.
        // It suffices to say that hideKeyboard() rather than hideKeyboardIfShown() (even though
        // with it the test would pass when it should but it doesn't properly test that the keyboard
        // was actually shown before trying to close it) should be called on the KeyboardHelper
        // object.
        keyboardHelper.hideKeyboard()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_In_Email_Input_Soft_Keyboard_Closes_When_Next_Ime_Action_Clicked() {

        // Since this has been replaced, it should be removed.
//        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(
            keyboardHelper = keyboardHelper, isSignInMode = TRUE
        )

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_In_Password_Input_Soft_Keyboard_Closes_When_Done_Ime_Action_Clicked() {

        // Since this has been replaced, it should be removed.
//        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(
            keyboardHelper = keyboardHelper, isSignInMode = TRUE
        )

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    // If this test fails, try a re-run (may be more than once - it should pass).
    @Test
    fun sign_In_Password_Input_Soft_Keyboard_Shown_When_Next_Ime_Action_Clicked_From_Email_Input() {

        // Since this has been replaced, it should be removed.
//        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(
            keyboardHelper = keyboardHelper, isSignInMode = TRUE
        )

        // This is no longer necessary and should be removed.
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsFocused()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsFocused()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_Up_No_Text_Field_Showing_Soft_Keyboard_By_Default() {

        // Since this has been replaced, it should be removed.
//        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(
            keyboardHelper = keyboardHelper, isSignInMode = FALSE
        )

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_Up_Email_Input_Shows_Soft_Keyboard_When_Clicked() {

        // Since this has been replaced, it should be removed.
//        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(
            keyboardHelper = keyboardHelper, isSignInMode = FALSE
        )

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_Up_Password_Input_Shows_Soft_Keyboard_When_Clicked() {

        // Since this has been replaced, it should be removed.
//        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(
            keyboardHelper = keyboardHelper, isSignInMode = FALSE
        )

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_Up_Email_Input_Soft_Keyboard_Manually_Closes_After_Shown() {

        // Since this has been replaced, it should be removed.
//        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(
            keyboardHelper = keyboardHelper, isSignInMode = FALSE
        )

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        // Before this point of trying to close the soft keyboard, the keyboard must have been shown
        // since a click was performed on the TextField which automatically shows the keyboard.
        // It suffices to say that hideKeyboard() rather than hideKeyboardIfShown() (even though
        // with it the test would pass when it should but it doesn't properly test that the keyboard
        // was actually shown before trying to close it) should be called on the KeyboardHelper
        // object.
        keyboardHelper.hideKeyboard()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_Up_Password_Input_Soft_Keyboard_Manually_Closes_After_Shown() {

        // Since this has been replaced, it should be removed.
//        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(
            keyboardHelper = keyboardHelper, isSignInMode = FALSE
        )

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        // Before this point of trying to close the soft keyboard, the keyboard must have been shown
        // since a click was performed on the TextField which automatically shows the keyboard.
        // It suffices to say that hideKeyboard() rather than hideKeyboardIfShown() (even though
        // with it the test would pass when it should but it doesn't properly test that the keyboard
        // was actually shown before trying to close it) should be called on the KeyboardHelper
        // object.
        keyboardHelper.hideKeyboard()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_Up_Email_Input_Soft_Keyboard_Closes_When_Next_Ime_Action_Clicked() {

        // Since this has been replaced, it should be removed.
//        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(
            keyboardHelper = keyboardHelper, isSignInMode = FALSE
        )

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_Up_Password_Input_Soft_Keyboard_Closes_When_Done_Ime_Action_Clicked() {

        // Since this has been replaced, it should be removed.
//        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(
            keyboardHelper = keyboardHelper, isSignInMode = FALSE
        )

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_Up_Password_Input_Soft_Keyboard_Shown_When_Next_Ime_Action_Clicked_From_Email_Input() {

        // Since this has been replaced, it should be removed.
//        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(
            keyboardHelper = keyboardHelper, isSignInMode = FALSE
        )

        // This is no longer necessary and should be removed.
//        navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsFocused()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsFocused()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

}