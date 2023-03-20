package emperorfin.android.militaryjet.ui.extensions.semanticsmatcher

import androidx.annotation.IntegerRes
import androidx.compose.ui.test.SemanticsMatcher
import emperorfin.android.militaryjet.ui.fortesting.CircularProgressIndicatorColorArgbSemantics
import emperorfin.android.militaryjet.ui.fortesting.CircularProgressIndicatorColorResSemantics


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 20th March, 2023.
 */


/**
 * Docs on custom semantics: https://stackoverflow.com/a/71389302 or
 * https://stackoverflow.com/questions/68662342/jetpack-compose-testing-assert-specific-image-is-set
 */

// CIRCULAR PROGRESS INDICATOR
/**
 * To easily find out the ARGB of a color to be used as the expected value during assertion,
 * there are two approaches to do this:
 *
 * - you can either call toArgb() on the color object and then logcat the returned value or
 * - you can just use any random assertion expected value, run the test case (which should
 * fail) and then get and use (as the correct assertion expected value) the assertion actual
 * value from the failed test error message (simplest approach).
 */
fun hasCircularProgressIndicatorColorArgb(circularProgressIndicatorColorInArgb: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(CircularProgressIndicatorColorArgbSemantics, circularProgressIndicatorColorInArgb)

fun hasCircularProgressIndicatorColorRes(@IntegerRes circularProgressIndicatorColorRes: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(CircularProgressIndicatorColorResSemantics, circularProgressIndicatorColorRes)

