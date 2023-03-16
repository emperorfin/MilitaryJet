package emperorfin.android.militaryjet.ui.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import emperorfin.android.militaryjet.test.R
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

        private const val INPUT_CONTENT_EMAIL_EMPTY: String = ""
        private const val INPUT_CONTENT_EMAIL: String = "contact@email.com"
        private const val INPUT_CONTENT_EMAIL_PREFIXED: String = "contact@email.co"
        private const val INPUT_CONTENT_EMAIL_SUFFIXED: String = ".uk"

        @StringRes
        private const val STRING_RES_EMAIL_ADDRESS: Int = R.string.label_email

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

    fun onNodeWithEmailInputAnd(
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

}