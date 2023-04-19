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

package emperorfin.android.militaryjet.ui.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import emperorfin.android.militaryjet.test.R
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasIconContentDescription
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasIconImageVector
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasIconTintArgb
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasTextColorArgb
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.Requirement
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT_ICON


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Sunday 19th March, 2023.
 */


class RequirementTestUtil(
    private val mContext: Context,
    private val mTargetContext: Context,
    private val composeTestRule: ComposeContentTestRule
) {

    private companion object {

        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        private const val THIS_STRING_MUST_BE_EMPTY: String = ""

        /**
         * To easily find out the ARGB of a color to be used as the expected value during assertion,
         * there are two approaches to do this:
         *
         * - you can either call toArgb() on the color object and then logcat the returned value or
         * - you can just use any random assertion expected value, run the test case (which should
         * fail) and then get and use (as the correct assertion expected value) the assertion actual
         * value from the failed test error message (simplest approach).
         */
        private const val COLOR_ARGB_TINT_PRIMARY: Int = -11576430
        /**
         * To easily find out the ARGB of a color to be used as the expected value during assertion,
         * there are two approaches to do this:
         *
         * - you can either call toArgb() on the color object and then logcat the returned value or
         * - you can just use any random assertion expected value, run the test case (which should
         * fail) and then get and use (as the correct assertion expected value) the assertion actual
         * value from the failed test error message (simplest approach).
         */
        private const val COLOR_ARGB_TINT_ON_SURFACE: Int = 1713052447

        @StringRes
        private const val STRING_RES_AT_LEAST_EIGHT_CHARACTERS: Int = R.string.password_requirement_characters
        @StringRes
        private const val STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED: Int = R.string.test_password_requirement_needed_characters
        @StringRes
        private const val STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED: Int = R.string.test_password_requirement_satisfied_characters

        @StringRes
        private const val STRING_RES_PASSWORD_REQUIREMENT_NEEDED: Int = R.string.content_description_icon_password_requirement_needed
        @StringRes
        private const val STRING_RES_PASSWORD_REQUIREMENT_SATISFIED: Int = R.string.content_description_icon_password_requirement_satisfied

        private val IMAGE_VECTOR_ICONS_DEFAULT_CHECK: ImageVector = Icons.Default.Check

    }

    // ------- FOR ..._AnotherApproach()

    fun setContentAsRequirementAndAssertItIsDisplayedAnotherApproach(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        requirementMessage: String,
        requirementSatisfied: Boolean
    ) {

        setContentAsRequirement(
            composeTestRule = composeTestRule,
            requirementMessage = requirementMessage,
            requirementSatisfied = requirementSatisfied
        )

        if (requirementSatisfied) {
            assertPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharactersAndTextColorArgbTintPrimaryIsDisplayedAnotherApproach()
        } else {
            assertPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharactersAndTextColorArgbTintOnSurfaceIsDisplayedAnotherApproach()
        }

        onNodeWithPasswordRequirementIconAndIconImageVectorIconsDefaultCheck(
            useUnmergedTree = TRUE
        )
            .assertIsDisplayed()

    }

    // ------- /FOR ..._AnotherApproach()

    fun setContentAsRequirementAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        requirementMessage: String,
        requirementSatisfied: Boolean
    ) {

        setContentAsRequirement(
            composeTestRule = composeTestRule,
            requirementMessage = requirementMessage,
            requirementSatisfied = requirementSatisfied
        )

        if (requirementSatisfied) {
            assertPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfiedAndTextColorArgbTintPrimaryIsDisplayed()
        } else {
            assertPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeededAndTextColorArgbTintOnSurfaceIsDisplayed()
        }

        onNodeWithPasswordRequirementIconAndIconImageVectorIconsDefaultCheck(
            useUnmergedTree = TRUE
        )
            .assertIsDisplayed()

    }

    private fun setContentAsRequirement(
        composeTestRule: ComposeContentTestRule,
        requirementMessage: String,
        requirementSatisfied: Boolean
    ) {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                Requirement(
                    message = requirementMessage,
                    satisfied = requirementSatisfied
                )
            }
        }

    }

    // ------- FOR ..._AnotherApproach()

    /**
     * This should be called in all test cases (with the _AnotherApproach suffixed in their names)
     * immediately after composing the [Requirement] composable in the
     * [ComposeContentTestRule.setContent] when password requirement is not satisfied.
     */
    private fun assertPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharactersAndTextColorArgbTintPrimaryIsDisplayedAnotherApproach(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharactersAndTextColorArgbTintPrimary(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    /**
     * This should be called in all test cases (with the _AnotherApproach suffixed in their names)
     * immediately after composing the [Requirement] composable in the
     * [ComposeContentTestRule.setContent] when password requirement is not satisfied.
     */
    private fun assertPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharactersAndTextColorArgbTintOnSurfaceIsDisplayedAnotherApproach(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharactersAndTextColorArgbTintOnSurface(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    // ------- /FOR ..._AnotherApproach()

    /**
     * This should be called in all test cases (without the _AnotherApproach suffixed in their names)
     * immediately after composing the [Requirement] composable in the
     * [ComposeContentTestRule.setContent] when password requirement is satisfied.
     */
    private fun assertPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfiedAndTextColorArgbTintPrimaryIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfiedAndTextColorArgbTintPrimary(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    /**
     * This should be called in all test cases (without the _AnotherApproach suffixed in their names)
     * immediately after composing the [Requirement] composable in the
     * [ComposeContentTestRule.setContent] when password requirement is not satisfied.
     */
    private fun assertPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeededAndTextColorArgbTintOnSurfaceIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeededAndTextColorArgbTintOnSurface(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    // ------- FOR ..._AnotherApproach()

    fun onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharactersAndTextColorArgbTintPrimary(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharacters().and(
                other = hasTextColorArgbTintPrimary()
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharactersAndTextColorArgbTintOnSurface(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersNeededAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharacters().and(
                other = hasTextColorArgbTintOnSurface()
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    // ------- /FOR ..._AnotherApproach()

    private fun onNodeWithPasswordRequirementIconAndIconImageVectorIconsDefaultCheck(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementIconAnd(
            otherMatcher = hasIconImageVectorIconsDefaultCheck(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordRequirementIconAndIconContentDescriptionPasswordRequirementSatisfied(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementIconAnd(
            otherMatcher = hasIconContentDescriptionPasswordRequirementSatisfied(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordRequirementIconAndIconContentDescriptionPasswordRequirementNeeded(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementIconAnd(
            otherMatcher = hasIconContentDescriptionPasswordRequirementNeeded(),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordRequirementIconAndIconImageVectorIconsDefaultCheckAndIconTintArgbTintOnSurface(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementIconAnd(
            otherMatcher = hasIconImageVectorIconsDefaultCheck().and(
                other = hasIconTintArgbTintOnSurface()
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordRequirementIconAndIconImageVectorIconsDefaultCheckAndIconTintArgbTintPrimary(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementIconAnd(
            otherMatcher = hasIconImageVectorIconsDefaultCheck().and(
                other = hasIconTintArgbTintPrimary()
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfiedAndTextColorArgbTintPrimary(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharactersSatisfied().and(
                other = hasTextColorArgbTintPrimary()
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    fun onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeededAndTextColorArgbTintOnSurface(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharactersNeeded().and(
                other = hasTextColorArgbTintOnSurface()
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    // ------- FOR ..._AnotherApproach()

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastEightCharactersSatisfied().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersNeededAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastEightCharactersNeeded().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    // ------- /FOR ..._AnotherApproach()

    private fun onNodeWithPasswordRequirementIconAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagPasswordRequirementIcon().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastEightCharacters().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun hasTestTagPasswordRequirementIcon(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementIconAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    // ------- FOR ..._AnotherApproach()

    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharactersSatisfied(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharactersNeeded(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED)
        )

    }

    // ------- /FOR ..._AnotherApproach()

    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharacters(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS)
        )

    }

    private fun hasTestTagsPasswordRequirementIconAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT_ICON + otherTestTag
        )

    }

    private fun hasTestTagsPasswordRequirementAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT + otherTestTag
        )

    }

    // Before using a semantics matcher, check the implementation of the utility functions in this
    // section if it's already available to avoid duplication.
    // The function names make the check easier.

    private fun hasIconImageVectorIconsDefaultCheck(): SemanticsMatcher {

        return hasIconImageVector(
            iconImageVector = IMAGE_VECTOR_ICONS_DEFAULT_CHECK
        )

    }

    private fun hasIconTintArgbTintPrimary(): SemanticsMatcher {

        return hasIconTintArgb(
            iconTintInArgb = COLOR_ARGB_TINT_PRIMARY
        )

    }

    private fun hasIconTintArgbTintOnSurface(): SemanticsMatcher {

        return hasIconTintArgb(
            iconTintInArgb = COLOR_ARGB_TINT_ON_SURFACE
        )

    }

    private fun hasIconContentDescriptionPasswordRequirementNeeded(): SemanticsMatcher {

        return hasIconContentDescription(
            iconContentDescription = mContext.getString(
                STRING_RES_PASSWORD_REQUIREMENT_NEEDED
            )
        )

    }

    private fun hasIconContentDescriptionPasswordRequirementSatisfied(): SemanticsMatcher {

        return hasIconContentDescription(
            iconContentDescription = mContext.getString(
                STRING_RES_PASSWORD_REQUIREMENT_SATISFIED
            )
        )

    }

    private fun hasTextColorArgbTintPrimary(): SemanticsMatcher {

        return hasTextColorArgb(
            textColorInArgb = COLOR_ARGB_TINT_PRIMARY
        )

    }

    private fun hasTextColorArgbTintOnSurface(): SemanticsMatcher {

        return hasTextColorArgb(
            textColorInArgb = COLOR_ARGB_TINT_ON_SURFACE
        )

    }

    private fun hasTextExactlyAtLeastEightCharacters(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastEightCharactersNeeded(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastEightCharactersSatisfied(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED),
            includeEditableText = FALSE
        )

    }

}