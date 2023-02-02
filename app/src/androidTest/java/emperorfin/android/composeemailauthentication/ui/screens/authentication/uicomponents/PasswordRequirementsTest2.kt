package emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.composeemailauthentication.test.R
import emperorfin.android.composeemailauthentication.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.PasswordRequirement.EIGHT_CHARACTERS
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.PasswordRequirement.CAPITAL_LETTER
import emperorfin.android.composeemailauthentication.ui.screens.authentication.enums.PasswordRequirement.NUMBER
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT
import emperorfin.android.composeemailauthentication.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENTS
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Tuesday 31st January, 2023.
 */


/**
 * This class is a clean version of [PasswordRequirementsTest] class.
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
 * [emperorfin.android.composeemailauthentication.R] and not
 * [emperorfin.android.composeemailauthentication.test.R].
 * - Every other thing during testing that involves the use of a resource (e.g. a string resource)
 * such as performing matches or assertions, be sure to use the resource from the androidTest source
 * set (which you should've provided a copy and always in sync with the one from the main source set).
 * And the R must be the one from [emperorfin.android.composeemailauthentication.test.R] instead of
 * [emperorfin.android.composeemailauthentication.R].
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

    }

    /**
     * Use this when resources are coming from the main source set, whether directly
     * (e.g. R.string.sample_text) or indirectly (e.g. [EIGHT_CHARACTERS.label]
     * which is directly using a string resource).
     *
     * To actually reference the resource, you use
     * [emperorfin.android.composeemailauthentication.R] and not
     * [emperorfin.android.composeemailauthentication.test.R]
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
     * [emperorfin.android.composeemailauthentication.test.R] and not
     * [emperorfin.android.composeemailauthentication.R]
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

        return composeTestRule
            .onNode(
                matcher = hasTestTag(
                    testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENTS
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    // ------- FOR ..._AnotherApproach()

    private fun onNodeWithPasswordRequirementAtLeastEightCharsSatisfiedForAtLeastEightChars(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharsSatisfiedAnd(
            otherMatcher = hasTextExactly(
                mContext.getString(R.string.password_requirement_characters),
                includeEditableText = FALSE
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedForAtLeastOneUppercaseLetter(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedAnd(
            otherMatcher = hasTextExactly(
                mContext.getString(R.string.password_requirement_capital),
                includeEditableText = FALSE
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedForAtLeastOneDigit(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedAnd(
            otherMatcher = hasTextExactly(
                mContext.getString(R.string.password_requirement_digit),
                includeEditableText = FALSE
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastEightCharsNeededForAtLeastEightChars(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharsNeededAnd(
            otherMatcher = hasTextExactly(
                mContext.getString(R.string.password_requirement_characters),
                includeEditableText = FALSE
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededForAtLeastOneUppercaseLetter(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededAnd(
            otherMatcher = hasTextExactly(
                mContext.getString(R.string.password_requirement_capital),
                includeEditableText = FALSE
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitNeededForAtLeastOneDigit(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneDigitNeededAnd(
            otherMatcher = hasTextExactly(
                mContext.getString(R.string.password_requirement_digit),
                includeEditableText = FALSE
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    // ------- /FOR ..._AnotherApproach()

    private fun onNodeWithPasswordRequirementAtLeastEightCharsForAtLeastEightCharsSatisfied(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharsAnd(
            otherMatcher = hasTextExactly(
                mContext.getString(R.string.test_password_requirement_satisfied_characters),
                includeEditableText = FALSE
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterForAtLeastOneUppercaseLetterSatisfied(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAnd(
            otherMatcher = hasTextExactly(
                mContext.getString(R.string.test_password_requirement_satisfied_capital),
                includeEditableText = FALSE
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitForAtLeastOneDigitSatisfied(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneDigitAnd(
            otherMatcher = hasTextExactly(
                mContext.getString(R.string.test_password_requirement_satisfied_digit),
                includeEditableText = FALSE
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastEightCharsForAtLeastEightCharsNeeded(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastEightCharsAnd(
            otherMatcher = hasTextExactly(
                mContext.getString(R.string.test_password_requirement_needed_characters),
                includeEditableText = FALSE
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneUppercaseLetterForAtLeastOneUppercaseLetterNeeded(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneUppercaseLetterAnd(
            otherMatcher = hasTextExactly(
                mContext.getString(R.string.test_password_requirement_needed_capital),
                includeEditableText = FALSE
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithPasswordRequirementAtLeastOneDigitForAtLeastOneDigitNeeded(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithPasswordRequirementAtLeastOneDigitAnd(
            otherMatcher = hasTextExactly(
                mContext.getString(R.string.test_password_requirement_needed_digit),
                includeEditableText = FALSE
            ),
            useUnmergedTree = useUnmergedTree
        )

    }

    // ------- FOR ..._AnotherApproach()

    private fun onNodeWithPasswordRequirementAtLeastEightCharsSatisfiedAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastEightCharsSatisfied().and(
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
                matcher = hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLettersSatisfied().and(
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

    private fun onNodeWithPasswordRequirementAtLeastEightCharsNeededAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastEightCharsNeeded().and(
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
                matcher = hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLettersNeeded().and(
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

    private fun onNodeWithPasswordRequirementAtLeastEightCharsAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagsPasswordRequirementAndAtLeastEightChars().and(
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

    // ------- FOR ..._AnotherApproach()

    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharsSatisfied(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(R.string.test_password_requirement_satisfied_characters)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLettersSatisfied(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(R.string.test_password_requirement_satisfied_capital)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneDigitSatisfied(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(R.string.test_password_requirement_satisfied_digit)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharsNeeded(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(R.string.test_password_requirement_needed_characters)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLettersNeeded(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(R.string.test_password_requirement_needed_capital)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneDigitNeeded(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(R.string.test_password_requirement_needed_digit)
        )

    }

    // ------- /FOR ..._AnotherApproach()

    private fun hasTestTagsPasswordRequirementAndAtLeastEightChars(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(R.string.password_requirement_characters)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetter(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(R.string.password_requirement_capital)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneDigit(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(R.string.password_requirement_digit)
        )

    }

    private fun hasTestTagsPasswordRequirementAnd(
        otherTestTag: String
    ): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_PASSWORD_REQUIREMENT + otherTestTag
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

        onNodeWithPasswordRequirementAtLeastEightCharsForAtLeastEightCharsNeeded(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterForAtLeastOneUppercaseLetterNeeded(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitForAtLeastOneDigitNeeded(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun no_Password_Requirement_Satisfied_AnotherApproach() {

        val satisfiedRequirements = emptyList<PasswordRequirement>()

        setContentAsPasswordRequirementsAndAssertItIsDisplayed(
            satisfiedRequirements = satisfiedRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharsNeededForAtLeastEightChars(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededForAtLeastOneUppercaseLetter(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitNeededForAtLeastOneDigit(
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

        onNodeWithPasswordRequirementAtLeastEightCharsForAtLeastEightCharsSatisfied(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterForAtLeastOneUppercaseLetterNeeded(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitForAtLeastOneDigitNeeded(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun only_At_Least_Eight_Characters_Satisfied_AnotherApproach() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(EIGHT_CHARACTERS)

        setContentAsPasswordRequirementsAndAssertItIsDisplayed(
            satisfiedRequirements = satisfiedRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharsSatisfiedForAtLeastEightChars(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededForAtLeastOneUppercaseLetter(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitNeededForAtLeastOneDigit(
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

        onNodeWithPasswordRequirementAtLeastEightCharsForAtLeastEightCharsNeeded(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterForAtLeastOneUppercaseLetterSatisfied(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitForAtLeastOneDigitNeeded(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun only_At_Least_One_Uppercase_Letter_Satisfied_AnotherApproach() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(CAPITAL_LETTER)

        setContentAsPasswordRequirementsAndAssertItIsDisplayed(
            satisfiedRequirements = satisfiedRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharsNeededForAtLeastEightChars(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedForAtLeastOneUppercaseLetter(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitNeededForAtLeastOneDigit(
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

        onNodeWithPasswordRequirementAtLeastEightCharsForAtLeastEightCharsNeeded(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterForAtLeastOneUppercaseLetterNeeded(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitForAtLeastOneDigitSatisfied(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
     */
    @Test
    fun only_At_Least_One_Digit_Satisfied_AnotherApproach() {

        val satisfiedRequirements: List<PasswordRequirement> = listOf(NUMBER)

        setContentAsPasswordRequirementsAndAssertItIsDisplayed(
            satisfiedRequirements = satisfiedRequirements
        )

        onNodeWithPasswordRequirementAtLeastEightCharsNeededForAtLeastEightChars(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterNeededForAtLeastOneUppercaseLetter(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedForAtLeastOneDigit(
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

        onNodeWithPasswordRequirementAtLeastEightCharsForAtLeastEightCharsSatisfied(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterForAtLeastOneUppercaseLetterSatisfied(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitForAtLeastOneDigitSatisfied(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

    }

    /**
     * For this test case to pass when it should, the code in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt
     * would need to be changed to be like the one in the file
     * app/src/main/java/emperorfin/android/composeemailauthentication/ui/screens/authentication/uicomponents/Requirement.kt.another_approach
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

        onNodeWithPasswordRequirementAtLeastEightCharsSatisfiedForAtLeastEightChars(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneUppercaseLetterSatisfiedForAtLeastOneUppercaseLetter(
            useUnmergedTree = TRUE // Optional
        )
            .assertIsDisplayed()

        onNodeWithPasswordRequirementAtLeastOneDigitSatisfiedForAtLeastOneDigit(
//            useUnmergedTree = TRUE // Optional
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