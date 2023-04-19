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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.VisualTransformation
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.*
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON_PASSWORD_HIDDEN
import emperorfin.android.militaryjet.ui.utils.PasswordInputTestUtil
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 18th March, 2023.
 */


/**
 * [PasswordInputTest4] class is a revision of this class.
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
class PasswordInputTest3 {

    private companion object {

        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        private const val INPUT_CONTENT_PASSWORD_EMPTY: String = ""
        private const val INPUT_CONTENT_PASSWORD: String = "passworD1"
        private const val INPUT_CONTENT_PREFIXED: String = "passworD"
        private const val INPUT_CONTENT_SUFFIXED: String = "12345"

        private val VISUAL_TRANSFORMATION_NONE: VisualTransformation = VisualTransformation.None

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

    private lateinit var passwordInputTestUtil: PasswordInputTestUtil

    @Before
    fun setUpContexts() {

        // See field's KDoc for more info.
        mTargetContext = InstrumentationRegistry.getInstrumentation().targetContext
//        mTargetContext = ApplicationProvider.getApplicationContext<Context>() // Haven't tested but might work.
        // See field's KDoc for more info.
        mContext = InstrumentationRegistry.getInstrumentation().context

        passwordInputTestUtil = PasswordInputTestUtil(
            mContext = mContext,
            mTargetContext = mTargetContext,
            composeTestRule = composeTestRule
        )

    }

    @Test
    fun input_Label_Displayed() {

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD_EMPTY
            )

    }

    @Test
    fun input_Content_Displayed() {

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                passwordVisualTransformation = VISUAL_TRANSFORMATION_NONE,
                password = INPUT_CONTENT_PASSWORD
            )

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextPassworD1()
            .assertIsDisplayed()

    }

    @Test
    fun input_Leading_Icon_Image_Vector_Displayed() {

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD_EMPTY
            )

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldLeadingIconImageVectorIconsDefaultLock()
            .assertIsDisplayed()

    }

    @Test
    fun input_Leading_Icon_Content_Description_Exists() {

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD_EMPTY
            )

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldLeadingIconContentDescriptionPasswordInputLeadingIcon()
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Click_Label_To_Show_Password_Exists_By_Default() {

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD_EMPTY
            )

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelShowPassword()
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Click_Label_Changed_To_Hide_Password_When_Trailing_Icon_Clicked() {

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD_EMPTY
            )

        // Optional
        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

        // Optional
        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelShowPassword()
            .assertIsDisplayed()

        // Optional
        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelHidePassword()
            .assertDoesNotExist()

        passwordInputTestUtil
            .onNodeWithPasswordInputTrailingIconAndContentDescriptionExactlyTrailingIconIndicatesPasswordHidden()
            .performClick()

        // Optional
        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelShowPassword()
            .assertDoesNotExist()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelHidePassword()
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Image_Vector_To_Show_Password_Displayed_By_Default() {

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD_EMPTY
            )

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconImageVectorIconsDefaultVisibility()
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Image_Vector_Changed_To_Hide_Password_When_Trailing_Icon_Clicked() {

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD_EMPTY
            )

        // Optional
        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

        // Optional
        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconImageVectorIconsDefaultVisibility()
            .assertIsDisplayed()

        // Optional
        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconImageVectorIconsDefaultVisibilityOff()
            .assertDoesNotExist()

        passwordInputTestUtil
            .onNodeWithPasswordInputTrailingIconAndContentDescriptionExactlyTrailingIconIndicatesPasswordHidden()
            .performClick()

        // Optional
        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconImageVectorIconsDefaultVisibility()
            .assertDoesNotExist()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconImageVectorIconsDefaultVisibilityOff()
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Content_Description_For_Hidden_Password_Exists_By_Default() {

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD_EMPTY
            )

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordHidden()
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Content_Description_For_Shown_Password_Exists_When_Trailing_Icon_Clicked() {

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD_EMPTY
            )

        // Optional
        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordHidden()
            .assertIsDisplayed()

        // Optional
        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordShown()
            .assertDoesNotExist()

        passwordInputTestUtil
            .onNodeWithPasswordInputTrailingIconAndContentDescriptionExactlyTrailingIconIndicatesPasswordHidden()
            .performClick()

        // Optional
        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordHidden()
            .assertDoesNotExist()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordShown()
            .assertIsDisplayed()

    }

    @Test
    fun input_Single_Line_Exists() {

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD_EMPTY
            )

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldSingleLineTrue()
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Keyboard_Type_Exists() {

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD_EMPTY
            )

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldKeyboardOptionsKeyboardTypePassword()
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Ime_Action_Exists() {

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD_EMPTY
            )

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldKeyboardOptionsImeActionDone()
            .assertIsDisplayed()

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun password_Changed_Callback_Triggered() {

        val password = INPUT_CONTENT_PREFIXED
        val appendedText = INPUT_CONTENT_SUFFIXED

        val onPasswordChanged: (password: String) -> Unit = mock()

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                // Optional since test doesn't fail when it should pass
                passwordVisualTransformation = VISUAL_TRANSFORMATION_NONE,
                password = password,
                onPasswordChanged = onPasswordChanged,
            )

        // Optional since test doesn't fail when it should pass
        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .apply {
                performTextInputSelection(
                    selection = TextRange(index = password.length)
                )

                performTextInput(
                    text = appendedText
                )
            }

        verify(
            mock = onPasswordChanged
        ).invoke(password + appendedText)

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun password_Changed_Callback_Triggered_WithoutMockito() {

        val password = INPUT_CONTENT_PREFIXED
        val appendedText = INPUT_CONTENT_SUFFIXED
        var passwordNew = ""

        var isClicked = FALSE

        val onPasswordChanged: (password: String) -> Unit = {
            passwordNew = it

            isClicked = TRUE
        }

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                // Optional since test doesn't fail when it should pass
                passwordVisualTransformation = VISUAL_TRANSFORMATION_NONE,
                password = password,
                onPasswordChanged = onPasswordChanged,
            )

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextExactlyPassword()
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

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD_EMPTY,
                onDoneClicked = onDoneClicked
            )

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextExactlyPassword()
            .apply {
                performTextInput(
                    text = INPUT_CONTENT_PASSWORD
                )

                performImeAction()
            }

        verify(
            mock = onDoneClicked
        ).invoke()

    }

    @Test
    fun done_Clicked_Callback_Triggered_WithoutMockito() {

        var isClicked = FALSE

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD_EMPTY,
                onDoneClicked = {
                    isClicked = TRUE
                }
            )

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextExactlyPassword()
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

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD_EMPTY
            )

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldVisualTransformationPassword()
            .assertIsDisplayed()

    }

    @Test
    fun input_Password_Visual_Transformation_Disabled_When_Trailing_Icon_Clicked() {

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD_EMPTY
            )

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldVisualTransformationNone()
            .assertDoesNotExist()

        // Optional
        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldVisualTransformationPassword()
            .apply {
                assertIsDisplayed()

                performTextInput(
                    text = INPUT_CONTENT_PASSWORD
                )
            }

        passwordInputTestUtil
            .onNodeWithPasswordInputTrailingIconAndContentDescriptionExactlyTrailingIconIndicatesPasswordHidden()
            .performClick()

        // Optional
        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldVisualTransformationPassword()
            .assertDoesNotExist()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldVisualTransformationNone()
            .assertIsDisplayed()

    }

    @Test
    fun input_Toggling_Between_Password_Visual_Transformation_From_When_Enabled_To_Disabled_To_Enabled() {

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD
            )

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldVisualTransformationNone()
            .assertDoesNotExist()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordShown()
            .assertDoesNotExist()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelHidePassword()
            .assertDoesNotExist()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldVisualTransformationPassword()
            .assertIsDisplayed()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordHidden()
            .assertIsDisplayed()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelShowPassword()
            .assertIsDisplayed()


        passwordInputTestUtil
            .onNodeWithPasswordInputTrailingIconAndContentDescriptionExactlyTrailingIconIndicatesPasswordHidden()
            .performClick()


        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldVisualTransformationPassword()
            .assertDoesNotExist()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordHidden()
            .assertDoesNotExist()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelShowPassword()
            .assertDoesNotExist()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldVisualTransformationNone()
            .assertIsDisplayed()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordShown()
            .assertIsDisplayed()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelHidePassword()
            .assertIsDisplayed()


        passwordInputTestUtil
            .onNodeWithPasswordInputTrailingIconAndContentDescriptionExactlyTrailingIconIndicatesPasswordShown()
            .performClick()


        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldVisualTransformationNone()
            .assertDoesNotExist()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordShown()
            .assertDoesNotExist()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelHidePassword()
            .assertDoesNotExist()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldVisualTransformationPassword()
            .assertIsDisplayed()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordHidden()
            .assertIsDisplayed()

        passwordInputTestUtil
            .onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelShowPassword()
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

        passwordInputTestUtil
            .setContentAsPasswordInputAndAssertItIsDisplayed(
                password = INPUT_CONTENT_PASSWORD_EMPTY
            )

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