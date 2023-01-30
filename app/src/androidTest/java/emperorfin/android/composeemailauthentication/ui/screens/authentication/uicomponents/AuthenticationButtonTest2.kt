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
import emperorfin.android.composeemailauthentication.ui.screens.authentication.stateholders.AuthenticationUiState
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 28th January, 2023.
 */


/**
 * This class is a clean version of [AuthenticationButtonTest] class.
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
class AuthenticationButtonTest2 {

    private companion object {

        private const val TRUE: Boolean = true
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

    private fun setContentAsAuthenticationButton(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        authenticationMode: AuthenticationMode,
        enableAuthentication: Boolean,
        onAuthenticate: () -> Unit = { }
    ) {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = authenticationMode,
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = onAuthenticate
                )
            }
        }

    }

    private fun setContentAsAuthenticationButtonAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        authenticationMode: AuthenticationMode,
        enableAuthentication: Boolean,
        onAuthenticate: () -> Unit = { }
    ) {

        setContentAsAuthenticationButton(
            composeTestRule = composeTestRule,
            authenticationMode = authenticationMode,
            enableAuthentication = enableAuthentication,
            onAuthenticate = onAuthenticate
        )

        if (authenticationMode == SIGN_IN) {
            assertAuthenticationButtonForSignInActionIsDisplayed()
        } else if (authenticationMode == SIGN_UP) {
            assertAuthenticationButtonForSignUpActionIsDisplayed()
        }

    }

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationButton]
     * composable in the [ComposeContentTestRule.setContent] whenever the [AuthenticationMode] is
     * [SIGN_IN]
     */
    private fun assertAuthenticationButtonForSignInActionIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationButtonForSignInAction()
            .apply {
                assertIsDisplayed()

                assertTextEquals(
                    mContext.getString(R.string.action_sign_in),
                    includeEditableText = FALSE
                )
            }

    }

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationButton]
     * composable in the [ComposeContentTestRule.setContent] whenever the [AuthenticationMode] is
     * [SIGN_UP]
     */
    private fun assertAuthenticationButtonForSignUpActionIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationButtonForSignUpAction()
            .apply {
                assertIsDisplayed()

                assertTextEquals(
                    mContext.getString(R.string.action_sign_up),
                    includeEditableText = FALSE
                )
            }

    }

    private fun onNodeWithAuthenticationButtonAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithAuthenticationButtonForSignInAction(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationButtonAnd(
            otherMatcher = hasTextExactly(
                mContext.getString(R.string.action_sign_in),
                includeEditableText = FALSE
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationButtonForSignUpAction(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationButtonAnd(
            otherMatcher = hasTextExactly(
                mContext.getString(R.string.action_sign_up),
                includeEditableText = FALSE
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
    fun sign_In_Action_Displayed() {

        setContentAsAuthenticationButtonAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            enableAuthentication = TRUE,
        )

    }

    @Test
    fun sign_Up_Action_Displayed() {

        setContentAsAuthenticationButtonAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            enableAuthentication = TRUE,
        )

    }

    @Test
    fun sign_In_Authentication_Callback_Triggered() {

        val onAuthenticate: () -> Unit = mock()

        setContentAsAuthenticationButtonAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            enableAuthentication = TRUE,
            onAuthenticate = onAuthenticate
        )

        onNodeWithAuthenticationButtonForSignInAction()
            .performClick()

        verify(
            mock = onAuthenticate
        ).invoke()

    }

    @Test
    fun sign_In_Authentication_Callback_Triggered_WithoutMockito() {

        var isClicked = FALSE

        setContentAsAuthenticationButtonAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            enableAuthentication = TRUE,
            onAuthenticate = {
                isClicked = TRUE
            }
        )

        onNodeWithAuthenticationButtonForSignInAction()
            .performClick()

        assertTrue(isClicked)

    }

    @Test
    fun sign_Up_Authentication_Callback_Triggered() {

        val onAuthenticate: () -> Unit = mock()

        setContentAsAuthenticationButtonAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            enableAuthentication = TRUE,
            onAuthenticate = onAuthenticate
        )

        onNodeWithAuthenticationButtonForSignUpAction()
            .performClick()

        verify(
            mock = onAuthenticate
        ).invoke()

    }

    @Test
    fun sign_Up_Authentication_Callback_Triggered_WithoutMockito() {

        var isClicked = FALSE

        setContentAsAuthenticationButtonAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            enableAuthentication = TRUE,
            onAuthenticate = {
                isClicked = TRUE
            }
        )

        onNodeWithAuthenticationButtonForSignUpAction()
            .performClick()

        assertTrue(isClicked)

    }

    @Test
    fun sign_In_Button_Disabled() {

        setContentAsAuthenticationButtonAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            enableAuthentication = FALSE
        )

        onNodeWithAuthenticationButtonForSignInAction()
            .apply {
                assertIsDisplayed()

                assert(
                    matcher = isNotEnabled()
                )
            }

    }

    @Test
    fun sign_In_Button_Enabled() {

        setContentAsAuthenticationButtonAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            enableAuthentication = TRUE
        )

        onNodeWithAuthenticationButtonForSignInAction()
            .apply {
                assertIsDisplayed()

                assertIsEnabled()
            }

    }

    @Test
    fun sign_Up_Button_Disabled() {

        setContentAsAuthenticationButtonAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            enableAuthentication = FALSE
        )

        onNodeWithAuthenticationButtonForSignUpAction()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Button_Enabled() {

        setContentAsAuthenticationButtonAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            enableAuthentication = TRUE
        )

        onNodeWithAuthenticationButtonForSignUpAction()
            .apply {
                assertIsDisplayed()

//            assertIsEnabled() // Or
                assert(
                    matcher = isEnabled()
                )
            }

    }

}