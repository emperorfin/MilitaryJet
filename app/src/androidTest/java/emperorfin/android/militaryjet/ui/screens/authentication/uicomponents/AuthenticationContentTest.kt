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

package emperorfin.android.militaryjet.ui.screens.authentication.uicomponents

import android.content.Context
import android.view.KeyEvent.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.militaryjet.test.R
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.AuthenticationScreen
import emperorfin.android.militaryjet.ui.screens.authentication.AuthenticationScreenTest
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.EIGHT_CHARACTERS
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.CAPITAL_LETTER
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.NUMBER
import emperorfin.android.militaryjet.ui.screens.authentication.stateholders.AuthenticationUiState
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATION_TITLE
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_ERROR_DIALOG
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_FORM_CONTENT_AREA
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PROGRESS
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_CONTENT
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 01th January, 2023.
 */


/**
 * The tests in this class are a subset of the ones in the [AuthenticationScreenTest] class but with
 * the tests in this class focused on testing the [AuthenticationContent] composable instead of
 * [AuthenticationScreen] composable.
 *
 * Also, the other tests in the [AuthenticationScreenTest] class that are excluded in this class are
 * the ones that includes things like performing click and text input operations. Such tests shouldn't
 * be included in a non-screen composable test class as, in my opinion, it's not a good compose UI
 * test practice.
 *
 * Requirement:
 *
 * - The number 1 requirement for all the test cases in this class to pass when they should is to
 * call ".semantics(mergeDescendants = true){}" without quotes on the Modifier object of the Box
 * composable inside of [AuthenticationContent]. This was commented out since, in my opinion, not a
 * good practice.
 * - The following classes are revisions of this class:
 * [AuthenticationContentTest2]
 * [AuthenticationContentTest3]
 * [AuthenticationContentTest4]
 * [AuthenticationContentTest5]
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
 * [emperorfin.android.militaryjet.R] and not
 * [emperorfin.android.militaryjet.test.R].
 * - Every other thing during testing that involves the use of a resource (e.g. a string resource)
 * such as performing matches or assertions, be sure to use the resource from the androidTest source
 * set (which you should've provided a copy and always in sync with the one from the main source set).
 * And the R must be the one from [emperorfin.android.militaryjet.test.R] instead of
 * [emperorfin.android.militaryjet.R].
 *
 * Be sure to have Configured res srcDirs for androidTest sourceSet in app/build.gradle file.
 * See the following:
 * - https://stackoverflow.com/questions/36955608/espresso-how-to-use-r-string-resources-of-androidtest-folder
 * - https://stackoverflow.com/questions/26663539/configuring-res-srcdirs-for-androidtest-sourceset
 */
class AuthenticationContentTest {

    private companion object {

        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        private const val INPUT_CONTENT_EMAIL: String = "contact@email.com"
        private const val INPUT_CONTENT_PASSWORD: String = "passworD1"
        private const val INPUT_CONTENT_PASSWORD_PASSWORD: String = "password"
        private const val INPUT_CONTENT_PASSWORD_PASS: String = "Pass"
        private const val INPUT_CONTENT_PASSWORD_1PASS: String = "1pass"

        private const val MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE =
            emperorfin.android.militaryjet.R.string.test_error_message

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

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN, // same as default value
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState
                ) {}
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_FORM_CONTENT_AREA
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Mode_Displayed_2() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN, // same as default value
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState
                ) {}
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_CONTENT
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

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState
                ) {}
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_CONTENT
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

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN, // same as default value
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState
                ) {}
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
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Title_Displayed() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP,
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState
                ) {}
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
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Disabled_Button_Displayed() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN, // same as default value
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                )
            )
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Disabled_Button_Displayed() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                )
            )
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_In_Enabled_Button_Displayed() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN,  // Same as default value
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
            EIGHT_CHARACTERS, CAPITAL_LETTER, NUMBER
        )

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                )
            )
            .apply {
                assertIsDisplayed()

                assertIsEnabled()
            }

    }

    @Test
    fun sign_In_Button_Disabled_When_Email_Input_Has_No_Content() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN,  // Same as default value
            password = INPUT_CONTENT_PASSWORD
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                )
            )
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_In_Button_Disabled_When_Password_Input_Has_No_Content() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN,  // Same as default value
            email = INPUT_CONTENT_EMAIL
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
            EIGHT_CHARACTERS, CAPITAL_LETTER, NUMBER
        )

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP,
            password = INPUT_CONTENT_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                )
            )
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Button_Disabled_When_Password_Input_Has_No_Content() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                )
            )
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun need_Account_Button_Displayed() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN,  // Same as default value
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun already_Have_Account_Button_Displayed() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP,
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Error_Alert_Dialog_Not_Displayed() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState
                ) {}
            }
        }

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
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_in_to_account),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            // Multiple matchers aren't necessary since the content (i.e. the title) of the
            // AuthenticationErrorDialog composable is the same for both SignIn and SignUp modes.
            .onNodeWithTag(
                TAG_AUTHENTICATION_ERROR_DIALOG
            )
            .assertDoesNotExist()

    }

    @Test
    fun sign_In_Error_Alert_Dialog_Displayed() {

        composeTestRule.setContent {
            AuthenticationContent(
                uiState = AuthenticationUiState(
                    error = MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE
                )
            ) {}
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_ERROR_DIALOG
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Error_Alert_Dialog_Displayed_2() {

        val uiState = AuthenticationUiState(
            error = MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState
                ) {}
            }
        }

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
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_in_to_account),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            // Multiple matchers aren't necessary since the content (i.e. the title) of the
            // AuthenticationErrorDialog composable is the same for both SignIn and SignUp modes.
            .onNodeWithTag(
                TAG_AUTHENTICATION_ERROR_DIALOG
            )
            .assertIsDisplayed()

    }

    // Goes straight to the "Sign Up" screen without having to navigate from the "Sign In" screen.
    @Test
    fun sign_Up_Error_Alert_Dialog_Not_Displayed() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState
                ) {}
            }
        }

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
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_up_for_account),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(
                // Multiple matchers aren't necessary since the content (i.e. the title) of the
                // AuthenticationErrorDialog composable is the same for both SignIn and SignUp modes.
                TAG_AUTHENTICATION_ERROR_DIALOG
            )
            .assertDoesNotExist()

    }

    // Goes straight to the "Sign Up" screen without having to navigate from the "Sign In" screen.
    @Test
    fun sign_Up_Error_Alert_Dialog_Displayed() {

        composeTestRule.setContent {
            AuthenticationContent(
                uiState = AuthenticationUiState(
                    authenticationMode = SIGN_UP,
//                    error = R.string.test_error_message
                    error = MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE
                )
            ) {}
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_ERROR_DIALOG
            )
            .assertIsDisplayed()

    }

    // Goes straight to the "Sign Up" screen without having to navigate from the "Sign In" screen.
    @Test
    fun sign_Up_Error_Alert_Dialog_Displayed_2() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP,
            error = MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState
                ) {}
            }
        }

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
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_up_for_account),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(
                // Multiple matchers aren't necessary since the content (i.e. the title) of the
                // AuthenticationErrorDialog composable is the same for both SignIn and SignUp modes.
                TAG_AUTHENTICATION_ERROR_DIALOG
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Progress_Indicator_Not_Displayed() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN, // Same as default value
            email = INPUT_CONTENT_EMAIL, // Optional but recommended
            password = INPUT_CONTENT_PASSWORD // Optional but recommended
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState
                ) {}
            }
        }

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
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_in_to_account),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            // Multiple matchers aren't necessary since the CircularProgressIndicator composable is
            // the same for both SignIn and SignUp modes.
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS
            )
            .assertDoesNotExist()

    }

    @Test
    fun sign_Up_Progress_Indicator_Not_Displayed() {

        composeTestRule.setContent {
            AuthenticationContent(
                uiState = AuthenticationUiState(
                    authenticationMode = SIGN_UP
                )
            ) {}
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS
            )
            .assertDoesNotExist()

    }

    @Test
    fun sign_Up_Progress_Indicator_Not_Displayed_2() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL, // Optional but recommended
            password = INPUT_CONTENT_PASSWORD // Optional but recommended
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState
                ) {}
            }
        }

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
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_up_for_account),
                        includeEditableText = FALSE
                    )
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
            // Multiple matchers aren't necessary since the CircularProgressIndicator composable is
            // the same for both SignIn and SignUp modes.
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS
            )
            .assertDoesNotExist()

    }

    @Test
    fun sign_In_Progress_Indicator_Displayed() {

        composeTestRule.setContent {
            AuthenticationContent(
                uiState = AuthenticationUiState(isLoading = TRUE)
            ) {}
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS
            )
            .assertIsDisplayed()

    }

    /**
     * Trying to confirm whether it's Sign In mode would fail the test since the loading
     * indicator displays immediately on top of the screen making this node to appear as if it's
     * not displayed/existed.
     *
     * Since this is not a screen test but an individual component test, it's fine to make the
     * assumption that this is a Sign In mode. And this function [sign_In_Title_Displayed] takes care of
     * testing if a screen is in Sign In mode.
     */
    @Test
    fun sign_In_Progress_Indicator_Displayed_2() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN, // same as default value
            isLoading = TRUE
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState
                ) {}
            }
        }

        // This would fail the test when it should pass. See this function's KDoc.
//        composeTestRule
//            .onNode(
//                matcher = hasTestTag(
//                    testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE
//                ).and(
//                    other = hasTextExactly(
//                        mContext.getString(R.string.label_sign_in_to_account),
//                        includeEditableText = FALSE
//                    )
//                )
//            )
//            .assertIsDisplayed() // Or
////            .assertExists()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Progress_Indicator_Displayed() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP,
            isLoading = TRUE
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState
                ) {}
            }
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_No_Password_Requirement_Satisfied() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                useUnmergedTree = TRUE
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
    fun sign_Up_No_Password_Requirement_Satisfied_AnotherApproach() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_Eight_Characters_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(EIGHT_CHARACTERS)

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                useUnmergedTree = TRUE
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

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(EIGHT_CHARACTERS)

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_One_Uppercase_Letter_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(CAPITAL_LETTER)

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_PASS,
            passwordRequirements = satisfiedPasswordRequirements
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                useUnmergedTree = TRUE
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

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(CAPITAL_LETTER)

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_PASS,
            passwordRequirements = satisfiedPasswordRequirements
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_One_Digit_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(NUMBER)

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_1PASS,
            passwordRequirements = satisfiedPasswordRequirements
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                useUnmergedTree = TRUE
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

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(NUMBER)

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_1PASS,
            passwordRequirements = satisfiedPasswordRequirements
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_All_Password_Requirements_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(
            EIGHT_CHARACTERS, CAPITAL_LETTER, NUMBER
        )

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                useUnmergedTree = TRUE
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

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(
            EIGHT_CHARACTERS, CAPITAL_LETTER, NUMBER
        )

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationContent(
                    uiState = uiState,
                    handleEvent = {}
                )
            }
        }

        composeTestRule
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
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

        composeTestRule
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
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

}