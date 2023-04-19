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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.militaryjet.test.R
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.*
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.screens.authentication.stateholders.AuthenticationUiState
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON_PASSWORD_HIDDEN
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Wednesday 30th November, 2022.
 */


/**
 * The following classes are revisions of this class:
 * [PasswordInputTest2]
 * [PasswordInputTest3]
 * [PasswordInputTest4]
 *
 * Important:
 *
 * - Try not to run all the test cases by running this test class as some tests might fail. If you do
 * and some tests fail, try running them one after the other. If a test fails, check the test
 * function's KDoc/comment (if any) on possible solution to make the test pass when it should.
 * - If you try to run a test and it fails, check the test function's KDoc/comment (if any) on
 * possible solution to make the test pass when it should.
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
class PasswordInputTest {

    private companion object {

        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        private const val INPUT_CONTENT_PASSWORD: String = "passworD1"

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
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.label_password),
                    includeEditableText = FALSE
                )
            )

    }

    @Test
    fun input_Label_Displayed_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

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
            .assertIsDisplayed()

    }

    @Test
    fun input_Content_Displayed() {

        val uiState = AuthenticationUiState(
            password = INPUT_CONTENT_PASSWORD
        )

        composeTestRule.setContent {
            PasswordInput(
                passwordVisualTransformation = VisualTransformation.None,
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assert(
                matcher = hasText(
                    text = INPUT_CONTENT_PASSWORD,
                    substring = FALSE,
                    ignoreCase = FALSE
                )
            )

    }

    @Test
    fun input_Content_Displayed_2() {

        val uiState = AuthenticationUiState(
            password = INPUT_CONTENT_PASSWORD
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    passwordVisualTransformation = VisualTransformation.None,
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    // Test will fail to assert for EditableText. So use hasText() to assert for
                    // EditableText (i.e. TextField value)
//                    other = hasTextExactly(
//                        INPUT_CONTENT_PASSWORD
//                    )
                    other = hasText(
                        text = INPUT_CONTENT_PASSWORD,
                        substring = FALSE,
                        ignoreCase = FALSE
                    )
                )
            )
            .assertIsDisplayed()

    }

    // This test would pass when it should but its wrong as there might be multiple nodes with the
    // same leading icon image vector semantics.
    // So see the version 3 of this test.
    @Test
    fun input_Leading_Icon_Image_Vector_Displayed() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTextFieldLeadingIconImageVector(
                    leadingIconImageVector = Icons.Default.Lock
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Leading_Icon_Image_Vector_Displayed_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assert(
                matcher = hasTextFieldLeadingIconImageVector(
                    leadingIconImageVector = Icons.Default.Lock
                )
            )

    }

    @Test
    fun input_Leading_Icon_Image_Vector_Displayed_3() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldLeadingIconImageVector(
                        leadingIconImageVector = Icons.Default.Lock
                    )
                )
            )
            .assertIsDisplayed()

    }

    // This test would pass when it should but its wrong as there might be multiple nodes with the
    // same leading icon content description semantics.
    // So see the version 3 of this test.
    @Test
    fun input_Leading_Icon_Content_Description_Exists() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTextFieldLeadingIconContentDescription(
                    leadingIconContentDescription = mContext.getString(
                        R.string.content_description_password_input_leading_icon
                    )
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Leading_Icon_Content_Description_Exists_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assert(
                matcher = hasTextFieldLeadingIconContentDescription(
                    leadingIconContentDescription = mContext.getString(
                        R.string.content_description_password_input_leading_icon
                    )
                )
            )

    }

    @Test
    fun input_Leading_Icon_Content_Description_Exists_3() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldLeadingIconContentDescription(
                        leadingIconContentDescription = mContext.getString(
                            R.string.content_description_password_input_leading_icon
                        )
                    )
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Click_Label_To_Show_Password_Exists_By_Default() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assert(
                matcher = hasTextFieldTrailingIconClickLabel(
                    trailingIconClickLabel = mContext.getString(
                        R.string.content_description_show_password
                    )
                )
            )

    }

    @Test
    fun input_Trailing_Icon_Click_Label_To_Show_Password_Exists_By_Default_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconClickLabel(
                        trailingIconClickLabel = mContext.getString(
                            R.string.content_description_show_password
                        )
                    )
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Click_Label_Changed_To_Hide_Password_When_Trailing_Icon_Clicked() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .apply {
                performTextInput(
                    text = INPUT_CONTENT_PASSWORD
                )

                assert(
                    matcher = hasTextFieldTrailingIconClickLabel(
                        trailingIconClickLabel = mContext.getString(
                            R.string.content_description_show_password
                        )
                    )
                )
            }

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
                )
            )
            // Optional
            .assert(
                matcher = hasTextFieldTrailingIconClickLabel(
                    trailingIconClickLabel = mContext.getString(
                        R.string.content_description_show_password
                    )
                ).not()
            )
            .assert(
                matcher = hasTextFieldTrailingIconClickLabel(
                    trailingIconClickLabel = mContext.getString(
                        R.string.content_description_hide_password
                    )
                )
            )

    }

    @Test
    fun input_Trailing_Icon_Click_Label_Changed_To_Hide_Password_When_Trailing_Icon_Clicked_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        // Optional
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
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconClickLabel(
                        trailingIconClickLabel = mContext.getString(
                            R.string.content_description_show_password
                        )
                    )
                )
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
                )
            )
            .performClick()

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconClickLabel(
                        trailingIconClickLabel = mContext.getString(
                            R.string.content_description_show_password
                        )
                    )
                )
            )
            .assertDoesNotExist()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconClickLabel(
                        trailingIconClickLabel = mContext.getString(
                            R.string.content_description_hide_password
                        )
                    )
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Click_Label_Changed_To_Hide_Password_When_Trailing_Icon_Clicked_3() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconClickLabel(
                        trailingIconClickLabel = mContext.getString(
                            R.string.content_description_show_password
                        )
                    )
                )
            )
            .assertIsDisplayed()

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconClickLabel(
                        trailingIconClickLabel = mContext.getString(
                            R.string.content_description_hide_password
                        )
                    )
                )
            )
            .assertDoesNotExist()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
                )
            )
            .performClick()

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconClickLabel(
                        trailingIconClickLabel = mContext.getString(
                            R.string.content_description_show_password
                        )
                    )
                )
            )
            .assertDoesNotExist()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconClickLabel(
                        trailingIconClickLabel = mContext.getString(
                            R.string.content_description_hide_password
                        )
                    )
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Image_Vector_To_Show_Password_Displayed_By_Default() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assert(
                matcher = hasTextFieldTrailingIconImageVector(
                    trailingIconImageVector = Icons.Default.Visibility
                )
            )

    }

    @Test
    fun input_Trailing_Icon_Image_Vector_To_Show_Password_Displayed_By_Default_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconImageVector(
                        trailingIconImageVector = Icons.Default.Visibility
                    )
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Image_Vector_Changed_To_Hide_Password_When_Trailing_Icon_Clicked() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .apply {
                performTextInput(
                    text = INPUT_CONTENT_PASSWORD
                )

                assert(
                    matcher = hasTextFieldTrailingIconImageVector(
                        trailingIconImageVector = Icons.Default.Visibility
                    )
                )
            }

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
                )
            )
            // Optional
            .assert(
                matcher = hasTextFieldTrailingIconImageVector(
                    trailingIconImageVector = Icons.Default.Visibility
                ).not()
            )
            .assert(
                matcher = hasTextFieldTrailingIconImageVector(
                    trailingIconImageVector = Icons.Default.VisibilityOff
                )
            )

    }

    @Test
    fun input_Trailing_Icon_Image_Vector_Changed_To_Hide_Password_When_Trailing_Icon_Clicked_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        // Optional
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
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconImageVector(
                        trailingIconImageVector = Icons.Default.Visibility
                    )
                )
            )
            .assertIsDisplayed()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
                )
            )
            .performClick()

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconImageVector(
                        trailingIconImageVector = Icons.Default.Visibility
                    )
                )
            )
            .assertDoesNotExist()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconImageVector(
                        trailingIconImageVector = Icons.Default.VisibilityOff
                    )
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Image_Vector_Changed_To_Hide_Password_When_Trailing_Icon_Clicked_3() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconImageVector(
                        trailingIconImageVector = Icons.Default.Visibility
                    )
                )
            )
            .assertIsDisplayed()

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconImageVector(
                        trailingIconImageVector = Icons.Default.VisibilityOff
                    )
                )
            )
            .assertDoesNotExist()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
                )
            )
            .performClick()

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconImageVector(
                        trailingIconImageVector = Icons.Default.Visibility
                    )
                )
            )
            .assertDoesNotExist()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconImageVector(
                        trailingIconImageVector = Icons.Default.VisibilityOff
                    )
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Content_Description_For_Hidden_Password_Exists_By_Default() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assert(
                matcher = hasTextFieldTrailingIconContentDescription(
                    trailingIconContentDescription = mContext.getString(
                        R.string.content_description_password_input_trailing_icon_password_hidden
                    )
                )
            )

    }

    @Test
    fun input_Trailing_Icon_Content_Description_For_Hidden_Password_Exists_By_Default_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconContentDescription(
                        trailingIconContentDescription = mContext.getString(
                            R.string.content_description_password_input_trailing_icon_password_hidden
                        )
                    )
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Content_Description_For_Shown_Password_Exists_When_Trailing_Icon_Clicked() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

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
                )
            )
            .assert(
                matcher = hasTextFieldTrailingIconContentDescription(
                    trailingIconContentDescription = mContext.getString(
                        R.string.content_description_password_input_trailing_icon_password_shown
                    )
                )
            )

    }

    @Test
    fun input_Trailing_Icon_Content_Description_For_Shown_Password_Exists_When_Trailing_Icon_Clicked_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
                )
            )
            .performClick()

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconContentDescription(
                        trailingIconContentDescription = mContext.getString(
                            R.string.content_description_password_input_trailing_icon_password_hidden
                        )
                    )
                )
            )
            .assertDoesNotExist()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconContentDescription(
                        trailingIconContentDescription = mContext.getString(
                            R.string.content_description_password_input_trailing_icon_password_shown
                        )
                    )
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Content_Description_For_Shown_Password_Exists_When_Trailing_Icon_Clicked_3() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconContentDescription(
                        trailingIconContentDescription = mContext.getString(
                            R.string.content_description_password_input_trailing_icon_password_hidden
                        )
                    )
                )
            )
            .assertIsDisplayed()

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconContentDescription(
                        trailingIconContentDescription = mContext.getString(
                            R.string.content_description_password_input_trailing_icon_password_shown
                        )
                    )
                )
            )
            .assertDoesNotExist()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
                )
            )
            .performClick()

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconContentDescription(
                        trailingIconContentDescription = mContext.getString(
                            R.string.content_description_password_input_trailing_icon_password_hidden
                        )
                    )
                )
            )
            .assertDoesNotExist()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldTrailingIconContentDescription(
                        trailingIconContentDescription = mContext.getString(
                            R.string.content_description_password_input_trailing_icon_password_shown
                        )
                    )
                )
            )
            .assertIsDisplayed()

    }

    // This test would pass when it should but its wrong as there might be multiple nodes with the
    // same single line semantics.
    // So see the version 3 of this test.
    @Test
    fun input_Single_Line_Exists() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
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
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
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

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
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
    // So see the version 3 of this test.
    @Test
    fun input_Keyboard_Options_Keyboard_Type_Exists() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTextFieldKeyboardOptionsKeyboardType(
                    keyboardOptionsKeyboardType = KeyboardType.Password
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Keyboard_Type_Exists_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assert(
                matcher = hasTextFieldKeyboardOptionsKeyboardType(
                    keyboardOptionsKeyboardType = KeyboardType.Password
                )
            )

    }

    @Test
    fun input_Keyboard_Options_Keyboard_Type_Exists_3() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldKeyboardOptionsKeyboardType(
                        keyboardOptionsKeyboardType = KeyboardType.Password
                    )
                )
            )
            .assertIsDisplayed()

    }

    // This test would pass when it should but its wrong as there might be multiple nodes with the
    // same keyboard options ime action semantics.
    // So see the version 3 of this test.
    @Test
    fun input_Keyboard_Options_Ime_Action_Exists() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTextFieldKeyboardOptionsImeAction(
                    keyboardOptionsImeAction = ImeAction.Done
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Ime_Action_Exists_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assert(
                matcher = hasTextFieldKeyboardOptionsImeAction(
                    keyboardOptionsImeAction = ImeAction.Done
                )
            )

    }

    @Test
    fun input_Keyboard_Options_Ime_Action_Exists_3() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldKeyboardOptionsImeAction(
                        keyboardOptionsImeAction = ImeAction.Done
                    )
                )
            )
            .assertIsDisplayed()

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun password_Changed_Callback_Triggered() {

        val password = "passworD"
        val appendedText = "12345"

        val onPasswordChanged: (password: String) -> Unit = mock()

        val uiState = AuthenticationUiState(
            password = password
        )

        composeTestRule.setContent {
            PasswordInput(
                // Optional since test doesn't fail when it should pass
                passwordVisualTransformation = VisualTransformation.None,
                password = uiState.password,
                onPasswordChanged = onPasswordChanged,
                onDoneClicked = { }
            )
        }

        // Optional since test doesn't fail when it should pass
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInputSelection(
                selection = TextRange(index = password.length)
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = appendedText
            )

        verify(
            mock = onPasswordChanged
        ).invoke(password + appendedText)

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun password_Changed_Callback_Triggered_2() {

        val password = "passworD"
        val appendedText = "12345"

        val onPasswordChanged: (password: String) -> Unit = mock()

        val uiState = AuthenticationUiState(
            password = password
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    // Optional since test doesn't fail when it should pass
                    passwordVisualTransformation = VisualTransformation.None,
                    password = uiState.password,
                    onPasswordChanged = onPasswordChanged,
                    onDoneClicked = { }
                )
            }
        }

        // Optional since test doesn't fail when it should pass
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInputSelection(
                selection = TextRange(index = password.length)
            )

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performTextInput(
                text = appendedText
            )

        verify(
            mock = onPasswordChanged
        ).invoke(password + appendedText)

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun password_Changed_Callback_Triggered_2_WithoutMockito() {

        val password = "passworD"
        val appendedText = "12345"
        var passwordNew = ""

        var isClicked = FALSE

        val onPasswordChanged: (password: String) -> Unit = {
            passwordNew = it

            isClicked = TRUE
        }

        val uiState = AuthenticationUiState(
            password = password
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    // Optional since test doesn't fail when it should pass
                    passwordVisualTransformation = VisualTransformation.None,
                    password = uiState.password,
                    onPasswordChanged = onPasswordChanged,
                    onDoneClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .apply {
                // Optional since test doesn't fail when it should pass
                performTextInputSelection(
                    selection = TextRange(index = password.length)
                )

                performTextInput(
                    text = appendedText
                )
            }

        assertTrue(isClicked)

//        assertThat(password + appendedText).isEqualTo(passwordNew) // Or
        assertEquals(password + appendedText, passwordNew)

    }

    @Test
    fun done_Clicked_Callback_Triggered() {

        val onDoneClicked: () -> Unit = mock()

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = onDoneClicked
            )
        }

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
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performImeAction()

        verify(
            mock = onDoneClicked
        ).invoke()

    }

    @Test
    fun done_Clicked_Callback_Triggered_2() {

        val onDoneClicked: () -> Unit = mock()

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = onDoneClicked
                )
            }
        }

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
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .performImeAction()

        verify(
            mock = onDoneClicked
        ).invoke()

    }

    @Test
    fun done_Clicked_Callback_Triggered_2_WithoutMockito() {

        val uiState = AuthenticationUiState()

        var isClicked = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = {
                        isClicked = TRUE
                    }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .apply {
                performTextInput(
                    text = INPUT_CONTENT_PASSWORD
                )

                performImeAction()
            }

        assertTrue(isClicked)

    }

    @Test
    fun input_Password_Visual_Transformation_Exists_By_Default() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .assert(
                matcher = hasTextFieldVisualTransformation(
                    visualTransformation = PasswordVisualTransformation()
                )
            )

    }

    @Test
    fun input_Password_Visual_Transformation_Exists_By_Default_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldVisualTransformation(
                        visualTransformation = PasswordVisualTransformation()
                    )
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Password_Visual_Transformation_Disabled_When_Trailing_Icon_Clicked() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                )
            )
            .apply {
                performTextInput(
                    text = INPUT_CONTENT_PASSWORD
                )

                assert(
                    matcher = hasTextFieldVisualTransformation(
                        visualTransformation = PasswordVisualTransformation()
                    )
                )
            }

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
                )
            )
            // Optional
            .assert(
                matcher = hasTextFieldVisualTransformation(
                    visualTransformation = PasswordVisualTransformation()
                ).not()
            )
            .assert(
                matcher = hasTextFieldVisualTransformation(
                    visualTransformation = VisualTransformation.None
                )
            )

    }

    @Test
    fun input_Password_Visual_Transformation_Disabled_When_Trailing_Icon_Clicked_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldVisualTransformation(
                        visualTransformation = VisualTransformation.None
                    )
                )
            )
            .assertDoesNotExist()

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldVisualTransformation(
                        visualTransformation = PasswordVisualTransformation()
                    )
                )
            )
            .apply {
                assertIsDisplayed()

                performTextInput(
                    text = INPUT_CONTENT_PASSWORD
                )
            }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
                )
            )
            .performClick()

        // Optional
        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldVisualTransformation(
                        visualTransformation = PasswordVisualTransformation()
                    )
                )
            )
            .assertDoesNotExist()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = hasTextFieldVisualTransformation(
                        visualTransformation = VisualTransformation.None
                    )
                )
            )
            .assertIsDisplayed()

    }

    /**
     * For this test to pass, from the file app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/PasswordInput.kt ,
     * uncomment the testTag(tag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON_PASSWORD_HIDDEN + isPasswordHidden)
     * and then comment out testTag(tag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON) from the
     * TextField trailing icon's Modifier. Also checkout the comments there for reasons for this
     * test approach.
     *
     * This is a useful test if you want to quickly cover test cases such as the following:
     * [input_Trailing_Icon_Click_Label_To_Show_Password_Exists_By_Default],
     * [input_Trailing_Icon_Click_Label_Changed_To_Hide_Password_When_Trailing_Icon_Clicked],
     * [input_Trailing_Icon_Image_Vector_To_Show_Password_Displayed_By_Default],
     * [input_Trailing_Icon_Image_Vector_Changed_To_Hide_Password_When_Trailing_Icon_Clicked],
     * [input_Trailing_Icon_Content_Description_For_Hidden_Password_Exists_By_Default]
     *
     * But I wouldn't recommend this testing approach as it's not test granularity. Test granularity
     * is the level of detail at which a software tests and test cases address a project.
     *
     * So if you unintentionally change the image vector of the [PasswordInput]'s TextField's
     * trailing icon and then run this test, it would still pass.
     */
    @Test
    fun password_Toggled_Reflects_State() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            PasswordInput(
                password = uiState.password,
                onPasswordChanged = { },
                onDoneClicked = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON_PASSWORD_HIDDEN +
                            TRUE.toString()
                )
            )
            .performClick()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON_PASSWORD_HIDDEN +
                            FALSE.toString()
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun input_Toggling_Between_Password_Visual_Transformation_From_When_Enabled_To_Disabled_To_Enabled() {
        /**
         * TODO: See already implemented corresponding (clean) test function in [PasswordInputTest2]
         */
    }

    /**
     * For this test to pass, from the file app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/PasswordInput.kt ,
     * uncomment the testTag(tag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON_PASSWORD_HIDDEN + isPasswordHidden)
     * and then comment out testTag(tag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON) from the
     * TextField trailing icon's Modifier. Also checkout the comments there for reasons for this
     * test approach.
     *
     * This is a useful test if you want to quickly cover test cases such as the following:
     * [input_Trailing_Icon_Click_Label_To_Show_Password_Exists_By_Default],
     * [input_Trailing_Icon_Click_Label_Changed_To_Hide_Password_When_Trailing_Icon_Clicked],
     * [input_Trailing_Icon_Image_Vector_To_Show_Password_Displayed_By_Default],
     * [input_Trailing_Icon_Image_Vector_Changed_To_Hide_Password_When_Trailing_Icon_Clicked],
     * [input_Trailing_Icon_Content_Description_For_Hidden_Password_Exists_By_Default]
     *
     * But I wouldn't recommend this testing approach as it's not test granularity. Test granularity
     * is the level of detail at which a software tests and test cases address a project.
     *
     * So if you unintentionally change the image vector of the [PasswordInput]'s TextField's
     * trailing icon and then run this test, it would still pass.
     */
    @Test
    fun password_Toggled_Reflects_State_2() {

        val uiState = AuthenticationUiState()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    password = uiState.password,
                    onPasswordChanged = { },
                    onDoneClicked = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON_PASSWORD_HIDDEN +
                            TRUE.toString()
                )
            )
            .performClick()

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON_PASSWORD_HIDDEN +
                            FALSE.toString()
                )
            )
            .assertIsDisplayed()

    }

}