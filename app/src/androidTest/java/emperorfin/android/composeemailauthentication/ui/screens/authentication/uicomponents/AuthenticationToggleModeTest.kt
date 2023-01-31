package emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.composeemailauthentication.test.R
import emperorfin.android.composeemailauthentication.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.composeemailauthentication.ui.screens.authentication.stateholders.AuthenticationUiState
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 25th November, 2022.
 */


/**
 * For a clean version of this class, see [AuthenticationToggleModeTest2]
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
class AuthenticationToggleModeTest {

    private companion object {

        private const val FALSE: Boolean = false
        private const val TRUE: Boolean = true

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

    @Before
    fun setUpContexts() {

        // See field's KDoc for more info.
        mTargetContext = InstrumentationRegistry.getInstrumentation().targetContext
//        mTargetContext = ApplicationProvider.getApplicationContext<Context>() // Haven't tested but might work.
        // See field's KDoc for more info.
        mContext = InstrumentationRegistry.getInstrumentation().context

    }

    @Test
    fun need_Account_Action_Displayed() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        composeTestRule.setContent {
            AuthenticationToggleMode(
                authenticationMode = uiState.authenticationMode,
                toggleAuthentication = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .assertTextEquals(
                mContext.getString(R.string.action_need_account)
            )

    }

    @Test
    fun need_Account_Action_Displayed_2() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        composeTestRule.setContent {
            AuthenticationToggleMode(
                authenticationMode = uiState.authenticationMode,
                toggleAuthentication = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.action_need_account)
                )
            )

    }

    @Test
    fun need_Account_Action_Displayed_3() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationToggleMode(
                    authenticationMode = uiState.authenticationMode,
                    toggleAuthentication = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.action_need_account),
                    includeEditableText = FALSE
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun need_Account_Action_Displayed_4() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationToggleMode(
                    authenticationMode = uiState.authenticationMode,
                    toggleAuthentication = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_need_account),
                        includeEditableText = FALSE
                    )
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun already_Have_Account_Action_Displayed() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            AuthenticationToggleMode(
                authenticationMode = uiState.authenticationMode,
                toggleAuthentication = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.action_already_have_account)
                )
            )

    }

    @Test
    fun already_Have_Account_Action_Displayed_2() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationToggleMode(
                    authenticationMode = uiState.authenticationMode,
                    toggleAuthentication = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.action_already_have_account),
                    includeEditableText = FALSE
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun already_Have_Account_Action_Displayed_3() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationToggleMode(
                    authenticationMode = uiState.authenticationMode,
                    toggleAuthentication = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_already_have_account),
                        includeEditableText = FALSE
                    )
                )
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Authentication_Toggle_Mode_Triggered() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        val toggleAuthentication: () -> Unit = mock()

        composeTestRule.setContent {
            AuthenticationToggleMode(
                authenticationMode = uiState.authenticationMode,
                toggleAuthentication = toggleAuthentication
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.action_already_have_account)
                )
            )
            .performClick()

        verify(
            mock = toggleAuthentication
        ).invoke()

    }

    @Test
    fun sign_In_Authentication_Toggle_Mode_Triggered_2() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        val toggleAuthentication: () -> Unit = mock()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationToggleMode(
                    authenticationMode = uiState.authenticationMode,
                    toggleAuthentication = toggleAuthentication
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.action_already_have_account),
                    includeEditableText = FALSE
                )
            )
            .performClick()

        verify(
            mock = toggleAuthentication
        ).invoke()

    }

    @Test
    fun sign_In_Authentication_Toggle_Mode_Triggered_3() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        val toggleAuthentication: () -> Unit = mock()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationToggleMode(
                    authenticationMode = uiState.authenticationMode,
                    toggleAuthentication = toggleAuthentication
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_already_have_account),
                        includeEditableText = FALSE
                    )
                )
            )
            .performClick()

        verify(
            mock = toggleAuthentication
        ).invoke()

    }

    @Test
    fun sign_In_Authentication_Toggle_Mode_Triggered_3_WithoutMockito() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        var isClicked = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationToggleMode(
                    authenticationMode = uiState.authenticationMode,
                    toggleAuthentication = {
                        isClicked = TRUE
                    }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_already_have_account),
                        includeEditableText = FALSE
                    )
                )
            )
            .performClick()

        assertTrue(isClicked)

    }

    @Test
    fun sign_Up_Authentication_Toggle_Mode_Triggered() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        val toggleAuthentication: () -> Unit = mock()

        composeTestRule.setContent {
            AuthenticationToggleMode(
                authenticationMode = uiState.authenticationMode,
                toggleAuthentication = toggleAuthentication
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.action_need_account)
                )
            )
            .performClick()

        verify(
            mock = toggleAuthentication
        ).invoke()

    }

    @Test
    fun sign_Up_Authentication_Toggle_Mode_Triggered_2() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        val toggleAuthentication: () -> Unit = mock()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationToggleMode(
                    authenticationMode = uiState.authenticationMode,
                    toggleAuthentication = toggleAuthentication
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                )
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.action_need_account),
                    includeEditableText = FALSE
                )
            )
            .performClick()

        verify(
            mock = toggleAuthentication
        ).invoke()

    }

    @Test
    fun sign_Up_Authentication_Toggle_Mode_Triggered_3() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        val toggleAuthentication: () -> Unit = mock()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationToggleMode(
                    authenticationMode = uiState.authenticationMode,
                    toggleAuthentication = toggleAuthentication
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_need_account),
                        includeEditableText = FALSE
                    )
                )
            )
            .performClick()

        verify(
            mock = toggleAuthentication
        ).invoke()

    }

    @Test
    fun sign_Up_Authentication_Toggle_Mode_Triggered_3_WithoutMockito() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        var isClicked = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationToggleMode(
                    authenticationMode = uiState.authenticationMode,
                    toggleAuthentication = {
                        isClicked = TRUE
                    }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_need_account),
                        includeEditableText = FALSE
                    )
                )
            )
            .performClick()

        assertTrue(isClicked)

    }

}