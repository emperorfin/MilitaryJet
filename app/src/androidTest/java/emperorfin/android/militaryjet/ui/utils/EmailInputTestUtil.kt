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
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import emperorfin.android.militaryjet.test.R
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.*
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.EmailInput
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_EMAIL


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 16th March, 2023.
 */


class EmailInputTestUtil(
    private val mContext: Context,
    private val mTargetContext: Context,
    private val composeTestRule: ComposeContentTestRule
) {

    private companion object {

        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        private val NULL = null

        private const val THIS_STRING_MUST_BE_EMPTY: String = ""
        private const val INPUT_CONTENT_EMAIL: String = "contact@email.com"

        @StringRes
        private const val STRING_RES_EMAIL_ADDRESS: Int = R.string.label_email

        private val IMAGE_VECTOR_ICONS_DEFAULT_EMAIL: ImageVector = Icons.Default.Email

        private val KEYBOARD_TYPE_EMAIL: KeyboardType = KeyboardType.Email

        private val IME_ACTION_NEXT: ImeAction = ImeAction.Next

    }

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