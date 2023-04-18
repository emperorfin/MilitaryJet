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

package emperorfin.android.militaryjet.ui.screens.authentication

import android.content.Context
import android.view.KeyEvent.*
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import emperorfin.android.militaryjet.test.R
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.extensions.waitUntilDoesNotExist
import emperorfin.android.militaryjet.ui.extensions.waitUntilExists
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.militaryjet.ui.screens.authentication.stateholders.AuthenticationUiState
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.AuthenticationContent
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATION_TITLE
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_CONTENT
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_EMAIL
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_ERROR_DIALOG
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PROGRESS
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_FORM_CONTENT_AREA
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT
import emperorfin.android.militaryjet.ui.utils.KeyboardHelper
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 22nd November, 2022.
 */


/**
 * Requirement:
 *
 * - The number 1 requirement for all the test cases in this class to pass when they should is to
 * call ".semantics(mergeDescendants = true){}" without quotes on the Modifier object of the Box
 * composable inside of [AuthenticationContent]. This was commented out since, in my opinion, not a
 * good practice.
 * - The following classes are revisions of this class:
 * [AuthenticationScreenTest2]
 * [AuthenticationScreenTest3]
 * [AuthenticationScreenTest4]
 * [AuthenticationScreenTest5]
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
 * Be sure to have Configured res srcDirs for androidTest sourceSet in app/build.gradle file.
 * See the following:
 * - https://stackoverflow.com/questions/36955608/espresso-how-to-use-r-string-resources-of-androidtest-folder
 * - https://stackoverflow.com/questions/26663539/configuring-res-srcdirs-for-androidtest-sourceset
 */
class AuthenticationScreenTest {

    companion object {

        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        private const val INPUT_CONTENT_EMAIL: String = "contact@email.com"
        private const val INPUT_CONTENT_PASSWORD: String = "passworD1"
        private const val INPUT_CONTENT_PASSWORD_PASSWORD: String = "password"
        private const val INPUT_CONTENT_PASSWORD_PASS: String = "Pass"
        private const val INPUT_CONTENT_PASSWORD_1PASS: String = "1pass"

        private const val MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE =
            emperorfin.android.militaryjet.R.string.test_error_message

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

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
    fun sign_Up_Mode_Displayed_After_Toggled() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
    fun sign_In_Title_Displayed_By_Default() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithText(
                // Use this when resources are coming from the androidTest (in this case) or test
                // source set.
                // Be sure to confirm that R is from emperorfin.android.militaryjet.test.R
//                InstrumentationRegistry.getInstrumentation().context.getString(R.string.label_sign_in_to_account)
                // Use this when resources are coming from the androidTest (in this case) or test
                // source set.
                // Be sure to confirm that R is from emperorfin.android.militaryjet.test.R
                InstrumentationRegistry.getInstrumentation().context.resources.getString(R.string.label_sign_in_to_account)
                // Use this when resources are coming from the main source set.
                // Be sure to confirm that R is from emperorfin.android.militaryjet.R
//                InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.label_sign_in_to_account)
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Title_Displayed_By_Default_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
    fun sign_In_Button_Displayed_By_Default() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithText(
                mContext.getString(R.string.action_sign_in)
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Button_Displayed_By_Default_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .assertIsDisplayed()

    }

    @Test
    fun need_Account_Displayed_By_Default() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithText(
                mContext.getString(R.string.action_need_account)
            )
            .assertIsDisplayed()

    }

    @Test
    fun need_Account_Displayed_By_Default_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
    fun sign_Up_Title_Displayed_After_Toggled() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithText(
                mContext.getString(R.string.action_need_account)
            )
            .performClick()

        composeTestRule
            .onNodeWithText(
                mContext.getString(R.string.label_sign_up_for_account)
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Title_Displayed_After_Toggled_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
    fun sign_Up_Button_Displayed_After_Toggle() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .assertTextEquals(
                mContext.getString(R.string.action_sign_up)
            )

    }

    @Test
    fun sign_Up_Button_Displayed_After_Toggle_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .assertIsDisplayed()

    }

    @Test
    fun already_Have_Account_Displayed_After_Toggle() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
            )
            .apply {
                performClick()

                assertTextEquals(
                    mContext.getString(R.string.action_already_have_account)
                )
            }

    }

    @Test
    fun already_Have_Account_Displayed_After_Toggle_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
    fun sign_In_Button_Disabled_By_Default() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .assertIsNotEnabled()

    }

    @Test
    fun sign_In_Button_Disabled_By_Default_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_By_Default() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_By_Default_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .assertIsNotEnabled()

    }

    @Test
    fun sign_In_Button_Enabled_With_Valid_Form_Content() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .assertIsEnabled()

    }

    @Test
    fun sign_In_Button_Enabled_With_Valid_Form_Content_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

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
            .assertIsDisplayed()
            .assertIsEnabled()

    }

    @Test
    fun sign_In_Button_Disabled_When_Email_Input_Has_No_Content() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .assertIsNotEnabled()

    }

    @Test
    fun sign_In_Button_Disabled_When_Email_Input_Has_No_Content_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

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
            .assertIsDisplayed()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_In_Button_Disabled_When_Password_Input_Has_No_Content() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .assertIsNotEnabled()

    }

    @Test
    fun sign_In_Button_Disabled_When_Password_Input_Has_No_Content_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

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
            .assertIsDisplayed()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Enabled_With_Valid_Form_Content() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .assertIsEnabled()

    }

    @Test
    fun sign_Up_Button_Enabled_With_Valid_Form_Content_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
                        mContext.getString(R.string.action_need_account)
                    )
                )
            )
            .performClick()

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
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

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
            .assertIsDisplayed()
            .assertIsEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_When_Email_Input_Has_No_Content() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_When_Email_Input_Has_No_Content_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
                        mContext.getString(R.string.action_need_account)
                    )
                )
            )
            .performClick()

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
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

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
            .assertIsDisplayed()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_When_Password_Input_Has_No_Content() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_When_Password_Input_Has_No_Content_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
                        mContext.getString(R.string.action_need_account)
                    )
                )
            )
            .performClick()

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
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

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
            .assertIsDisplayed()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_In_Error_Alert_Dialog_Not_Displayed_By_Default() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_ERROR_DIALOG
            )
            .assertDoesNotExist()

    }

    @Test
    fun sign_In_Error_Alert_Dialog_Not_Displayed_By_Default_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
                testTag = TAG_AUTHENTICATION_ERROR_DIALOG
            )
            .assertDoesNotExist()

    }

    @Test
    fun sign_Up_Error_Alert_Dialog_Not_Displayed_By_Default() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_ERROR_DIALOG
            )
            .assertDoesNotExist()

    }

    @Test
    fun sign_Up_Error_Alert_Dialog_Not_Displayed_By_Default_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
                        mContext.getString(R.string.action_need_account)
                    )
                )
            )
            .performClick()

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
            // Multiple matchers aren't necessary since the content (i.e. the title) of the
            // AuthenticationErrorDialog composable is the same for both SignIn and SignUp modes.
            .onNodeWithTag(
                TAG_AUTHENTICATION_ERROR_DIALOG
            )
            .assertDoesNotExist()

    }

    @Test
    fun sign_In_Error_Alert_Dialog_Displayed_After_Error() {
        
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
    fun sign_In_Error_Alert_Dialog_Displayed_After_Error_2() {

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
    fun sign_In_Error_Alert_Dialog_Displayed_After_Error_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
                    testTag = TAG_AUTHENTICATION_CONTENT
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_in_to_account),
                        includeEditableText = FALSE
                    )
                )
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

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
            .performClick()

        composeTestRule.waitUntilExists(
            matcher = hasTestTag(
                testTag = TAG_AUTHENTICATION_ERROR_DIALOG
            ),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            useUnmergedTree = TRUE // Optional.
        )

        composeTestRule
            .onNode(
                // Multiple matchers aren't necessary since the content (i.e. the title) of the
                // AuthenticationErrorDialog composable is the same for both SignIn and SignUp modes.
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_ERROR_DIALOG
                ),
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

    }

    // Goes straight to the "Sign Up" screen without having to navigate from the "Sign In" screen.
    @Test
    fun sign_Up_Error_Alert_Dialog_Displayed_After_Error() {

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
    fun sign_Up_Error_Alert_Dialog_Displayed_After_Error_2() {

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
    fun sign_Up_Error_Alert_Dialog_Displayed_After_Error_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
                    testTag = TAG_AUTHENTICATION_CONTENT
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_in_to_account),
                        includeEditableText = FALSE
                    )
                )
            )
            .assertIsDisplayed()

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
            .performClick()

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
                    testTag = TAG_AUTHENTICATION_CONTENT
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_up_for_account),
                        includeEditableText = FALSE
                    )
                )
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

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
            .performClick()

        composeTestRule.waitUntilExists(
            matcher = hasTestTag(
                testTag = TAG_AUTHENTICATION_ERROR_DIALOG
            ),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            useUnmergedTree = TRUE // Optional.
        )

        composeTestRule
            .onNode(
                // Multiple matchers aren't necessary since the content (i.e. the title) of the
                // AuthenticationErrorDialog composable is the same for both SignIn and SignUp modes.
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_ERROR_DIALOG
                ),
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Progress_Indicator_Not_Displayed_By_Default() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS
            )
            .assertDoesNotExist()

    }

    @Test
    fun sign_In_Progress_Indicator_Not_Displayed_By_Default_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
    fun sign_Up_Progress_Indicator_Not_Displayed_By_Default() {

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
    fun sign_Up_Progress_Indicator_Not_Displayed_By_Default_2() {

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
            // Multiple matchers aren't necessary since the CircularProgressIndicator composable is
            // the same for both SignIn and SignUp modes.
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS
            )
            .assertDoesNotExist()

    }

    @Test
    fun sign_Up_Progress_Indicator_Not_Displayed_By_Default_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
                    testTag = TAG_AUTHENTICATION_CONTENT
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_in_to_account),
                        includeEditableText = FALSE
                    )
                )
            )
            .assertIsDisplayed()

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
            .performClick()

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
                    testTag = TAG_AUTHENTICATION_CONTENT
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_up_for_account),
                        includeEditableText = FALSE
                    )
                )
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
    fun sign_In_Progress_Indicator_Displayed_While_Loading() {

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
    fun sign_In_Progress_Indicator_Displayed_While_Loading_2() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS
            )
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
    fun sign_In_Progress_Indicator_Displayed_While_Loading_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

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
            .performClick()

        composeTestRule
            // Multiple matchers aren't necessary since the CircularProgressIndicator composable is
            // the same for both SignIn and SignUp modes.
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Progress_Indicator_Displayed_While_Loading() {

        composeTestRule.setContent {
            AuthenticationContent(
                uiState = AuthenticationUiState(
                    authenticationMode = SIGN_UP,
                    isLoading = TRUE
                )
            ) {}
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS
            )
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
    fun sign_Up_Progress_Indicator_Displayed_While_Loading_2() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS
            )
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
    fun sign_Up_Progress_Indicator_Displayed_While_Loading_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
                        mContext.getString(R.string.action_need_account)
                    )
                )
            )
            .performClick()

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
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

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
            .performClick()

        composeTestRule
            // Multiple matchers aren't necessary since the CircularProgressIndicator composable is
            // the same for both SignIn and SignUp modes.
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS
            )
            .assertIsDisplayed()

    }

    /**
     * This is not a proper way of testing a particular behaviour on an actual screen composable. No
     * matter the length of the loading delay, it doesn't affect this test since the UI state (from
     * the ViewModel) is manually provided (and that the loading parameter not set to true).
     *
     * So what this means is that the screen's actual loading state (as a result of showing the
     * progress indicator) isn't even tested before testing for when the screen is not in a loading
     * state (as a result of the progress indicator disappearing).
     */
    @Test
    fun sign_In_Progress_Indicator_Not_Displayed_After_Loading() {

        composeTestRule.setContent {
            AuthenticationContent(
                uiState = AuthenticationUiState(
                    email = INPUT_CONTENT_EMAIL,
                    password = INPUT_CONTENT_PASSWORD
                )
            ) {}
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS
            )
            .assertDoesNotExist()

    }

    /**
     * This is not a proper way of testing a particular behaviour on an actual screen composable. No
     * matter the length of the loading delay, it doesn't affect this test since the UI state (from
     * the ViewModel) is manually provided (and that the loading parameter not set to true).
     *
     * So what this means is that the screen's actual loading state (as a result of showing the
     * progress indicator) isn't even tested before testing for when the screen is not in a loading
     * state (as a result of the progress indicator disappearing).
     */
    @Test
    fun sign_In_Progress_Indicator_Not_Displayed_After_Loading_2() {

        val uiState = AuthenticationUiState(
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD
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
            .performClick()

        composeTestRule
            // Multiple matchers aren't necessary since the CircularProgressIndicator composable is
            // the same for both SignIn and SignUp modes.
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS
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
    fun sign_In_Progress_Indicator_Not_Displayed_After_Loading_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
                    testTag = TAG_AUTHENTICATION_CONTENT
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_in_to_account),
                        includeEditableText = FALSE
                    )
                )
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

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
            .performClick()

        composeTestRule.waitUntilDoesNotExist(
            matcher = hasTestTag(
                testTag = TAG_AUTHENTICATION_PROGRESS
            ),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            useUnmergedTree = TRUE // Optional.
        )

        composeTestRule
            .onNode(
                // Multiple matchers aren't necessary since the CircularProgressIndicator composable is
                // the same for both SignIn and SignUp modes.
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PROGRESS
                ),
                useUnmergedTree = TRUE // Optional.
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
    fun sign_In_Progress_Indicator_Not_Displayed_After_Loading_4() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
                    testTag = TAG_AUTHENTICATION_CONTENT
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_in_to_account),
                        includeEditableText = FALSE
                    )
                )
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

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
            .performClick()

        composeTestRule
            // Multiple matchers aren't necessary since the CircularProgressIndicator composable is
            // the same for both SignIn and SignUp modes.
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS,
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        composeTestRule.waitUntilDoesNotExist(
            matcher = hasTestTag(
                testTag = TAG_AUTHENTICATION_PROGRESS
            ),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            useUnmergedTree = TRUE // Optional.
        )

        composeTestRule
            .onNode(
                // Multiple matchers aren't necessary since the CircularProgressIndicator composable is
                // the same for both SignIn and SignUp modes.
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PROGRESS
                ),
                useUnmergedTree = TRUE // Optional.
            )
            .assertDoesNotExist()

    }

    /**
     * This is not a proper way of testing a particular behaviour on an actual screen composable. No
     * matter the length of the loading delay, it doesn't affect this test since the UI state (from
     * the ViewModel) is manually provided (and that the loading parameter not set to true).
     *
     * So what this means is that the screen's actual loading state (as a result of showing the
     * progress indicator) isn't even tested before testing for when the screen is not in a loading
     * state (as a result of the progress indicator disappearing).
     */
    @Test
    fun sign_Up_Progress_Indicator_Not_Displayed_After_Loading() {

        composeTestRule.setContent {
            AuthenticationContent(
                uiState = AuthenticationUiState(
                    authenticationMode = SIGN_UP,
                    email = INPUT_CONTENT_EMAIL,
                    password = INPUT_CONTENT_PASSWORD
                )
            ) {}
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS
            )
            .assertDoesNotExist()

    }

    /**
     * This is not a proper way of testing a particular behaviour on an actual screen composable. No
     * matter the length of the loading delay, it doesn't affect this test since the UI state (from
     * the ViewModel) is manually provided (and that the loading parameter not set to true).
     *
     * So what this means is that the screen's actual loading state (as a result of showing the
     * progress indicator) isn't even tested before testing for when the screen is not in a loading
     * state (as a result of the progress indicator disappearing).
     */
    @Test
    fun sign_Up_Progress_Indicator_Not_Displayed_After_Loading_2() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD
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
            .performClick()

        composeTestRule
            // Multiple matchers aren't necessary since the CircularProgressIndicator composable is
            // the same for both SignIn and SignUp modes.
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS
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
    fun sign_Up_Progress_Indicator_Not_Displayed_After_Loading_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
                    testTag = TAG_AUTHENTICATION_CONTENT
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_in_to_account),
                        includeEditableText = FALSE
                    )
                )
            )
            .assertIsDisplayed()

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
            .performClick()

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
                    testTag = TAG_AUTHENTICATION_CONTENT
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_up_for_account),
                        includeEditableText = FALSE
                    )
                )
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

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
            .performClick()

        composeTestRule
            // Multiple matchers aren't necessary since the CircularProgressIndicator composable is
            // the same for both SignIn and SignUp modes.
            .onNodeWithTag(
                TAG_AUTHENTICATION_PROGRESS,
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        composeTestRule.waitUntilDoesNotExist(
            matcher = hasTestTag(
                testTag = TAG_AUTHENTICATION_PROGRESS
            ),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            useUnmergedTree = TRUE // Optional.
        )

        composeTestRule
            .onNode(
                // Multiple matchers aren't necessary since the CircularProgressIndicator composable is
                // the same for both SignIn and SignUp modes.
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PROGRESS
                ),
                useUnmergedTree = TRUE // Optional.
            )
            .assertDoesNotExist()

    }

    /**
     * While we could perform assertions against the individual children that already have tags
     * assigned to them, this approach allows us to refer to the content area as a whole. Similar to
     * the previous test, we can perform the authentication flow, fol- lowed by performing the
     * assertion that the content area exists within our UI.
     *
     * Because there has been an error state loaded at this point, there will be an alert dialog
     * composed within our UI. For this reason we use the exists check in- stead of a displayed
     * check, this is because the alert dialog will be covered a good chunk of the content UI so we
     * cannot always guarantee that the displayed assertion would be satisfied.
     *
     * NOTE:
     *
     * To make this test case pass, try reducing the time delay (to 1L) in the authenticate()
     * function in app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/stateholders/AuthenticationViewModel.kt
     * For a production app, the time delay (since in this project it just simulates network request)
     * can't be manipulated as the network or I/O operation determines this.
     *
     * To circumvent this situation, try using the Compose waitUntil() test API or adopting the test
     * doubles ( https://developer.android.com/training/testing/fundamentals/test-doubles ).
     */
    @Test
    fun sign_In_Ui_Components_Displayed_After_Loading() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_FORM_CONTENT_AREA,
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assertExists()

    }

    /**
     * Waiting for things… the wrong way
     *
     * Using Thread.sleep() but this is not a good practice. Please don't do this.
     *
     * This test will either run slower than needed or fail. When you have hundreds or thousands of
     * UI tests, you want tests to be as fast as possible.
     *
     * Also, sometimes emulators or devices misbehave and jank, making that operation take a bit
     * longer than those 3000ms, breaking your build. When you have hundreds of tests this becomes
     * a huge issue.
     */
    @Test
    fun sign_In_Ui_Components_Displayed_After_Loading_2() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .performClick()

        Thread.sleep(3_000L)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_FORM_CONTENT_AREA,
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
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
    fun sign_In_Ui_Components_Displayed_After_Loading_3() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = TIMEOUT_MILLIS_2500L) {
            composeTestRule
                .onAllNodesWithText(
                    mContext.getString(R.string.label_sign_in_to_account)
                )
                .fetchSemanticsNodes().size == 1
        }

        composeTestRule
            .onNodeWithText(
                mContext.getString(R.string.label_sign_in_to_account)
            )
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
    fun sign_In_Ui_Components_Displayed_After_Loading_4() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = TIMEOUT_MILLIS_2500L) {
            composeTestRule
                .onAllNodesWithTag(
                    TAG_AUTHENTICATION_FORM_CONTENT_AREA,
                    // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                    // was added to the modifier in the Box composable in
                    // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                    useUnmergedTree = TRUE
                )
                .fetchSemanticsNodes().size == 1
        }

        composeTestRule
            .onNodeWithText(
                mContext.getString(R.string.label_sign_in_to_account)
            )
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
    fun sign_In_Ui_Components_Displayed_After_Loading_5() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = TIMEOUT_MILLIS_2500L) {
            composeTestRule
                .onAllNodesWithText(
                    mContext.getString(R.string.label_sign_in_to_account)
                )
                .fetchSemanticsNodes().size == 1
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_FORM_CONTENT_AREA,
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
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
    fun sign_In_Ui_Components_Displayed_After_Loading_6() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = TIMEOUT_MILLIS_2500L) {
            composeTestRule
                .onAllNodesWithTag(
                    TAG_AUTHENTICATION_FORM_CONTENT_AREA,
                    // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                    // was added to the modifier in the Box composable in
                    // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                    useUnmergedTree = TRUE
                )
                .fetchSemanticsNodes().size == 1
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_FORM_CONTENT_AREA,
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
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
    fun sign_In_Ui_Components_Displayed_After_Loading_7() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .performClick()

        composeTestRule.waitUntilExists(
            matcher = hasText(
                text = mContext.getString(R.string.label_sign_in_to_account),
                substring = FALSE,
                ignoreCase = FALSE
            ),
            timeoutMillis = TIMEOUT_MILLIS_2500L
        )

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_FORM_CONTENT_AREA,
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
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
    fun sign_In_Ui_Components_Displayed_After_Loading_8() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .performClick()

        composeTestRule.waitUntilDoesNotExist(
            matcher = hasTestTag(
                testTag = TAG_AUTHENTICATION_PROGRESS
            ),
            timeoutMillis = TIMEOUT_MILLIS_2500L
        )

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_FORM_CONTENT_AREA,
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
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
    fun sign_In_Ui_Components_Displayed_After_Loading_9() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .performClick()

        composeTestRule.waitUntilExists(
            matcher = hasTestTag(
                testTag = TAG_AUTHENTICATION_FORM_CONTENT_AREA
            ),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
            // was added to the modifier in the Box composable in
            // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
            useUnmergedTree = TRUE
        )

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_FORM_CONTENT_AREA,
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
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
    fun sign_In_Ui_Components_Displayed_After_Loading_10() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

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
            .performClick()

        composeTestRule.waitUntilExists(
            matcher = hasTestTag(
                testTag = TAG_AUTHENTICATION_FORM_CONTENT_AREA
            ),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
            // was added to the modifier in the Box composable in
            // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
            useUnmergedTree = TRUE
        )

        // Or
//        composeTestRule.waitUntilExists(
//            matcher = hasTestTag(
//                testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE
//            ).and(
//                // Optional but recommended.
//                // This is just to confirm the screen since components like EmailInput (others are
//                // PasswordInput, AuthenticationToggleMode, AuthenticationErrorDialog, AuthenticationTitle,
//                // AuthenticationButton) are being used in multiple screens such as SignUp and SignIn screens.
//                // Although, here, the screens are modes while the actual screen is just a single AuthenticationScreen.
//                // So it best to uniquely identify a component when used in more than one screen or modes.
//                // TODO:
//                //  To uniquely identify a composable which is being used in multiple screens, its best to
//                //  assign each screen a screen tag to be used as sub-tags for the composable.
//                //  That way, when a screen that is being tested with the composable passes, you are sure
//                //  that the composable was uniquely identified for that particular screen and not just any
//                //  screen. This means that that particular screen is properly tested.
//                other = hasTextExactly(
//                    mContext.getString(R.string.label_sign_in_to_account),
//                    includeEditableText = FALSE
//                )
//            ),
//            timeoutMillis = TIMEOUT_MILLIS_2500L,
//            // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
//            // was added to the modifier in the Box composable in
//            // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
//            useUnmergedTree = TRUE
//        )

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
            .assertExists()

        // Or
//        composeTestRule
//            .onNode(
//                matcher = hasTestTag(
//                    testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE
//                ).and(
//                    // Optional but recommended.
//                    // This is just to confirm the screen since components like EmailInput (others are
//                    // PasswordInput, AuthenticationToggleMode, AuthenticationErrorDialog, AuthenticationTitle,
//                    // AuthenticationButton) are being used in multiple screens such as SignUp and SignIn screens.
//                    // Although, here, the screens are modes while the actual screen is just a single AuthenticationScreen.
//                    // So it best to uniquely identify a component when used in more than one screen or modes.
//                    // TODO:
//                    //  To uniquely identify a composable which is being used in multiple screens, its best to
//                    //  assign each screen a screen tag to be used as sub-tags for the composable.
//                    //  That way, when a screen that is being tested with the composable passes, you are sure
//                    //  that the composable was uniquely identified for that particular screen and not just any
//                    //  screen. This means that that particular screen is properly tested.
//                    other = hasTextExactly(
//                        mContext.getString(R.string.label_sign_in_to_account),
//                        includeEditableText = FALSE
//                    )
//                ),
//                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
//                // was added to the modifier in the Box composable in
//                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
//                useUnmergedTree = TRUE
//            )
//            .assertExists()

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
    fun sign_In_Ui_Components_Displayed_After_Loading_11() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
                    testTag = TAG_AUTHENTICATION_CONTENT
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_in_to_account),
                        includeEditableText = FALSE
                    )
                )
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

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
            .performClick()

        composeTestRule.waitUntilExists(
            matcher = hasTestTag(
                testTag = TAG_AUTHENTICATION_CONTENT
            ),
            timeoutMillis = TIMEOUT_MILLIS_2500L
        )

        // TODO: This would fail the test but worked a while ago before being commented out.
//        composeTestRule
//            .onNode(
//                matcher = hasTestTag(
//                    testTag = TAG_AUTHENTICATION_CONTENT
//                ).and(
//                    other = hasTextExactly(
//                        mContext.getString(R.string.label_sign_in_to_account),
//                        includeEditableText = FALSE
//                    )
//                )
//            )
//            .assertExists()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_CONTENT
                )
            )
            .assertExists()

    }

    /**
     * NOTE:
     *
     * To make this test case pass, try reducing the time delay (to 1L) in the authenticate()
     * function in app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/stateholders/AuthenticationViewModel.kt
     * For a production app, the time delay (since in this project it just simulates network request)
     * can't be manipulated as the network or I/O operation determines this.
     *
     * To circumvent this situation, try using the Compose waitUntil() test API or adopting the test
     * doubles ( https://developer.android.com/training/testing/fundamentals/test-doubles ).
     */
    @Test
    fun sign_Up_Ui_Components_Displayed_After_Loading() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_FORM_CONTENT_AREA,
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
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
    fun sign_Up_Ui_Components_Displayed_After_Loading_2() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
            )
            .performClick()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
            )
            .performClick()

        composeTestRule
            .waitUntilExists(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_FORM_CONTENT_AREA
                ),
                timeoutMillis = TIMEOUT_MILLIS_2500L,
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_FORM_CONTENT_AREA,
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
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
    fun sign_Up_Ui_Components_Displayed_After_Loading_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

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
            .performClick()

        composeTestRule
            .waitUntilExists(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_FORM_CONTENT_AREA
                ),
                timeoutMillis = TIMEOUT_MILLIS_2500L,
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_FORM_CONTENT_AREA,
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
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
    fun sign_Up_Ui_Components_Displayed_After_Loading_4() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
                    testTag = TAG_AUTHENTICATION_CONTENT
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_in_to_account),
                        includeEditableText = FALSE
                    )
                )
            )
            .assertIsDisplayed()

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
            .performClick()

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
                    testTag = TAG_AUTHENTICATION_CONTENT
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_sign_up_for_account),
                        includeEditableText = FALSE
                    )
                )
            )
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_EMAIL
            )
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_INPUT_PASSWORD
            )
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

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
            .performClick()

        composeTestRule
            .waitUntilExists(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_CONTENT
                ),
                timeoutMillis = TIMEOUT_MILLIS_2500L
            )

        composeTestRule
            .onNodeWithTag(
                TAG_AUTHENTICATION_CONTENT
            )
            .assertExists()

    }

    @Test
    fun sign_In_Email_Text_Field_Is_Focused_When_Clicked() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .apply {
                performClick()

                assertIsFocused()
            }

    }

    @Test
    fun sign_In_Password_Text_Field_Is_Focused_When_Clicked() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .apply {
                performClick()

                assertIsFocused()
            }

    }

    @Test
    fun sign_Up_Email_Text_Field_Is_Focused_When_Clicked() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .apply {
                performClick()

                assertIsFocused()
            }

    }

    @Test
    fun sign_Up_Password_Text_Field_Is_Focused_When_Clicked() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .apply {
                performClick()

                assertIsFocused()
            }

    }

    @Test
    fun sign_In_Password_Text_Field_Is_Focused_When_Next_Ime_Action_Clicked_From_Email_Text_Field() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_EMAIL
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performImeAction()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsFocused()

    }

    @Test
    fun sign_In_Password_Text_Field_Is_Focused_When_Next_Ime_Action_Clicked_From_Email_Text_Field_2() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsFocused()

    }

    @Test
    fun sign_In_Password_Text_Field_Is_Focused_When_Next_Ime_Action_Clicked_From_Email_Text_Field_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsFocused()

    }

    @Test
    fun sign_Up_Password_Text_Field_Is_Focused_When_Next_Ime_Action_Clicked_From_Email_Text_Field() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsFocused()

    }

    @Test
    fun sign_In_Progress_Indicator_Displays_When_Done_Ime_Action_Clicked_From_Password_Text_Field() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_PASSWORD
                )

                performImeAction()
            }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PROGRESS
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Progress_Indicator_Displays_When_Done_Ime_Action_Clicked_From_Password_Text_Field() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_PASSWORD
                )

                performImeAction()
            }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PROGRESS
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_No_Password_Requirement_Satisfied_By_Default() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .performClick()

        composeTestRule
            .onNode(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_characters),
                    includeEditableText = FALSE
                )
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_capital),
                    includeEditableText = FALSE
                )
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_digit),
                    includeEditableText = FALSE
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_No_Password_Requirement_Satisfied_By_Default_2() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .performClick()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_characters)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_characters),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_capital)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_capital),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_digit)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_digit),
                    includeEditableText = FALSE
                )
            )

    }

    @Test
    fun sign_Up_No_Password_Requirement_Satisfied_By_Default_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_characters),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_capital),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_digit),
                    includeEditableText = FALSE
                )
            )

    }

    @Test
    fun sign_Up_No_Password_Requirement_Satisfied_By_Default_4() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
    fun sign_Up_No_Password_Requirement_Satisfied_By_Default_AnotherApproach() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .performClick()

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

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_No_Password_Requirement_Satisfied_By_Default_AnotherApproach_2() {

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .performClick()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_characters)
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_characters),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_capital)
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_capital),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_digit)
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_digit),
                    includeEditableText = FALSE
                )
            )

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_No_Password_Requirement_Satisfied_By_Default_AnotherApproach_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_characters),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_capital),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_digit),
                    includeEditableText = FALSE
                )
            )

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_No_Password_Requirement_Satisfied_By_Default_AnotherApproach_4() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .performClick()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASSWORD
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_characters)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_satisfied_characters),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_capital)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_capital),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_digit)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_digit),
                    includeEditableText = FALSE
                )
            )

    }

    @Test
    fun sign_Up_Only_At_Least_Eight_Characters_Satisfied_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASSWORD
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_satisfied_characters),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_capital),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_digit),
                    includeEditableText = FALSE
                )
            )

    }

    @Test
    fun sign_Up_Only_At_Least_Eight_Characters_Satisfied_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASSWORD
            )

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

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .performClick()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASSWORD
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_satisfied_characters)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_characters),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_capital)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_capital),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_digit)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_digit),
                    includeEditableText = FALSE
                )
            )

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_Only_At_Least_Eight_Characters_Satisfied_AnotherApproach_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASSWORD
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_characters),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_capital),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_digit),
                    includeEditableText = FALSE
                )
            )

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_Only_At_Least_Eight_Characters_Satisfied_AnotherApproach_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASSWORD
            )

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

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .performClick()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASS
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_characters)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_characters),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_capital)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_satisfied_capital),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_digit)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_digit),
                    includeEditableText = FALSE
                )
            )

    }

    @Test
    fun sign_Up_Only_At_Least_One_Uppercase_Letter_Satisfied_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASS
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_characters),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_satisfied_capital),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_digit),
                    includeEditableText = FALSE
                )
            )

    }

    @Test
    fun sign_Up_Only_At_Least_One_Uppercase_Letter_Satisfied_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASS
            )

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

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .performClick()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASS
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_characters)
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_characters),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_satisfied_capital)
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_capital),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_digit)
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_digit),
                    includeEditableText = FALSE
                )
            )

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_Only_At_Least_One_Uppercase_Letter_Satisfied_AnotherApproach_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASS
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_characters),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_capital),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_digit),
                    includeEditableText = FALSE
                )
            )

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_Only_At_Least_One_Uppercase_Letter_Satisfied_AnotherApproach_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASS
            )

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

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .performClick()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_1PASS
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_characters)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_characters),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_capital)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_capital),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_digit)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_satisfied_digit),
                    includeEditableText = FALSE
                )
            )

    }

    @Test
    fun sign_Up_Only_At_Least_One_Digit_Satisfied_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_1PASS
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_characters),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_needed_capital),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_satisfied_digit),
                    includeEditableText = FALSE
                )
            )

    }

    @Test
    fun sign_Up_Only_At_Least_One_Digit_Satisfied_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_1PASS
            )

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

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .performClick()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_1PASS
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_characters)
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_characters),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_capital)
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_capital),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_satisfied_digit)
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_digit),
                    includeEditableText = FALSE
                )
            )

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_Only_At_Least_One_Digit_Satisfied_AnotherApproach_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_1PASS
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_characters),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_capital),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_digit),
                    includeEditableText = FALSE
                )
            )

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_Only_At_Least_One_Digit_Satisfied_AnotherApproach_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_1PASS
            )

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

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .performClick()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_characters)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_satisfied_characters),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_capital)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_satisfied_capital),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_digit)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_satisfied_digit),
                    includeEditableText = FALSE
                )
            )

    }

    @Test
    fun sign_Up_All_Password_Requirements_Satisfied_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_satisfied_characters),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_satisfied_capital),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.test_password_requirement_satisfied_digit),
                    includeEditableText = FALSE
                )
            )

    }

    @Test
    fun sign_Up_All_Password_Requirements_Satisfied_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

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

        composeTestRule.setContent {
            AuthenticationScreen()
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .performClick()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_satisfied_characters)
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_characters),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_satisfied_capital)
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_capital),
                    includeEditableText = FALSE
                )
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_satisfied_digit)
                ),
                // The argument was added because .testTag(tag = TAG_AUTHENTICATION_CONTENT).semantics(mergeDescendants = true){}
                // was added to the modifier in the Box composable in
                // app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/AuthenticationContent.kt
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_digit),
                    includeEditableText = FALSE
                )
            )

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_All_Password_Requirements_Satisfied_AnotherApproach_2() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_characters),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_capital),
                    includeEditableText = FALSE
                )
            )

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
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_digit),
                    includeEditableText = FALSE
                )
            )

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun sign_Up_All_Password_Requirements_Satisfied_AnotherApproach_3() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

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

    @Test
    fun sign_In_Perform_Key_Press_On_Text_Fields() {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_email),
                        includeEditableText = FALSE
                    )
                )
            )
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
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
                )
            )
            .performClick()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_password),
                        includeEditableText = FALSE
                    )
                )
            )
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

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_email),
                        includeEditableText = FALSE
                    )
                )
            )
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
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
                )
            )
            .performClick()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.label_password),
                        includeEditableText = FALSE
                    )
                )
            )
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

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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

        // This should have been used but navigation has been between modes (SignIn and SignUp) but
        // inside of a single AuthenticationScreen screen (i.e. route/destination).
//        Espresso.pressBack()

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
            .performClick()

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

    }

    @Test
    fun sign_In_No_Text_Field_Showing_Soft_Keyboard_By_Default() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_In_No_Text_Field_Showing_Soft_Keyboard_By_Default_2() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsNotFocused()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_In_Email_Input_Shows_Soft_Keyboard_When_Clicked() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_In_Email_Input_Shows_Soft_Keyboard_When_Clicked_2() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_In_Password_Input_Shows_Soft_Keyboard_When_Clicked() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_In_Email_Input_Soft_Keyboard_Manually_Closes_After_Shown() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        keyboardHelper.hideKeyboardIfShown()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_In_Email_Input_Soft_Keyboard_Manually_Closes_After_Shown_2() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
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

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        keyboardHelper.hideKeyboardIfShown()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_In_Password_Input_Soft_Keyboard_Manually_Closes_After_Shown_2() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
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

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_In_Password_Input_Soft_Keyboard_Closes_When_Done_Ime_Action_Clicked() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_In_Password_Input_Soft_Keyboard_Shown_When_Next_Ime_Action_Clicked_From_Email_Input() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performImeAction()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    /**
     * If test fails when it should pass, try a re-run. Or use the following improved test case:
     * [sign_In_Password_Input_Soft_Keyboard_Shown_When_Next_Ime_Action_Clicked_From_Email_Input_3]
     */
    @Test
    fun sign_In_Password_Input_Soft_Keyboard_Shown_When_Next_Ime_Action_Clicked_From_Email_Input_2() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsNotFocused()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performClick()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsFocused()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsNotFocused()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsNotFocused()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_In_Password_Input_Soft_Keyboard_Shown_When_Next_Ime_Action_Clicked_From_Email_Input_3() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsNotFocused()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsFocused()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsNotFocused()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsNotFocused()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsFocused()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_Up_No_Text_Field_Showing_Soft_Keyboard_By_Default() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_Up_No_Text_Field_Showing_Soft_Keyboard_By_Default_2() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsNotFocused()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_Up_Email_Input_Shows_Soft_Keyboard_When_Clicked() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_Up_Email_Input_Shows_Soft_Keyboard_When_Clicked_2() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_Up_Password_Input_Shows_Soft_Keyboard_When_Clicked() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_Up_Email_Input_Soft_Keyboard_Manually_Closes_After_Shown() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        keyboardHelper.hideKeyboardIfShown()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_Up_Email_Input_Soft_Keyboard_Manually_Closes_After_Shown_2() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
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

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        keyboardHelper.hideKeyboardIfShown()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_Up_Password_Input_Soft_Keyboard_Manually_Closes_After_Shown_2() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
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

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_Up_Password_Input_Soft_Keyboard_Closes_When_Done_Ime_Action_Clicked() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_Up_Password_Input_Soft_Keyboard_Shown_When_Next_Ime_Action_Clicked_From_Email_Input() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performImeAction()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_Up_Password_Input_Soft_Keyboard_Shown_When_Next_Ime_Action_Clicked_From_Email_Input_2() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsNotFocused()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performClick()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsFocused()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsNotFocused()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsNotFocused()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_Up_Password_Input_Soft_Keyboard_Shown_When_Next_Ime_Action_Clicked_From_Email_Input_3() {

        val keyboardHelper = KeyboardHelper(composeRule = composeTestRule)

        composeTestRule.setContent {
            keyboardHelper.initialize()

            ComposeEmailAuthenticationTheme {
                AuthenticationScreen()
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
            .performClick()

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
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsNotFocused()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsFocused()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsNotFocused()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertIsNotFocused()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assertIsFocused()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

}