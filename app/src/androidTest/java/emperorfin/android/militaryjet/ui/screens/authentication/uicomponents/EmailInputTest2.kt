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
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.ui.graphics.vector.ImageVector
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
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_EMAIL
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 23rd January, 2023.
 */


/**
 * The following classes are revisions of this class:
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
class EmailInputTest2 {

    private companion object {

        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        private val NULL = null

        private const val THIS_STRING_MUST_BE_EMPTY: String = ""

        private const val INPUT_CONTENT_EMAIL_EMPTY: String = ""
        private const val INPUT_CONTENT_EMAIL: String = "contact@email.com"
        private const val INPUT_CONTENT_EMAIL_PREFIXED: String = "contact@email.co"
        private const val INPUT_CONTENT_EMAIL_SUFFIXED: String = ".uk"

        @StringRes
        private const val STRING_RES_EMAIL_ADDRESS: Int = R.string.label_email

        private val IMAGE_VECTOR_ICONS_DEFAULT_EMAIL: ImageVector = Icons.Default.Email

        private val KEYBOARD_TYPE_EMAIL: KeyboardType = KeyboardType.Email

        private val IME_ACTION_NEXT: ImeAction = ImeAction.Next

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

    private fun setContentAsEmailInputAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        email: String? = NULL,
        onEmailChanged: (email: String) -> Unit = { },
        onNextClicked: () -> Unit = { }
    ) {

        setContentAsEmailInput(
            composeTestRule = composeTestRule,
            email = email,
            onEmailChanged = onEmailChanged,
            onNextClicked= onNextClicked
        )

        assertEmailInputAndTextExactlyEmailAddressIsDisplayed()

    }

    private fun setContentAsEmailInput(
        composeTestRule: ComposeContentTestRule,
        email: String?,
        onEmailChanged: (email: String) -> Unit,
        onNextClicked: () -> Unit
    ) {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                EmailInput(
                    email = email,
                    onEmailChanged = onEmailChanged,
                    onNextClicked = onNextClicked
                )
            }
        }

    }

    /**
     * This should be called in all test cases immediately after composing the [EmailInput]
     * composable in the [ComposeContentTestRule.setContent]
     */
    private fun assertEmailInputAndTextExactlyEmailAddressIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithEmailInputAndTextExactlyEmailAddress()
            .assertIsDisplayed()

    }

    private fun onNodeWithEmailInputAndTextExactlyEmailAddress(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        // Works
//        return onNodeWithEmailInputAnd(
//            otherMatcher = hasText(
//                mContext.getString(STRING_RES_EMAIL_ADDRESS)
//            ),
//            useUnmergedTree = useUnmergedTree
//        )

        // Recommended
        return onNodeWithEmailInputAnd(
            otherMatcher = hasTextExactlyEmailAddress(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithEmailInputAndTextContactAtEmailDotCom(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithEmailInputAnd(
            otherMatcher = hasTextContactAtEmailDotCom(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithEmailInputAndTextFieldLeadingIconImageVectorIconsDefaultEmail(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithEmailInputAnd(
            otherMatcher = hasTextFieldLeadingIconImageVectorIconsDefaultEmail(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithEmailInputAndTextFieldLeadingIconContentDescriptionEmailIcon(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithEmailInputAnd(
            otherMatcher = hasTextFieldLeadingIconContentDescriptionEmailIcon(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithEmailInputAndTextFieldSingleLineTrue(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithEmailInputAnd(
            otherMatcher = hasTextFieldSingleLineTrue(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithEmailInputAndTextFieldKeyboardOptionsKeyboardTypeEmail(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithEmailInputAnd(
            otherMatcher = hasTextFieldKeyboardOptionsKeyboardTypeEmail(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithEmailInputAndImeActionNext(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithEmailInputAnd(
            otherMatcher = hasImeActionNext(),
            useUnmergedTree = useUnmergedTree
        )

    }

    // ------- FOR ..._AnotherApproach()

    private fun onNodeWithEmailInputAndTextFieldKeyboardOptionsImeActionNext(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithEmailInputAnd(
            otherMatcher = hasTextFieldKeyboardOptionsImeActionNext(),
            useUnmergedTree = useUnmergedTree
        )

    }

    // ------- /FOR ..._AnotherApproach()

    private fun onNodeWithEmailInputAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagEmailInput().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun hasTestTagEmailInput(): SemanticsMatcher {

        return hasTestTagsEmailInputAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagsEmailInputAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_INPUT_EMAIL + otherTestTag
        )

    }

    // Before using a semantics matcher, check the implementation of the utility functions in this
    // section if it's already available to avoid duplication.
    // The function names make the check easier.

    private fun hasTextExactlyEmailAddress(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_EMAIL_ADDRESS),
            includeEditableText = FALSE
        )

    }

    private fun hasTextContactAtEmailDotCom(): SemanticsMatcher {

        return hasText(
            text = INPUT_CONTENT_EMAIL
        )

    }

    private fun hasTextFieldLeadingIconImageVectorIconsDefaultEmail(): SemanticsMatcher {

        return hasTextFieldLeadingIconImageVector(
            leadingIconImageVector = IMAGE_VECTOR_ICONS_DEFAULT_EMAIL
        )

    }

    private fun hasTextFieldLeadingIconContentDescriptionEmailIcon(): SemanticsMatcher {

        return hasTextFieldLeadingIconContentDescription(
            leadingIconContentDescription = mContext.getString(
                R.string.content_description_email_input_leading_icon
            )
        )

    }

    private fun hasTextFieldSingleLineTrue(): SemanticsMatcher {

        return hasTextFieldSingleLine(
            singleLine = TRUE
        )

    }

    private fun hasTextFieldKeyboardOptionsKeyboardTypeEmail(): SemanticsMatcher {

        return hasTextFieldKeyboardOptionsKeyboardType(
            keyboardOptionsKeyboardType = KEYBOARD_TYPE_EMAIL
        )

    }

    private fun hasImeActionNext(): SemanticsMatcher {

        return hasImeAction(
            actionType = IME_ACTION_NEXT
        )

    }

    private fun hasTextFieldKeyboardOptionsImeActionNext(): SemanticsMatcher {

        return hasTextFieldKeyboardOptionsImeAction(
            keyboardOptionsImeAction = IME_ACTION_NEXT
        )

    }

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

        setContentAsEmailInputAndAssertItIsDisplayed(
            email = INPUT_CONTENT_EMAIL_EMPTY
        )

    }

    @Test
    fun input_Content_Displayed() {

        setContentAsEmailInputAndAssertItIsDisplayed(
            email = INPUT_CONTENT_EMAIL
        )

        onNodeWithEmailInputAndTextContactAtEmailDotCom()
            .assertIsDisplayed()
        
    }

    @Test
    fun input_Leading_Icon_Image_Vector_Displayed() {

        setContentAsEmailInputAndAssertItIsDisplayed(
            email = INPUT_CONTENT_EMAIL_EMPTY
        )

        onNodeWithEmailInputAndTextFieldLeadingIconImageVectorIconsDefaultEmail()
            .assertIsDisplayed()

    }

    @Test
    fun input_Leading_Icon_Content_Description_Exists() {

        setContentAsEmailInputAndAssertItIsDisplayed(
            email = INPUT_CONTENT_EMAIL_EMPTY
        )

        onNodeWithEmailInputAndTextFieldLeadingIconContentDescriptionEmailIcon()
            .assertIsDisplayed()

    }

    @Test
    fun input_Single_Line_Exists() {

        setContentAsEmailInputAndAssertItIsDisplayed(
            email = INPUT_CONTENT_EMAIL_EMPTY
        )

        onNodeWithEmailInputAndTextFieldSingleLineTrue()
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Keyboard_Type_Exists() {

        setContentAsEmailInputAndAssertItIsDisplayed(
            email = INPUT_CONTENT_EMAIL_EMPTY
        )

        onNodeWithEmailInputAndTextFieldKeyboardOptionsKeyboardTypeEmail()
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Ime_Action_Exists() {

        setContentAsEmailInputAndAssertItIsDisplayed(
            email = INPUT_CONTENT_EMAIL_EMPTY
        )

        onNodeWithEmailInputAndImeActionNext()
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Ime_Action_Exists_AnotherApproach() {

        setContentAsEmailInputAndAssertItIsDisplayed(
            email = INPUT_CONTENT_EMAIL_EMPTY
        )

        onNodeWithEmailInputAndTextFieldKeyboardOptionsImeActionNext()
            .assertIsDisplayed()

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun email_Changed_Callback_Triggered() {

        val emailAddress = INPUT_CONTENT_EMAIL_PREFIXED
        val appendedText = INPUT_CONTENT_EMAIL_SUFFIXED

        val onEmailChanged: (email: String) -> Unit = mock()

        setContentAsEmailInputAndAssertItIsDisplayed(
            email = emailAddress,
            onEmailChanged = onEmailChanged
        )

        onNodeWithEmailInputAndTextExactlyEmailAddress()
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

        setContentAsEmailInputAndAssertItIsDisplayed(
            email = emailAddress,
            onEmailChanged = onEmailChanged
        )

        onNodeWithEmailInputAndTextExactlyEmailAddress()
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

        setContentAsEmailInputAndAssertItIsDisplayed(
            email = INPUT_CONTENT_EMAIL_EMPTY,
            onNextClicked = onNextClicked
        )

        onNodeWithEmailInputAndTextExactlyEmailAddress()
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

        setContentAsEmailInputAndAssertItIsDisplayed(
            email = INPUT_CONTENT_EMAIL_EMPTY,
            onNextClicked = {
                isClicked = TRUE
            }
        )

        onNodeWithEmailInputAndTextExactlyEmailAddress()
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