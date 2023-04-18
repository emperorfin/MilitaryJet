/**
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

package emperorfin.android.militaryjet.ui.screens.authentication.stateholders

import androidx.annotation.StringRes
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


//@Immutable
data class AuthenticationUiState(
    val authenticationMode: AuthenticationMode = SIGN_IN,
    val email: String? = null,
    val password: String? = null,
    val passwordRequirements: List<PasswordRequirement> = emptyList(),
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
                    PasswordRequirement.values().toList()
                ))

    }

}