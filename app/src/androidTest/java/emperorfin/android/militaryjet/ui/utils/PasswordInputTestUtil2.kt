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

package emperorfin.android.militaryjet.ui.utils

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.text.input.VisualTransformation
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.*
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.PasswordInput
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.FALSE
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.TRUE
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.SINGLE_LINE_TRUE
import emperorfin.android.militaryjet.ui.constants.NothingConstants.NULL
import emperorfin.android.militaryjet.ui.constants.StringConstants.THIS_STRING_MUST_BE_EMPTY
import emperorfin.android.militaryjet.ui.constants.StringConstants.INPUT_CONTENT_PASSWORD
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_PASSWORD
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_PASSWORD_SHOW
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_PASSWORD_HIDE
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_PASSWORD_INPUT_LEADING_ICON
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_TRAILING_ICON_INDICATES_PASSWORD_SHOWN
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_TRAILING_ICON_INDICATES_PASSWORD_HIDDEN
import emperorfin.android.militaryjet.ui.constants.ImageVectorConstants.IMAGE_VECTOR_ICONS_DEFAULT_LOCK
import emperorfin.android.militaryjet.ui.constants.ImageVectorConstants.IMAGE_VECTOR_ICONS_DEFAULT_VISIBILITY
import emperorfin.android.militaryjet.ui.constants.ImageVectorConstants.IMAGE_VECTOR_ICONS_DEFAULT_VISIBILITY_OFF
import emperorfin.android.militaryjet.ui.constants.VisualTransformationConstants.VISUAL_TRANSFORMATION_NONE
import emperorfin.android.militaryjet.ui.constants.VisualTransformationConstants.VISUAL_TRANSFORMATION_PASSWORD
import emperorfin.android.militaryjet.ui.constants.KeyboardTypeConstants.KEYBOARD_TYPE_PASSWORD
import emperorfin.android.militaryjet.ui.constants.ImeActionConstants.IME_ACTION_DONE


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 31st March, 2023.
 */


class PasswordInputTestUtil2(
    private val mContext: Context,
    private val mTargetContext: Context,
    private val composeTestRule: ComposeContentTestRule
) {

    fun setContentAsPasswordInputAndAssertItIsDisplayed(
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

    fun onNodeWithPasswordInputTrailingIconAndContentDescriptionExactlyTrailingIconIndicatesPasswordHidden(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputTrailingIconAnd(
            otherMatcher = hasContentDescriptionExactlyTrailingIconIndicatesPasswordHidden(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordInputTrailingIconAndContentDescriptionExactlyTrailingIconIndicatesPasswordShown(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputTrailingIconAnd(
            otherMatcher = hasContentDescriptionExactlyTrailingIconIndicatesPasswordShown(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordInputAndTextPassworD1(
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

    fun onNodeWithPasswordInputAndTextFieldLeadingIconImageVectorIconsDefaultLock(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldLeadingIconImageVectorIconsDefaultLock(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordInputAndTextFieldLeadingIconContentDescriptionPasswordInputLeadingIcon(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldLeadingIconContentDescriptionPasswordInputLeadingIcon(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelShowPassword(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconClickLabelShowPassword(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordInputAndTextFieldTrailingIconClickLabelHidePassword(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconClickLabelHidePassword(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordInputAndTextFieldTrailingIconImageVectorIconsDefaultVisibility(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconImageVectorIconsDefaultVisibility(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordInputAndTextFieldTrailingIconImageVectorIconsDefaultVisibilityOff(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconImageVectorIconsDefaultVisibilityOff(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordShown(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordShown(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordInputAndTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordHidden(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldTrailingIconContentDescriptionTrailingIconIndicatesPasswordHidden(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordInputAndTextFieldVisualTransformationNone(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldVisualTransformationNone(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordInputAndTextFieldVisualTransformationPassword(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldVisualTransformationPassword(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordInputAndTextFieldSingleLineTrue(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldSingleLineTrue(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordInputAndTextFieldKeyboardOptionsKeyboardTypePassword(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldKeyboardOptionsKeyboardTypePassword(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordInputAndTextFieldKeyboardOptionsImeActionDone(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordInputAnd(
            otherMatcher = hasTextFieldKeyboardOptionsImeActionDone(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordInputAndTextExactlyPassword(
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

}