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

package emperorfin.android.components.ui.screens.authentication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import emperorfin.android.components.ui.screens.authentication.stateholders.AuthenticationViewModel
import emperorfin.android.components.ui.screens.authentication.uicomponents.AuthenticationContent
import emperorfin.android.components.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.components.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_SCREEN


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


@Composable
fun AuthenticationScreen() {
//    MaterialTheme {
//
//    }

    val viewModel: AuthenticationViewModel = viewModel()

    /**
     * The [AuthenticationContent] composable was wrapped inside of [Box] in order to attach a test
     * tag to the [AuthenticationScreen] composable.
     */
    Box(
        modifier = Modifier.testTag(TAG_AUTHENTICATION_SCREEN),
        contentAlignment = Alignment.Center
    ) {
        AuthenticationContent(
            modifier = Modifier.fillMaxWidth(),
            uiState = viewModel.uiState.collectAsState().value,
            handleEvent = viewModel::handleEvent
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun AuthenticationScreenPreview() {
    ComposeEmailAuthenticationTheme {
        AuthenticationScreen()
    }
}