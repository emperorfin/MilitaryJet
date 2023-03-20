package emperorfin.android.militaryjet.ui.extensions.semanticsmatcher

import androidx.compose.ui.test.SemanticsMatcher
import emperorfin.android.militaryjet.ui.fortesting.AlertDialogConfirmButtonTextSemantics
import emperorfin.android.militaryjet.ui.fortesting.AlertDialogTextSemantics
import emperorfin.android.militaryjet.ui.fortesting.AlertDialogTitleSemantics


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 20th March, 2023.
 */


/**
 * Docs on custom semantics: https://stackoverflow.com/a/71389302 or
 * https://stackoverflow.com/questions/68662342/jetpack-compose-testing-assert-specific-image-is-set
 */

// ALERT DIALOG
fun hasAlertDialogTitle(alertDialogTitle: String): SemanticsMatcher =
    SemanticsMatcher.expectValue(AlertDialogTitleSemantics, alertDialogTitle)

fun hasAlertDialogText(alertDialogText: String): SemanticsMatcher =
    SemanticsMatcher.expectValue(AlertDialogTextSemantics, alertDialogText)

/**
 * TODO: Rename [hasAlertDialogConfirmButtonText] to hasAlertDialogConfirmButtonAction
 */
fun hasAlertDialogConfirmButtonText(alertDialogConfirmButtonText: String): SemanticsMatcher =
    SemanticsMatcher.expectValue(AlertDialogConfirmButtonTextSemantics, alertDialogConfirmButtonText)