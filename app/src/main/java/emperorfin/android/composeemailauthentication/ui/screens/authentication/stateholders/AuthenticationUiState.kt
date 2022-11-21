package emperorfin.android.composeemailauthentication.ui.screens.authentication.stateholders

import androidx.annotation.StringRes
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.PasswordRequirements


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


//@Immutable
data class AuthenticationUiState(
    val authenticationMode: AuthenticationMode = SIGN_IN,
    val email: String? = null,
    val password: String? = null,
    val passwordRequirements: List<PasswordRequirements> = emptyList(),
    val isLoading: Boolean = FALSE,
//    val error: String? = null
    @StringRes val error: Int? = null
) {

    companion object {

        // BOOLEAN
        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

    }

    fun checkFormValidity(): Boolean {

        return password?.isNotEmpty() == TRUE &&
                email?.isNotEmpty() == TRUE &&
                (authenticationMode == SIGN_IN || passwordRequirements.containsAll(
                    PasswordRequirements.values().toList()
                ))

    }

}