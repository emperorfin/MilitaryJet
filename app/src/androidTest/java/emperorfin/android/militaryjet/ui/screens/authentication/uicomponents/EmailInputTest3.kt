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
import androidx.compose.ui.text.TextRange
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.*
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.utils.EmailInputTestUtil
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 16th March, 2023.
 */


/**
 * [EmailInputTest4] class is a revision of this class.
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
class EmailInputTest3 {

    private companion object {

        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        private const val INPUT_CONTENT_EMAIL_EMPTY: String = ""
        private const val INPUT_CONTENT_EMAIL: String = "contact@email.com"
        private const val INPUT_CONTENT_EMAIL_PREFIXED: String = "contact@email.co"
        private const val INPUT_CONTENT_EMAIL_SUFFIXED: String = ".uk"

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

    private lateinit var emailInputTestUtil: EmailInputTestUtil

    @Before
    fun setUpContexts() {

        // See field's KDoc for more info.
        mTargetContext = InstrumentationRegistry.getInstrumentation().targetContext
//        mTargetContext = ApplicationProvider.getApplicationContext<Context>() // Haven't tested but might work.
        // See field's KDoc for more info.
        mContext = InstrumentationRegistry.getInstrumentation().context

        emailInputTestUtil = EmailInputTestUtil(
            mContext = mContext,
            mTargetContext = mTargetContext,
            composeTestRule = composeTestRule
        )

    }

    @Test
    fun input_Label_Displayed() {

        emailInputTestUtil
            .setContentAsEmailInputAndAssertItIsDisplayed(
                email = INPUT_CONTENT_EMAIL_EMPTY
            )

    }

    @Test
    fun input_Content_Displayed() {

        emailInputTestUtil
            .setContentAsEmailInputAndAssertItIsDisplayed(
                email = INPUT_CONTENT_EMAIL
            )

        emailInputTestUtil
            .onNodeWithEmailInputAndTextContactAtEmailDotCom()
            .assertIsDisplayed()

    }

    @Test
    fun input_Leading_Icon_Image_Vector_Displayed() {

        emailInputTestUtil
            .setContentAsEmailInputAndAssertItIsDisplayed(
                email = INPUT_CONTENT_EMAIL_EMPTY
            )

        emailInputTestUtil
            .onNodeWithEmailInputAndTextFieldLeadingIconImageVectorIconsDefaultEmail()
            .assertIsDisplayed()

    }

    @Test
    fun input_Leading_Icon_Content_Description_Exists() {

        emailInputTestUtil
            .setContentAsEmailInputAndAssertItIsDisplayed(
                email = INPUT_CONTENT_EMAIL_EMPTY
            )

        emailInputTestUtil
            .onNodeWithEmailInputAndTextFieldLeadingIconContentDescriptionEmailIcon()
            .assertIsDisplayed()

    }

    @Test
    fun input_Single_Line_Exists() {

        emailInputTestUtil
            .setContentAsEmailInputAndAssertItIsDisplayed(
                email = INPUT_CONTENT_EMAIL_EMPTY
            )

        emailInputTestUtil
            .onNodeWithEmailInputAndTextFieldSingleLineTrue()
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Keyboard_Type_Exists() {

        emailInputTestUtil
            .setContentAsEmailInputAndAssertItIsDisplayed(
                email = INPUT_CONTENT_EMAIL_EMPTY
            )

        emailInputTestUtil
            .onNodeWithEmailInputAndTextFieldKeyboardOptionsKeyboardTypeEmail()
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Ime_Action_Exists() {

        emailInputTestUtil
            .setContentAsEmailInputAndAssertItIsDisplayed(
                email = INPUT_CONTENT_EMAIL_EMPTY
            )

        emailInputTestUtil
            .onNodeWithEmailInputAndImeActionNext()
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Ime_Action_Exists_AnotherApproach() {

        emailInputTestUtil
            .setContentAsEmailInputAndAssertItIsDisplayed(
                email = INPUT_CONTENT_EMAIL_EMPTY
            )

        emailInputTestUtil
            .onNodeWithEmailInputAndTextFieldKeyboardOptionsImeActionNext()
            .assertIsDisplayed()

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun email_Changed_Callback_Triggered() {

        val emailAddress = INPUT_CONTENT_EMAIL_PREFIXED
        val appendedText = INPUT_CONTENT_EMAIL_SUFFIXED

        val onEmailChanged: (email: String) -> Unit = mock()

        emailInputTestUtil
            .setContentAsEmailInputAndAssertItIsDisplayed(
                email = emailAddress,
                onEmailChanged = onEmailChanged
            )

        emailInputTestUtil
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
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
    fun email_Changed_Callback_Triggered_WithoutMockito() {

        val emailAddress = INPUT_CONTENT_EMAIL_PREFIXED
        val appendedText = INPUT_CONTENT_EMAIL_SUFFIXED
        var emailAddressNew = ""

        var isClicked = FALSE

        val onEmailChanged: (email: String) -> Unit = { email ->
            emailAddressNew = email

            isClicked = TRUE
        }

        emailInputTestUtil
            .setContentAsEmailInputAndAssertItIsDisplayed(
                email = emailAddress,
                onEmailChanged = onEmailChanged
            )

        emailInputTestUtil
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
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

        val onNextClicked: () -> Unit = mock()

        emailInputTestUtil
            .setContentAsEmailInputAndAssertItIsDisplayed(
                email = INPUT_CONTENT_EMAIL_EMPTY,
                onNextClicked = onNextClicked
            )

        emailInputTestUtil
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
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
    fun next_Clicked_Callback_Triggered_WithoutMockito() {

        var isClicked = FALSE

        emailInputTestUtil
            .setContentAsEmailInputAndAssertItIsDisplayed(
                email = INPUT_CONTENT_EMAIL_EMPTY,
                onNextClicked = {
                    isClicked = TRUE
                }
            )

        emailInputTestUtil
            .onNodeWithEmailInputAndTextExactlyEmailAddress()
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