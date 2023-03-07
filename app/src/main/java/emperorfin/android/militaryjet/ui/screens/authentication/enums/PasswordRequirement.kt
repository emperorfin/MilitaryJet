package emperorfin.android.militaryjet.ui.screens.authentication.enums

import androidx.annotation.StringRes
import emperorfin.android.militaryjet.R


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


enum class PasswordRequirement(@StringRes val label: Int) {

    EIGHT_CHARACTERS(R.string.password_requirement_characters),
    CAPITAL_LETTER(R.string.password_requirement_capital),
    NUMBER(R.string.password_requirement_digit)

}