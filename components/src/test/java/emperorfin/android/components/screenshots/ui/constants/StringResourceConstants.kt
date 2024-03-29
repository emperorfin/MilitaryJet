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

package emperorfin.android.components.screenshots.ui.constants

import androidx.annotation.StringRes
import emperorfin.android.components.test.R
import emperorfin.android.components.ui.screens.authentication.enums.PasswordRequirement


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 1st June, 2023.
 */


/**
 * TODO: Constant variables with "STRING_RES_" prefixed in their names should be renamed with
 *  "RES_STRING_" prefixed in their names instead of "STRING_RES_". E.g. [STRING_RES_SIGN_IN_TO_YOUR_ACCOUNT]
 *  should be renamed to RES_STRING_SIGN_IN_TO_YOUR_ACCOUNT.
 */
object StringResourceConstants {

    // TODO: Should be renamed to MAIN_SOURCE_SET_RES_STRING_TEST_ERROR_MESSAGE
    @JvmField
    @StringRes
    val MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE: Int =
        emperorfin.android.components.R.string.test_error_message

    @JvmField
    @StringRes
    val MAIN_SOURCE_SET_STRING_RES_AT_LEAST_EIGHT_CHARACTERS: Int = PasswordRequirement.EIGHT_CHARACTERS.label

    @JvmField
    @StringRes
    val STRING_RES_SIGN_IN_TO_YOUR_ACCOUNT: Int = R.string.label_sign_in_to_account
    @JvmField
    @StringRes
    val STRING_RES_SIGN_UP_FOR_AN_ACCOUNT: Int = R.string.label_sign_up_for_account

    @JvmField
    @StringRes
    val STRING_RES_EMAIL_ADDRESS: Int = R.string.label_email

    @JvmField
    @StringRes
    val STRING_RES_PASSWORD: Int = R.string.label_password
    @JvmField
    @StringRes
    val STRING_RES_PASSWORD_SHOW: Int = R.string.content_description_show_password
    @JvmField
    @StringRes
    val STRING_RES_PASSWORD_HIDE: Int = R.string.content_description_hide_password
    @JvmField
    @StringRes
    val STRING_RES_PASSWORD_INPUT_LEADING_ICON: Int = R.string.content_description_password_input_leading_icon
    @JvmField
    @StringRes
    val STRING_RES_TRAILING_ICON_INDICATES_PASSWORD_SHOWN: Int =
        R.string.content_description_password_input_trailing_icon_password_shown
    @JvmField
    @StringRes
    val STRING_RES_TRAILING_ICON_INDICATES_PASSWORD_HIDDEN: Int =
        R.string.content_description_password_input_trailing_icon_password_hidden

    @JvmField
    @StringRes
    val STRING_RES_NEED_AN_ACCOUNT: Int = R.string.action_need_account
    @JvmField
    @StringRes
    val STRING_RES_ALREADY_HAVE_AN_ACCOUNT: Int = R.string.action_already_have_account
    @JvmField
    @StringRes
    val STRING_RES_SIGN_IN: Int = R.string.action_sign_in
    @JvmField
    @StringRes
    val STRING_RES_SIGN_UP: Int = R.string.action_sign_up

    @JvmField
    @StringRes
    val STRING_RES_AT_LEAST_EIGHT_CHARACTERS: Int = R.string.password_requirement_characters
    @JvmField
    @StringRes
    val STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER: Int = R.string.password_requirement_capital
    @JvmField
    @StringRes
    val STRING_RES_AT_LEAST_ONE_DIGIT: Int = R.string.password_requirement_digit
    @JvmField
    @StringRes
    val STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED: Int = R.string.test_password_requirement_needed_characters
    @JvmField
    @StringRes
    val STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_NEEDED: Int = R.string.test_password_requirement_needed_capital
    @JvmField
    @StringRes
    val STRING_RES_AT_LEAST_ONE_DIGIT_NEEDED: Int = R.string.test_password_requirement_needed_digit
    @JvmField
    @StringRes
    val STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED: Int = R.string.test_password_requirement_satisfied_characters
    @JvmField
    @StringRes
    val STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_SATISFIED: Int = R.string.test_password_requirement_satisfied_capital
    @JvmField
    @StringRes
    val STRING_RES_AT_LEAST_ONE_DIGIT_SATISFIED: Int = R.string.test_password_requirement_satisfied_digit

    @JvmField
    @StringRes
    val STRING_RES_PASSWORD_REQUIREMENT_NEEDED: Int = R.string.content_description_icon_password_requirement_needed
    @JvmField
    @StringRes
    val STRING_RES_PASSWORD_REQUIREMENT_SATISFIED: Int = R.string.content_description_icon_password_requirement_satisfied

    @JvmField
    @StringRes
    val STRING_RES_WHOOPS: Int = R.string.error_title
    @JvmField
    @StringRes
    val STRING_RES_OK: Int = R.string.error_action_ok
    @JvmField
    @StringRes
    val STRING_RES_SOME_ERROR: Int = R.string.test_error_message

}