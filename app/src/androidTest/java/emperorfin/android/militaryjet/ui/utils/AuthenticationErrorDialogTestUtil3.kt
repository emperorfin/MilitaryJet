package emperorfin.android.militaryjet.ui.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasAlertDialogConfirmButtonText
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasAlertDialogText
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasAlertDialogTitle
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.AuthenticationErrorDialog
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_ERROR_DIALOG
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_ERROR_DIALOG_CONFIRM_BUTTON
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.TRUE
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.FALSE
import emperorfin.android.militaryjet.ui.constants.StringConstants.THIS_STRING_MUST_BE_EMPTY
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_OK
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_SOME_ERROR
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.STRING_RES_WHOOPS


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Wednesday 12th April, 2023.
 */


class AuthenticationErrorDialogTestUtil3(
    private val mContext: Context,
    private val mTargetContext: Context,
    private val composeTestRule: ComposeContentTestRule
) {

    fun setContentAsAuthenticationErrorDialogAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        @StringRes error: Int,
        onDismissError: () -> Unit = { }
    ) {

        setContentAsAuthenticationErrorDialog(
            composeTestRule = composeTestRule,
            error = error,
            onDismissError = onDismissError
        )

        assertAuthenticationErrorDialogAndAlertDialogTitleWhoopsIsDisplayed()

    }

    private fun setContentAsAuthenticationErrorDialog(
        composeTestRule: ComposeContentTestRule,
        @StringRes error: Int,
        onDismissError: () -> Unit
    ) {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationErrorDialog(
                    error = error,
                    onDismissError = onDismissError
                )
            }
        }

    }

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationErrorDialog]
     * composable in the [ComposeContentTestRule.setContent].
     */
    private fun assertAuthenticationErrorDialogAndAlertDialogTitleWhoopsIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops()
            .assertIsDisplayed()

    }

    private fun onNodeWithAuthenticationErrorDialogAndAlertDialogTitleWhoops(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationErrorDialogAnd(
            otherMatcher = hasAlertDialogTitleWhoops(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithAuthenticationErrorDialogAndAlertDialogTextSomeError(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationErrorDialogAnd(
            otherMatcher = hasAlertDialogTextSomeError(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithAuthenticationErrorDialogAndAlertDialogConfirmButtonTextOk(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationErrorDialogAnd(
            otherMatcher = hasAlertDialogConfirmButtonTextOk(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithAuthenticationErrorDialogConfirmButtonAndTextExactlyOk(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationErrorDialogConfirmButtonAnd(
            otherMatcher = hasTextExactlyOk(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationErrorDialogConfirmButtonAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagAuthenticationErrorDialogConfirmButton().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithAuthenticationErrorDialogAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagAuthenticationErrorDialog().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun hasTestTagAuthenticationErrorDialogConfirmButton(): SemanticsMatcher {

        return hasTestTagsAuthenticationErrorDialogConfirmButtonAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagAuthenticationErrorDialog(): SemanticsMatcher {

        return hasTestTagsAuthenticationErrorDialogAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagsAuthenticationErrorDialogConfirmButtonAnd(otherTestTag: String):
            SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_ERROR_DIALOG_CONFIRM_BUTTON + otherTestTag
        )

    }

    private fun hasTestTagsAuthenticationErrorDialogAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_ERROR_DIALOG + otherTestTag
        )

    }

    // Before using a semantics matcher, check the implementation of the utility functions in this
    // section if it's already available to avoid duplication.
    // The function names make the check easier.

    fun hasAlertDialogTitleWhoops(): SemanticsMatcher {

        return hasAlertDialogTitle(
            alertDialogTitle = mContext.getString(STRING_RES_WHOOPS)
        )

    }

    private fun hasAlertDialogTextSomeError(): SemanticsMatcher {

        return hasAlertDialogText(
            alertDialogText = mContext.getString(STRING_RES_SOME_ERROR)
        )

    }

    private fun hasAlertDialogConfirmButtonTextOk(): SemanticsMatcher {

        return hasAlertDialogConfirmButtonText(
            alertDialogConfirmButtonText = mContext.getString(STRING_RES_OK)
        )

    }

    private fun hasTextExactlyOk(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_OK),
            includeEditableText = FALSE
        )

    }

}