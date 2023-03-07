package emperorfin.android.militaryjet.ui.screens.authentication.uicomponents

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.militaryjet.test.R
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasIconContentDescription
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasIconImageVector
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasIconTintArgb
import emperorfin.android.militaryjet.ui.extensions.semanticsmatcher.hasTextColorArgb
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.EIGHT_CHARACTERS
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT_ICON
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 02th February, 2023.
 */


/**
 * This class is a clean version of [RequirementTest] class.
 *
 * Important:
 *
 * - Try not to run all the test cases by running this test class as some tests might fail. If you do
 * and some tests fail, try running them one after the other. If a test fails, check the test
 * function's KDoc/comment (if any) on possible solution to make the test pass when it should.
 * - If you try to run a test and it fails, check the test function's KDoc/comment (if any) on
 * possible solution to make the test pass when it should.
 * - Test cases with "_AnotherApproachX" (where X may or may not contain a number) suffixed in their
 * function names might fail. A little changes would need to be made for them to pass. Kindly take a
 * look at the function's KDoc/comment on how to make the test pass when it should.
 *
 * Notes:
 *
 * - If you ever need to pass a resource (e.g. a string resource) into a composable during testing,
 * be sure to use the one from the main source set and the R must be the one from
 * [emperorfin.android.militaryjet.R] and not
 * [emperorfin.android.militaryjet.test.R].
 * - Every other thing during testing that involves the use of a resource (e.g. a string resource)
 * such as performing matches or assertions, be sure to use the resource from the androidTest source
 * set (which you should've provided a copy and always in sync with the one from the main source set).
 * And the R must be the one from [emperorfin.android.militaryjet.test.R] instead of
 * [emperorfin.android.militaryjet.R].
 *
 * Be sure to have Configured res srcDirs for androidTest sourceSet in app/build.gradle file.
 * See the following:
 * - https://stackoverflow.com/questions/36955608/espresso-how-to-use-r-string-resources-of-androidtest-folder
 * - https://stackoverflow.com/questions/26663539/configuring-res-srcdirs-for-androidtest-sourceset
 */
class RequirementTest2 {

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

        @StringRes
        private val MAIN_SOURCE_SET_STRING_RES_AT_LEAST_EIGHT_CHARACTERS: Int = EIGHT_CHARACTERS.label

        private val IMAGE_VECTOR_ICONS_DEFAULT_CHECK: ImageVector = Icons.Default.Check

    }

    /**
     * Use this when resources are coming from the main source set, whether directly
     * (e.g. R.string.sample_text) or indirectly (e.g. [EIGHT_CHARACTERS.label]
     * which is directly using a string resource).
     *
     * To actually reference the resource, you use
     * [emperorfin.android.militaryjet.R] and not
     * [emperorfin.android.militaryjet.test.R]
     *
     * So let's say if you want to reference a string resource, that string resource should come
     * from app/src/main/res/values/strings.xml XML resource file which must be, as you may have
     * noticed, from the main source set.
     */
    private lateinit var mTargetContext: Context

    /**
     * Use this when resources are coming from the androidTest or test source set. In this case, the
     * resources should come from androidTest (not test) source set.
     *
     * To actually reference the resource, you use
     * [emperorfin.android.militaryjet.test.R] and not
     * [emperorfin.android.militaryjet.R]
     *
     * So let's say if you want to reference a string resource, that string resource should come
     * from app/src/androidTest/res/values/strings.xml XML resource file which must be, as you may
     * have noticed, from the androidTest source set. And always update this file with the changes
     * made to the app/src/main/res/values/strings.xml XML resource file from the main source set.
     * And of course, you may/should re-run existing test(s) to be sure they don't fail as a result
     * of the synchronization.
     *
     * Be sure to have Configured res srcDirs for androidTest sourceSet in app/build.gradle file.
     * See the following:
     * - https://stackoverflow.com/questions/36955608/espresso-how-to-use-r-string-resources-of-androidtest-folder
     * - https://stackoverflow.com/questions/26663539/configuring-res-srcdirs-for-androidtest-sourceset
     */
    private lateinit var mContext: Context

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    // ------- FOR ..._AnotherApproach()

    private fun setContentAsRequirementAndAssertItIsDisplayedAnotherApproach(
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

    private fun setContentAsRequirementAndAssertItIsDisplayed(
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

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharactersAndTextColorArgbTintPrimary(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharacters().and(
                other = hasTextColorArgbTintPrimary()
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharactersAndTextColorArgbTintOnSurface(
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

    private fun onNodeWithPasswordRequirementIconAndIconContentDescriptionPasswordRequirementSatisfied(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementIconAnd(
            otherMatcher = hasIconContentDescriptionPasswordRequirementSatisfied(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementIconAndIconContentDescriptionPasswordRequirementNeeded(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementIconAnd(
            otherMatcher = hasIconContentDescriptionPasswordRequirementNeeded(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementIconAndIconImageVectorIconsDefaultCheckAndIconTintArgbTintOnSurface(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementIconAnd(
            otherMatcher = hasIconImageVectorIconsDefaultCheck().and(
                other = hasIconTintArgbTintOnSurface()
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementIconAndIconImageVectorIconsDefaultCheckAndIconTintArgbTintPrimary(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementIconAnd(
            otherMatcher = hasIconImageVectorIconsDefaultCheck().and(
                other = hasIconTintArgbTintPrimary()
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfiedAndTextColorArgbTintPrimary(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharactersSatisfied().and(
                other = hasTextColorArgbTintPrimary()
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeededAndTextColorArgbTintOnSurface(
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

    @Before
    fun setUpContexts() {

        // See field's KDoc for more info.
        mTargetContext = InstrumentationRegistry.getInstrumentation().targetContext
//        mTargetContext = ApplicationProvider.getApplicationContext<Context>() // Haven't tested but might work.
        // See field's KDoc for more info.
        mContext = InstrumentationRegistry.getInstrumentation().context

    }

    @Test
    fun icon_Image_Vector_Displays_With_Default_Color_When_Password_Requirement_Not_Satisfied() {

        val requirementMessage: String = mTargetContext.getString(MAIN_SOURCE_SET_STRING_RES_AT_LEAST_EIGHT_CHARACTERS)

        val requirementSatisfied: Boolean = FALSE

        setContentAsRequirementAndAssertItIsDisplayed(
            requirementMessage = requirementMessage,
            requirementSatisfied = requirementSatisfied
        )

        onNodeWithPasswordRequirementIconAndIconImageVectorIconsDefaultCheckAndIconTintArgbTintOnSurface(
            useUnmergedTree = TRUE
        )
            .assertIsDisplayed()

    }

    @Test
    fun icon_Image_Vector_Displays_With_Changed_Color_When_Password_Requirement_Satisfied() {

        val requirementMessage: String = mTargetContext.getString(MAIN_SOURCE_SET_STRING_RES_AT_LEAST_EIGHT_CHARACTERS)

        val requirementSatisfied: Boolean = TRUE

        setContentAsRequirementAndAssertItIsDisplayed(
            requirementMessage = requirementMessage,
            requirementSatisfied = requirementSatisfied
        )

        onNodeWithPasswordRequirementIconAndIconImageVectorIconsDefaultCheckAndIconTintArgbTintPrimary(
            useUnmergedTree = TRUE
        )
            .assertIsDisplayed()

    }

    @Test
    fun icon_Content_Description_Exists_When_Password_Requirement_Not_Satisfied() {

        val requirementMessage: String = mTargetContext.getString(MAIN_SOURCE_SET_STRING_RES_AT_LEAST_EIGHT_CHARACTERS)

        val requirementSatisfied: Boolean = FALSE

        setContentAsRequirementAndAssertItIsDisplayed(
            requirementMessage = requirementMessage,
            requirementSatisfied = requirementSatisfied
        )

        onNodeWithPasswordRequirementIconAndIconContentDescriptionPasswordRequirementNeeded(
            useUnmergedTree = TRUE
        )
            .assertIsDisplayed()

    }

    @Test
    fun icon_Content_Description_Changed_When_Password_Requirement_Satisfied() {

        val requirementMessage: String = mTargetContext.getString(MAIN_SOURCE_SET_STRING_RES_AT_LEAST_EIGHT_CHARACTERS)

        val requirementSatisfied: Boolean = TRUE

        setContentAsRequirementAndAssertItIsDisplayed(
            requirementMessage = requirementMessage,
            requirementSatisfied = requirementSatisfied
        )

        // Optional
        onNodeWithPasswordRequirementIconAndIconContentDescriptionPasswordRequirementNeeded(
            useUnmergedTree = TRUE
        )
            .assertDoesNotExist()

        onNodeWithPasswordRequirementIconAndIconContentDescriptionPasswordRequirementSatisfied(
            useUnmergedTree = TRUE
        )
            .assertIsDisplayed()

    }

    @Test
    fun text_Displays_With_Default_Color_When_Password_Requirement_Not_Satisfied() {

        val requirementMessage: String = mTargetContext.getString(MAIN_SOURCE_SET_STRING_RES_AT_LEAST_EIGHT_CHARACTERS)

        val requirementSatisfied: Boolean = FALSE

        setContentAsRequirementAndAssertItIsDisplayed(
            requirementMessage = requirementMessage,
            requirementSatisfied = requirementSatisfied
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeededAndTextColorArgbTintOnSurface(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun text_Displays_With_Default_Color_When_Password_Requirement_Not_Satisfied_AnotherApproach() {

        val requirementMessage: String = mTargetContext.getString(MAIN_SOURCE_SET_STRING_RES_AT_LEAST_EIGHT_CHARACTERS)

        val requirementSatisfied: Boolean = FALSE

        setContentAsRequirementAndAssertItIsDisplayedAnotherApproach(
            requirementMessage = requirementMessage,
            requirementSatisfied = requirementSatisfied
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharactersAndTextColorArgbTintOnSurface(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    @Test
    fun text_Displays_With_Changed_Color_When_Password_Requirement_Satisfied() {

        val requirementMessage: String = mTargetContext.getString(MAIN_SOURCE_SET_STRING_RES_AT_LEAST_EIGHT_CHARACTERS)

        val requirementSatisfied: Boolean = TRUE

        setContentAsRequirementAndAssertItIsDisplayed(
            requirementMessage = requirementMessage,
            requirementSatisfied = requirementSatisfied
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfiedAndTextColorArgbTintPrimary(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/militaryjet/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun text_Displays_With_Changed_Color_When_Password_Requirement_Satisfied_AnotherApproach() {

        val requirementMessage: String = mTargetContext.getString(MAIN_SOURCE_SET_STRING_RES_AT_LEAST_EIGHT_CHARACTERS)

        val requirementSatisfied: Boolean = TRUE

        setContentAsRequirementAndAssertItIsDisplayedAnotherApproach(
            requirementMessage = requirementMessage,
            requirementSatisfied = requirementSatisfied
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharactersAndTextColorArgbTintPrimary(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

}