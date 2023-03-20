package emperorfin.android.militaryjet.ui.fortesting

import androidx.compose.ui.graphics.vector.ImageVector
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

// ICON
val IconImageVectorSemantics = SemanticsPropertyKey<ImageVector>("IconImageVectorSemantics")
val IconContentDescriptionSemantics = SemanticsPropertyKey<String>("IconContentDescriptionSemantics")
val IconTintArgbSemantics = SemanticsPropertyKey<Int>("IconTintArgbSemantics")
val IconTintResSemantics = SemanticsPropertyKey<Int>("IconTintResSemantics")

var SemanticsPropertyReceiver.iconImageVector by IconImageVectorSemantics
var SemanticsPropertyReceiver.iconContentDescription by IconContentDescriptionSemantics
var SemanticsPropertyReceiver.iconTintArgb by IconTintArgbSemantics
var SemanticsPropertyReceiver.iconTintRes by IconTintResSemantics