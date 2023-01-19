package emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.composeemailauthentication.test.R
import emperorfin.android.composeemailauthentication.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.composeemailauthentication.ui.screens.authentication.stateholders.AuthenticationUiState
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATION_TITLE
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_CONTENT
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_FORM_CONTENT_AREA
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 01th January, 2023.
 */


/**
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
class AuthenticationFormTest {

    private companion object {

        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        private const val INPUT_CONTENT_EMAIL_EMPTY: String = ""
        private const val INPUT_CONTENT_EMAIL: String = "contact@email.com"
        private const val INPUT_CONTENT_PASSWORD_EMPTY: String = ""
        private const val INPUT_CONTENT_PASSWORD: String = "passworD1"
        private const val INPUT_CONTENT_PASSWORD_PASSWORD: String = "password"
        private const val INPUT_CONTENT_PASSWORD_PASS: String = "Pass"
        private const val INPUT_CONTENT_PASSWORD_1PASS: String = "1pass"

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

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_IN,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL_EMPTY,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_EMPTY,
                    onPasswordChanged = { },
                    completedPasswordRequirements = emptyList(),
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_FORM_CONTENT_AREA
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Mode_Displayed_2() {

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_IN,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL_EMPTY,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_EMPTY,
                    onPasswordChanged = { },
                    completedPasswordRequirements = emptyList(),
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_FORM_CONTENT_AREA
                )
            )
            .assertIsDisplayed()

        composeTestRule
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
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Mode_Displayed() {

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_UP,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL_EMPTY,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_EMPTY,
                    onPasswordChanged = { },
                    completedPasswordRequirements = emptyList(),
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_FORM_CONTENT_AREA
                )
            )
            .assertIsDisplayed()

        composeTestRule
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
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Title_Displayed() {

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_IN,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL_EMPTY,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_EMPTY,
                    onPasswordChanged = { },
                    completedPasswordRequirements = emptyList(),
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
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
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Title_Displayed() {

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_UP,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL_EMPTY,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_EMPTY,
                    onPasswordChanged = { },
                    completedPasswordRequirements = emptyList(),
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
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
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Disabled_Button_Displayed() {

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_IN,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL_EMPTY,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_EMPTY,
                    onPasswordChanged = { },
                    completedPasswordRequirements = emptyList(),
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
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
                )
            )
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Disabled_Button_Displayed() {

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_UP,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL_EMPTY,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_EMPTY,
                    onPasswordChanged = { },
                    completedPasswordRequirements = emptyList(),
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
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
                )
            )
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_In_Enabled_Button_Displayed() {

        val enableAuthentication: Boolean = TRUE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_IN,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD,
                    onPasswordChanged = { },
                    completedPasswordRequirements = emptyList(),
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
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
                )
            )
            .apply {
                assertIsDisplayed()

                assertIsEnabled()
            }

    }

    @Test
    fun sign_Up_Enabled_Button_Displayed() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(
            PasswordRequirement.EIGHT_CHARACTERS,
            PasswordRequirement.CAPITAL_LETTER,
            PasswordRequirement.NUMBER
        )

        val enableAuthentication: Boolean = TRUE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_UP,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD,
                    onPasswordChanged = { },
                    completedPasswordRequirements = satisfiedPasswordRequirements,
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
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
                )
            )
            .apply {
                assertIsDisplayed()

                assertIsEnabled()
            }

    }

    @Test
    fun sign_In_Button_Disabled_When_Email_Input_Has_No_Content() {

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_IN,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL_EMPTY,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD,
                    onPasswordChanged = { },
                    completedPasswordRequirements = emptyList(),
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
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
                )
            )
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_In_Button_Disabled_When_Password_Input_Has_No_Content() {

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_IN,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_EMPTY,
                    onPasswordChanged = { },
                    completedPasswordRequirements = emptyList(),
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
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
                )
            )
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Button_Disabled_When_Email_Input_Has_No_Content() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(
            PasswordRequirement.EIGHT_CHARACTERS,
            PasswordRequirement.CAPITAL_LETTER,
            PasswordRequirement.NUMBER
        )

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_UP,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL_EMPTY,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD,
                    onPasswordChanged = { },
                    completedPasswordRequirements = satisfiedPasswordRequirements,
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
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
                )
            )
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Button_Disabled_When_Password_Input_Has_No_Content() {

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_UP,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_EMPTY,
                    onPasswordChanged = { },
                    completedPasswordRequirements = emptyList(),
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
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
                )
            )
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun need_Account_Button_Displayed() {

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_IN,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL_EMPTY,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_EMPTY,
                    onPasswordChanged = { },
                    completedPasswordRequirements = emptyList(),
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
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
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun already_Have_Account_Button_Displayed() {

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_UP,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL_EMPTY,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_EMPTY,
                    onPasswordChanged = { },
                    completedPasswordRequirements = emptyList(),
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
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
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_No_Password_Requirement_Satisfied() {

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_UP,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL_EMPTY,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_EMPTY,
                    onPasswordChanged = { },
                    completedPasswordRequirements = emptyList(),
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_characters)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_needed_characters),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_capital)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_needed_capital),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_digit)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_needed_digit),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
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

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_UP,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL_EMPTY,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_EMPTY,
                    onPasswordChanged = { },
                    completedPasswordRequirements = emptyList(),
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_characters)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_characters),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_capital)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_capital),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_digit)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_digit),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_Eight_Characters_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(PasswordRequirement.EIGHT_CHARACTERS)

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_UP,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_PASSWORD,
                    onPasswordChanged = { },
                    completedPasswordRequirements = satisfiedPasswordRequirements,
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_characters)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_satisfied_characters),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_capital)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_needed_capital),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_digit)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_needed_digit),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
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

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(PasswordRequirement.EIGHT_CHARACTERS)

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_UP,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_PASSWORD,
                    onPasswordChanged = { },
                    completedPasswordRequirements = satisfiedPasswordRequirements,
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_satisfied_characters)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_characters),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_capital)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_capital),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_digit)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_digit),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_One_Uppercase_Letter_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(PasswordRequirement.CAPITAL_LETTER)

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_UP,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_PASS,
                    onPasswordChanged = { },
                    completedPasswordRequirements = satisfiedPasswordRequirements,
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_characters)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_needed_characters),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_capital)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_satisfied_capital),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_digit)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_needed_digit),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
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

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(PasswordRequirement.CAPITAL_LETTER)

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_UP,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_PASS,
                    onPasswordChanged = { },
                    completedPasswordRequirements = satisfiedPasswordRequirements,
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_characters)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_characters),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_satisfied_capital)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_capital),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_digit)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_digit),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_One_Digit_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(PasswordRequirement.NUMBER)

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_UP,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_1PASS,
                    onPasswordChanged = { },
                    completedPasswordRequirements = satisfiedPasswordRequirements,
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_characters)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_needed_characters),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_capital)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_needed_capital),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_digit)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_satisfied_digit),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
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

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(PasswordRequirement.NUMBER)

        val enableAuthentication: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_UP,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD_1PASS,
                    onPasswordChanged = { },
                    completedPasswordRequirements = satisfiedPasswordRequirements,
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_characters)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_characters),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_capital)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_capital),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_satisfied_digit)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_digit),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_All_Password_Requirements_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(
            PasswordRequirement.EIGHT_CHARACTERS,
            PasswordRequirement.CAPITAL_LETTER,
            PasswordRequirement.NUMBER
        )

        val enableAuthentication: Boolean = TRUE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_UP,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD,
                    onPasswordChanged = { },
                    completedPasswordRequirements = satisfiedPasswordRequirements,
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_characters)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_satisfied_characters),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_capital)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_satisfied_capital),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_digit)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.test_password_requirement_satisfied_digit),
                        includeEditableText = FALSE
                    )
                ),
                useUnmergedTree = TRUE
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
            PasswordRequirement.EIGHT_CHARACTERS,
            PasswordRequirement.CAPITAL_LETTER,
            PasswordRequirement.NUMBER
        )

        val enableAuthentication: Boolean = TRUE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = SIGN_UP,
                    onToggleMode = { },
                    email = INPUT_CONTENT_EMAIL,
                    onEmailChanged = { },
                    password = INPUT_CONTENT_PASSWORD,
                    onPasswordChanged = { },
                    completedPasswordRequirements = satisfiedPasswordRequirements,
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_satisfied_characters)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_characters),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_satisfied_capital)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_capital),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_satisfied_digit)
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.password_requirement_digit),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

}