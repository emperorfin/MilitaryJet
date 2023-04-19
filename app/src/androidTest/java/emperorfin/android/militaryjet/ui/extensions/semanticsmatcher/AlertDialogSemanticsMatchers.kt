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