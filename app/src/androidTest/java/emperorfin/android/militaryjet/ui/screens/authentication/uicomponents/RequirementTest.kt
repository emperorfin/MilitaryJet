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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.militaryjet.test.R
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasIconContentDescription
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasIconImageVector
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasIconTintArgb
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasTextColorArgb
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT_ICON
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 06th December, 2022.
 */


/**
 * The following classes are revisions of this class:
 * [RequirementTest2]
 * [RequirementTest3]
 * [RequirementTest4]
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
class RequirementTest {

    private companion object {

        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        /**
         * To easily find out the ARGB of a color to be used as the expected value during assertion,
         * there are two approaches to do this:
         *
         * - you can either call toArgb() on the color object and then logcat the returned value or
         * - you can just use any random assertion expected value, run the test case (which should
         * fail) and then get and use (as the correct assertion expected value) the assertion actual
         * value from the failed test error message (simplest approach).
         */
        private const val COLOR_ARGB_TINT_PRIMARY: Int = -11576430
        /**
         * To easily find out the ARGB of a color to be used as the expected value during assertion,
         * there are two approaches to do this:
         *
         * - you can either call toArgb() on the color object and then logcat the returned value or
         * - you can just use any random assertion expected value, run the test case (which should
         * fail) and then get and use (as the correct assertion expected value) the assertion actual
         * value from the failed test error message (simplest approach).
         */
        private const val COLOR_ARGB_TINT_ON_SURFACE: Int = 1713052447

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
    fun icon_Image_Vector_Displays_With_Default_Color_When_Password_Requirement_Not_Satisfied() {

        val requirementMessage: String = mTargetContext.getString(
            // Or PasswordRequirement.CAPITAL_LETTER.label or PasswordRequirement.NUMBER.label
            PasswordRequirement.EIGHT_CHARACTERS.label
        )

        val requirementSatisfied: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                Requirement(
                    message = requirementMessage,
                    satisfied = requirementSatisfied
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT_ICON
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasIconImageVector(
                    iconImageVector = Icons.Default.Check
                ).and(
                    other = hasIconTintArgb(
                        iconTintInArgb = COLOR_ARGB_TINT_ON_SURFACE
                    )
                )
            )

    }

    @Test
    fun icon_Image_Vector_Displays_With_Default_Color_When_Password_Requirement_Not_Satisfied_2() {

        val requirementMessage: String = mTargetContext.getString(
            // Or PasswordRequirement.CAPITAL_LETTER.label or PasswordRequirement.NUMBER.label
            PasswordRequirement.EIGHT_CHARACTERS.label
        )

        val requirementSatisfied: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                Requirement(
                    message = requirementMessage,
                    satisfied = requirementSatisfied
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT_ICON
                ).and(
                    other = hasIconImageVector(
                        iconImageVector = Icons.Default.Check
                    )
                ).and(
                    other = hasIconTintArgb(
                        iconTintInArgb = COLOR_ARGB_TINT_ON_SURFACE
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun icon_Image_Vector_Displays_With_Changed_Color_When_Password_Requirement_Satisfied() {

        val requirementMessage: String = mTargetContext.getString(
            // Or PasswordRequirement.CAPITAL_LETTER.label or PasswordRequirement.NUMBER.label
            PasswordRequirement.EIGHT_CHARACTERS.label
        )

        val requirementSatisfied: Boolean = TRUE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                Requirement(
                    message = requirementMessage,
                    satisfied = requirementSatisfied
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT_ICON
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasIconImageVector(
                    iconImageVector = Icons.Default.Check
                ).and(
                    other = hasIconTintArgb(
                        iconTintInArgb = COLOR_ARGB_TINT_PRIMARY
                    )
                )
            )

    }

    @Test
    fun icon_Image_Vector_Displays_With_Changed_Color_When_Password_Requirement_Satisfied_2() {

        val requirementMessage: String = mTargetContext.getString(
            // Or PasswordRequirement.CAPITAL_LETTER.label or PasswordRequirement.NUMBER.label
            PasswordRequirement.EIGHT_CHARACTERS.label
        )

        val requirementSatisfied: Boolean = TRUE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                Requirement(
                    message = requirementMessage,
                    satisfied = requirementSatisfied
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT_ICON
                ).and(
                    other = hasIconImageVector(
                        iconImageVector = Icons.Default.Check
                    )
                ).and(
                    other = hasIconTintArgb(
                        iconTintInArgb = COLOR_ARGB_TINT_PRIMARY
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun icon_Content_Description_Exists_When_Password_Requirement_Not_Satisfied() {

        val requirementMessage: String = mTargetContext.getString(
            // Or PasswordRequirement.CAPITAL_LETTER.label or PasswordRequirement.NUMBER.label
            PasswordRequirement.EIGHT_CHARACTERS.label
        )

        val requirementSatisfied: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                Requirement(
                    message = requirementMessage,
                    satisfied = requirementSatisfied
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT_ICON
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasIconContentDescription(
                    iconContentDescription = mContext.getString(
                        R.string.content_description_icon_password_requirement_needed
                    )
                )
            )

    }

    @Test
    fun icon_Content_Description_Exists_When_Password_Requirement_Not_Satisfied_2() {

        val requirementMessage: String = mTargetContext.getString(
            // Or PasswordRequirement.CAPITAL_LETTER.label or PasswordRequirement.NUMBER.label
            PasswordRequirement.EIGHT_CHARACTERS.label
        )

        val requirementSatisfied: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                Requirement(
                    message = requirementMessage,
                    satisfied = requirementSatisfied
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT_ICON
                ).and(
                    other = hasIconContentDescription(
                        iconContentDescription = mContext.getString(
                            R.string.content_description_icon_password_requirement_needed
                        )
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun icon_Content_Description_Changed_When_Password_Requirement_Satisfied() {

        val requirementMessage: String = mTargetContext.getString(
            // Or PasswordRequirement.CAPITAL_LETTER.label or PasswordRequirement.NUMBER.label
            PasswordRequirement.EIGHT_CHARACTERS.label
        )

        val requirementSatisfied: Boolean = TRUE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                Requirement(
                    message = requirementMessage,
                    satisfied = requirementSatisfied
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT_ICON
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasIconContentDescription(
                    iconContentDescription = mContext.getString(
                        R.string.content_description_icon_password_requirement_satisfied
                    )
                )
            )

    }

    @Test
    fun icon_Content_Description_Changed_When_Password_Requirement_Satisfied_2() {

        val requirementMessage: String = mTargetContext.getString(
            // Or PasswordRequirement.CAPITAL_LETTER.label or PasswordRequirement.NUMBER.label
            PasswordRequirement.EIGHT_CHARACTERS.label
        )

        val requirementSatisfied: Boolean = TRUE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                Requirement(
                    message = requirementMessage,
                    satisfied = requirementSatisfied
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT_ICON
                ).and(
                    other = hasIconContentDescription(
                        iconContentDescription = mContext.getString(
                            R.string.content_description_icon_password_requirement_satisfied
                        )
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun icon_Content_Description_Changed_When_Password_Requirement_Satisfied_3() {

        val requirementMessage: String = mTargetContext.getString(
            // Or PasswordRequirement.CAPITAL_LETTER.label or PasswordRequirement.NUMBER.label
            PasswordRequirement.EIGHT_CHARACTERS.label
        )

        val requirementSatisfied: Boolean = TRUE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                Requirement(
                    message = requirementMessage,
                    satisfied = requirementSatisfied
                )
            }
        }

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT_ICON
                ).and(
                    other = hasIconContentDescription(
                        iconContentDescription = mContext.getString(
                            R.string.content_description_icon_password_requirement_needed
                        )
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertDoesNotExist()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT_ICON
                ).and(
                    other = hasIconContentDescription(
                        iconContentDescription = mContext.getString(
                            R.string.content_description_icon_password_requirement_satisfied
                        )
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun text_Displays_With_Default_Color_When_Password_Requirement_Not_Satisfied() {

        val requirementMessage: String = mTargetContext.getString(
            PasswordRequirement.EIGHT_CHARACTERS.label
        )

        val requirementSatisfied: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                Requirement(
                    message = requirementMessage,
                    satisfied = requirementSatisfied
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_characters)
                ).and(
                    other = hasTextColorArgb(
                        textColorInArgb = COLOR_ARGB_TINT_ON_SURFACE
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
    fun text_Displays_With_Default_Color_When_Password_Requirement_Not_Satisfied_AnotherApproach() {

        val requirementMessage: String = mTargetContext.getString(
            PasswordRequirement.EIGHT_CHARACTERS.label
        )

        val requirementSatisfied: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                Requirement(
                    message = requirementMessage,
                    satisfied = requirementSatisfied
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_needed_characters)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_characters),
                    includeEditableText = FALSE
                ).and(
                    other = hasTextColorArgb(
                        textColorInArgb = COLOR_ARGB_TINT_ON_SURFACE
                    )
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
    fun text_Displays_With_Default_Color_When_Password_Requirement_Not_Satisfied_AnotherApproach_2() {

        val requirementMessage: String = mTargetContext.getString(
            PasswordRequirement.EIGHT_CHARACTERS.label
        )

        val requirementSatisfied: Boolean = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                Requirement(
                    message = requirementMessage,
                    satisfied = requirementSatisfied
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
                ).and(
                    other = hasTextColorArgb(
                        textColorInArgb = COLOR_ARGB_TINT_ON_SURFACE
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

    @Test
    fun text_Displays_With_Changed_Color_When_Password_Requirement_Satisfied() {

        val requirementMessage: String = mTargetContext.getString(
            PasswordRequirement.EIGHT_CHARACTERS.label
        )

        val requirementSatisfied: Boolean = TRUE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                Requirement(
                    message = requirementMessage,
                    satisfied = requirementSatisfied
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.password_requirement_characters)
                ).and(
                    other = hasTextColorArgb(
                        textColorInArgb = COLOR_ARGB_TINT_PRIMARY
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
    fun text_Displays_With_Changed_Color_When_Password_Requirement_Satisfied_AnotherApproach() {

        val requirementMessage: String = mTargetContext.getString(
            PasswordRequirement.EIGHT_CHARACTERS.label
        )

        val requirementSatisfied: Boolean = TRUE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                Requirement(
                    message = requirementMessage,
                    satisfied = requirementSatisfied
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT +
                            mContext.getString(R.string.test_password_requirement_satisfied_characters)
                ),
                useUnmergedTree = TRUE
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.password_requirement_characters),
                    includeEditableText = FALSE
                ).and(
                    other = hasTextColorArgb(
                        textColorInArgb = COLOR_ARGB_TINT_PRIMARY
                    )
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
    fun text_Displays_With_Changed_Color_When_Password_Requirement_Satisfied_AnotherApproach_2() {

        val requirementMessage: String = mTargetContext.getString(
            PasswordRequirement.EIGHT_CHARACTERS.label
        )

        val requirementSatisfied: Boolean = TRUE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                Requirement(
                    message = requirementMessage,
                    satisfied = requirementSatisfied
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
                ).and(
                    other = hasTextColorArgb(
                        textColorInArgb = COLOR_ARGB_TINT_PRIMARY
                    )
                ),
                useUnmergedTree = TRUE
            )
            .assertIsDisplayed()

    }

}