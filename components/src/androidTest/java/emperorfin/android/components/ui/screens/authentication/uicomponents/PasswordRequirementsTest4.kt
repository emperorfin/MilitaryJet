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
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.components.test.R
import emperorfin.android.components.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.components.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.components.ui.screens.authentication.enums.PasswordRequirement.EIGHT_CHARACTERS
import emperorfin.android.components.ui.screens.authentication.enums.PasswordRequirement.CAPITAL_LETTER
import emperorfin.android.components.ui.screens.authentication.enums.PasswordRequirement.NUMBER
import emperorfin.android.components.ui.utils.PasswordRequirementsTestUtil2
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import emperorfin.android.components.ui.constants.BooleanConstants.TRUE


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 31st March, 2023.
 */


/**
 * [PasswordRequirementsTest5] class is a revision of this class.
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
class PasswordRequirementsTest4 {

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

    private lateinit var passwordRequirementsTestUtil: PasswordRequirementsTestUtil2

    @Before
    fun setUpContexts() {

        // See field's KDoc for more info.
        mTargetContext = InstrumentationRegistry.getInstrumentation().targetContext
//        mTargetContext = ApplicationProvider.getApplicationContext<Context>() // Haven't tested but might work.
        // See field's KDoc for more info.
        mContext = InstrumentationRegistry.getInstrumentation().context

        passwordRequirementsTestUtil = PasswordRequirementsTestUtil2(
            mContext = mContext,
            mTargetContext = mTargetContext,
            composeTestRule = composeTestRule
        )

    }

    @Test
    fun no_Password_Requirement_Satisfied() {

        val satisfiedRequirements = emptyList<PasswordRequirement>()

        passwordRequirementsTestUtil
            .setContentAsPasswordRequirementsAndAssertItIsDisplayed(
                satisfiedRequirements = satisfiedRequirements
            )

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
                useUnmergedTree = TRUE // Optional
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
    fun no_Password_Requirement_Satisfied_AnotherApproach() {

        val satisfiedRequirements = emptyList<PasswordRequirement>()

        passwordRequirementsTestUtil
            .setContentAsPasswordRequirementsAndAssertItIsDisplayed(
                satisfiedRequirements = satisfiedRequirements
            )

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

    @Test
    fun only_At_Least_Eight_Characters_Satisfied() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(EIGHT_CHARACTERS)

        passwordRequirementsTestUtil
            .setContentAsPasswordRequirementsAndAssertItIsDisplayed(
                satisfiedRequirements = satisfiedRequirements
            )

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfied(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
                useUnmergedTree = TRUE // Optional
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
    fun only_At_Least_Eight_Characters_Satisfied_AnotherApproach() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(EIGHT_CHARACTERS)

        passwordRequirementsTestUtil
            .setContentAsPasswordRequirementsAndAssertItIsDisplayed(
                satisfiedRequirements = satisfiedRequirements
            )

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

    @Test
    fun only_At_Least_One_Uppercase_Letter_Satisfied() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(CAPITAL_LETTER)

        passwordRequirementsTestUtil
            .setContentAsPasswordRequirementsAndAssertItIsDisplayed(
                satisfiedRequirements = satisfiedRequirements
            )

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterSatisfied(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
                useUnmergedTree = TRUE // Optional
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
    fun only_At_Least_One_Uppercase_Letter_Satisfied_AnotherApproach() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(CAPITAL_LETTER)

        passwordRequirementsTestUtil
            .setContentAsPasswordRequirementsAndAssertItIsDisplayed(
                satisfiedRequirements = satisfiedRequirements
            )

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

    @Test
    fun only_At_Least_One_Digit_Satisfied() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(NUMBER)

        passwordRequirementsTestUtil
            .setContentAsPasswordRequirementsAndAssertItIsDisplayed(
                satisfiedRequirements = satisfiedRequirements
            )

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitSatisfied(
                useUnmergedTree = TRUE // Optional
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
    fun only_At_Least_One_Digit_Satisfied_AnotherApproach() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(NUMBER)

        passwordRequirementsTestUtil
            .setContentAsPasswordRequirementsAndAssertItIsDisplayed(
                satisfiedRequirements = satisfiedRequirements
            )

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

    @Test
    fun all_Password_Requirements_Satisfied() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(
            EIGHT_CHARACTERS,
            CAPITAL_LETTER,
            NUMBER
        )

        passwordRequirementsTestUtil
            .setContentAsPasswordRequirementsAndAssertItIsDisplayed(
                satisfiedRequirements = satisfiedRequirements
            )

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfied(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterSatisfied(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitSatisfied(
                useUnmergedTree = TRUE // Optional
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
    fun all_Password_Requirements_Satisfied_AnotherApproach() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(
            EIGHT_CHARACTERS,
            CAPITAL_LETTER,
            NUMBER
        )

        passwordRequirementsTestUtil
            .setContentAsPasswordRequirementsAndAssertItIsDisplayed(
                satisfiedRequirements = satisfiedRequirements
            )

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharacters(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAndTextExactlyAtLeastOneUppercaseLetter(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

        passwordRequirementsTestUtil
            .onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAndTextExactlyAtLeastOneDigit(
                useUnmergedTree = TRUE // Optional
            )
            .assertIsDisplayed()

    }

    @Test
    fun password_Requirements_Displayed_With_State() {

        val requirements: List<PasswordRequirement> = PasswordRequirement.values().toList()

        val satisfied: PasswordRequirement = requirements[(0 until 3).random()]

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordRequirements(
                    satisfiedRequirements = listOf(satisfied)
                )
            }
        }

        PasswordRequirement.values().forEach {
            val requirement: String = mTargetContext.getString(it.label)

            val result: String = if (it == satisfied) {
                mContext.getString(R.string.password_requirement_satisfied, requirement)
            } else {
                mContext.getString(R.string.password_requirement_needed, requirement)
            }

            composeTestRule
                .onNodeWithText(
                    text = result
                )
                .assertIsDisplayed()
        }

    }

}