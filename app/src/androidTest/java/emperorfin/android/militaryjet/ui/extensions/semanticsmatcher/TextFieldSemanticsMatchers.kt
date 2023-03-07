package emperorfin.android.militaryjet.ui.extensions.semanticsmatcher

import androidx.annotation.IntegerRes
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import emperorfin.android.militaryjet.ui.fortesting.*


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

/**
 * To easily find out the ARGB of a color to be used as the expected value during assertion,
 * there are two approaches to do this:
 *
 * - you can either call toArgb() on the color object and then logcat the returned value or
 * - you can just use any random assertion expected value, run the test case (which should
 * fail) and then get and use (as the correct assertion expected value) the assertion actual
 * value from the failed test error message (simplest approach).
 */
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

/**
 * To easily find out the ARGB of a color to be used as the expected value during assertion,
 * there are two approaches to do this:
 *
 * - you can either call toArgb() on the color object and then logcat the returned value or
 * - you can just use any random assertion expected value, run the test case (which should
 * fail) and then get and use (as the correct assertion expected value) the assertion actual
 * value from the failed test error message (simplest approach).
 */
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

/**
 * To easily find out the ARGB of a color to be used as the expected value during assertion,
 * there are two approaches to do this:
 *
 * - you can either call toArgb() on the color object and then logcat the returned value or
 * - you can just use any random assertion expected value, run the test case (which should
 * fail) and then get and use (as the correct assertion expected value) the assertion actual
 * value from the failed test error message (simplest approach).
 */
fun hasIconTintArgb(iconTintInArgb: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(IconTintArgbSemantics, iconTintInArgb)

fun hasIconTintRes(@IntegerRes iconTintRes: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(IconTintResSemantics, iconTintRes)


// TEXT
/**
 * To easily find out the ARGB of a color to be used as the expected value during assertion,
 * there are two approaches to do this:
 *
 * - you can either call toArgb() on the color object and then logcat the returned value or
 * - you can just use any random assertion expected value, run the test case (which should
 * fail) and then get and use (as the correct assertion expected value) the assertion actual
 * value from the failed test error message (simplest approach).
 */
fun hasTextColorArgb(textColorInArgb: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(TextColorArgbSemantics, textColorInArgb)

fun hasTextColorRes(@IntegerRes textColorRes: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(TextColorResSemantics, textColorRes)


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

