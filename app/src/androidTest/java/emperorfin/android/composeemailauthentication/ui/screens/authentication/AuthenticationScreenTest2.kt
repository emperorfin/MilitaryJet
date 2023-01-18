package emperorfin.android.composeemailauthentication.ui.screens.authentication

import android.content.Context
import android.view.KeyEvent.*
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import emperorfin.android.composeemailauthentication.test.R
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.composeemailauthentication.ui.extensions.waitUntilDoesNotExist
import emperorfin.android.composeemailauthentication.ui.extensions.waitUntilExists
import emperorfin.android.composeemailauthentication.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.AuthenticationContent
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATION_TITLE
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_CONTENT
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_EMAIL
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_ERROR_DIALOG
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PROGRESS
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT
import emperorfin.android.composeemailauthentication.ui.utils.KeyboardHelper
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 06th January, 2023.
 */


/**
 * This class is a clean version of [AuthenticationScreenTest] class.
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
 * [emperorfin.android.composeemailauthentication.R] and not
 * [emperorfin.android.composeemailauthentication.test.R].
 * - Every other thing during testing that involves the use of a resource (e.g. a string resource)
 * such as performing matches or assertions, be sure to use the resource from the androidTest source
 * set (which you should've provided a copy and always in sync with the one from the main source set).
 * And the R must be the one from [emperorfin.android.composeemailauthentication.test.R] instead of
 * [emperorfin.android.composeemailauthentication.R].
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

        private const val INPUT_CONTENT_EMAIL: String = "contact@email.com"
        private const val INPUT_CONTENT_PASSWORD: String = "passworD1"
        private const val INPUT_CONTENT_PASSWORD_PASSWORD: String = "password"
        private const val INPUT_CONTENT_PASSWORD_PASS: String = "Pass"
        private const val INPUT_CONTENT_PASSWORD_1PASS: String = "1pass"

        private const val MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE =
            emperorfin.android.composeemailauthentication.R.string.test_error_message

        /**
         * Since the delay time millis in the authenticate() function in app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/stateholders/AuthenticationViewModel.kt
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
     * [emperorfin.android.composeemailauthentication.R] and not
     * [emperorfin.android.composeemailauthentication.test.R]
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
     * [emperorfin.android.composeemailauthentication.test.R] and not
     * [emperorfin.android.composeemailauthentication.R]
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

    private fun setContentAsAuthenticationScreen(composeTestRule: ComposeContentTestRule) {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
            }
        }

    }

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

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationScreen]
     * composable in the [ComposeContentTestRule.setContent]
     */
    private fun assertAuthenticationScreenIsDisplayed(composeTestRule: ComposeContentTestRule) {

        onNodeWithAuthenticationScreen()
            .assertIsDisplayed()

    }

    private fun assertSignInTitleIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithSignInTitle()
            .assertIsDisplayed()

    }

    private fun assertSignUpTitleIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithSignUpTitle()
            .assertIsDisplayed()

    }

    private fun navigateFromSigninToSignupModesAndConfirmTitles(
        composeTestRule: ComposeContentTestRule
    ) {

        assertSignInTitleIsDisplayed(composeTestRule)

        onNodeWithNeedAccountButton()
            .performClick()

        assertAuthenticationScreenIsDisplayed(composeTestRule)

        assertSignUpTitleIsDisplayed(composeTestRule)

    }

    private fun assertSignInModeIsDisplayed(composeTestRule: ComposeContentTestRule) {

        assertAuthenticationScreenIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed(composeTestRule)

    }

    private fun assertSignUpModeIsDisplayed(composeTestRule: ComposeContentTestRule) {

        assertAuthenticationScreenIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

    }

    private fun setContentAsAuthenticationScreenAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule
    ) {

        setContentAsAuthenticationScreen(composeTestRule)

        assertAuthenticationScreenIsDisplayed(composeTestRule)

    }

    private fun setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule, keyboardHelper: KeyboardHelper
    ) {

        setContentAsAuthenticationScreenWithKeyboardHelperInit(composeTestRule, keyboardHelper)

        assertAuthenticationScreenIsDisplayed(composeTestRule)

    }

    private fun onNodeWithAuthenticationScreen(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_CONTENT
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithSignInTitle(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE
                ).and(
                    // Optional but recommended.
                    // This is just to confirm the screen since components like EmailInput (others are
                    // PasswordInput, AuthenticationToggleMode, AuthenticationErrorDialog, AuthenticationTitle,
                    // AuthenticationButton) are being used in multiple screens such as SignUp and SignIn screens.
                    // Although, here, the screens are modes while the actual screen is just a single AuthenticationScreen.
                    // So it best to uniquely identify a component when used in more than one screen or modes.
                    // TODO:
                    //  To uniquely identify a composable which is being used in multiple screens, its best to
                    //  assign each screen a screen tag to be used as sub-tags for the composable.
                    //  That way, when a screen that is being tested with the composable passes, you are sure
                    //  that the composable was uniquely identified for that particular screen and not just any
                    //  screen. This means that that particular screen is properly tested.
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_in_to_account),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithSignUpTitle(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE
                ).and(
                    // Optional but recommended.
                    // This is just to confirm the screen since components like EmailInput (others are
                    // PasswordInput, AuthenticationToggleMode, AuthenticationErrorDialog, AuthenticationTitle,
                    // AuthenticationButton) are being used in multiple screens such as SignUp and SignIn screens.
                    // Although, here, the screens are modes while the actual screen is just a single AuthenticationScreen.
                    // So it best to uniquely identify a component when used in more than one screen or modes.
                    // TODO:
                    //  To uniquely identify a composable which is being used in multiple screens, its best to
                    //  assign each screen a screen tag to be used as sub-tags for the composable.
                    //  That way, when a screen that is being tested with the composable passes, you are sure
                    //  that the composable was uniquely identified for that particular screen and not just any
                    //  screen. This means that that particular screen is properly tested.
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_up_for_account),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithSignInButton(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    // Optional but recommended.
                    // This is just to confirm the screen since components like EmailInput (others are
                    // PasswordInput, AuthenticationToggleMode, AuthenticationErrorDialog, AuthenticationTitle,
                    // AuthenticationButton) are being used in multiple screens such as SignUp and SignIn screens.
                    // Although, here, the screens are modes while the actual screen is just a single AuthenticationScreen.
                    // So it best to uniquely identify a component when used in more than one screen or modes.
                    // TODO:
                    //  To uniquely identify a composable which is being used in multiple screens, its best to
                    //  assign each screen a screen tag to be used as sub-tags for the composable.
                    //  That way, when a screen that is being tested with the composable passes, you are sure
                    //  that the composable was uniquely identified for that particular screen and not just any
                    //  screen. This means that that particular screen is properly tested.
                    other = hasTextExactly(
                        mContext.getString(R.string.action_sign_in),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithSignUpButton(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    // Optional but recommended.
                    // This is just to confirm the screen since components like EmailInput (others are
                    // PasswordInput, AuthenticationToggleMode, AuthenticationErrorDialog, AuthenticationTitle,
                    // AuthenticationButton) are being used in multiple screens such as SignUp and SignIn screens.
                    // Although, here, the screens are modes while the actual screen is just a single AuthenticationScreen.
                    // So it best to uniquely identify a component when used in more than one screen or modes.
                    // TODO:
                    //  To uniquely identify a composable which is being used in multiple screens, its best to
                    //  assign each screen a screen tag to be used as sub-tags for the composable.
                    //  That way, when a screen that is being tested with the composable passes, you are sure
                    //  that the composable was uniquely identified for that particular screen and not just any
                    //  screen. This means that that particular screen is properly tested.
                    other = hasTextExactly(
                        mContext.getString(R.string.action_sign_up),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithNeedAccountButton(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                ).and(
                    // Optional but recommended.
                    // This is just to confirm the screen since components like EmailInput (others are
                    // PasswordInput, AuthenticationToggleMode, AuthenticationErrorDialog, AuthenticationTitle,
                    // AuthenticationButton) are being used in multiple screens such as SignUp and SignIn screens.
                    // Although, here, the screens are modes while the actual screen is just a single AuthenticationScreen.
                    // So it best to uniquely identify a component when used in more than one screen or modes.
                    // TODO:
                    //  To uniquely identify a composable which is being used in multiple screens, its best to
                    //  assign each screen a screen tag to be used as sub-tags for the composable.
                    //  That way, when a screen that is being tested with the composable passes, you are sure
                    //  that the composable was uniquely identified for that particular screen and not just any
                    //  screen. This means that that particular screen is properly tested.
                    other = hasTextExactly(
                        mContext.getString(R.string.action_need_account),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithAlreadyHaveAccountButton(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                ).and(
                    // Optional but recommended.
                    // This is just to confirm the screen since components like EmailInput (others are
                    // PasswordInput, AuthenticationToggleMode, AuthenticationErrorDialog, AuthenticationTitle,
                    // AuthenticationButton) are being used in multiple screens such as SignUp and SignIn screens.
                    // Although, here, the screens are modes while the actual screen is just a single AuthenticationScreen.
                    // So it best to uniquely identify a component when used in more than one screen or modes.
                    // TODO:
                    //  To uniquely identify a composable which is being used in multiple screens, its best to
                    //  assign each screen a screen tag to be used as sub-tags for the composable.
                    //  That way, when a screen that is being tested with the composable passes, you are sure
                    //  that the composable was uniquely identified for that particular screen and not just any
                    //  screen. This means that that particular screen is properly tested.
                    other = hasTextExactly(
                        mContext.getString(R.string.action_already_have_account),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithEmailInput(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        // This one works.
//        return composeTestRule
//            .onNodeWithTag(
//                testTag = TAG_AUTHENTICATION_INPUT_EMAIL,
//                useUnmergedTree = useUnmergedTree
//            )

        // This is recommended.
        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_email),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordInput(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        // This one works.
//        return composeTestRule
//            .onNodeWithTag(
//                testTag = TAG_AUTHENTICATION_INPUT_PASSWORD,
//                useUnmergedTree = useUnmergedTree
//            )

        // This is recommended.
        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_password),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithErrorDialog(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                // Multiple matchers aren't necessary since the content (i.e. the title) of the
                // AuthenticationErrorDialog composable is the same for both SignIn and SignUp modes.
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_ERROR_DIALOG
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithProgressIndicator(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                // Multiple matchers aren't necessary since the CircularProgressIndicator composable is
                // the same for both SignIn and SignUp modes.
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PROGRESS
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordInputTrailingIcon(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementOfEightCharsNeeded(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_characters)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_needed_characters),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementOfOneUppercaseLetterNeeded(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_capital)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_needed_capital),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementOfOneDigitNeeded(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_digit)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_needed_digit),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementOfEightCharsNeededAnotherApproach(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_characters)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_characters),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementOfOneUppercaseLetterNeededAnotherApproach(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_capital)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_capital),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementOfOneDigitNeededAnotherApproach(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_digit)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_digit),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementOfEightCharsSatisfied(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_characters)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_satisfied_characters),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementOfOneUppercaseLetterSatisfied(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_capital)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_satisfied_capital),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementOfOneDigitSatisfied(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_digit)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_satisfied_digit),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementOfEightCharsSatisfiedAnotherApproach(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_satisfied_characters)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_characters),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementOfOneUppercaseLetterSatisfiedAnotherApproach(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_satisfied_capital)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_capital),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementOfOneDigitSatisfiedAnotherApproach(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_satisfied_digit)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_digit),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = useUnmergedTree
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

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

//        assertSignInModeIsDisplayed(composeTestRule)
        assertSignInTitleIsDisplayed(composeTestRule)

    }

    @Test
    fun sign_Up_Mode_Displayed_After_Toggled() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

//        assertSignUpModeIsDisplayed(composeTestRule)
        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

    }

    @Test
    fun sign_In_Title_Displayed_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed(composeTestRule)

    }

    @Test
    fun sign_Up_Title_Displayed_After_Toggled() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

    }

    @Test
    fun sign_In_Button_Displayed_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed()

        onNodeWithSignInButton()
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Button_Displayed_After_Toggle() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithSignUpButton()
            .assertIsDisplayed()

    }

    @Test
    fun need_Account_Displayed_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed()

        onNodeWithNeedAccountButton()
            .assertIsDisplayed()

    }

    @Test
    fun already_Have_Account_Displayed_After_Toggle() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithAlreadyHaveAccountButton()
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Button_Disabled_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed()

        onNodeWithSignInButton()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithSignUpButton()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_In_Button_Enabled_With_Valid_Form_Content() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed()

        onNodeWithEmailInput()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInput()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithSignInButton()
            .assertIsDisplayed()
            .assertIsEnabled()

    }

    @Test
    fun sign_In_Button_Disabled_When_Email_Input_Has_No_Content() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed()

        onNodeWithPasswordInput()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithSignInButton()
            .assertIsDisplayed()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_In_Button_Disabled_When_Password_Input_Has_No_Content() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed()

        onNodeWithEmailInput()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithSignInButton()
            .assertIsDisplayed()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Enabled_With_Valid_Form_Content() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInput()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInput()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithSignUpButton()
            .assertIsDisplayed()
            .assertIsEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_When_Email_Input_Has_No_Content() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInput()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithSignUpButton()
            .assertIsDisplayed()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_When_Password_Input_Has_No_Content() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInput()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithSignUpButton()
            .assertIsDisplayed()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_In_Error_Alert_Dialog_Not_Displayed_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed()

        onNodeWithErrorDialog()
            .assertDoesNotExist()

    }

    @Test
    fun sign_Up_Error_Alert_Dialog_Not_Displayed_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithErrorDialog()
            .assertDoesNotExist()

    }

    /**
     * NOTE:
     *
     * For this test case to pass when it should, whatever time delay that's used in the
     * authenticate() function in app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/stateholders/AuthenticationViewModel.kt,
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

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed()

        onNodeWithEmailInput()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInput()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithSignInButton()
            .performClick()

        composeTestRule.waitUntilExists(
            matcher = hasTestTag(
                testTag = TAG_AUTHENTICATION_ERROR_DIALOG
            ),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            useUnmergedTree = TRUE // Optional.
        )

        onNodeWithErrorDialog(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    /**
     * NOTE:
     *
     * For this test case to pass when it should, whatever time delay that's used in the
     * authenticate() function in app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/stateholders/AuthenticationViewModel.kt,
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

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInput()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInput()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithSignUpButton()
            .performClick()

        composeTestRule.waitUntilExists(
            matcher = hasTestTag(
                testTag = TAG_AUTHENTICATION_ERROR_DIALOG
            ),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            useUnmergedTree = TRUE // Optional.
        )

        onNodeWithErrorDialog(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Progress_Indicator_Not_Displayed_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed()

        onNodeWithProgressIndicator()
            .assertDoesNotExist()

    }

    @Test
    fun sign_Up_Progress_Indicator_Not_Displayed_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithProgressIndicator()
            .assertDoesNotExist()

    }

    /**
     * NOTE:
     *
     * Reducing the time delay to, e.g., 1 millisecond in the authenticate() function in
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/stateholders/AuthenticationViewModel.kt
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

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed()

        onNodeWithEmailInput()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInput()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithSignInButton()
            .performClick()

        onNodeWithProgressIndicator()
            .assertIsDisplayed()

    }

    /**
     * NOTE:
     *
     * Reducing the time delay to, e.g., 1 millisecond in the authenticate() function in
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/stateholders/AuthenticationViewModel.kt
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

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInput()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInput()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithSignUpButton()
            .performClick()

        onNodeWithProgressIndicator()
            .assertIsDisplayed()

    }

    /**
     * NOTE:
     *
     * For this test case to pass when it should, whatever time delay that's used in the
     * authenticate() function in app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/stateholders/AuthenticationViewModel.kt,
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

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed()

        onNodeWithEmailInput()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInput()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithSignInButton()
            .performClick()

        onNodeWithProgressIndicator(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        composeTestRule.waitUntilDoesNotExist(
            matcher = hasTestTag(
                testTag = TAG_AUTHENTICATION_PROGRESS
            ),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            useUnmergedTree = TRUE // Optional.
        )

        onNodeWithProgressIndicator(
            useUnmergedTree = TRUE // Optional
        )
            .assertDoesNotExist()

    }

    /**
     * NOTE:
     *
     * For this test case to pass when it should, whatever time delay that's used in the
     * authenticate() function in app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/stateholders/AuthenticationViewModel.kt,
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

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInput()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInput()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithSignUpButton()
            .performClick()

        onNodeWithProgressIndicator(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        composeTestRule.waitUntilDoesNotExist(
            matcher = hasTestTag(
                testTag = TAG_AUTHENTICATION_PROGRESS
            ),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            useUnmergedTree = TRUE // Optional.
        )

        onNodeWithProgressIndicator(
            useUnmergedTree = TRUE // Optional
        )
            .assertDoesNotExist()

    }

    /**
     * NOTE:
     *
     * For this test case to pass when it should, whatever time delay that's used in the
     * authenticate() function in app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/stateholders/AuthenticationViewModel.kt,
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

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed()

        onNodeWithEmailInput()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInput()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithSignInButton()
            .performClick()

        composeTestRule.waitUntilExists(
            matcher = hasTestTag(
                testTag = TAG_AUTHENTICATION_CONTENT
//                testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE
            ),
            timeoutMillis = TIMEOUT_MILLIS_2500L
        )

        onNodeWithAuthenticationScreen()
            .assertExists()

        // For this to work, you would have to use TAG_AUTHENTICATION_AUTHENTICATION_TITLE instead
        // of TAG_AUTHENTICATION_CONTENT in the above waitUntilExists(.) function. Note that
        // waitUntilExists(.) is an extension function on  ComposeContentTestRule.
//        onNodeWithSignInTitle()
//            .assertExists()

    }

    /**
     * NOTE:
     *
     * For this test case to pass when it should, whatever time delay that's used in the
     * authenticate() function in app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/stateholders/AuthenticationViewModel.kt,
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

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInput()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        onNodeWithPasswordInput()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        onNodeWithSignUpButton()
            .performClick()

        composeTestRule
            .waitUntilExists(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_CONTENT
//                    testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE
                ),
                timeoutMillis = TIMEOUT_MILLIS_2500L
            )

        onNodeWithAuthenticationScreen()
            .assertExists()

        // For this to work, you would have to use TAG_AUTHENTICATION_AUTHENTICATION_TITLE instead
        // of TAG_AUTHENTICATION_CONTENT in the above waitUntilExists(.) function. Note that
        // waitUntilExists(.) is an extension function on  ComposeContentTestRule.
//        onNodeWithSignUpTitle()
//            .assertExists()

    }

    //....|

    @Test
    fun sign_In_Email_Text_Field_Is_Focused_When_Clicked() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed()

        onNodeWithEmailInput()
            .apply {
                performClick()

                assertIsFocused()
            }

    }

    @Test
    fun sign_In_Password_Text_Field_Is_Focused_When_Clicked() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed()

        onNodeWithPasswordInput()
            .apply {
                performClick()

                assertIsFocused()
            }

    }

    @Test
    fun sign_Up_Email_Text_Field_Is_Focused_When_Clicked() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInput()
            .apply {
                performClick()

                assertIsFocused()
            }

    }

    @Test
    fun sign_Up_Password_Text_Field_Is_Focused_When_Clicked() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInput()
            .apply {
                performClick()

                assertIsFocused()
            }

    }

    //....|

    @Test
    fun sign_In_Password_Text_Field_Is_Focused_When_Next_Ime_Action_Clicked_From_Email_Text_Field() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed()

        onNodeWithEmailInput()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        onNodeWithPasswordInput()
            .assertIsFocused()

    }

    @Test
    fun sign_Up_Password_Text_Field_Is_Focused_When_Next_Ime_Action_Clicked_From_Email_Text_Field() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInput()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        onNodeWithPasswordInput()
            .assertIsFocused()

    }

    @Test
    fun sign_In_Progress_Indicator_Displays_When_Done_Ime_Action_Clicked_From_Password_Text_Field() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed()

        onNodeWithEmailInput()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        onNodeWithPasswordInput()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_PASSWORD
                )

                performImeAction()
            }

        onNodeWithProgressIndicator()
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Progress_Indicator_Displays_When_Done_Ime_Action_Clicked_From_Password_Text_Field() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInput()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        onNodeWithPasswordInput()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_PASSWORD
                )

                performImeAction()
            }

        onNodeWithProgressIndicator()
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_No_Password_Requirement_Satisfied_By_Default() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordRequirementOfEightCharsNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneUppercaseLetterNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneDigitNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_No_Password_Requirement_Satisfied_By_Default_AnotherApproach() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordRequirementOfEightCharsNeededAnotherApproach(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneUppercaseLetterNeededAnotherApproach(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneDigitNeededAnotherApproach(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_Eight_Characters_Satisfied() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInput()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASSWORD
            )

        onNodeWithPasswordRequirementOfEightCharsSatisfied(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneUppercaseLetterNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneDigitNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_Only_At_Least_Eight_Characters_Satisfied_AnotherApproach() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInput()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASSWORD
            )

        onNodeWithPasswordRequirementOfEightCharsSatisfiedAnotherApproach(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneUppercaseLetterNeededAnotherApproach(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneDigitNeededAnotherApproach(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_One_Uppercase_Letter_Satisfied() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInput()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASS
            )

        onNodeWithPasswordRequirementOfEightCharsNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneUppercaseLetterSatisfied(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneDigitNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_Only_At_Least_One_Uppercase_Letter_Satisfied_AnotherApproach() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInput()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASS
            )

        onNodeWithPasswordRequirementOfEightCharsNeededAnotherApproach(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneUppercaseLetterSatisfiedAnotherApproach(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneDigitNeededAnotherApproach(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_One_Digit_Satisfied() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInput()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_1PASS
            )

        onNodeWithPasswordRequirementOfEightCharsNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneUppercaseLetterNeeded(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneDigitSatisfied(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_Only_At_Least_One_Digit_Satisfied_AnotherApproach() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInput()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_1PASS
            )

        onNodeWithPasswordRequirementOfEightCharsNeededAnotherApproach(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneUppercaseLetterNeededAnotherApproach(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneDigitSatisfiedAnotherApproach(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_All_Password_Requirements_Satisfied() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInput()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

        onNodeWithPasswordRequirementOfEightCharsSatisfied(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneUppercaseLetterSatisfied(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneDigitSatisfied(
            useUnmergedTree = TRUE // Optional.
        )
            .assertIsDisplayed()

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_All_Password_Requirements_Satisfied_AnotherApproach() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInput()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

        onNodeWithPasswordRequirementOfEightCharsSatisfiedAnotherApproach(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneUppercaseLetterSatisfiedAnotherApproach(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementOfOneDigitSatisfiedAnotherApproach(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Perform_Key_Press_On_Text_Fields() {

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        assertSignInTitleIsDisplayed()

        onNodeWithEmailInput()
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

        onNodeWithPasswordInput()
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

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInput()
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

        onNodeWithPasswordInput()
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

        setContentAsAuthenticationScreenAndAssertItIsDisplayed(composeTestRule)

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        // This should have been used but navigation has been between modes (SignIn and SignUp) but
        // inside of a single AuthenticationScreen screen (i.e. route/destination).
//        Espresso.pressBack()

        onNodeWithAlreadyHaveAccountButton()
            .performClick()

        assertSignInTitleIsDisplayed()

    }

    @Test
    fun sign_In_No_Text_Field_Showing_Soft_Keyboard_By_Default() {

        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
        )

        assertSignInTitleIsDisplayed()

        onNodeWithEmailInput()
            .assertIsNotFocused()

        onNodeWithPasswordInput()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_In_Email_Input_Shows_Soft_Keyboard_When_Clicked() {

        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
        )

        assertSignInTitleIsDisplayed()

        onNodeWithEmailInput()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInput()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        onNodeWithEmailInput()
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_In_Password_Input_Shows_Soft_Keyboard_When_Clicked() {

        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
        )

        assertSignInTitleIsDisplayed()

        onNodeWithPasswordInput()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithPasswordInput()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        onNodeWithPasswordInput()
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_In_Email_Input_Soft_Keyboard_Manually_Closes_After_Shown() {

        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
        )

        assertSignInTitleIsDisplayed()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInput()
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

        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
        )

        assertSignInTitleIsDisplayed()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithPasswordInput()
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

        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
        )

        assertSignInTitleIsDisplayed()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInput()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        onNodeWithEmailInput()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_In_Password_Input_Soft_Keyboard_Closes_When_Done_Ime_Action_Clicked() {

        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
        )

        assertSignInTitleIsDisplayed()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithPasswordInput()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        onNodeWithPasswordInput()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_In_Password_Input_Soft_Keyboard_Shown_When_Next_Ime_Action_Clicked_From_Email_Input() {

        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
        )

        assertSignInTitleIsDisplayed()

        onNodeWithEmailInput()
            .assertIsNotFocused()

        onNodeWithPasswordInput()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInput()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        onNodeWithEmailInput()
            .assertIsFocused()

        onNodeWithPasswordInput()
            .assertIsNotFocused()

        onNodeWithEmailInput()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInput()
            .assertIsNotFocused()

        onNodeWithPasswordInput()
            .assertIsFocused()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_Up_No_Text_Field_Showing_Soft_Keyboard_By_Default() {

        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
        )

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInput()
            .assertIsNotFocused()

        onNodeWithPasswordInput()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_Up_Email_Input_Shows_Soft_Keyboard_When_Clicked() {

        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
        )

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInput()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInput()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        onNodeWithEmailInput()
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_Up_Password_Input_Shows_Soft_Keyboard_When_Clicked() {

        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
        )

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithPasswordInput()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithPasswordInput()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        onNodeWithPasswordInput()
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_Up_Email_Input_Soft_Keyboard_Manually_Closes_After_Shown() {

        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
        )

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInput()
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

        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
        )

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithPasswordInput()
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

        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
        )

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInput()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        onNodeWithEmailInput()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_Up_Password_Input_Soft_Keyboard_Closes_When_Done_Ime_Action_Clicked() {

        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
        )

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithPasswordInput()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        onNodeWithPasswordInput()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_Up_Password_Input_Soft_Keyboard_Shown_When_Next_Ime_Action_Clicked_From_Email_Input() {

        setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
        )

        navigateFromSigninToSignupModesAndConfirmTitles(composeTestRule)

        onNodeWithEmailInput()
            .assertIsNotFocused()

        onNodeWithPasswordInput()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInput()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        onNodeWithEmailInput()
            .assertIsFocused()

        onNodeWithPasswordInput()
            .assertIsNotFocused()

        onNodeWithEmailInput()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        onNodeWithEmailInput()
            .assertIsNotFocused()

        onNodeWithPasswordInput()
            .assertIsFocused()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

}