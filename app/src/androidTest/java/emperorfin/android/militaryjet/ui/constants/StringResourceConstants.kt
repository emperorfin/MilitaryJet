package emperorfin.android.militaryjet.ui.constants

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import emperorfin.android.militaryjet.test.R
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasAlertDialogTitle
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasCircularProgressIndicatorColorArgb
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.AuthenticationScreen
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATION_TITLE
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_EMAIL
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_ERROR_DIALOG
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PROGRESS
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_INPUT_PASSWORD_TRAILING_ICON
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_SCREEN


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 21st March, 2023.
 */


object StringResourceConstants {

    @StringRes
    const val MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE: Int =
        emperorfin.android.militaryjet.R.string.test_error_message

    @StringRes
    const val STRING_RES_SIGN_IN_TO_YOUR_ACCOUNT: Int = R.string.label_sign_in_to_account
    @StringRes
    const val STRING_RES_SIGN_UP_FOR_AN_ACCOUNT: Int = R.string.label_sign_up_for_account
    @StringRes
    const val STRING_RES_EMAIL_ADDRESS: Int = R.string.label_email
    @StringRes
    const val STRING_RES_PASSWORD: Int = R.string.label_password
    @StringRes
    const val STRING_RES_NEED_AN_ACCOUNT: Int = R.string.action_need_account
    @StringRes
    const val STRING_RES_ALREADY_HAVE_AN_ACCOUNT: Int = R.string.action_already_have_account
    @StringRes
    const val STRING_RES_SIGN_IN: Int = R.string.action_sign_in
    @StringRes
    const val STRING_RES_SIGN_UP: Int = R.string.action_sign_up

    @StringRes
    const val STRING_RES_AT_LEAST_EIGHT_CHARACTERS: Int = R.string.password_requirement_characters
    @StringRes
    const val STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER: Int = R.string.password_requirement_capital
    @StringRes
    const val STRING_RES_AT_LEAST_ONE_DIGIT: Int = R.string.password_requirement_digit
    @StringRes
    const val STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED: Int = R.string.test_password_requirement_needed_characters
    @StringRes
    const val STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_NEEDED: Int = R.string.test_password_requirement_needed_capital
    @StringRes
    const val STRING_RES_AT_LEAST_ONE_DIGIT_NEEDED: Int = R.string.test_password_requirement_needed_digit
    @StringRes
    const val STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED: Int = R.string.test_password_requirement_satisfied_characters
    @StringRes
    const val STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_SATISFIED: Int = R.string.test_password_requirement_satisfied_capital
    @StringRes
    const val STRING_RES_AT_LEAST_ONE_DIGIT_SATISFIED: Int = R.string.test_password_requirement_satisfied_digit

    @StringRes
    const val STRING_RES_WHOOPS: Int = R.string.error_title

}