package emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.composeemailauthentication.test.R
import emperorfin.android.composeemailauthentication.ui.extensions.semanticsmatcher.hasAlertDialogConfirmButtonText
import emperorfin.android.composeemailauthentication.ui.extensions.semanticsmatcher.hasAlertDialogText
import emperorfin.android.composeemailauthentication.ui.extensions.semanticsmatcher.hasAlertDialogTitle
import emperorfin.android.composeemailauthentication.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_ERROR_DIALOG
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_ERROR_DIALOG_CONFIRM_BUTTON
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 30th January, 2023.
 */


/**
 * This class is a clean version of [AuthenticationErrorDialogTest] class.
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
class AuthenticationErrorDialogTest2 {

    private companion object {

        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        @StringRes
        private const val MAIN_SOURCE_SET_RES_STRING_TEST_ERROR_MESSAGE: Int =
            emperorfin.android.composeemailauthentication.R.string.test_error_message

        @StringRes
        private const val STRING_RES_OK: Int = R.string.error_action_ok
        @StringRes
        private const val STRING_RES_SOME_ERROR: Int = R.string.test_error_message
        @StringRes
        private const val STRING_RES_WHOOPS: Int = R.string.error_title

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

    private fun setContentAsAuthenticationErrorDialogAndAssertItIsDisplayed(
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

    private fun onNodeWithAuthenticationErrorDialogAndAlertDialogTextSomeError(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationErrorDialogAnd(
            otherMatcher = hasAlertDialogTextSomeError(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationErrorDialogAndAlertDialogConfirmButtonTextOk(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationErrorDialogAnd(
            otherMatcher = hasAlertDialogConfirmButtonTextOk(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationErrorDialogConfirmButtonAndTextExactlyOk(
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

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_ERROR_DIALOG_CONFIRM_BUTTON
        )

    }

    private fun hasTestTagAuthenticationErrorDialog(): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_ERROR_DIALOG
        )

    }

    // Before using a semantics matcher, check the implementation of the utility functions in this
    // section if it's already available to avoid duplication.
    // The function names make the check easier.

    private fun hasAlertDialogTitleWhoops(): SemanticsMatcher {

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

    @Before
    fun setUpContexts() {

        // See field's KDoc for more info.
        mTargetContext = InstrumentationRegistry.getInstrumentation().targetContext
//        mTargetContext = ApplicationProvider.getApplicationContext<Context>() // Haven't tested but might work.
        // See field's KDoc for more info.
        mContext = InstrumentationRegistry.getInstrumentation().context

    }

    @Test
    fun dialog_Title_Displayed() {

        setContentAsAuthenticationErrorDialogAndAssertItIsDisplayed(
            error = MAIN_SOURCE_SET_RES_STRING_TEST_ERROR_MESSAGE,
            onDismissError = { }
        )

    }

    @Test
    fun dialog_Text_Displayed() {

        setContentAsAuthenticationErrorDialogAndAssertItIsDisplayed(
            error = MAIN_SOURCE_SET_RES_STRING_TEST_ERROR_MESSAGE,
            onDismissError = { }
        )

        onNodeWithAuthenticationErrorDialogAndAlertDialogTextSomeError()
            .assertIsDisplayed()

    }

    @Test
    fun dialog_Confirm_Button_Text_Displayed() {

        setContentAsAuthenticationErrorDialogAndAssertItIsDisplayed(
            error = MAIN_SOURCE_SET_RES_STRING_TEST_ERROR_MESSAGE,
            onDismissError = { }
        )

        onNodeWithAuthenticationErrorDialogAndAlertDialogConfirmButtonTextOk()
            .assertIsDisplayed()

    }

    @Test
    fun dialog_Dismiss_Error_Callback_Triggered() {

        val onDismissError: () -> Unit = mock()

        setContentAsAuthenticationErrorDialogAndAssertItIsDisplayed(
            error = MAIN_SOURCE_SET_RES_STRING_TEST_ERROR_MESSAGE,
            onDismissError = onDismissError
        )

        onNodeWithAuthenticationErrorDialogConfirmButtonAndTextExactlyOk()
            .performClick()

        verify(
            mock = onDismissError
        ).invoke()

    }

    @Test
    fun dialog_Dismiss_Error_Callback_Triggered_WithoutMockito() {

        var isClicked = FALSE

        setContentAsAuthenticationErrorDialogAndAssertItIsDisplayed(
            error = MAIN_SOURCE_SET_RES_STRING_TEST_ERROR_MESSAGE,
            onDismissError = {
                isClicked = TRUE
            }
        )

        onNodeWithAuthenticationErrorDialogConfirmButtonAndTextExactlyOk()
            .performClick()

        assertTrue(isClicked)

    }

}