package emperorfin.android.militaryjet.ui.extensions

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