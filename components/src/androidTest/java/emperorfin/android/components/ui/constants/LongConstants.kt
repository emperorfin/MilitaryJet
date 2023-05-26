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

package emperorfin.android.components.ui.constants

import androidx.compose.ui.test.junit4.ComposeContentTestRule


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 21st March, 2023.
 */


object LongConstants {

    /**
     * Since the delay time millis in the authenticate() function in app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/stateholders/AuthenticationViewModel.kt
     * is 2000L, the [ComposeContentTestRule.waitUntil] test API timeoutMillis should be
     * something greater than 2_000L such as 2_010L or 2_500L instead of the default 1_000L
     * value.
     *
     * For scenarios where the amount of time an operation such as network call is unknown, you
     * should keep increasing the timeoutMillis of the [ComposeContentTestRule.waitUntil] until
     * the test doesn't throw any error or exception related to timeout exception.
     */
    const val TIMEOUT_MILLIS_2500L: Long = 2_500L

}