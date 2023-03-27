package emperorfin.android.militaryjet.ui.screens.authentication.uicomponents

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.utils.AuthenticationErrorDialogTestUtil2
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.TRUE
import emperorfin.android.militaryjet.ui.constants.BooleanConstants.FALSE
import emperorfin.android.militaryjet.ui.constants.StringResourceConstants.MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 26th March, 2023.
 */


/**
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
class AuthenticationErrorDialogTest4 {

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

    private lateinit var authenticationErrorDialogTestUtil: AuthenticationErrorDialogTestUtil2

    @Before
    fun setUpContexts() {

        // See field's KDoc for more info.
        mTargetContext = InstrumentationRegistry.getInstrumentation().targetContext
//        mTargetContext = ApplicationProvider.getApplicationContext<Context>() // Haven't tested but might work.
        // See field's KDoc for more info.
        mContext = InstrumentationRegistry.getInstrumentation().context

        authenticationErrorDialogTestUtil = AuthenticationErrorDialogTestUtil2(
            mContext = mContext,
            mTargetContext = mTargetContext,
            composeTestRule = composeTestRule
        )

    }

    @Test
    fun dialog_Title_Displayed() {

        authenticationErrorDialogTestUtil
            .setContentAsAuthenticationErrorDialogAndAssertItIsDisplayed(
                error = MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE,
                onDismissError = { }
            )

    }

    @Test
    fun dialog_Text_Displayed() {

        authenticationErrorDialogTestUtil
            .setContentAsAuthenticationErrorDialogAndAssertItIsDisplayed(
                error = MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE,
                onDismissError = { }
            )

        authenticationErrorDialogTestUtil
            .onNodeWithAuthenticationErrorDialogAndAlertDialogTextSomeError()
            .assertIsDisplayed()

    }

    @Test
    fun dialog_Confirm_Button_Text_Displayed() {

        authenticationErrorDialogTestUtil
            .setContentAsAuthenticationErrorDialogAndAssertItIsDisplayed(
                error = MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE,
                onDismissError = { }
            )

        authenticationErrorDialogTestUtil
            .onNodeWithAuthenticationErrorDialogAndAlertDialogConfirmButtonTextOk()
            .assertIsDisplayed()

    }

    @Test
    fun dialog_Dismiss_Error_Callback_Triggered() {

        val onDismissError: () -> Unit = mock()

        authenticationErrorDialogTestUtil
            .setContentAsAuthenticationErrorDialogAndAssertItIsDisplayed(
                error = MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE,
                onDismissError = onDismissError
            )

        authenticationErrorDialogTestUtil
            .onNodeWithAuthenticationErrorDialogConfirmButtonAndTextExactlyOk()
            .performClick()

        verify(
            mock = onDismissError
        ).invoke()

    }

    @Test
    fun dialog_Dismiss_Error_Callback_Triggered_WithoutMockito() {

        var isClicked = FALSE

        authenticationErrorDialogTestUtil
            .setContentAsAuthenticationErrorDialogAndAssertItIsDisplayed(
                error = MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE,
                onDismissError = {
                    isClicked = TRUE
                }
            )

        authenticationErrorDialogTestUtil
            .onNodeWithAuthenticationErrorDialogConfirmButtonAndTextExactlyOk()
            .performClick()

        assertTrue(isClicked)

    }

}