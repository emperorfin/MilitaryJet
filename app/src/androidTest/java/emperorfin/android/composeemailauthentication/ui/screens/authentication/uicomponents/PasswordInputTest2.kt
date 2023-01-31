package emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents

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
import emperorfin.android.composeemailauthentication.test.R
import emperorfin.android.composeemailauthentication.ui.extensions.semanticsmatcher.*
import emperorfin.android.composeemailauthentication.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.composeemailauthentication.ui.screens.authentication.stateholders.AuthenticationUiState
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON_PASSWORD_HIDDEN
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
 * This class is a clean version of [PasswordInputTest] class.
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
 * [emperorfin.android.composeemailauthentication.R] and not
 * [emperorfin.android.composeemailauthentication.test.R].
 * - Every other thing during testing that involves the use of a resource (e.g. a string resource)
 * such as performing matches or assertions, be sure to use the resource from the androidTest source
 * set (which you should've provided a copy and always in sync with the one from the main source set).
 * And the R must be the one from [emperorfin.android.composeemailauthentication.test.R] instead of
 * [emperorfin.android.composeemailauthentication.R].
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

        private const val INPUT_CONTENT_PASSWORD_EMPTY: String = ""
        private const val INPUT_CONTENT_PASSWORD: String = "passworD1"
        private const val INPUT_CONTENT_PREFIXED: String = "passworD"
        private const val INPUT_CONTENT_SUFFIXED: String = "12345"

    }

    /**
     * Use this when resources are coming from the main source set, whether directly
     * (e.g. R.string.sample_text) or indirectly (e.g. [PasswordRequirement.EIGHT_CHARACTERS.label]
     * which is directly using a string resource).
     *
     * To actually reference the resource, you use
     * [emperorfin.android.composeemailauthentication.R] and not
     * [emperorfin.android.composeemailauthentication.test.R]
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
     * [emperorfin.android.composeemailauthentication.test.R] and not
     * [emperorfin.android.composeemailauthentication.R]
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
    private fun assertPasswordInputIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithPasswordInput()
            .assertIsDisplayed()

    }

    private fun setContentAsPasswordInputAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        passwordVisualTransformation: VisualTransformation = PasswordVisualTransformation(),
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

        assertPasswordInputIsDisplayed()

    }

    private fun onNodeWithPasswordInput(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextExactly(
                mContext.getString(R.string.label_password),
                includeEditableText = FALSE
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
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD
                ).and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordInputTrailingIconWhenPasswordHidden(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
                ).and(
                    // This semantics matcher is from the Compose test library.
                    other = hasContentDescriptionExactly(
                        mContext.getString(
                            R.string.content_description_password_input_trailing_icon_password_hidden
                        )
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordInputTrailingIconWhenPasswordShown(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
                ).and(
                    // This semantics matcher is from the Compose test library.
                    other = hasContentDescriptionExactly(
                        mContext.getString(
                            R.string.content_description_password_input_trailing_icon_password_shown
                        )
                    )
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordInputTrailingIconClickLabelToShowPassword(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconClickLabel(
                trailingIconClickLabel = mContext.getString(
                    R.string.content_description_show_password
                )
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputTrailingIconClickLabelToHidePassword(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconClickLabel(
                trailingIconClickLabel = mContext.getString(
                    R.string.content_description_hide_password
                )
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputTrailingIconImageVectorToShowPassword(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconImageVector(
                trailingIconImageVector = Icons.Default.Visibility
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputTrailingIconImageVectorToHidePassword(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconImageVector(
                trailingIconImageVector = Icons.Default.VisibilityOff
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputTrailingIconContentDescriptionWhenPasswordShown(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconContentDescription(
                trailingIconContentDescription = mContext.getString(
                    R.string.content_description_password_input_trailing_icon_password_shown
                )
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputTrailingIconContentDescriptionWhenPasswordHidden(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconContentDescription(
                trailingIconContentDescription = mContext.getString(
                    R.string.content_description_password_input_trailing_icon_password_hidden
                )
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputWhenPasswordVisualTransformationDisabled(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldVisualTransformation(
                visualTransformation = VisualTransformation.None
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputWhenPasswordVisualTransformationEnabled(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldVisualTransformation(
                visualTransformation = PasswordVisualTransformation()
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputSingleLineTrue(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldSingleLine(
                singleLine = TRUE
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputKeyboardOptionsKeyboardTypePassword(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldKeyboardOptionsKeyboardType(
                keyboardOptionsKeyboardType = KeyboardType.Password
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordInputKeyboardOptionsImeActionDone(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldKeyboardOptionsImeAction(
                keyboardOptionsImeAction = ImeAction.Done
            ),
            useUnmergedTree = useUnmergedTree
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
            passwordVisualTransformation = VisualTransformation.None,
            password = INPUT_CONTENT_PASSWORD
        )

        onNodeWithPasswordInputAnd(
            // Test will fail to assert for EditableText. So use hasText() to assert for
            // EditableText (i.e. TextField value)
//            otherMatcher = hasTextExactly(
//                INPUT_CONTENT_PASSWORD
//            )
            otherMatcher = hasText(
                text = INPUT_CONTENT_PASSWORD,
                substring = FALSE,
                ignoreCase = FALSE
            )
        )
            .assertIsDisplayed()

    }

    @Test
    fun input_Leading_Icon_Image_Vector_Displayed() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldLeadingIconImageVector(
                leadingIconImageVector = Icons.Default.Lock
            )
        )
            .assertIsDisplayed()

    }

    @Test
    fun input_Leading_Icon_Content_Description_Exists() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldLeadingIconContentDescription(
                leadingIconContentDescription = mContext.getString(
                    R.string.content_description_password_input_leading_icon
                )
            )
        )
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Click_Label_To_Show_Password_Exists_By_Default() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputTrailingIconClickLabelToShowPassword()
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Click_Label_Changed_To_Hide_Password_When_Trailing_Icon_Clicked() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        // Optional
        onNodeWithPasswordInput()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

        // Optional
        onNodeWithPasswordInputTrailingIconClickLabelToShowPassword()
            .assertIsDisplayed()

        // Optional
        onNodeWithPasswordInputTrailingIconClickLabelToHidePassword()
            .assertDoesNotExist()

        onNodeWithPasswordInputTrailingIconWhenPasswordHidden()
            .performClick()

        // Optional
        onNodeWithPasswordInputTrailingIconClickLabelToShowPassword()
            .assertDoesNotExist()

        onNodeWithPasswordInputTrailingIconClickLabelToHidePassword()
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Image_Vector_To_Show_Password_Displayed_By_Default() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputTrailingIconImageVectorToShowPassword()
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Image_Vector_Changed_To_Hide_Password_When_Trailing_Icon_Clicked() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        // Optional
        onNodeWithPasswordInput()
            .performTextInput(
                text = INPUT_CONTENT_PASSWORD
            )

        // Optional
        onNodeWithPasswordInputTrailingIconImageVectorToShowPassword()
            .assertIsDisplayed()

        // Optional
        onNodeWithPasswordInputTrailingIconImageVectorToHidePassword()
            .assertDoesNotExist()

        onNodeWithPasswordInputTrailingIconWhenPasswordHidden()
            .performClick()

        // Optional
        onNodeWithPasswordInputTrailingIconImageVectorToShowPassword()
            .assertDoesNotExist()

        onNodeWithPasswordInputTrailingIconImageVectorToHidePassword()
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Content_Description_For_Hidden_Password_Exists_By_Default() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputTrailingIconContentDescriptionWhenPasswordHidden()
            .assertIsDisplayed()

    }

    @Test
    fun input_Trailing_Icon_Content_Description_For_Shown_Password_Exists_When_Trailing_Icon_Clicked() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        // Optional
        onNodeWithPasswordInputTrailingIconContentDescriptionWhenPasswordHidden()
            .assertIsDisplayed()

        // Optional
        onNodeWithPasswordInputTrailingIconContentDescriptionWhenPasswordShown()
            .assertDoesNotExist()

        onNodeWithPasswordInputTrailingIconWhenPasswordHidden()
            .performClick()

        // Optional
        onNodeWithPasswordInputTrailingIconContentDescriptionWhenPasswordHidden()
            .assertDoesNotExist()

        onNodeWithPasswordInputTrailingIconContentDescriptionWhenPasswordShown()
            .assertIsDisplayed()

    }

    @Test
    fun input_Single_Line_Exists() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputSingleLineTrue()
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Keyboard_Type_Exists() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputKeyboardOptionsKeyboardTypePassword()
            .assertIsDisplayed()

    }

    @Test
    fun input_Keyboard_Options_Ime_Action_Exists() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputKeyboardOptionsImeActionDone()
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
            passwordVisualTransformation = VisualTransformation.None,
            password = password,
            onPasswordChanged = onPasswordChanged,
        )

        // Optional since test doesn't fail when it should pass
        onNodeWithPasswordInput()
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
            passwordVisualTransformation = VisualTransformation.None,
            password = password,
            onPasswordChanged = onPasswordChanged,
        )

        onNodeWithPasswordInput()
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

        onNodeWithPasswordInput()
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

        onNodeWithPasswordInput()
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

        onNodeWithPasswordInputWhenPasswordVisualTransformationEnabled()
            .assertIsDisplayed()

    }

    @Test
    fun input_Password_Visual_Transformation_Disabled_When_Trailing_Icon_Clicked() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD_EMPTY
        )

        onNodeWithPasswordInputWhenPasswordVisualTransformationDisabled()
            .assertDoesNotExist()

        // Optional
        onNodeWithPasswordInputWhenPasswordVisualTransformationEnabled()
            .apply {
                assertIsDisplayed()

                performTextInput(
                    text = INPUT_CONTENT_PASSWORD
                )
            }

        onNodeWithPasswordInputTrailingIconWhenPasswordHidden()
            .performClick()

        // Optional
        onNodeWithPasswordInputWhenPasswordVisualTransformationEnabled()
            .assertDoesNotExist()

        onNodeWithPasswordInputWhenPasswordVisualTransformationDisabled()
            .assertIsDisplayed()

    }

    @Test
    fun input_Toggling_Between_Password_Visual_Transformation_From_When_Enabled_To_Disabled_To_Enabled() {

        setContentAsPasswordInputAndAssertItIsDisplayed(
            password = INPUT_CONTENT_PASSWORD
        )

        onNodeWithPasswordInputWhenPasswordVisualTransformationDisabled()
            .assertDoesNotExist()

        onNodeWithPasswordInputTrailingIconContentDescriptionWhenPasswordShown()
            .assertDoesNotExist()

        onNodeWithPasswordInputTrailingIconClickLabelToHidePassword()
            .assertDoesNotExist()

        onNodeWithPasswordInputWhenPasswordVisualTransformationEnabled()
            .assertIsDisplayed()

        onNodeWithPasswordInputTrailingIconContentDescriptionWhenPasswordHidden()
            .assertIsDisplayed()

        onNodeWithPasswordInputTrailingIconClickLabelToShowPassword()
            .assertIsDisplayed()


        onNodeWithPasswordInputTrailingIconWhenPasswordHidden()
            .performClick()


        onNodeWithPasswordInputWhenPasswordVisualTransformationEnabled()
            .assertDoesNotExist()

        onNodeWithPasswordInputTrailingIconContentDescriptionWhenPasswordHidden()
            .assertDoesNotExist()

        onNodeWithPasswordInputTrailingIconClickLabelToShowPassword()
            .assertDoesNotExist()

        onNodeWithPasswordInputWhenPasswordVisualTransformationDisabled()
            .assertIsDisplayed()

        onNodeWithPasswordInputTrailingIconContentDescriptionWhenPasswordShown()
            .assertIsDisplayed()

        onNodeWithPasswordInputTrailingIconClickLabelToHidePassword()
            .assertIsDisplayed()


        onNodeWithPasswordInputTrailingIconWhenPasswordShown()
            .performClick()


        onNodeWithPasswordInputWhenPasswordVisualTransformationDisabled()
            .assertDoesNotExist()

        onNodeWithPasswordInputTrailingIconContentDescriptionWhenPasswordShown()
            .assertDoesNotExist()

        onNodeWithPasswordInputTrailingIconClickLabelToHidePassword()
            .assertDoesNotExist()

        onNodeWithPasswordInputWhenPasswordVisualTransformationEnabled()
            .assertIsDisplayed()

        onNodeWithPasswordInputTrailingIconContentDescriptionWhenPasswordHidden()
            .assertIsDisplayed()

        onNodeWithPasswordInputTrailingIconClickLabelToShowPassword()
            .assertIsDisplayed()

    }

    /**
     * For this test to pass, from the file app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/PasswordInput.kt ,
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