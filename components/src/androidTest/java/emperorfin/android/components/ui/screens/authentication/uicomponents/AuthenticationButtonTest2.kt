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

package emperorfin.android.components.ui.screens.authentication.uicomponents

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.components.test.R
import emperorfin.android.components.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.components.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.components.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.components.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.components.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
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
 * The following classes are revisions of this class:
 * [AuthenticationButtonTest3]
 * [AuthenticationButtonTest4]
 * [AuthenticationButtonTest5]
 *
 * Notes:
 *
 * - If you ever need to pass a resource (e.g. a string resource) into a composable during testing,
 * be sure to use the one from the main source set and the R must be the one from
 * [emperorfin.android.components.R] and not
 * [emperorfin.android.components.test.R].
 * - Every other thing during testing that involves the use of a resource (e.g. a string resource)
 * such as performing matches or assertions, be sure to use the resource from the androidTest source
 * set (which you should've provided a copy and always in sync with the one from the main source set).
 * And the R must be the one from [emperorfin.android.components.test.R] instead of
 * [emperorfin.android.components.R].
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

        private const val THIS_STRING_MUST_BE_EMPTY: String = ""

        @StringRes
        private val STRING_RES_SIGN_IN: Int = R.string.action_sign_in
        @StringRes
        private val STRING_RES_SIGN_UP: Int = R.string.action_sign_up

    }

    /**
     * Use this when resources are coming from the main source set, whether directly
     * (e.g. R.string.sample_text) or indirectly (e.g. [PasswordRequirement.EIGHT_CHARACTERS.label]
     * which is directly using a string resource).
     *
     * To actually reference the resource, you use
     * [emperorfin.android.components.R] and not
     * [emperorfin.android.components.test.R]
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
     * [emperorfin.android.components.test.R] and not
     * [emperorfin.android.components.R]
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
            assertAuthenticationButtonAndTextExactlySignInIsDisplayed()
        } else if (authenticationMode == SIGN_UP) {
            assertAuthenticationButtonAndTextExactlySignUpIsDisplayed()
        }

    }

    private fun setContentAsAuthenticationButton(
        composeTestRule: ComposeContentTestRule,
        authenticationMode: AuthenticationMode,
        enableAuthentication: Boolean,
        onAuthenticate: () -> Unit
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

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationButton]
     * composable in the [ComposeContentTestRule.setContent] whenever the [AuthenticationMode] is
     * [SIGN_IN]
     */
    private fun assertAuthenticationButtonAndTextExactlySignInIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .apply {
                assertIsDisplayed()

                assertTextEqualsSignIn(this)
            }

    }

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationButton]
     * composable in the [ComposeContentTestRule.setContent] whenever the [AuthenticationMode] is
     * [SIGN_UP]
     */
    private fun assertAuthenticationButtonAndTextExactlySignUpIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertTextEqualsSignUp(this)
            }

    }

    private fun onNodeWithAuthenticationButtonAndTextExactlySignIn(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationButtonAnd(
            otherMatcher = hasTextExactlySignIn(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationButtonAndTextExactlySignUp(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationButtonAnd(
            otherMatcher = hasTextExactlySignUp(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationButtonAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagAuthenticationButton().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun hasTestTagAuthenticationButton(): SemanticsMatcher {

        return hasTestTagsAuthenticationButtonAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagsAuthenticationButtonAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON + otherTestTag
        )

    }

    // Before using a semantics matcher, check the implementation of the utility functions in this
    // section if it's already available to avoid duplication.
    // The function names make the check easier.

    private fun hasTextExactlySignUp(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_SIGN_UP),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlySignIn(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_SIGN_IN),
            includeEditableText = FALSE
        )

    }

    private fun assertTextEqualsSignUp(
        semanticsNodeInteraction: SemanticsNodeInteraction
    ): SemanticsNodeInteraction {

        return semanticsNodeInteraction.assertTextEquals(
            mContext.getString(STRING_RES_SIGN_UP),
            includeEditableText = FALSE
        )

    }

    private fun assertTextEqualsSignIn(
        semanticsNodeInteraction: SemanticsNodeInteraction
    ): SemanticsNodeInteraction {

        return semanticsNodeInteraction.assertTextEquals(
            mContext.getString(STRING_RES_SIGN_IN),
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

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
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

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
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

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
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

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .performClick()

        assertTrue(isClicked)

    }

    @Test
    fun sign_In_Button_Disabled() {

        setContentAsAuthenticationButtonAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            enableAuthentication = FALSE
        )

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
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

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
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

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
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

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

//            assertIsEnabled() // Or
                assert(
                    matcher = isEnabled()
                )
            }

    }

}