package emperorfin.android.militaryjet.ui.screens.authentication.uicomponents

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.militaryjet.test.R
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.EIGHT_CHARACTERS
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.CAPITAL_LETTER
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.NUMBER
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENTS
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 31st January, 2023.
 */


/**
 * This class is a clean version of [PasswordRequirementsTest] class. For a cleaner version, see
 * [PasswordRequirementsTest3] class.
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
class PasswordRequirementsTest2 {

    private companion object {

        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        private const val THIS_STRING_MUST_BE_EMPTY: String = ""

        @StringRes
        private const val STRING_RES_AT_LEAST_EIGHT_CHARACTERS: Int = R.string.password_requirement_characters
        @StringRes
        private const val STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER: Int = R.string.password_requirement_capital
        @StringRes
        private const val STRING_RES_AT_LEAST_ONE_DIGIT: Int = R.string.password_requirement_digit
        @StringRes
        private const val STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED: Int = R.string.test_password_requirement_needed_characters
        @StringRes
        private const val STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_NEEDED: Int = R.string.test_password_requirement_needed_capital
        @StringRes
        private const val STRING_RES_AT_LEAST_ONE_DIGIT_NEEDED: Int = R.string.test_password_requirement_needed_digit
        @StringRes
        private const val STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED: Int = R.string.test_password_requirement_satisfied_characters
        @StringRes
        private const val STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_SATISFIED: Int = R.string.test_password_requirement_satisfied_capital
        @StringRes
        private const val STRING_RES_AT_LEAST_ONE_DIGIT_SATISFIED: Int = R.string.test_password_requirement_satisfied_digit

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

    private fun setContentAsPasswordRequirementsAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        satisfiedRequirements: List<PasswordRequirement> = emptyList()
    ) {

        setContentAsPasswordRequirements(
            composeTestRule = composeTestRule,
            satisfiedRequirements = satisfiedRequirements
        )

        assertPasswordRequirementsIsDisplayed()

    }

    private fun setContentAsPasswordRequirements(
        composeTestRule: ComposeContentTestRule,
        satisfiedRequirements: List<PasswordRequirement>
    ) {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordRequirements(satisfiedRequirements = satisfiedRequirements)
            }
        }

    }

    /**
     * This should be called in all test cases immediately after composing the [PasswordRequirements]
     * composable in the [ComposeContentTestRule.setContent].
     */
    private fun assertPasswordRequirementsIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithPasswordRequirements()
            .assertIsDisplayed()

    }

    private fun onNodeWithPasswordRequirements(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        val thisStringCouldBeAnything = ""

        return onNodeWithPasswordRequirementsAnd(
            otherMatcher = hasTextExactly(
                thisStringCouldBeAnything,
                includeEditableText = FALSE
            ).not(),
            useUnmergedTree = useUnmergedTree
        )

    }

    // ------- FOR ..._AnotherApproach()

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharacters(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharacters(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAndTextExactlyAtLeastOneUppercaseLetter(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAnd(
            otherMatcher = hasTextExactlyAtLeastOneUppercaseLetter(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAndTextExactlyAtLeastOneDigit(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAnd(
            otherMatcher = hasTextExactlyAtLeastOneDigit(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersNeededAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharacters(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAnd(
            otherMatcher = hasTextExactlyAtLeastOneUppercaseLetter(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneDigitNeededAnd(
            otherMatcher = hasTextExactlyAtLeastOneDigit(),
            useUnmergedTree = useUnmergedTree
        )

    }

    // ------- /FOR ..._AnotherApproach()

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfied(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharactersSatisfied(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterSatisfied(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAnd(
            otherMatcher = hasTextExactlyAtLeastOneUppercaseLetterSatisfied(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitSatisfied(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneDigitAnd(
            otherMatcher = hasTextExactlyAtLeastOneDigitSatisfied(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharactersAnd(
            otherMatcher = hasTextExactlyAtLeastEightCharactersNeeded(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAnd(
            otherMatcher = hasTextExactlyAtLeastOneUppercaseLetterNeeded(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneDigitAnd(
            otherMatcher = hasTextExactlyAtLeastOneDigitNeeded(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementsAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagPasswordRequirements().and(
                    other = otherMatcher
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

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetterSatisfied().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastOneDigitSatisfied().and(
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

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetterNeeded().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitNeededAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastOneDigitNeeded().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    // ------- /FOR ..._AnotherApproach()

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

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetter().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastOneDigit().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun hasTestTagPasswordRequirements(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementsAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagsPasswordRequirementsAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENTS + otherTestTag
        )

    }

    // ------- FOR ..._AnotherApproach()

    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharactersSatisfied(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetterSatisfied(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_SATISFIED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneDigitSatisfied(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT_SATISFIED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharactersNeeded(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetterNeeded(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_NEEDED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneDigitNeeded(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT_NEEDED)
        )

    }

    // ------- /FOR ..._AnotherApproach()

    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharacters(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetter(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneDigit(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT)
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

    private fun hasTextExactlyAtLeastEightCharacters(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastOneUppercaseLetter(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastOneDigit(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastEightCharactersNeeded(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastOneUppercaseLetterNeeded(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_NEEDED),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastOneDigitNeeded(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT_NEEDED),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastEightCharactersSatisfied(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastOneUppercaseLetterSatisfied(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_SATISFIED),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAtLeastOneDigitSatisfied(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT_SATISFIED),
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
    fun no_Password_Requirement_Satisfied() {

        val satisfiedRequirements = emptyList<PasswordRequirement>()

        setContentAsPasswordRequirementsAndAssertItIsDisplayed(
            satisfiedRequirements = satisfiedRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
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
    fun no_Password_Requirement_Satisfied_AnotherApproach() {

        val satisfiedRequirements = emptyList<PasswordRequirement>()

        setContentAsPasswordRequirementsAndAssertItIsDisplayed(
            satisfiedRequirements = satisfiedRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    @Test
    fun only_At_Least_Eight_Characters_Satisfied() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(EIGHT_CHARACTERS)

        setContentAsPasswordRequirementsAndAssertItIsDisplayed(
            satisfiedRequirements = satisfiedRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfied(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
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
    fun only_At_Least_Eight_Characters_Satisfied_AnotherApproach() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(EIGHT_CHARACTERS)

        setContentAsPasswordRequirementsAndAssertItIsDisplayed(
            satisfiedRequirements = satisfiedRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharacters(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    @Test
    fun only_At_Least_One_Uppercase_Letter_Satisfied() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(CAPITAL_LETTER)

        setContentAsPasswordRequirementsAndAssertItIsDisplayed(
            satisfiedRequirements = satisfiedRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterSatisfied(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitNeeded(
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
    fun only_At_Least_One_Uppercase_Letter_Satisfied_AnotherApproach() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(CAPITAL_LETTER)

        setContentAsPasswordRequirementsAndAssertItIsDisplayed(
            satisfiedRequirements = satisfiedRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAndTextExactlyAtLeastOneUppercaseLetter(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitNeededAndTextExactlyAtLeastOneDigit(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    @Test
    fun only_At_Least_One_Digit_Satisfied() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(NUMBER)

        setContentAsPasswordRequirementsAndAssertItIsDisplayed(
            satisfiedRequirements = satisfiedRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersNeeded(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterNeeded(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitSatisfied(
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
    fun only_At_Least_One_Digit_Satisfied_AnotherApproach() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(NUMBER)

        setContentAsPasswordRequirementsAndAssertItIsDisplayed(
            satisfiedRequirements = satisfiedRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersNeededAndTextExactlyAtLeastEightCharacters(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAndTextExactlyAtLeastOneUppercaseLetter(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAndTextExactlyAtLeastOneDigit(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    @Test
    fun all_Password_Requirements_Satisfied() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(
            EIGHT_CHARACTERS,
            CAPITAL_LETTER,
            NUMBER
        )

        setContentAsPasswordRequirementsAndAssertItIsDisplayed(
            satisfiedRequirements = satisfiedRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersAndTextExactlyAtLeastEightCharactersSatisfied(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAndTextExactlyAtLeastOneUppercaseLetterSatisfied(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitAndTextExactlyAtLeastOneDigitSatisfied(
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
    fun all_Password_Requirements_Satisfied_AnotherApproach() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(
            EIGHT_CHARACTERS,
            CAPITAL_LETTER,
            NUMBER
        )

        setContentAsPasswordRequirementsAndAssertItIsDisplayed(
            satisfiedRequirements = satisfiedRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharactersSatisfiedAndTextExactlyAtLeastEightCharacters(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAndTextExactlyAtLeastOneUppercaseLetter(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAndTextExactlyAtLeastOneDigit(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    @Test
    fun password_Requirements_Displayed_With_State() {

        val requirements: List<PasswordRequirement> = PasswordRequirement.values().toList()

        val satisfied: PasswordRequirement = requirements[(0 until 3).random()]

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                PasswordRequirements(
                    satisfiedRequirements = listOf(satisfied)
                )
            }
        }

        PasswordRequirement.values().forEach {
            val requirement: String = mTargetContext.getString(it.label)

            val result: String = if (it == satisfied) {
                mContext.getString(R.string.password_requirement_satisfied, requirement)
            } else {
                mContext.getString(R.string.password_requirement_needed, requirement)
            }

            composeTestRule
                .onNodeWithText(
                    text = result
                )
                .assertIsDisplayed()
        }

    }

}