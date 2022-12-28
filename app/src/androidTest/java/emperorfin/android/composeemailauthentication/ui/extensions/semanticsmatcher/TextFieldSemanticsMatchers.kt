package emperorfin.android.composeemailauthentication.ui.extensions.semanticsmatcher

import androidx.annotation.IntegerRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import emperorfin.android.composeemailauthentication.ui.fortesting.*


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 26th November, 2022.
 */


/**
 * Docs on custom semantics: https://stackoverflow.com/a/71389302 or
 * https://stackoverflow.com/questions/68662342/jetpack-compose-testing-assert-specific-image-is-set
 */

// TEXTFIELD
// ----------- For Leading Icon --------------------
fun hasTextFieldLeadingIconImageVector(leadingIconImageVector: ImageVector): SemanticsMatcher =
    SemanticsMatcher.expectValue(TextFieldLeadingIconImageVectorSemantics, leadingIconImageVector)

fun hasTextFieldLeadingIconContentDescription(leadingIconContentDescription: String): SemanticsMatcher =
    SemanticsMatcher.expectValue(TextFieldLeadingIconContentDescriptionSemantics, leadingIconContentDescription)

fun hasTextFieldLeadingIconTintArgb(leadingIconTintInArgb: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(TextFieldLeadingIconTintArgbSemantics, leadingIconTintInArgb)

fun hasTextFieldLeadingIconTintRes(@IntegerRes leadingIconTintRes: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(TextFieldLeadingIconTintResSemantics, leadingIconTintRes)

// ----------- For Trailing Icon --------------------
fun hasTextFieldTrailingIconClickLabel(trailingIconClickLabel: String): SemanticsMatcher =
    SemanticsMatcher.expectValue(TextFieldTrailingIconClickLabelSemantics, trailingIconClickLabel)

fun hasTextFieldTrailingIconImageVector(trailingIconImageVector: ImageVector): SemanticsMatcher =
    SemanticsMatcher.expectValue(TextFieldTrailingIconImageVectorSemantics, trailingIconImageVector)

fun hasTextFieldTrailingIconContentDescription(trailingIconContentDescription: String): SemanticsMatcher =
    SemanticsMatcher.expectValue(TextFieldTrailingIconContentDescriptionSemantics, trailingIconContentDescription)

fun hasTextFieldTrailingIconTintArgb(trailingIconTintInArgb: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(TextFieldTrailingIconTintArgbSemantics, trailingIconTintInArgb)

fun hasTextFieldTrailingIconTintRes(@IntegerRes trailingIconTintRes: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(TextFieldTrailingIconTintResSemantics, trailingIconTintRes)

// ----------- For Single Line --------------------
fun hasTextFieldSingleLine(singleLine: Boolean): SemanticsMatcher =
    SemanticsMatcher.expectValue(TextFieldSingleLineSemantics, singleLine)

// ----------- For Keyboard Options --------------------
fun hasTextFieldKeyboardOptionsKeyboardType(keyboardOptionsKeyboardType: KeyboardType): SemanticsMatcher =
    SemanticsMatcher.expectValue(TextFieldKeyboardOptionsKeyboardTypeSemantics, keyboardOptionsKeyboardType)

fun hasTextFieldKeyboardOptionsImeAction(keyboardOptionsImeAction: ImeAction): SemanticsMatcher =
    SemanticsMatcher.expectValue(TextFieldKeyboardOptionsImeActionSemantics, keyboardOptionsImeAction)

// ----------- For Visual Transformation --------------------
fun hasTextFieldVisualTransformation(visualTransformation: VisualTransformation): SemanticsMatcher =
    SemanticsMatcher.expectValue(TextFieldVisualTransformationSemantics, visualTransformation)


// ICON
fun hasIconImageVector(iconImageVector: ImageVector): SemanticsMatcher =
    SemanticsMatcher.expectValue(IconImageVectorSemantics, iconImageVector)

fun hasIconContentDescription(iconContentDescription: String): SemanticsMatcher =
    SemanticsMatcher.expectValue(IconContentDescriptionSemantics, iconContentDescription)

fun hasIconTintArgb(iconTintInArgb: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(IconTintArgbSemantics, iconTintInArgb)

fun hasIconTintRes(@IntegerRes iconTintRes: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(IconTintResSemantics, iconTintRes)


// TEXT
fun hasTextColorArgb(textColorInArgb: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(TextColorArgbSemantics, textColorInArgb)

fun hasTextColorRes(@IntegerRes textColorRes: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(TextColorResSemantics, textColorRes)


// ALERT DIALOG
fun hasAlertDialogTitle(alertDialogTitle: String): SemanticsMatcher =
    SemanticsMatcher.expectValue(AlertDialogTitleSemantics, alertDialogTitle)

fun hasAlertDialogText(alertDialogText: String): SemanticsMatcher =
    SemanticsMatcher.expectValue(AlertDialogTextSemantics, alertDialogText)

fun hasAlertDialogConfirmButtonText(alertDialogConfirmButtonText: String): SemanticsMatcher =
    SemanticsMatcher.expectValue(AlertDialogConfirmButtonTextSemantics, alertDialogConfirmButtonText)

