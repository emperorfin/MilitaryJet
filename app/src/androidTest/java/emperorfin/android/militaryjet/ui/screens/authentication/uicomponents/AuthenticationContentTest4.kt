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

package emperorfin.android.militaryjet.ui.screens.authentication.uicomponents

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.militaryjet.ui.screens.authentication.AuthenticationScreen
import emperorfin.android.militaryjet.ui.screens.authentication.AuthenticationScreenTest3
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.EIGHT_CHARACTERS
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.CAPITAL_LETTER
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.NUMBER
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.TRUE
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.FALSE
import emperorfin.android.militaryjet.ui.constants.NothingConstants.NULL
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_EMAIL
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_PASSWORD
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_PASSWORD_PASSWORD
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_PASSWORD_PASS
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_PASSWORD_1PASS
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE
import emperorfin.android.militaryjet.ui.utils.AuthenticationContentTestUtil2
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 24th March, 2023.
 */


/**
 * [AuthenticationContentTest5] class is a revision of this class.
 *
 * The tests in this class are a subset of the ones in the [AuthenticationScreenTest3] class but with
 * the tests in this class focused on testing the [AuthenticationContent] composable instead of
 * [AuthenticationScreen] composable.
 *
 * Also, the other tests in the [AuthenticationScreenTest3] class that are excluded in this class are
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
class AuthenticationContentTest4 {

    /**
     * Use this when resources are coming from the main source set, whether directly
     * (e.g. R.string.sample_text) or indirectly (e.g. [EIGHT_CHARACTERS.label]
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

    private lateinit var authenticationContentTestUtil: AuthenticationContentTestUtil2

    @Before
    fun setUpContexts() {

        // See field's KDoc for more info.
        mTargetContext = InstrumentationRegistry.getInstrumentation().targetContext
//        mTargetContext = ApplicationProvider.getApplicationContext<Context>() // Haven't tested but might work.
        // See field's KDoc for more info.
        mContext = InstrumentationRegistry.getInstrumentation().context

        authenticationContentTestUtil = AuthenticationContentTestUtil2(
            mContext = mContext,
            mTargetContext = mTargetContext,
            composeTestRule = composeTestRule
        )

    }

    @Test
    fun sign_In_Mode_Displayed() {

        authenticationContentTestUtil
            .assertSignInModeIsDisplayed()

    }

    @Test
    fun sign_Up_Mode_Displayed() {

        authenticationContentTestUtil
            .assertSignUpModeIsDisplayed()

    }

    @Test
    fun sign_In_Title_Displayed() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(authenticationMode = SIGN_IN)

        /**
         * No longer necessary since it's now part of the implementation of
         * [AuthenticationContentTestUtil.setContentAsAuthenticationContentAndAssertItIsDisplayed]
         */
//        authenticationContentTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()

    }

    @Test
    fun sign_Up_Title_Displayed() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(authenticationMode = SIGN_UP)

        /**
         * No longer necessary since it's now part of the implementation of
         * [AuthenticationContentTestUtil.setContentAsAuthenticationContentAndAssertItIsDisplayed]
         */
//        authenticationContentTestUtil.assertAuthenticationTitleAndTextExactlySignUpForAnAccountIsDisplayed()

    }

    @Test
    fun sign_In_Disabled_Button_Displayed() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(authenticationMode = SIGN_IN)

        authenticationContentTestUtil
            .onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Disabled_Button_Displayed() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(authenticationMode = SIGN_UP)

        authenticationContentTestUtil
            .onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_In_Enabled_Button_Displayed() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_IN,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD
            )

        authenticationContentTestUtil
            .onNodeWithAuthenticationButtonAndTextExactlySignIn()
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

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD,
                passwordRequirements = satisfiedPasswordRequirements
            )

        authenticationContentTestUtil
            .onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertIsEnabled()
            }

    }

    @Test
    fun sign_In_Button_Disabled_When_Email_Input_Has_No_Content() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_IN,
                password = INPUT_CONTENT_PASSWORD
            )

        authenticationContentTestUtil
            .onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_In_Button_Disabled_When_Password_Input_Has_No_Content() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_IN,
                email = INPUT_CONTENT_EMAIL
            )

        authenticationContentTestUtil
            .onNodeWithAuthenticationButtonAndTextExactlySignIn()
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

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                password = INPUT_CONTENT_PASSWORD,
                passwordRequirements = satisfiedPasswordRequirements
            )

        authenticationContentTestUtil
            .onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Button_Disabled_When_Password_Input_Has_No_Content() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL
            )

        authenticationContentTestUtil
            .onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun need_Account_Button_Displayed() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_IN
            )

        authenticationContentTestUtil
            .onNodeWithAuthenticationToggleModeAndTextExactlyNeedAnAccount()
            .assertIsDisplayed()

    }

    @Test
    fun already_Have_Account_Button_Displayed() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP
            )

        authenticationContentTestUtil
            .onNodeWithAuthenticationToggleModeAndTextExactlyAlreadyHaveAnAccount()
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Error_Alert_Dialog_Not_Displayed() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_IN
            )

        authenticationContentTestUtil
            .onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops()
            .assertDoesNotExist()

    }

    @Test
    fun sign_In_Error_Alert_Dialog_Displayed() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_IN,
                error = MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE
            )

        authenticationContentTestUtil
            .onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops()
            .assertIsDisplayed()

    }

    // Goes straight to the "Sign Up" screen without having to navigate from the "Sign In" screen.
    @Test
    fun sign_Up_Error_Alert_Dialog_Not_Displayed() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP
            )

        authenticationContentTestUtil
            .onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops()
            .assertDoesNotExist()

    }

    // Goes straight to the "Sign Up" screen without having to navigate from the "Sign In" screen.
    @Test
    fun sign_Up_Error_Alert_Dialog_Displayed() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                error = MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE
            )

        authenticationContentTestUtil
            .onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops()
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Progress_Indicator_Not_Displayed() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_IN,
                email = INPUT_CONTENT_EMAIL, // Optional but recommended
                password = INPUT_CONTENT_PASSWORD // Optional but recommended
            )

        authenticationContentTestUtil
            .onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
            .assertDoesNotExist()

    }

    @Test
    fun sign_Up_Progress_Indicator_Not_Displayed() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL, // Optional but recommended
                password = INPUT_CONTENT_PASSWORD // Optional but recommended
            )

        authenticationContentTestUtil
            .onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
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
         * [AuthenticationContentTestUtil.setContentAsAuthenticationContentAndAssertItIsDisplayed]
         * calls [AuthenticationContentTestUtil.assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed].
         * See this test case's KDoc.
         */
//        authenticationContentTestUtil.setContentAsAuthenticationContentAndAssertItIsDisplayed(
//            authenticationMode = SIGN_IN,
//            isLoading = TRUE
//        )

        authenticationContentTestUtil
            .setContentAsAuthenticationContent(
                composeTestRule = composeTestRule,
                authenticationMode = SIGN_IN,
                isLoading = TRUE,
                email = NULL,
                password = NULL,
                passwordRequirements = emptyList(),
                error = NULL
            )

        authenticationContentTestUtil
            .assertAuthenticationContentIsDisplayed()

        authenticationContentTestUtil
            .onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
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
         * [AuthenticationContentTestUtil.setContentAsAuthenticationContentAndAssertItIsDisplayed]
         * calls [AuthenticationContentTestUtil.assertAuthenticationTitleAndTextExactlySignUpForAnAccountIsDisplayed].
         * See this test case's KDoc.
         */
//        authenticationContentTestUtil.setContentAsAuthenticationContentAndAssertItIsDisplayed(
//            authenticationMode = SIGN_UP,
//            isLoading = TRUE
//        )

        authenticationContentTestUtil
            .setContentAsAuthenticationContent(
                composeTestRule = composeTestRule,
                authenticationMode = SIGN_UP,
                isLoading = TRUE,
                email = NULL,
                password = NULL,
                passwordRequirements = emptyList(),
                error = NULL
            )

        authenticationContentTestUtil
            .assertAuthenticationContentIsDisplayed()

        authenticationContentTestUtil
            .onNodeWithCircularProgressIndicatorAndCircularProgressIndicatorColorArgbPresetColor()
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_No_Password_Requirement_Satisfied() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP
            )

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
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
    fun sign_Up_No_Password_Requirement_Satisfied_AnotherApproach() {

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP
            )

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_Eight_Characters_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(EIGHT_CHARACTERS)

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD_PASSWORD,
                passwordRequirements = satisfiedPasswordRequirements
            )

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfied(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
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

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(EIGHT_CHARACTERS)

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD_PASSWORD,
                passwordRequirements = satisfiedPasswordRequirements
            )

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_One_Uppercase_Letter_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(CAPITAL_LETTER)

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD_PASS,
                passwordRequirements = satisfiedPasswordRequirements
            )

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterSatisfied(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
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

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(CAPITAL_LETTER)

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD_PASS,
                passwordRequirements = satisfiedPasswordRequirements
            )

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_One_Digit_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(NUMBER)

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD_1PASS,
                passwordRequirements = satisfiedPasswordRequirements
            )

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
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

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(NUMBER)

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD_1PASS,
                passwordRequirements = satisfiedPasswordRequirements
            )

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAndTextExactlyAtLeastOneDigit(
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

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD,
                passwordRequirements = satisfiedPasswordRequirements
            )

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfied(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterSatisfied(
                useUnmergedTree = TRUE // Optional.
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
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

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(
            EIGHT_CHARACTERS,
            CAPITAL_LETTER,
            NUMBER
        )

        authenticationContentTestUtil
            .setContentAsAuthenticationContentAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD,
                passwordRequirements = satisfiedPasswordRequirements
            )

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationContentTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

}