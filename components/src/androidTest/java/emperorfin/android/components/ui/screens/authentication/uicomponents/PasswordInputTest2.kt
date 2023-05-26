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
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasTextFieldKeyboardOptionsImeAction
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasTextFieldKeyboardOptionsKeyboardType
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasTextFieldLeadingIconContentDescription
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasTextFieldLeadingIconImageVector
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasTextFieldSingleLine
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasTextFieldTrailingIconClickLabel
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasTextFieldTrailingIconContentDescription
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasTextFieldTrailingIconImageVector
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasTextFieldVisualTransformation
import emperorfin.android.components.test.R
import emperorfin.android.components.ui.extensions.semanticsmatcher.*
import emperorfin.android.components.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.components.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON_PASSWORD_HIDDEN
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 26th January, 2023.
 */


/**
 * The following classes are revisions of this class:
 * [PasswordInputTest3]
 * [PasswordInputTest4]
 * [PasswordInputTest5]
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
class PasswordInputTest2 {

    private companion object {

        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        private val NULL = null

        private const val THIS_STRING_MUST_BE_EMPTY: String = ""

        private const val INPUT_CONTENT_PASSWORD_EMPTY: String = ""
        private const val INPUT_CONTENT_PASSWORD: String = "passworD1"
        private const val INPUT_CONTENT_PREFIXED: String = "passworD"
        private const val INPUT_CONTENT_SUFFIXED: String = "12345"

        @StringRes
        private val STRING_RES_PASSWORD: Int = R.string.label_password
        @StringRes
        private val STRING_RES_PASSWORD_SHOW: Int = R.string.content_description_show_password
        @StringRes
        private val STRING_RES_PASSWORD_HIDE: Int = R.string.content_description_hide_password
        @StringRes
        private val STRING_RES_PASSWORD_INPUT_LEADING_ICON: Int = R.string.content_description_password_input_leading_icon
        @StringRes
        private val STRING_RES_TRAILING_ICON_INDICATES_PASSWORD_SHOWN: Int =
            R.string.content_description_password_input_trailing_icon_password_shown
        @StringRes
        private val STRING_RES_TRAILING_ICON_INDICATES_PASSWORD_HIDDEN: Int =
            R.string.content_description_password_input_trailing_icon_password_hidden

        private val IMAGE_VECTOR_ICONS_DEFAULT_LOCK: ImageVector = Icons.Default.Lock
        private val IMAGE_VECTOR_ICONS_DEFAULT_VISIBILITY: ImageVector = Icons.Default.Visibility
        private val IMAGE_VECTOR_ICONS_DEFAULT_VISIBILITY_OFF: ImageVector = Icons.Default.VisibilityOff

        private val VISUAL_TRANSFORMATION_NONE: VisualTransformation = VisualTransformation.None
        private val VISUAL_TRANSFORMATION_PASSWORD: VisualTransformation = PasswordVisualTransformation()

        private const val SINGLE_LINE_TRUE: Boolean = TRUE

        private val KEYBOARD_TYPE_PASSWORD: KeyboardType = KeyboardType.Password

        private val IME_ACTION_DONE: ImeAction = ImeAction.Done

    }

    /**
     * Use this when resources are coming from the main source set, whether directly
     * (e.g. R.string.sample_text) or indirectly (e.g. [PasswordRequirement.EIGHT_CHARACTERS.label]
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

    private fun setContentAsPasswordInputAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        passwordVisualTransformation: VisualTransformation = VISUAL_TRANSFORMATION_PASSWORD,
        password: String? = NULL,
        onPasswordChanged: (password: String) -> Unit = { },
        onDoneClicked: () -> Unit = { }
    ) {

        setContentAsPasswordInput(
            passwordVisualTransformation = passwordVisualTransformation,
            composeTestRule = composeTestRule,
            password = password,
            onPasswordChanged = onPasswordChanged,
            onDoneClicked= onDoneClicked
        )

        assertPasswordInputAndTextExactlyPasswordIsDisplayed()

    }

    private fun setContentAsPasswordInput(
        composeTestRule: ComposeContentTestRule,
        passwordVisualTransformation: VisualTransformation,
        password: String?,
        onPasswordChanged: (password: String) -> Unit,
        onDoneClicked: () -> Unit
    ) {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordInput(
                    passwordVisualTransformation = passwordVisualTransformation,
                    password = password,
                    onPasswordChanged = onPasswordChanged,
                    onDoneClicked = onDoneClicked
                )
            }
        }

    }

    /**
     * This should be called in all test cases immediately after composing the [PasswordInput]
     * composable in the [ComposeContentTestRule.setContent]
     */
    private fun assertPasswordInputAndTextExactlyPasswordIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithPasswordInputAndTextExactlyPassword()
            .assertIsDisplayed()

    }

    private fun onNodeWithPasswordInputTrailingIconAndContentDescriptionExactlyTrailingIconIndicatesPasswordHidden(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputTrailingIconAnd(
            otherMatcher = hasContentDescriptionExactlyTrailingIconIndicatesPasswordHidden(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputTrailingIconAndContentDescriptionExactlyTrailingIconIndicatesPasswordShown(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputTrailingIconAnd(
            otherMatcher = hasContentDescriptionExactlyTrailingIconIndicatesPasswordShown(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputAndTextPassworD1(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            // Test will fail to assert for EditableText. So use hasText() to assert for
            // EditableText (i.e. TextField value)
//            otherMatcher = hasTextExactly(
//                INPUT_CONTENT_PASSWORD
//            )
            otherMatcher = hasTextPassworD1(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputAndTextFieldLeadingIconImageVectorIconsDefaultLock(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldLeadingIconImageVectorIconsDefaultLock(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputAndTextFieldLeadingIconContentDescriptionPasswordInputLeadingIcon(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldLeadingIconContentDescriptionPasswordInputLeadingIcon(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelShowPassword(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconClickLabelShowPassword(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelHidePassword(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconClickLabelHidePassword(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputAndTextFieldTrailingIconImageVectorIconsDefaultVisibility(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconImageVectorIconsDefaultVisibility(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputAndTextFieldTrailingIconImageVectorIconsDefaultVisibilityOff(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconImageVectorIconsDefaultVisibilityOff(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordShown(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordShown(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordHidden(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordHidden(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputAndTextFieldVisualTransformationNone(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldVisualTransformationNone(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputAndTextFieldVisualTransformationPassword(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldVisualTransformationPassword(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputAndTextFieldSingleLineTrue(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldSingleLineTrue(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputAndTextFieldKeyboardOptionsKeyboardTypePassword(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldKeyboardOptionsKeyboardTypePassword(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputAndTextFieldKeyboardOptionsImeActionDone(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldKeyboardOptionsImeActionDone(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputAndTextExactlyPassword(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextExactlyPassword(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputTrailingIconAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagPasswordInputTrailingIcon().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordInputAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagPasswordInput().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun hasTestTagPasswordInputTrailingIcon(): SemanticsMatcher {

        return hasTestTagsPasswordInputTrailingIconAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagPasswordInput(): SemanticsMatcher {

        return hasTestTagsPasswordInputAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagsPasswordInputTrailingIconAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON + otherTestTag
        )

    }

    private fun hasTestTagsPasswordInputAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_INPUT_PASSWORD + otherTestTag
        )

    }

    // Before using a semantics matcher, check the implementation of the utility functions in this
    // section if it's already available to avoid duplication.
    // The function names make the check easier.

    private fun hasTextExactlyPassword(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_PASSWORD),
            includeEditableText = FALSE
        )

    }

    private fun hasTextPassworD1(): SemanticsMatcher {

        return hasText(
            text = INPUT_CONTENT_PASSWORD,
            substring = FALSE,
            ignoreCase = FALSE
        )

    }

    private fun hasTextFieldLeadingIconImageVectorIconsDefaultLock(): SemanticsMatcher {

        return hasTextFieldLeadingIconImageVector(
            leadingIconImageVector = IMAGE_VECTOR_ICONS_DEFAULT_LOCK
        )

    }

    private fun hasTextFieldLeadingIconContentDescriptionPasswordInputLeadingIcon(): SemanticsMatcher {

        return hasTextFieldLeadingIconContentDescription(
            leadingIconContentDescription = mContext.getString(
                STRING_RES_PASSWORD_INPUT_LEADING_ICON
            )
        )

    }

    private fun hasTextFieldTrailingIconClickLabelShowPassword(): SemanticsMatcher {

        return hasTextFieldTrailingIconClickLabel(
            trailingIconClickLabel = mContext.getString(
                STRING_RES_PASSWORD_SHOW
            )
        )

    }

    private fun hasTextFieldTrailingIconClickLabelHidePassword(): SemanticsMatcher {

        return hasTextFieldTrailingIconClickLabel(
            trailingIconClickLabel = mContext.getString(
                STRING_RES_PASSWORD_HIDE
            )
        )

    }

    private fun hasContentDescriptionExactlyTrailingIconIndicatesPasswordShown(): SemanticsMatcher {

        // This semantics matcher is from the Compose test library.
        return hasContentDescriptionExactly(
            mContext.getString(
                STRING_RES_TRAILING_ICON_INDICATES_PASSWORD_SHOWN
            )
        )

    }

    private fun hasContentDescriptionExactlyTrailingIconIndicatesPasswordHidden(): SemanticsMatcher {

        // This semantics matcher is from the Compose test library.
        return hasContentDescriptionExactly(
            mContext.getString(
                STRING_RES_TRAILING_ICON_INDICATES_PASSWORD_HIDDEN
            )
        )

    }

    private fun hasTextFieldKeyboardOptionsImeActionDone(): SemanticsMatcher {

        return hasTextFieldKeyboardOptionsImeAction(
            keyboardOptionsImeAction = IME_ACTION_DONE
        )

    }

    private fun hasTextFieldKeyboardOptionsKeyboardTypePassword(): SemanticsMatcher {

        return hasTextFieldKeyboardOptionsKeyboardType(
            keyboardOptionsKeyboardType = KEYBOARD_TYPE_PASSWORD
        )

    }

    private fun hasTextFieldSingleLineTrue(): SemanticsMatcher {

        return hasTextFieldSingleLine(
            singleLine = SINGLE_LINE_TRUE
        )

    }

    private fun hasTextFieldVisualTransformationPassword(): SemanticsMatcher {

        return hasTextFieldVisualTransformation(
            visualTransformation = VISUAL_TRANSFORMATION_PASSWORD
        )

    }

    private fun hasTextFieldVisualTransformationNone(): SemanticsMatcher {

        return hasTextFieldVisualTransformation(
            visualTransformation = VISUAL_TRANSFORMATION_NONE
        )

    }

    private fun hasTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordShown():
            SemanticsMatcher {

        return hasTextFieldTrailingIconContentDescription(
            trailingIconContentDescription = mContext.getString(
                STRING_RES_TRAILING_ICON_INDICATES_PASSWORD_SHOWN
            )
        )

    }

    private fun hasTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordHidden():
            SemanticsMatcher {

        return hasTextFieldTrailingIconContentDescription(
            trailingIconContentDescription = mContext.getString(
                STRING_RES_TRAILING_ICON_INDICATES_PASSWORD_HIDDEN
            )
        )

    }

    private fun hasTextFieldTrailingIconImageVectorIconsDefaultVisibility(): SemanticsMatcher {

        return hasTextFieldTrailingIconImageVector(
            trailingIconImageVector = IMAGE_VECTOR_ICONS_DEFAULT_VISIBILITY
        )

    }

    private fun hasTextFieldTrailingIconImageVectorIconsDefaultVisibilityOff(): SemanticsMatcher {

        return hasTextFieldTrailingIconImageVector(
            trailingIconImageVector = IMAGE_VECTOR_ICONS_DEFAULT_VISIBILITY_OFF
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

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

    }

    @Test
    fun input_Content_Displayed() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            passwordVisualTransformation = VISUAL_TRANSFORMATION_NONE,
            password = INPUT_CONTENT_PASSWORD
        )

        onNodeWithPasswordInputAndTextPassworD1()
            .assertIsDisplayed()

    }

    @Test
    fun input_Leading_Icon_Image_Vector_Displayed() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputAndTextFieldLeadingIconImageVectorIconsDefaultLock()
            .assertIsDisplayed()

    }

    @Test
    fun input_Leading_Icon_Content_Description_Exists() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputAndTextFieldLeadingIconContentDescriptionPasswordInputLeadingIcon()
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Click_Label_To_Show_Password_Exists_By_Default() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelShowPassword()
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Click_Label_Changed_To_Hide_Password_When_Trailing_Icon_Clicked() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        // Optional
        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

        // Optional
        onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelShowPassword()
            .assertIsDisplayed()

        // Optional
        onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelHidePassword()
            .assertDoesNotExist()

        onNodeWithPasswordInputTrailingIconAndContentDescriptionExactlyTrailingIconIndicatesPasswordHidden()
            .performClick()

        // Optional
        onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelShowPassword()
            .assertDoesNotExist()

        onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelHidePassword()
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Image_Vector_To_Show_Password_Displayed_By_Default() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputAndTextFieldTrailingIconImageVectorIconsDefaultVisibility()
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Image_Vector_Changed_To_Hide_Password_When_Trailing_Icon_Clicked() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        // Optional
        onNodeWithPasswordInputAndTextExactlyPassword()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

        // Optional
        onNodeWithPasswordInputAndTextFieldTrailingIconImageVectorIconsDefaultVisibility()
            .assertIsDisplayed()

        // Optional
        onNodeWithPasswordInputAndTextFieldTrailingIconImageVectorIconsDefaultVisibilityOff()
            .assertDoesNotExist()

        onNodeWithPasswordInputTrailingIconAndContentDescriptionExactlyTrailingIconIndicatesPasswordHidden()
            .performClick()

        // Optional
        onNodeWithPasswordInputAndTextFieldTrailingIconImageVectorIconsDefaultVisibility()
            .assertDoesNotExist()

        onNodeWithPasswordInputAndTextFieldTrailingIconImageVectorIconsDefaultVisibilityOff()
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Content_Description_For_Hidden_Password_Exists_By_Default() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordHidden()
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Content_Description_For_Shown_Password_Exists_When_Trailing_Icon_Clicked() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        // Optional
        onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordHidden()
            .assertIsDisplayed()

        // Optional
        onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordShown()
            .assertDoesNotExist()

        onNodeWithPasswordInputTrailingIconAndContentDescriptionExactlyTrailingIconIndicatesPasswordHidden()
            .performClick()

        // Optional
        onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordHidden()
            .assertDoesNotExist()

        onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordShown()
            .assertIsDisplayed()

    }

    @Test
    fun input_Single_Line_Exists() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputAndTextFieldSingleLineTrue()
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Keyboard_Type_Exists() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputAndTextFieldKeyboardOptionsKeyboardTypePassword()
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Ime_Action_Exists() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputAndTextFieldKeyboardOptionsImeActionDone()
            .assertIsDisplayed()

    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun password_Changed_Callback_Triggered() {

        val password = INPUT_CONTENT_PREFIXED
        val appendedText = INPUT_CONTENT_SUFFIXED

        val onPasswordChanged: (password: String) -> Unit = mock()

        setContentAsPasswordInputAndAssertItIsDisplayed(
            // Optional since test doesn't fail when it should pass
            passwordVisualTransformation = VISUAL_TRANSFORMATION_NONE,
            password = password,
            onPasswordChanged = onPasswordChanged,
        )

        // Optional since test doesn't fail when it should pass
        onNodeWithPasswordInputAndTextExactlyPassword()
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

        setContentAsPasswordInputAndAssertItIsDisplayed(
            // Optional since test doesn't fail when it should pass
            passwordVisualTransformation = VISUAL_TRANSFORMATION_NONE,
            password = password,
            onPasswordChanged = onPasswordChanged,
        )

        onNodeWithPasswordInputAndTextExactlyPassword()
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

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY,
            onDoneClicked = onDoneClicked
        )

        onNodeWithPasswordInputAndTextExactlyPassword()
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

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY,
            onDoneClicked = {
                isClicked = TRUE
            }
        )

        onNodeWithPasswordInputAndTextExactlyPassword()
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

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputAndTextFieldVisualTransformationPassword()
            .assertIsDisplayed()

    }

    @Test
    fun input_Password_Visual_Transformation_Disabled_When_Trailing_Icon_Clicked() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputAndTextFieldVisualTransformationNone()
            .assertDoesNotExist()

        // Optional
        onNodeWithPasswordInputAndTextFieldVisualTransformationPassword()
            .apply {
                assertIsDisplayed()

                performTextInput(
                    text = INPUT_CONTENT_PASSWORD
                )
            }

        onNodeWithPasswordInputTrailingIconAndContentDescriptionExactlyTrailingIconIndicatesPasswordHidden()
            .performClick()

        // Optional
        onNodeWithPasswordInputAndTextFieldVisualTransformationPassword()
            .assertDoesNotExist()

        onNodeWithPasswordInputAndTextFieldVisualTransformationNone()
            .assertIsDisplayed()

    }

    @Test
    fun input_Toggling_Between_Password_Visual_Transformation_From_When_Enabled_To_Disabled_To_Enabled() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD
        )

        onNodeWithPasswordInputAndTextFieldVisualTransformationNone()
            .assertDoesNotExist()

        onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordShown()
            .assertDoesNotExist()

        onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelHidePassword()
            .assertDoesNotExist()

        onNodeWithPasswordInputAndTextFieldVisualTransformationPassword()
            .assertIsDisplayed()

        onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordHidden()
            .assertIsDisplayed()

        onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelShowPassword()
            .assertIsDisplayed()


        onNodeWithPasswordInputTrailingIconAndContentDescriptionExactlyTrailingIconIndicatesPasswordHidden()
            .performClick()


        onNodeWithPasswordInputAndTextFieldVisualTransformationPassword()
            .assertDoesNotExist()

        onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordHidden()
            .assertDoesNotExist()

        onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelShowPassword()
            .assertDoesNotExist()

        onNodeWithPasswordInputAndTextFieldVisualTransformationNone()
            .assertIsDisplayed()

        onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordShown()
            .assertIsDisplayed()

        onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelHidePassword()
            .assertIsDisplayed()


        onNodeWithPasswordInputTrailingIconAndContentDescriptionExactlyTrailingIconIndicatesPasswordShown()
            .performClick()


        onNodeWithPasswordInputAndTextFieldVisualTransformationNone()
            .assertDoesNotExist()

        onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordShown()
            .assertDoesNotExist()

        onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelHidePassword()
            .assertDoesNotExist()

        onNodeWithPasswordInputAndTextFieldVisualTransformationPassword()
            .assertIsDisplayed()

        onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordHidden()
            .assertIsDisplayed()

        onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelShowPassword()
            .assertIsDisplayed()

    }

    /**
     * For this test to pass, from the file app/src/main/java/emperorfin/android/components/ui/screens/authentication/uicomponents/PasswordInput.kt ,
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

        setContentAsPasswordInputAndAssertItIsDisplayed(
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