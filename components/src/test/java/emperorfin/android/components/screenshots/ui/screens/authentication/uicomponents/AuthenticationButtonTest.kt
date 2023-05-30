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
import emperorfin.android.components.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.components.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.components.ui.screens.authentication.uicomponents.AuthenticationButton
import emperorfin.android.components.screenshots.ui.constants.BooleanConstants.FALSE
import emperorfin.android.components.screenshots.ui.constants.BooleanConstants.TRUE
import emperorfin.android.components.ui.res.theme.ComposeEmailAuthenticationTheme
import org.junit.Rule
import org.junit.Test


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 29th May, 2023.
 */


class AuthenticationButtonTest {

    @get:Rule
    val paparazzi = Paparazzi(
//        theme = "android:Theme.MaterialComponents.Light.NoActionBar"
//        deviceConfig = DeviceConfig.NEXUS_5.copy(softButtons = false, screenHeight = 1),
//        renderingMode = SessionParams.RenderingMode.V_SCROLL

//        deviceConfig = DeviceConfig.PIXEL.copy(screenWidth = DeviceConfig.PIXEL.screenWidth * 2, softButtons = false)

        deviceConfig = DeviceConfig.PIXEL_3,
        renderingMode = SessionParams.RenderingMode.SHRINK,
        showSystemUi = false
    )

    @Test
    fun sign_In_Button_Enabled() {
        paparazzi.snapshot {
            ComposeEmailAuthenticationTheme(isForScreenshotTest = TRUE) {
                Box(Modifier.background(Color.White)) {
                    AuthenticationButton(
                        authenticationMode = SIGN_IN,
                        enableAuthentication = TRUE,
                        onAuthenticate = { }
                    )
                }
            }
        }
    }

    @Test
    fun sign_Up_Button_Enabled() {
        paparazzi.snapshot {
            ComposeEmailAuthenticationTheme(isForScreenshotTest = TRUE) {
                Box(Modifier.background(Color.White)) {
                    AuthenticationButton(
                        authenticationMode = SIGN_UP,
                        enableAuthentication = TRUE,
                        onAuthenticate = { }
                    )
                }
            }
        }
    }

    @Test
    fun sign_In_Button_Disabled() {
        paparazzi.snapshot {
            ComposeEmailAuthenticationTheme(isForScreenshotTest = TRUE) {
                Box(Modifier.background(Color.White)) {
                    AuthenticationButton(
                        authenticationMode = SIGN_IN,
                        enableAuthentication = FALSE,
                        onAuthenticate = { }
                    )
                }
            }
        }
    }

    @Test
    fun sign_Up_Button_Disabled() {
        paparazzi.snapshot {
            ComposeEmailAuthenticationTheme(isForScreenshotTest = TRUE) {
                Box(Modifier.background(Color.White)) {
                    AuthenticationButton(
                        authenticationMode = SIGN_UP,
                        enableAuthentication = FALSE,
                        onAuthenticate = { }
                    )
                }
            }
        }
    }

}