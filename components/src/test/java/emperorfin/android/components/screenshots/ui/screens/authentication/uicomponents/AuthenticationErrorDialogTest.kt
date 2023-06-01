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

package emperorfin.android.components.screenshots.ui.screens.authentication.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import emperorfin.android.components.screenshots.ui.constants.BooleanConstants
import org.junit.Rule
import emperorfin.android.components.screenshots.ui.constants.StringResourceConstants.MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE
import emperorfin.android.components.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.components.ui.screens.authentication.uicomponents.AuthenticationErrorDialog
import org.junit.Test


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 01th June, 2023.
 */


class AuthenticationErrorDialogTest {

    /**
     * Passing [SessionParams.RenderingMode.SHRINK] as the value assigned to the renderingMode
     * parameter while instantiating [Paparazzi] would cause the [dialog_Displays] test case to fail.
     */
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_3,
//        renderingMode = SessionParams.RenderingMode.SHRINK,
        showSystemUi = false
    )

    @Test
    fun dialog_Displays() {

        paparazzi.snapshot {
            ComposeEmailAuthenticationTheme(isForScreenshotTest = BooleanConstants.TRUE) {
                Box(Modifier.background(Color.White)) {
                    AuthenticationErrorDialog(
                        error = MAIN_SOURCE_SET_STRING_RES_TEST_ERROR_MESSAGE,
                        onDismissError = { }
                    )
                }
            }
        }

    }

}