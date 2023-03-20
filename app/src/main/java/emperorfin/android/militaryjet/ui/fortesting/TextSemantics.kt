package emperorfin.android.militaryjet.ui.fortesting

import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 20th March, 2023.
 */


/**
 * Docs on custom semantics: https://stackoverflow.com/a/71389302 or
 * https://stackoverflow.com/questions/68662342/jetpack-compose-testing-assert-specific-image-is-set
 */

// TEXT
val TextColorArgbSemantics = SemanticsPropertyKey<Int>("TextColorArgbSemantics")
val TextColorResSemantics = SemanticsPropertyKey<Int>("TextColorResSemantics")

var SemanticsPropertyReceiver.textColorArgb by TextColorArgbSemantics
var SemanticsPropertyReceiver.textColorRes by TextColorResSemantics