package emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.composeemailauthentication.test.R
import emperorfin.android.composeemailauthentication.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.composeemailauthentication.ui.screens.authentication.AuthenticationScreen
import emperorfin.android.composeemailauthentication.ui.screens.authentication.AuthenticationScreenTest
import emperorfin.android.composeemailauthentication.ui.screens.authentication.AuthenticationScreenTest2
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.PasswordRequirement.EIGHT_CHARACTERS
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.PasswordRequirement.CAPITAL_LETTER
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.PasswordRequirement.NUMBER
import emperorfin.android.composeemailauthentication.ui.screens.authentication.stateholders.AuthenticationUiState
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_CONTENT
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATION_TITLE
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_ERROR_DIALOG
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_EMAIL
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PROGRESS
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 19th January, 2023.
 */


/**
 * This class is a clean version of [AuthenticationContentTest] class.
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
 * [emperorfin.android.composeemailauthentication.R] and not
 * [emperorfin.android.composeemailauthentication.test.R].
 * - Every other thing during testing that involves the use of a resource (e.g. a string resource)
 * such as performing matches or assertions, be sure to use the resource from the androidTest source
 * set (which you should've provided a copy and always in sync with the one from the main source set).
 * And the R must be the one from [emperorfin.android.composeemailauthentication.test.R] instead of
 * [emperorfin.android.composeemailauthentication.R].
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

        private const val INPUT_CONTENT_EMAIL: String = "contact@email.com"
        private const val INPUT_CONTENT_PASSWORD: String = "passworD1"
        private const val INPUT_CONTENT_PASSWORD_PASSWORD: String = "password"
        private const val INPUT_CONTENT_PASSWORD_PASS: String = "Pass"
        private const val INPUT_CONTENT_PASSWORD_1PASS: String = "1pass"

        private const val MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE =
            emperorfin.android.composeemailauthentication.R.string.test_error_message

    }

    /**
     * Use this when resources are coming from the main source set, whether directly
     * (e.g. R.string.sample_text) or indirectly (e.g. [EIGHT_CHARACTERS.label]
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
            assertSignInTitleIsDisplayed()
        } else if (authenticationMode == SIGN_UP) {
            assertSignUpTitleIsDisplayed()
        }

    }

    private fun onNodeWithAuthenticationContent(
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

        onNodeWithSignInButton()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Disabled_Button_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(authenticationMode = SIGN_UP)

        onNodeWithSignUpButton()
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

        onNodeWithSignInButton()
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

        onNodeWithSignUpButton()
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

        onNodeWithSignInButton()
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

        onNodeWithSignInButton()
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

        onNodeWithSignUpButton()
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

        onNodeWithSignUpButton()
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

        onNodeWithNeedAccountButton()
            .assertIsDisplayed()

    }

    @Test
    fun already_Have_Account_Button_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP
        )

        onNodeWithAlreadyHaveAccountButton()
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Error_Alert_Dialog_Not_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN
        )

        onNodeWithErrorDialog()
            .assertDoesNotExist()

    }

    @Test
    fun sign_In_Error_Alert_Dialog_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            error = MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE
        )

        onNodeWithErrorDialog()
            .assertIsDisplayed()

    }

    // Goes straight to the "Sign Up" screen without having to navigate from the "Sign In" screen.
    @Test
    fun sign_Up_Error_Alert_Dialog_Not_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP
        )

        onNodeWithErrorDialog()
            .assertDoesNotExist()

    }

    // Goes straight to the "Sign Up" screen without having to navigate from the "Sign In" screen.
    @Test
    fun sign_Up_Error_Alert_Dialog_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            error = MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE
        )

        onNodeWithErrorDialog()
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Progress_Indicator_Not_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            email = INPUT_CONTENT_EMAIL, // Optional but recommended
            password = INPUT_CONTENT_PASSWORD // Optional but recommended
        )

        onNodeWithProgressIndicator()
            .assertDoesNotExist()

    }

    @Test
    fun sign_Up_Progress_Indicator_Not_Displayed() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL, // Optional but recommended
            password = INPUT_CONTENT_PASSWORD // Optional but recommended
        )

        onNodeWithProgressIndicator()
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
         * [setContentAsAuthenticationContentAndAssertItIsDisplayed] calls [assertSignInTitleIsDisplayed].
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

        onNodeWithProgressIndicator()
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
         * [setContentAsAuthenticationContentAndAssertItIsDisplayed] calls [assertSignInTitleIsDisplayed].
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

        onNodeWithProgressIndicator()
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_No_Password_Requirement_Satisfied() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP
        )

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
    fun sign_Up_No_Password_Requirement_Satisfied_AnotherApproach() {

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP
        )

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

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(EIGHT_CHARACTERS)

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements
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

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(EIGHT_CHARACTERS)

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements
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

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(CAPITAL_LETTER)

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_PASS,
            passwordRequirements = satisfiedPasswordRequirements
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

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(CAPITAL_LETTER)

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_PASS,
            passwordRequirements = satisfiedPasswordRequirements
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

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(NUMBER)

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_1PASS,
            passwordRequirements = satisfiedPasswordRequirements
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

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(NUMBER)

        setContentAsAuthenticationContentAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_1PASS,
            passwordRequirements = satisfiedPasswordRequirements
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
    
}