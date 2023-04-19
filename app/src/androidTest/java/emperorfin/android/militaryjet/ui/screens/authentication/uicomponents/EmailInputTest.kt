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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.militaryjet.test.R
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.*
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.screens.authentication.stateholders.AuthenticationUiState
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_EMAIL
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 25th November, 2022.
 */


/**
 * The following classes are revisions of this class:
 * [EmailInputTest2]
 * [EmailInputTest3]
 * [EmailInputTest4]
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
class EmailInputTest {

    private companion object {

        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        private const val INPUT_CONTENT_EMAIL: String = "contact@email.com"

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
    fun input_Label_Displayed() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            EmailInput(
                email = uiState.email,
                onEmailChanged = { },
                onNextClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertTextEquals(
                mContext.getString(R.string.label_email),
                includeEditableText = FALSE
            )

    }

    @Test
    fun input_Label_Displayed_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assertTextEquals(
                mContext.getString(R.string.label_email),
                includeEditableText = FALSE
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Label_Displayed_3() {

        val uiState = AuthenticationUiState()

        val hasTextExactlyEmail: SemanticsMatcher = hasTextExactly(
            mContext.getString(R.string.label_email),
            includeEditableText = FALSE
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasTextExactlyEmail
                )
            )
            .assert(
                matcher = hasTextExactlyEmail
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Label_Displayed_4() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

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
            .assertIsDisplayed()

    }

    @Test
    fun input_Content_Displayed() {

        val uiState = AuthenticationUiState(
            email = INPUT_CONTENT_EMAIL
        )

        composeTestRule.setContent {
            EmailInput(
                email = uiState.email,
                onEmailChanged = { },
                onNextClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            // Test will fail to assert for EditableText. So use assertTextContains() to assert for
            // EditableText (i.e. TextField value)
//            .assertTextEquals(
//                INPUT_CONTENT_EMAIL
//            )
            .assertTextContains(
                value = INPUT_CONTENT_EMAIL
            )

    }

    @Test
    fun input_Content_Displayed_2() {

        val uiState = AuthenticationUiState(
            email = INPUT_CONTENT_EMAIL
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            // Test will fail to assert for EditableText. So use assertTextContains() to assert for
            // EditableText (i.e. TextField value)
//            .assertTextEquals(
//                INPUT_CONTENT_EMAIL
//            )
            .assertTextContains(
                value = INPUT_CONTENT_EMAIL
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Content_Displayed_3() {

        val uiState = AuthenticationUiState(
            email = INPUT_CONTENT_EMAIL
        )

        val hasEditableTextEmailAddress: SemanticsMatcher = hasText(
            text = INPUT_CONTENT_EMAIL
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasEditableTextEmailAddress
                )
            )
            // Test will fail to assert for EditableText. So use hasText() to assert for
            // EditableText (i.e. TextField value)
//            .assert(
//                matcher = hasTextExactly(
//                    INPUT_CONTENT_EMAIL
//                )
//            )
            .assert(
                matcher = hasEditableTextEmailAddress
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Content_Displayed_4() {

        val uiState = AuthenticationUiState(
            email = INPUT_CONTENT_EMAIL
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    // Test will fail to assert for EditableText. So use hasText() to assert for
                    // EditableText (i.e. TextField value)
//                    other = hasTextExactly(
//                        INPUT_CONTENT_EMAIL
//                    )
                    other = hasText(
                        text = INPUT_CONTENT_EMAIL
                    )
                )
            )
            .assertIsDisplayed()

    }

    // This test would pass when it should but its wrong as there might be multiple nodes with the
    // same leading icon image vector semantics.
    // So see the version 5 of this test.
    @Test
    fun input_Leading_Icon_Image_Vector_Displayed() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            EmailInput(
                email = uiState.email,
                onEmailChanged = { },
                onNextClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTextFieldLeadingIconImageVector(
                    leadingIconImageVector = Icons.Default.Email
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Leading_Icon_Image_Vector_Displayed_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            EmailInput(
                email = uiState.email,
                onEmailChanged = { },
                onNextClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assert(
                matcher = hasTextFieldLeadingIconImageVector(
                    leadingIconImageVector = Icons.Default.Email
                )
            )

    }

    @Test
    fun input_Leading_Icon_Image_Vector_Displayed_3() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assert(
                matcher = hasTextFieldLeadingIconImageVector(
                    leadingIconImageVector = Icons.Default.Email
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Leading_Icon_Image_Vector_Displayed_4() {

        val uiState = AuthenticationUiState()

        val hasTextFieldLeadingIconImageVectorEmail: SemanticsMatcher = hasTextFieldLeadingIconImageVector(
            leadingIconImageVector = Icons.Default.Email
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasTextFieldLeadingIconImageVectorEmail
                )
            )
            .assert(
                matcher = hasTextFieldLeadingIconImageVectorEmail
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Leading_Icon_Image_Vector_Displayed_5() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasTextFieldLeadingIconImageVector(
                        leadingIconImageVector = Icons.Default.Email
                    )
                )
            )
            .assertIsDisplayed()

    }

    // This test would pass when it should but its wrong as there might be multiple nodes with the
    // same leading icon content description semantics.
    // So see the version 5 of this test.
    @Test
    fun input_Leading_Icon_Content_Description_Exists() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            EmailInput(
                email = uiState.email,
                onEmailChanged = { },
                onNextClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTextFieldLeadingIconContentDescription(
                    leadingIconContentDescription = mContext.getString(
                        R.string.content_description_email_input_leading_icon
                    )
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Leading_Icon_Content_Description_Exists_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            EmailInput(
                email = uiState.email,
                onEmailChanged = { },
                onNextClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assert(
                matcher = hasTextFieldLeadingIconContentDescription(
                    leadingIconContentDescription = mContext.getString(
                        R.string.content_description_email_input_leading_icon
                    )
                )
            )

    }

    @Test
    fun input_Leading_Icon_Content_Description_Exists_3() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assert(
                matcher = hasTextFieldLeadingIconContentDescription(
                    leadingIconContentDescription = mContext.getString(
                        R.string.content_description_email_input_leading_icon
                    )
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Leading_Icon_Content_Description_Exists_4() {

        val uiState = AuthenticationUiState()

        val hasEmailTextFieldLeadingIconContentDescription: SemanticsMatcher =
            hasTextFieldLeadingIconContentDescription(
                leadingIconContentDescription = mContext.getString(
                    R.string.content_description_email_input_leading_icon
                )
            )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasEmailTextFieldLeadingIconContentDescription
                )
            )
            .assert(
                matcher = hasEmailTextFieldLeadingIconContentDescription
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Leading_Icon_Content_Description_Exists_5() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasTextFieldLeadingIconContentDescription(
                        leadingIconContentDescription = mContext.getString(
                            R.string.content_description_email_input_leading_icon
                        )
                    )
                )
            )
            .assertIsDisplayed()

    }

    // This test would pass when it should but its wrong as there might be multiple nodes with the
    // same single line semantics.
    // So see the version 4 of this test.
    @Test
    fun input_Single_Line_Exists() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            EmailInput(
                email = uiState.email,
                onEmailChanged = { },
                onNextClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTextFieldSingleLine(
                    singleLine = TRUE
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Single_Line_Exists_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            EmailInput(
                email = uiState.email,
                onEmailChanged = { },
                onNextClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assert(
                matcher = hasTextFieldSingleLine(
                    singleLine = TRUE
                )
            )

    }

    @Test
    fun input_Single_Line_Exists_3() {

        val uiState = AuthenticationUiState()

        val hasTextFieldSingleLine: SemanticsMatcher = hasTextFieldSingleLine(
            singleLine = TRUE
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasTextFieldSingleLine
                )
            )
            .assert(
                matcher = hasTextFieldSingleLine
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Single_Line_Exists_4() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasTextFieldSingleLine(
                        singleLine = TRUE
                    )
                )
            )
            .assertIsDisplayed()

    }

    // This test would pass when it should but its wrong as there might be multiple nodes with the
    // same keyboard options keyboard type semantics.
    // So see the version 4 of this test.
    @Test
    fun input_Keyboard_Options_Keyboard_Type_Exists() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            EmailInput(
                email = uiState.email,
                onEmailChanged = { },
                onNextClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTextFieldKeyboardOptionsKeyboardType(
                    keyboardOptionsKeyboardType = KeyboardType.Email
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Keyboard_Type_Exists_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            EmailInput(
                email = uiState.email,
                onEmailChanged = { },
                onNextClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assert(
                matcher = hasTextFieldKeyboardOptionsKeyboardType(
                    keyboardOptionsKeyboardType = KeyboardType.Email
                )
            )

    }

    @Test
    fun input_Keyboard_Options_Keyboard_Type_Exists_3() {

        val uiState = AuthenticationUiState()

        val hasTextFieldKeyboardOptionsKeyboardType: SemanticsMatcher =
            hasTextFieldKeyboardOptionsKeyboardType(
                keyboardOptionsKeyboardType = KeyboardType.Email
            )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasTextFieldKeyboardOptionsKeyboardType
                )
            )
            .assert(
                matcher = hasTextFieldKeyboardOptionsKeyboardType
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Keyboard_Type_Exists_4() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasTextFieldKeyboardOptionsKeyboardType(
                        keyboardOptionsKeyboardType = KeyboardType.Email
                    )
                )
            )
            .assertIsDisplayed()

    }

    // This test would pass when it should but its wrong as there might be multiple nodes with the
    // same ime action semantics.
    // So see the version 1D of this test.
    @Test
    fun input_Keyboard_Options_Ime_Action_Exists() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            EmailInput(
                email = uiState.email,
                onEmailChanged = { },
                onNextClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasImeAction(
                    ImeAction.Next
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Ime_Action_Exists_1B() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            EmailInput(
                email = uiState.email,
                onEmailChanged = { },
                onNextClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assert(
                matcher = hasImeAction(
                    ImeAction.Next
                )
            )

    }

    @Test
    fun input_Keyboard_Options_Ime_Action_Exists_1C() {

        val uiState = AuthenticationUiState()

        val hasImeAction: SemanticsMatcher = hasImeAction(
            ImeAction.Next
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasImeAction
                )
            )
            .assert(
                matcher = hasImeAction
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Ime_Action_Exists_1D() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasImeAction(
                        ImeAction.Next
                    )
                )
            )
            .assertIsDisplayed()

    }

    // This test would pass when it should but its wrong as there might be multiple nodes with the
    // same keyboard options ime action semantics.
    // So see the version 2D of this test.
    @Test
    fun input_Keyboard_Options_Ime_Action_Exists_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            EmailInput(
                email = uiState.email,
                onEmailChanged = { },
                onNextClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTextFieldKeyboardOptionsImeAction(
                    keyboardOptionsImeAction = ImeAction.Next
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Ime_Action_Exists_2B() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            EmailInput(
                email = uiState.email,
                onEmailChanged = { },
                onNextClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .assert(
                matcher = hasTextFieldKeyboardOptionsImeAction(
                    keyboardOptionsImeAction = ImeAction.Next
                )
            )

    }

    @Test
    fun input_Keyboard_Options_Ime_Action_Exists_2C() {

        val uiState = AuthenticationUiState()

        val hasTextFieldKeyboardOptionsImeAction: SemanticsMatcher =
            hasTextFieldKeyboardOptionsImeAction(
            keyboardOptionsImeAction = ImeAction.Next
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasTextFieldKeyboardOptionsImeAction
                )
            )
            .assert(
                matcher = hasTextFieldKeyboardOptionsImeAction
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Ime_Action_Exists_2D() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasTextFieldKeyboardOptionsImeAction(
                        keyboardOptionsImeAction = ImeAction.Next
                    )
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun email_Changed_Callback_Triggered() {

        val emailAddress = "contact@email.co"
        val appendedText = ".uk"

        val onEmailChanged: (email: String) -> Unit = mock()

        val uiState = AuthenticationUiState(
            email = emailAddress
        )
        
        composeTestRule.setContent { 
            EmailInput(
                isTest = TRUE,
                email = uiState.email,
                onEmailChanged = onEmailChanged,
                onNextClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performTextInput(
                text = appendedText
            )

        verify(
            mock = onEmailChanged
        ).invoke(emailAddress + appendedText)

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun email_Changed_Callback_Triggered_2() {

        val emailAddress = "contact@email.co"
        val appendedText = ".uk"

        val onEmailChanged: (email: String) -> Unit = mock()

        val uiState = AuthenticationUiState(
            email = emailAddress
        )

        composeTestRule.setContent {
            EmailInput(
                email = uiState.email,
                onEmailChanged = onEmailChanged,
                onNextClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performTextInputSelection(
                selection = TextRange(emailAddress.length)
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .performTextInput(
                text = appendedText
            )

        verify(
            mock = onEmailChanged
        ).invoke(emailAddress + appendedText)

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun email_Changed_Callback_Triggered_3() {

        val emailAddress = "contact@email.co"
        val appendedText = ".uk"

        val onEmailChanged: (email: String) -> Unit = mock()

        val uiState = AuthenticationUiState(
            email = emailAddress
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = onEmailChanged,
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                )
            )
            .apply {
                performTextInputSelection(
                    selection = TextRange(emailAddress.length)
                )

                performTextInput(
                    text = appendedText
                )
            }

        verify(
            mock = onEmailChanged
        ).invoke(emailAddress + appendedText)

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun email_Changed_Callback_Triggered_4() {

        val emailAddress = "contact@email.co"
        val appendedText = ".uk"

        val onEmailChanged: (email: String) -> Unit = mock()

        val uiState = AuthenticationUiState(
            email = emailAddress
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = onEmailChanged,
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasText(
                        mContext.getString(R.string.label_email)
                    )
                )
            )
            .apply {
                performTextInputSelection(
                    selection = TextRange(emailAddress.length)
                )

                performTextInput(
                    text = appendedText
                )
            }

        verify(
            mock = onEmailChanged
        ).invoke(emailAddress + appendedText)

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun email_Changed_Callback_Triggered_4_WithoutMockito() {

        val emailAddress = "contact@email.co"
        val appendedText = ".uk"
        var emailAddressNew = ""

        var isClicked = FALSE

        val onEmailChanged: (email: String) -> Unit = { email ->
            emailAddressNew = email

            isClicked = TRUE
        }

        val uiState = AuthenticationUiState(
            email = emailAddress
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = onEmailChanged,
                    onNextClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_EMAIL
                ).and(
                    other = hasText(
                        mContext.getString(R.string.label_email)
                    )
                )
            )
            .apply {
                performTextInputSelection(
                    selection = TextRange(emailAddress.length)
                )

                performTextInput(
                    text = appendedText
                )
            }

        assertTrue(isClicked)

//        assertThat(emailAddress + appendedText).isEqualTo(emailAddressNew) // Or
        assertEquals(emailAddress + appendedText, emailAddressNew)
    }

    @Test
    fun next_Clicked_Callback_Triggered() {

        val uiState = AuthenticationUiState()

        val onNextClicked: () -> Unit = mock()

        composeTestRule.setContent {
            EmailInput(
                email = uiState.email,
                onEmailChanged = { },
                onNextClicked = onNextClicked
            )
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

        verify(
            mock = onNextClicked
        ).invoke()

    }

    @Test
    fun next_Clicked_Callback_Triggered_2() {

        val uiState = AuthenticationUiState()

        val onNextClicked: () -> Unit = mock()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = onNextClicked
                )
            }
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

        verify(
            mock = onNextClicked
        ).invoke()

    }

    @Test
    fun next_Clicked_Callback_Triggered_3() {

        val uiState = AuthenticationUiState()

        val onNextClicked: () -> Unit = mock()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = onNextClicked
                )
            }
        }

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
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        verify(
            mock = onNextClicked
        ).invoke()

    }

    @Test
    fun next_Clicked_Callback_Triggered_3_WithoutMockito() {

        val uiState = AuthenticationUiState()

        var isClicked = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = uiState.email,
                    onEmailChanged = { },
                    onNextClicked = {
                        isClicked = TRUE
                    }
                )
            }
        }

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
                // Optional
                performTextInput(
                    text = INPUT_CONTENT_EMAIL
                )

                performImeAction()
            }

        assertTrue(isClicked)

    }

}

