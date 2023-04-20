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
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.EIGHT_CHARACTERS
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.CAPITAL_LETTER
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.NUMBER
import emperorfin.android.militaryjet.ui.utils.AuthenticationFormTestUtil3
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.FALSE
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.TRUE
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_EMAIL_EMPTY
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_EMAIL
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_PASSWORD_EMPTY
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_PASSWORD
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_PASSWORD_PASSWORD
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_PASSWORD_PASS
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_PASSWORD_1PASS


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 09th April, 2023.
 */


/**
 * The tests in this class are a subset of the ones in the [AuthenticationContentTest4] class but
 * with the tests in this class focused on testing the [AuthenticationForm] composable instead of
 * [AuthenticationContent] composable.
 *
 * Also, the other tests in the [AuthenticationContentTest4] class that are excluded in this class are
 * the ones that includes testing for error dialog (and error state) and progress indicator (and
 * loading state). These tests are excluded in this class since the [AuthenticationForm] composable
 * which is under test doesn't include the error dialog and progress indicator composables
 * (or components).
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
class AuthenticationFormTest5 {

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

    private lateinit var authenticationFormTestUtil: AuthenticationFormTestUtil3

    @Before
    fun setUpContexts() {

        // See field's KDoc for more info.
        mTargetContext = InstrumentationRegistry.getInstrumentation().targetContext
//        mTargetContext = ApplicationProvider.getApplicationContext<Context>() // Haven't tested but might work.
        // See field's KDoc for more info.
        mContext = InstrumentationRegistry.getInstrumentation().context

        authenticationFormTestUtil = AuthenticationFormTestUtil3(
            mContext = mContext,
            mTargetContext = mTargetContext,
            composeTestRule = composeTestRule
        )

    }

    @Test
    fun sign_In_Mode_Displayed() {

        authenticationFormTestUtil
            .assertSignInModeIsDisplayed()

    }

    @Test
    fun sign_Up_Mode_Displayed() {

        authenticationFormTestUtil
            .assertSignUpModeIsDisplayed()

    }

    @Test
    fun sign_In_Title_Displayed() {

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_IN,
                email = INPUT_CONTENT_EMAIL_EMPTY,
                password = INPUT_CONTENT_PASSWORD_EMPTY,
                passwordRequirements = emptyList(),
                enableAuthentication = FALSE
            )

    }

    @Test
    fun sign_Up_Title_Displayed() {

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL_EMPTY,
                password = INPUT_CONTENT_PASSWORD_EMPTY,
                passwordRequirements = emptyList(),
                enableAuthentication = FALSE
            )

    }

    @Test
    fun sign_In_Disabled_Button_Displayed() {

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_IN,
                email = INPUT_CONTENT_EMAIL_EMPTY,
                password = INPUT_CONTENT_PASSWORD_EMPTY,
                passwordRequirements = emptyList(),
                enableAuthentication = FALSE
            )

        authenticationFormTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Disabled_Button_Displayed() {

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL_EMPTY,
                password = INPUT_CONTENT_PASSWORD_EMPTY,
                passwordRequirements = emptyList(),
                enableAuthentication = FALSE
            )

        authenticationFormTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_In_Enabled_Button_Displayed() {

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_IN,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD,
                passwordRequirements = emptyList(),
                enableAuthentication = TRUE
            )

        authenticationFormTestUtil
            .authenticationButtonTestUtil3
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

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD,
                passwordRequirements = satisfiedPasswordRequirements,
                enableAuthentication = TRUE
            )

        authenticationFormTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertIsEnabled()
            }

    }

    @Test
    fun sign_In_Button_Disabled_When_Email_Input_Has_No_Content() {

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_IN,
                email = INPUT_CONTENT_EMAIL_EMPTY,
                password = INPUT_CONTENT_PASSWORD,
                passwordRequirements = emptyList(),
                enableAuthentication = FALSE
            )

        authenticationFormTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_In_Button_Disabled_When_Password_Input_Has_No_Content() {

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_IN,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD_EMPTY,
                passwordRequirements = emptyList(),
                enableAuthentication = FALSE
            )

        authenticationFormTestUtil
            .authenticationButtonTestUtil3
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

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL_EMPTY,
                password = INPUT_CONTENT_PASSWORD,
                passwordRequirements = satisfiedPasswordRequirements,
                enableAuthentication = FALSE
            )

        authenticationFormTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Button_Disabled_When_Password_Input_Has_No_Content() {

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD_EMPTY,
                passwordRequirements = emptyList(),
                enableAuthentication = FALSE
            )

        authenticationFormTestUtil
            .authenticationButtonTestUtil3
            .onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun need_Account_Button_Displayed() {

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_IN,
                email = INPUT_CONTENT_EMAIL_EMPTY,
                password = INPUT_CONTENT_PASSWORD_EMPTY,
                passwordRequirements = emptyList(),
                enableAuthentication = FALSE
            )

        authenticationFormTestUtil
            .onNodeWithAuthenticationToggleModeAndTextExactlyNeedAnAccount()
            .assertIsDisplayed()

    }

    @Test
    fun already_Have_Account_Button_Displayed() {

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL_EMPTY,
                password = INPUT_CONTENT_PASSWORD_EMPTY,
                passwordRequirements = emptyList(),
                enableAuthentication = FALSE
            )

        authenticationFormTestUtil
            .onNodeWithAuthenticationToggleModeAndTextExactlyAlreadyHaveAnAccount()
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_No_Password_Requirement_Satisfied() {

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL_EMPTY,
                password = INPUT_CONTENT_PASSWORD_EMPTY,
                passwordRequirements = emptyList(),
                enableAuthentication = FALSE
            )

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
                useUnmergedTree = TRUE // Optional
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

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL_EMPTY,
                password = INPUT_CONTENT_PASSWORD_EMPTY,
                passwordRequirements = emptyList(),
                enableAuthentication = FALSE
            )

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_Eight_Characters_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(EIGHT_CHARACTERS)

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD_PASSWORD,
                passwordRequirements = satisfiedPasswordRequirements,
                enableAuthentication = FALSE
            )

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfied(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
                useUnmergedTree = TRUE // Optional
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

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD_PASSWORD,
                passwordRequirements = satisfiedPasswordRequirements,
                enableAuthentication = FALSE
            )

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_One_Uppercase_Letter_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(CAPITAL_LETTER)

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD_PASS,
                passwordRequirements = satisfiedPasswordRequirements,
                enableAuthentication = FALSE
            )

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterSatisfied(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
                useUnmergedTree = TRUE // Optional
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

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD_PASS,
                passwordRequirements = satisfiedPasswordRequirements,
                enableAuthentication = FALSE
            )

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Only_At_Least_One_Digit_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(NUMBER)

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD_1PASS,
                passwordRequirements = satisfiedPasswordRequirements,
                enableAuthentication = FALSE
            )

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitSatisfied(
                useUnmergedTree = TRUE // Optional
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

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD_1PASS,
                passwordRequirements = satisfiedPasswordRequirements,
                enableAuthentication = FALSE
            )

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
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

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD,
                passwordRequirements = satisfiedPasswordRequirements,
                enableAuthentication = TRUE
            )

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfied(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterSatisfied(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitSatisfied(
                useUnmergedTree = TRUE // Optional
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

        authenticationFormTestUtil
            .setContentAsAuthenticationFormAndAssertItIsDisplayed(
                authenticationMode = SIGN_UP,
                email = INPUT_CONTENT_EMAIL,
                password = INPUT_CONTENT_PASSWORD,
                passwordRequirements = satisfiedPasswordRequirements,
                enableAuthentication = TRUE
            )

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        authenticationFormTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

}