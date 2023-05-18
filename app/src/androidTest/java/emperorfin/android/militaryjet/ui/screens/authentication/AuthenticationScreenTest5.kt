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
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.extensions.waitUntilDoesNotExist
import emperorfin.android.militaryjet.ui.extensions.waitUntilExists
import emperorfin.android.militaryjet.ui.utils.AuthenticationScreenTestUtil3
import emperorfin.android.militaryjet.ui.utils.KeyboardHelper
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.TRUE
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.FALSE
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_EMAIL
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_PASSWORD
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_PASSWORD_PASSWORD
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_PASSWORD_PASS
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_PASSWORD_1PASS
import emperorfin.android.militaryjet.ui.constants.LongConstants.TIMEOUT_MILLIS_2500L


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 09th April, 2023.
 */


/**
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
class AuthenticationScreenTest5 {

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

    private lateinit var authenticationScreenTestUtil: AuthenticationScreenTestUtil3

    @Before
    fun setUpContexts() {

        // See field's KDoc for more info.
        mTargetContext = InstrumentationRegistry.getInstrumentation().targetContext
//        mTargetContext = ApplicationProvider.getApplicationContext<Context>() // Haven't tested but might work.
        // See field's KDoc for more info.
        mContext = InstrumentationRegistry.getInstrumentation().context

        authenticationScreenTestUtil = AuthenticationScreenTestUtil3(
            mContext = mContext,
            mTargetContext = mTargetContext,
            composeTestRule = composeTestRule
        )

    }

    @Test
    fun sign_In_Mode_Displayed_By_Default() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertSignInModeIsDisplayed(composeTestRule)
        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed(composeTestRule)

    }

    @Test
    fun sign_Up_Mode_Displayed_After_Toggled() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertSignUpModeIsDisplayed(composeTestRule)
        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

    }

    @Test
    fun sign_In_Title_Displayed_By_Default() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed(composeTestRule)

    }

    @Test
    fun sign_Up_Title_Displayed_After_Toggled() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

    }

    @Test
    fun sign_In_Button_Displayed_By_Default() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Button_Displayed_After_Toggle() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .assertIsDisplayed()

    }

    @Test
    fun need_Account_Displayed_By_Default() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .authenticationToggleModeTestUtil3
            .onNodeWithAuthenticationToggleModeAndTextExactlyNeedAnAccount()
            .assertIsDisplayed()

    }

    @Test
    fun already_Have_Account_Displayed_After_Toggle() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .authenticationToggleModeTestUtil3
            .onNodeWithAuthenticationToggleModeAndTextExactlyAlreadyHaveAnAccount()
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Button_Disabled_By_Default() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_By_Default() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_In_Button_Enabled_With_Valid_Form_Content() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .assertIsDisplayed()
            .assertIsEnabled()

    }

    @Test
    fun sign_In_Button_Disabled_When_Email_Input_Has_No_Content() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .assertIsDisplayed()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_In_Button_Disabled_When_Password_Input_Has_No_Content() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .assertIsDisplayed()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Enabled_With_Valid_Form_Content() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .assertIsDisplayed()
            .assertIsEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_When_Email_Input_Has_No_Content() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .assertIsDisplayed()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_When_Password_Input_Has_No_Content() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .assertIsDisplayed()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_In_Error_Alert_Dialog_Not_Displayed_By_Default() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .authenticationErrorDialogTestUtil3
            .onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops()
            .assertDoesNotExist()

    }

    @Test
    fun sign_Up_Error_Alert_Dialog_Not_Displayed_By_Default() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .authenticationErrorDialogTestUtil3
            .onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops()
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

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .performClick()

        composeTestRule.waitUntilExists(
            matcher = authenticationScreenTestUtil.hasTestTagAuthenticationErrorDialogAndHasAlertDialogTitleWhoops(),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            useUnmergedTree = TRUE // Optional.
        )

        authenticationScreenTestUtil
            .authenticationErrorDialogTestUtil3
            .onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops(
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

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .performClick()

        composeTestRule.waitUntilExists(
            matcher = authenticationScreenTestUtil.hasTestTagAuthenticationErrorDialogAndHasAlertDialogTitleWhoops(),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            useUnmergedTree = TRUE // Optional.
        )

        authenticationScreenTestUtil
            .authenticationErrorDialogTestUtil3
            .onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Progress_Indicator_Not_Displayed_By_Default() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
            .assertDoesNotExist()

    }

    @Test
    fun sign_Up_Progress_Indicator_Not_Displayed_By_Default() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
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

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .performClick()

        authenticationScreenTestUtil
            .onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
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

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .performClick()

        authenticationScreenTestUtil
            .onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
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

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .performClick()

        authenticationScreenTestUtil
            .onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        composeTestRule.waitUntilDoesNotExist(
            matcher = authenticationScreenTestUtil.hasTestTagCircularProgressIndicatorAndHasCircularProgressIndicatorColorArgbPresetColor(),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            useUnmergedTree = TRUE // Optional.
        )

        authenticationScreenTestUtil
            .onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor(
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

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .performClick()

        authenticationScreenTestUtil
            .onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        composeTestRule.waitUntilDoesNotExist(
            matcher = authenticationScreenTestUtil.hasTestTagCircularProgressIndicatorAndHasCircularProgressIndicatorColorArgbPresetColor(),
            timeoutMillis = TIMEOUT_MILLIS_2500L,
            useUnmergedTree = TRUE // Optional.
        )

        authenticationScreenTestUtil
            .onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor(
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

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .performClick()

        composeTestRule.waitUntilExists(
            matcher = authenticationScreenTestUtil.hasTestTagAuthenticationTitleAndHasTextExactlySignInToYourAccount(), // authenticationScreenTestUtil.hasTestTagAuthenticationScreen()
            timeoutMillis = TIMEOUT_MILLIS_2500L
        )

        authenticationScreenTestUtil
            .onNodeWithAuthenticationScreen()
            .assertExists()

        /**
         * This would fail the test if [AuthenticationScreenTestUtil3.hasTestTagAuthenticationScreen] is used in the above
         * [ComposeContentTestRule.waitUntilExists] function (NB: this is an extension function).
         */
        authenticationScreenTestUtil
            .authenticationTitleTestUtil3
            .onNodeWithAuthenticationTitleAndTextExactlySignInToYourAccount()
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

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performTextInput(text = INPUT_CONTENT_EMAIL)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(text = INPUT_CONTENT_PASSWORD)

        authenticationScreenTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .performClick()

        composeTestRule
            .waitUntilExists(
                matcher = authenticationScreenTestUtil.hasTestTagAuthenticationTitleAndHasTextExactlySignUpForAnAccount(), // authenticationScreenTestUtil.hasTestTagAuthenticationScreen()
                timeoutMillis = TIMEOUT_MILLIS_2500L
            )

        authenticationScreenTestUtil
            .onNodeWithAuthenticationScreen()
            .assertExists()

        /**
         * This would fail the test if [AuthenticationScreenTestUtil3.hasTestTagAuthenticationScreen]
         * is used in the above [ComposeContentTestRule.waitUntilExists] function (NB: this is an
         * extension function).
         */
        authenticationScreenTestUtil
            .authenticationTitleTestUtil3
            .onNodeWithAuthenticationTitleAndTextExactlySignUpForAnAccount()
            .assertExists()

    }

    //....|

    @Test
    fun sign_In_Email_Text_Field_Is_Focused_When_Clicked() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .apply {
                performClick()

                assertIsFocused()
            }

    }

    @Test
    fun sign_In_Password_Text_Field_Is_Focused_When_Clicked() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .apply {
                performClick()

                assertIsFocused()
            }

    }

    @Test
    fun sign_Up_Email_Text_Field_Is_Focused_When_Clicked() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .apply {
                performClick()

                assertIsFocused()
            }

    }

    @Test
    fun sign_Up_Password_Text_Field_Is_Focused_When_Clicked() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .apply {
                performClick()

                assertIsFocused()
            }

    }

    //....|

    @Test
    fun sign_In_Password_Text_Field_Is_Focused_When_Next_Ime_Action_Clicked_From_Email_Text_Field() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsFocused()

    }

    @Test
    fun sign_Up_Password_Text_Field_Is_Focused_When_Next_Ime_Action_Clicked_From_Email_Text_Field() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsFocused()

    }

    @Test
    fun sign_In_Progress_Indicator_Displays_When_Done_Ime_Action_Clicked_From_Password_Text_Field() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_PASSWORD
                )

                performImeAction()
            }

        authenticationScreenTestUtil
            .onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Progress_Indicator_Displays_When_Done_Ime_Action_Clicked_From_Password_Text_Field() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .apply {
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_PASSWORD
                )

                performImeAction()
            }

        authenticationScreenTestUtil
            .onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_No_Password_Requirement_Satisfied_By_Default() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
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

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_Eight_Characters_Satisfied() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASSWORD
            )

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfied(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
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

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASSWORD
            )

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_One_Uppercase_Letter_Satisfied() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASS
            )

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterSatisfied(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
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

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_PASS
            )

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_One_Digit_Satisfied() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_1PASS
            )

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitSatisfied(
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

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD_1PASS
            )

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_All_Password_Requirements_Satisfied() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfied(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterSatisfied(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitSatisfied(
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

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationScreenTestUtil
            .passwordRequirementsTestUtil3
            .onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Perform_Key_Press_On_Text_Fields() {

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = TRUE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
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
        authenticationScreenTestUtil
            .onNodeWithPasswordInputTrailingIcon()
            .performClick()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
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

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
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
        authenticationScreenTestUtil
            .onNodeWithPasswordInputTrailingIcon()
            .performClick()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
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

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(isSignInMode = FALSE)

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        // This should have been used but navigation has been between modes (SignIn and SignUp) but
        // inside of a single AuthenticationScreen screen (i.e. route/destination).
//        Espresso.pressBack()

        authenticationScreenTestUtil
            .authenticationToggleModeTestUtil3
            .onNodeWithAuthenticationToggleModeAndTextExactlyAlreadyHaveAnAccount()
            .performClick()

        authenticationScreenTestUtil
            .authenticationTitleTestUtil3
            .assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

    }

    @Test
    fun sign_In_No_Text_Field_Showing_Soft_Keyboard_By_Default() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = TRUE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_In_Email_Input_Shows_Soft_Keyboard_When_Clicked() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = TRUE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_In_Password_Input_Shows_Soft_Keyboard_When_Clicked() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = TRUE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_In_Email_Input_Soft_Keyboard_Manually_Closes_After_Shown() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = TRUE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
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
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = TRUE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
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

    /**
     * This test case used to pass in the previous commit ( 49b3e8f10c345a254f3d28a669216903501f2716 )
     * before the update of most of this project's dependencies such as libraries and plugins.
     * If it fails, please use this revised test case
     * [sign_In_Email_Input_Soft_Keyboard_Closes_When_Next_Ime_Action_Clicked_2]
     */
    @Test
    fun sign_In_Email_Input_Soft_Keyboard_Closes_When_Next_Ime_Action_Clicked() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = TRUE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_In_Email_Input_Soft_Keyboard_Closes_When_Next_Ime_Action_Clicked_2() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = TRUE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_In_Password_Input_Soft_Keyboard_Closes_When_Done_Ime_Action_Clicked() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = TRUE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    /**
     * This test case sometimes passes after multiple re-runs when it fails in the previous commit
     * ( 49b3e8f10c345a254f3d28a669216903501f2716 ) before the update of most of this project's dependencies such as libraries and plugins.
     * If it fails, please use this revised test case
     * [sign_In_Password_Input_Soft_Keyboard_Shown_When_Next_Ime_Action_Clicked_From_Email_Input_2]
     */
    @Test
    fun sign_In_Password_Input_Soft_Keyboard_Shown_When_Next_Ime_Action_Clicked_From_Email_Input() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = TRUE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsFocused()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsFocused()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_In_Password_Input_Soft_Keyboard_Shown_When_Next_Ime_Action_Clicked_From_Email_Input_2() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = TRUE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsFocused()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsFocused()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_Up_No_Text_Field_Showing_Soft_Keyboard_By_Default() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = FALSE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_Up_Email_Input_Shows_Soft_Keyboard_When_Clicked() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = FALSE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_Up_Password_Input_Shows_Soft_Keyboard_When_Clicked() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = FALSE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_Up_Email_Input_Soft_Keyboard_Manually_Closes_After_Shown() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = FALSE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
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
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = FALSE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
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

    /**
     * This test case used to pass in the previous commit ( 49b3e8f10c345a254f3d28a669216903501f2716 )
     * before the update of most of this project's dependencies such as libraries and plugins.
     * If it fails, please use this revised test case
     * [sign_Up_Email_Input_Soft_Keyboard_Closes_When_Next_Ime_Action_Clicked_2]
     */
    @Test
    fun sign_Up_Email_Input_Soft_Keyboard_Closes_When_Next_Ime_Action_Clicked() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = FALSE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    @Test
    fun sign_Up_Email_Input_Soft_Keyboard_Closes_When_Next_Ime_Action_Clicked_2() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = FALSE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_Up_Password_Input_Soft_Keyboard_Closes_When_Done_Ime_Action_Clicked() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = FALSE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

    }

    /**
     * This test case used to pass in the previous commit ( 49b3e8f10c345a254f3d28a669216903501f2716 )
     * before the update of most of this project's dependencies such as libraries and plugins.
     * If it fails, please use this revised test case
     * [sign_Up_Password_Input_Soft_Keyboard_Shown_When_Next_Ime_Action_Clicked_From_Email_Input_2]
     */
    @Test
    fun sign_Up_Password_Input_Soft_Keyboard_Shown_When_Next_Ime_Action_Clicked_From_Email_Input() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = FALSE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsFocused()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performImeAction()

        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsFocused()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

    @Test
    fun sign_Up_Password_Input_Soft_Keyboard_Shown_When_Next_Ime_Action_Clicked_From_Email_Input_2() {

        // Since this has been replaced, it should be removed.
//        authenticationScreenTestUtil.setContentAsAuthenticationScreenWithKeyboardHelperInitAndAssertItIsDisplayed(
//            composeTestRule= composeTestRule, keyboardHelper = keyboardHelper
//        )

        authenticationScreenTestUtil
            .setContentAsAuthenticationScreenAndAssertItIsDisplayed(
                keyboardHelper = keyboardHelper, isSignInMode = FALSE
            )

        // This is no longer necessary and should be removed.
//        authenticationScreenTestUtil.navigateFromSignInToSignUpModesAndConfirmTitles(composeTestRule)

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performClick()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsFocused()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsNotFocused()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .performImeAction()

//        keyboardHelper.waitForKeyboardVisibility(visible = FALSE)
        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

//        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isFalse()
        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

        authenticationScreenTestUtil
            .emailInputTestUtil3
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsNotFocused()

        authenticationScreenTestUtil
            .passwordInputTestUtil3
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsFocused()

        keyboardHelper.waitForKeyboardVisibility(visible = TRUE)

        assertThat(keyboardHelper.isSoftwareKeyboardShown()).isTrue()

    }

}