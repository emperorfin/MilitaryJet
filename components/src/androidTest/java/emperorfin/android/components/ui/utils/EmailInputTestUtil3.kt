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

package emperorfin.android.components.ui.utils

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import emperorfin.android.components.test.R
import emperorfin.android.components.ui.extensions.semanticsmatcher.*
import emperorfin.android.components.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.components.ui.screens.authentication.uicomponents.EmailInput
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_EMAIL
import emperorfin.android.components.ui.constants.BooleanConstants.FALSE
import emperorfin.android.components.ui.constants.BooleanConstants.TRUE
import emperorfin.android.components.ui.constants.NothingConstants.NULL
import emperorfin.android.components.ui.constants.StringConstants.THIS_STRING_MUST_BE_EMPTY
import emperorfin.android.components.ui.constants.StringConstants.INPUT_CONTENT_EMAIL
import emperorfin.android.components.ui.constants.StringResourceConstants.STRING_RES_EMAIL_ADDRESS
import emperorfin.android.components.ui.constants.ImageVectorConstants.IMAGE_VECTOR_ICONS_DEFAULT_EMAIL
import emperorfin.android.components.ui.constants.KeyboardTypeConstants.KEYBOARD_TYPE_EMAIL
import emperorfin.android.components.ui.constants.ImeActionConstants.IME_ACTION_NEXT
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasTextFieldKeyboardOptionsImeAction
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasTextFieldKeyboardOptionsKeyboardType
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasTextFieldLeadingIconContentDescription
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasTextFieldLeadingIconImageVector
import emperorfin.android.components.ui.extensions.semanticsmatcher.hasTextFieldSingleLine


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 01th May, 2023.
 */


class EmailInputTestUtil3(
    private val mContext: Context,
    private val mTargetContext: Context,
    private val composeTestRule: ComposeContentTestRule
) {

    fun setContentAsEmailInputAndAssertItIsDisplayed(
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

    fun onNodeWithEmailInputAndTextExactlyEmailAddress(
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

    fun onNodeWithEmailInputAndTextContactAtEmailDotCom(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithEmailInputAnd(
            otherMatcher = hasTextContactAtEmailDotCom(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithEmailInputAndTextFieldLeadingIconImageVectorIconsDefaultEmail(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithEmailInputAnd(
            otherMatcher = hasTextFieldLeadingIconImageVectorIconsDefaultEmail(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithEmailInputAndTextFieldLeadingIconContentDescriptionEmailIcon(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithEmailInputAnd(
            otherMatcher = hasTextFieldLeadingIconContentDescriptionEmailIcon(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithEmailInputAndTextFieldSingleLineTrue(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithEmailInputAnd(
            otherMatcher = hasTextFieldSingleLineTrue(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithEmailInputAndTextFieldKeyboardOptionsKeyboardTypeEmail(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithEmailInputAnd(
            otherMatcher = hasTextFieldKeyboardOptionsKeyboardTypeEmail(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithEmailInputAndImeActionNext(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithEmailInputAnd(
            otherMatcher = hasImeActionNext(),
            useUnmergedTree = useUnmergedTree
        )

    }

    // ------- FOR ..._AnotherApproach()

    fun onNodeWithEmailInputAndTextFieldKeyboardOptionsImeActionNext(
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

}