package emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.composeemailauthentication.test.R
import emperorfin.android.composeemailauthentication.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATION_TITLE
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 30th January, 2023.
 */


/**
 * This class is a clean version of [AuthenticationTitleTest] class.
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
class AuthenticationTitleTest2 {

    private companion object {

        private const val FALSE: Boolean = false

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

    private fun setContentAsAuthenticationTitleAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        authenticationMode: AuthenticationMode
    ) {

        setContentAsAuthenticationTitle(
            composeTestRule = composeTestRule,
            authenticationMode = authenticationMode
        )

        if (authenticationMode == SIGN_IN) {
            assertAuthenticationTitleForSignInTitleIsDisplayed()
        } else if (authenticationMode == SIGN_UP) {
            assertAuthenticationTitleForSignUpTitleIsDisplayed()
        }

    }

    private fun setContentAsAuthenticationTitle(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        authenticationMode: AuthenticationMode
    ) {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationTitle(
                    authenticationMode = authenticationMode
                )
            }
        }

    }

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationTitle]
     * composable in the [ComposeContentTestRule.setContent] whenever the [AuthenticationMode] is
     * [SIGN_UP]
     */
    private fun assertAuthenticationTitleForSignUpTitleIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationTitleForSignUpTitle()
            .assertIsDisplayed()

    }

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationTitle]
     * composable in the [ComposeContentTestRule.setContent] whenever the [AuthenticationMode] is
     * [SIGN_IN]
     */
    private fun assertAuthenticationTitleForSignInTitleIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationTitleForSignInTitle()
            .assertIsDisplayed()

    }

    private fun onNodeWithAuthenticationTitleForSignUpTitle(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationTitleAnd(
            otherMatcher = hasTextExactly(
                mContext.getString(R.string.label_sign_up_for_account),
                includeEditableText = FALSE
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationTitleForSignInTitle(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationTitleAnd(
            otherMatcher = hasTextExactly(
                mContext.getString(R.string.label_sign_in_to_account),
                includeEditableText = FALSE
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationTitleAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE
                ).and(
                    other = otherMatcher
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
    fun sign_In_Title_Displayed() {

        setContentAsAuthenticationTitleAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN
        )

    }

    @Test
    fun sign_Up_Title_Displayed() {

        setContentAsAuthenticationTitleAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP
        )

    }

}