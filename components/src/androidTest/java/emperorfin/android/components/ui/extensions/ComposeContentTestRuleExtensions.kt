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

package emperorfin.android.components.ui.extensions

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.ComposeContentTestRule


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 24th November, 2022.
 */
 
 
fun ComposeContentTestRule.waitUntilNodeCount(
    matcher: SemanticsMatcher,
    count: Int,
    timeoutMillis: Long = TIMEOUT_MILLIS_1000L,
    useUnmergedTree: Boolean = FALSE
) {

    this.waitUntil(timeoutMillis = timeoutMillis) {
        this.onAllNodes(
            matcher = matcher,
            useUnmergedTree = useUnmergedTree
        ).fetchSemanticsNodes().size == count
    }

}

fun ComposeContentTestRule.waitUntilExists(
    matcher: SemanticsMatcher,
    timeoutMillis: Long = TIMEOUT_MILLIS_1000L,
    useUnmergedTree: Boolean = FALSE
) {

    return this.waitUntilNodeCount(
        matcher = matcher,
        count = ONE,
        timeoutMillis = timeoutMillis,
        useUnmergedTree = useUnmergedTree
    )

}

fun ComposeContentTestRule.waitUntilDoesNotExist(
    matcher: SemanticsMatcher,
    timeoutMillis: Long = TIMEOUT_MILLIS_1000L,
    useUnmergedTree: Boolean = FALSE
) {

    return this.waitUntilNodeCount(
        matcher = matcher,
        count = ZERO,
        timeoutMillis = timeoutMillis,
        useUnmergedTree = useUnmergedTree
    )

}

private const val FALSE: Boolean = false

private const val ZERO: Int = 0
private const val ONE: Int = 1

private const val TIMEOUT_MILLIS_1000L: Long = 1_000L