package emperorfin.android.militaryjet.ui.fortesting

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 26th November, 2022.
 */


/**
 * Docs on custom semantics: https://stackoverflow.com/a/71389302 or
 * https://stackoverflow.com/questions/68662342/jetpack-compose-testing-assert-specific-image-is-set
 */

// TODO: Should add other semantics for things like TextField placeholder, etc.

// TEXTFIELD
// ----------- For Leading Icon --------------------
val TextFieldLeadingIconImageVectorSemantics = SemanticsPropertyKey<ImageVector>("TextFieldLeadingIconImageVectorSemantics")
val TextFieldLeadingIconContentDescriptionSemantics = SemanticsPropertyKey<String>("TextFieldLeadingIconContentDescriptionSemantics")
val TextFieldLeadingIconTintArgbSemantics = SemanticsPropertyKey<Int>("TextFieldLeadingIconTintArgbSemantics")
val TextFieldLeadingIconTintResSemantics = SemanticsPropertyKey<Int>("TextFieldLeadingIconTintResSemantics")

// ----------- For Trailing Icon --------------------
val TextFieldTrailingIconClickLabelSemantics = SemanticsPropertyKey<String>("TextFieldTrailingIconClickLabelSemantics")
val TextFieldTrailingIconImageVectorSemantics = SemanticsPropertyKey<ImageVector>("TextFieldTrailingIconImageVectorSemantics")
val TextFieldTrailingIconContentDescriptionSemantics = SemanticsPropertyKey<String>("TextFieldTrailingIconContentDescriptionSemantics")
val TextFieldTrailingIconTintArgbSemantics = SemanticsPropertyKey<Int>("TextFieldTrailingIconTintArgbSemantics")
val TextFieldTrailingIconTintResSemantics = SemanticsPropertyKey<Int>("TextFieldTrailingIconTintResSemantics")

// ----------- For Single Line --------------------
val TextFieldSingleLineSemantics = SemanticsPropertyKey<Boolean>("TextFieldSingleLineSemantics")

// ----------- For Keyboard Options --------------------
val TextFieldKeyboardOptionsKeyboardTypeSemantics = SemanticsPropertyKey<KeyboardType>("TextFieldKeyboardOptionsKeyboardTypeSemantics")
val TextFieldKeyboardOptionsImeActionSemantics = SemanticsPropertyKey<ImeAction>("TextFieldKeyboardOptionsImeActionSemantics")

// ----------- For Visual Transformation --------------------
val TextFieldVisualTransformationSemantics = SemanticsPropertyKey<VisualTransformation>("TextFieldVisualTransformationSemantics")


// ----------- For Leading Icon --------------------
var SemanticsPropertyReceiver.textFieldLeadingIconImageVector by TextFieldLeadingIconImageVectorSemantics
var SemanticsPropertyReceiver.textFieldLeadingIconContentDescription by TextFieldLeadingIconContentDescriptionSemantics
var SemanticsPropertyReceiver.textFieldLeadingIconTintArgb by TextFieldLeadingIconTintArgbSemantics
var SemanticsPropertyReceiver.textFieldLeadingIconTintRes by TextFieldLeadingIconTintResSemantics

// ----------- For Trailing Icon --------------------
// TODO: Add other semantics such as content description and tint for trailing icon
var SemanticsPropertyReceiver.textFieldTrailingIconClickLabel by TextFieldTrailingIconClickLabelSemantics
var SemanticsPropertyReceiver.textFieldTrailingIconImageVector by TextFieldTrailingIconImageVectorSemantics
var SemanticsPropertyReceiver.textFieldTrailingIconContentDescription by TextFieldTrailingIconContentDescriptionSemantics
var SemanticsPropertyReceiver.textFieldTrailingIconTintArgb by TextFieldTrailingIconTintArgbSemantics
var SemanticsPropertyReceiver.textFieldTrailingIconTintRes by TextFieldTrailingIconTintResSemantics

// ----------- For Single Line --------------------
var SemanticsPropertyReceiver.textFieldSingleLine by TextFieldSingleLineSemantics

// ----------- For Keyboard Options --------------------
var SemanticsPropertyReceiver.textFieldKeyboardOptionsKeyboardType by TextFieldKeyboardOptionsKeyboardTypeSemantics
var SemanticsPropertyReceiver.textFieldKeyboardOptionsImeAction by TextFieldKeyboardOptionsImeActionSemantics

// ----------- For Visual Transformation --------------------
var SemanticsPropertyReceiver.textFieldVisualTransformation by TextFieldVisualTransformationSemantics
