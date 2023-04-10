package emperorfin.android.militaryjet.ui.screens.authentication.uicomponents

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.militaryjet.test.R
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.screens.authentication.stateholders.AuthenticationUiState
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertTrue
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 25th November, 2022.
 */


/**
 * The following classes are revisions of this class:
 * [AuthenticationButtonTest2]
 * [AuthenticationButtonTest3]
 * [AuthenticationButtonTest4]
 * [AuthenticationButtonTest5]
 *
 * Notes:
 *
 * - If you ever need to pass a resource (e.g. a string resource) into a composable during testing,
 * be sure to use the one from the main source set and the R must be the one from
 * [emperorfin.android.militaryjet.R] and not
 * [emperorfin.android.militaryjet.test.R].
 * - Every other thing during testing that involves the use of a resource (e.g. a string resource)
 * such as performing matches or assertions, be sure to use the resource from the androidTest source
 * set (which you should've provided a copy and always in sync with the one from the main source set).
 * And the R must be the one from [emperorfin.android.militaryjet.test.R] instead of
 * [emperorfin.android.militaryjet.R].
 *
 * Be sure to have Configured res srcDirs for androidTest sourceSet in app/build.gradle file.
 * See the following:
 * - https://stackoverflow.com/questions/36955608/espresso-how-to-use-r-string-resources-of-androidtest-folder
 * - https://stackoverflow.com/questions/26663539/configuring-res-srcdirs-for-androidtest-sourceset
 */
class AuthenticationButtonTest {

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
     * [emperorfin.android.militaryjet.R] and not
     * [emperorfin.android.militaryjet.test.R]
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
     * [emperorfin.android.militaryjet.test.R] and not
     * [emperorfin.android.militaryjet.R]
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
    fun sign_In_Action_Displayed() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        composeTestRule.setContent {
            AuthenticationButton(
                authenticationMode = uiState.authenticationMode,
                enableAuthentication = TRUE,
                onAuthenticate = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                )
            )
            .assertTextEquals(
                mContext.getString(R.string.action_sign_in)
            )

    }

    @Test
    fun sign_In_Action_Displayed_2() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = TRUE,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_sign_in),
                        includeEditableText = FALSE
                    )
                )
            )
            .assertTextEquals(
                mContext.getString(R.string.action_sign_in),
                includeEditableText = FALSE
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_In_Action_Displayed_3() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = TRUE,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_sign_in),
                        includeEditableText = FALSE
                    )
                )
            )
            .apply {
                assertIsDisplayed()

                assertTextEquals(
                    mContext.getString(R.string.action_sign_in),
                    includeEditableText = FALSE
                )
            }

    }

    @Test
    fun sign_Up_Action_Displayed() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            AuthenticationButton(
                authenticationMode = uiState.authenticationMode,
                enableAuthentication = TRUE,
                onAuthenticate = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                )
            )
            .assertTextEquals(
                mContext.getString(R.string.action_sign_up)
            )

    }

    @Test
    fun sign_Up_Action_Displayed_2() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = TRUE,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_sign_up),
                        includeEditableText = FALSE
                    )
                )
            )
            .assertTextEquals(
                mContext.getString(R.string.action_sign_up),
                includeEditableText = FALSE
            )
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_Action_Displayed_3() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = TRUE,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_sign_up),
                        includeEditableText = FALSE
                    )
                )
            )
            .apply {
                assertIsDisplayed()

                assertTextEquals(
                    mContext.getString(R.string.action_sign_up),
                    includeEditableText = FALSE
                )
            }

    }

    @Test
    fun sign_In_Authentication_Callback_Triggered() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        val onAuthenticate: () -> Unit = mock()
        
        composeTestRule.setContent { 
            AuthenticationButton(
                authenticationMode = uiState.authenticationMode,
                enableAuthentication = TRUE,
                onAuthenticate = onAuthenticate
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                )
            )
            .performClick()

        verify(
            mock = onAuthenticate
        ).invoke()

    }

    @Test
    fun sign_In_Authentication_Callback_Triggered_2() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        val onAuthenticate: () -> Unit = mock()

        composeTestRule.setContent {
            AuthenticationButton(
                authenticationMode = uiState.authenticationMode,
                enableAuthentication = TRUE,
                onAuthenticate = onAuthenticate
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                )
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.action_sign_in)
                )
            )
            .performClick()

        verify(
            mock = onAuthenticate
        ).invoke()

    }

    @Test
    fun sign_In_Authentication_Callback_Triggered_3() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        val onAuthenticate: () -> Unit = mock()

        val hasTextExactlySignIn: SemanticsMatcher = hasTextExactly(
            mContext.getString(R.string.action_sign_in),
            includeEditableText = FALSE
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = TRUE,
                    onAuthenticate = onAuthenticate
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactlySignIn
                )
            )
            .assert(
                matcher = hasTextExactlySignIn
            )
            .performClick()

        verify(
            mock = onAuthenticate
        ).invoke()

    }

    @Test
    fun sign_In_Authentication_Callback_Triggered_4() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        val onAuthenticate: () -> Unit = mock()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = TRUE,
                    onAuthenticate = onAuthenticate
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_sign_in),
                        includeEditableText = FALSE
                    )
                )
            )
            .performClick()

        verify(
            mock = onAuthenticate
        ).invoke()

    }

    @Test
    fun sign_In_Authentication_Callback_Triggered_4_WithoutMockito() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        var isClicked = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = TRUE,
                    onAuthenticate = {
                        isClicked = TRUE
                    }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_sign_in),
                        includeEditableText = FALSE
                    )
                )
            )
            .performClick()

        assertTrue(isClicked)

    }

    @Test
    fun sign_Up_Authentication_Callback_Triggered() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        val onAuthenticate: () -> Unit = mock()

        composeTestRule.setContent {
            AuthenticationButton(
                authenticationMode = uiState.authenticationMode,
                enableAuthentication = TRUE,
                onAuthenticate = onAuthenticate
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                )
            )
            .assert(
                matcher = hasTextExactly(
                    mContext.getString(R.string.action_sign_up)
                )
            )
            .performClick()

        verify(
            mock = onAuthenticate
        ).invoke()

    }

    @Test
    fun sign_Up_Authentication_Callback_Triggered_2() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        val onAuthenticate: () -> Unit = mock()

        val hasTextExactlySignUp: SemanticsMatcher = hasTextExactly(
            mContext.getString(R.string.action_sign_up),
            includeEditableText = FALSE
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = TRUE,
                    onAuthenticate = onAuthenticate
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactlySignUp
                )
            )
            .assert(
                matcher = hasTextExactlySignUp
            )
            .performClick()

        verify(
            mock = onAuthenticate
        ).invoke()

    }

    @Test
    fun sign_Up_Authentication_Callback_Triggered_3() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        val onAuthenticate: () -> Unit = mock()

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = TRUE,
                    onAuthenticate = onAuthenticate
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_sign_up),
                        includeEditableText = FALSE
                    )
                )
            )
            .performClick()

        verify(
            mock = onAuthenticate
        ).invoke()

    }

    @Test
    fun sign_Up_Authentication_Callback_Triggered_3_WithoutMockito() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        var isClicked = FALSE

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = TRUE,
                    onAuthenticate = {
                        isClicked = TRUE
                    }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_sign_up),
                        includeEditableText = FALSE
                    )
                )
            )
            .performClick()

        assertTrue(isClicked)

    }

    @Test
    fun sign_In_Button_Disabled() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        composeTestRule.setContent {
            AuthenticationButton(
                authenticationMode = uiState.authenticationMode,
                enableAuthentication = FALSE,
                onAuthenticate = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                )
            )
            .assertIsNotEnabled()

    }

    @Test
    fun sign_In_Button_Disabled_2() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        composeTestRule.setContent {
            AuthenticationButton(
                authenticationMode = uiState.authenticationMode,
                enableAuthentication = FALSE,
                onAuthenticate = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                )
            )
            .assertTextEquals(
                mContext.getString(R.string.action_sign_in)
            )
            .assertIsNotEnabled()

    }

    @Test
    fun sign_In_Button_Disabled_3() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        composeTestRule.setContent {
            AuthenticationButton(
                authenticationMode = uiState.authenticationMode,
                enableAuthentication = FALSE,
                onAuthenticate = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                )
            )
            .assert(
                matcher = hasText(
                    text = mContext.getString(R.string.action_sign_in)
                )
            )
            .assert(
                matcher = isNotEnabled()
            )

    }

    @Test
    fun sign_In_Button_Disabled_4() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        val hasTextExactlySignIn: SemanticsMatcher = hasTextExactly(
            mContext.getString(R.string.action_sign_in),
            includeEditableText = FALSE
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = FALSE,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactlySignIn
                )
            )
            .assert(
                matcher = hasTextExactlySignIn
            )
            .assertIsDisplayed()
            .assert(
                matcher = isNotEnabled()
            )

    }

    @Test
    fun sign_In_Button_Disabled_5() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = FALSE,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_sign_in),
                        includeEditableText = FALSE
                    )
                )
            )
            .apply {
                assertIsDisplayed()

                assert(
                    matcher = isNotEnabled()
                )
            }

    }

    @Test
    fun sign_In_Button_Enabled() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        composeTestRule.setContent {
            AuthenticationButton(
                authenticationMode = uiState.authenticationMode,
                enableAuthentication = TRUE,
                onAuthenticate = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                )
            )
            .assertIsEnabled()

    }

    @Test
    fun sign_In_Button_Enabled_2() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        composeTestRule.setContent {
            AuthenticationButton(
                authenticationMode = uiState.authenticationMode,
                enableAuthentication = TRUE,
                onAuthenticate = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                )
            )
            .assert(
                matcher = hasText(
                    text = mContext.getString(R.string.action_sign_in)
                )
            )
            .assertIsEnabled()

    }

    @Test
    fun sign_In_Button_Enabled_3() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        val hasTextExactlySignIn: SemanticsMatcher = hasTextExactly(
            mContext.getString(R.string.action_sign_in),
            includeEditableText = FALSE
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = TRUE,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactlySignIn
                )
            )
            .assert(
                matcher = hasTextExactlySignIn
            )
            .assertIsDisplayed()
            .assertIsEnabled()

    }

    @Test
    fun sign_In_Button_Enabled_4() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_IN
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = TRUE,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_sign_in),
                        includeEditableText = FALSE
                    )
                )
            )
            .apply {
                assertIsDisplayed()

                assertIsEnabled()
            }

    }

    @Test
    fun sign_Up_Button_Disabled() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            AuthenticationButton(
                authenticationMode = uiState.authenticationMode,
                enableAuthentication = FALSE,
                onAuthenticate = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                )
            )
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_2() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            AuthenticationButton(
                authenticationMode = uiState.authenticationMode,
                enableAuthentication = FALSE,
                onAuthenticate = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                )
            )
            .assert(
                matcher = hasText(
                    text = mContext.getString(R.string.action_sign_up)
                )
            )
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_3() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        val hasTextExactlySignUp: SemanticsMatcher = hasTextExactly(
            mContext.getString(R.string.action_sign_up),
            includeEditableText = FALSE
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = FALSE,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactlySignUp
                )
            )
            .assert(
                matcher = hasTextExactlySignUp
            )
            .assertIsDisplayed()
            .assertIsNotEnabled()

    }

    @Test
    fun sign_Up_Button_Disabled_4() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = FALSE,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_sign_up),
                        includeEditableText = FALSE
                    )
                )
            )
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Button_Enabled() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            AuthenticationButton(
                authenticationMode = uiState.authenticationMode,
                enableAuthentication = TRUE,
                onAuthenticate = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                )
            )
            .assertIsEnabled()

    }

    @Test
    fun sign_Up_Button_Enabled_2() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            AuthenticationButton(
                authenticationMode = uiState.authenticationMode,
                enableAuthentication = TRUE,
                onAuthenticate = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                )
            )
            .assert(
                matcher = hasText(
                    text = mContext.getString(R.string.action_sign_up)
                )
            )
            .assertIsEnabled()

    }

    @Test
    fun sign_Up_Button_Enabled_3() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            AuthenticationButton(
                authenticationMode = uiState.authenticationMode,
                enableAuthentication = TRUE,
                onAuthenticate = { }
            )
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                )
            )
            .assert(
                matcher = hasText(
                    text = mContext.getString(R.string.action_sign_up)
                )
            )
//            .assertIsEnabled() // Or
            .assert(
                matcher = isEnabled()
            )

    }

    @Test
    fun sign_Up_Button_Enabled_4() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        val hasTextExactlySignUp: SemanticsMatcher = hasTextExactly(
            mContext.getString(R.string.action_sign_up),
            includeEditableText = FALSE
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = TRUE,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactlySignUp
                )
            )
            .assert(
                matcher = hasTextExactlySignUp
            )
            .assertIsDisplayed()
//            .assertIsEnabled() // Or
            .assert(
                matcher = isEnabled()
            )

    }

    @Test
    fun sign_Up_Button_Enabled_5() {

        val uiState = AuthenticationUiState(
            authenticationMode = SIGN_UP
        )

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationButton(
                    authenticationMode = uiState.authenticationMode,
                    enableAuthentication = TRUE,
                    onAuthenticate = { }
                )
            }
        }

        composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
                ).and(
                    other = hasTextExactly(
                        mContext.getString(R.string.action_sign_up),
                        includeEditableText = FALSE
                    )
                )
            )
            .apply {
                assertIsDisplayed()

//            assertIsEnabled() // Or
                assert(
                    matcher = isEnabled()
                )
            }

    }

}