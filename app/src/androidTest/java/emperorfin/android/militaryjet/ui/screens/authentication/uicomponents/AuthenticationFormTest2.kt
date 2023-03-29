package emperorfin.android.militaryjet.ui.screens.authentication.uicomponents

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import emperorfin.android.militaryjet.test.R
import emperorfin.android.militaryjet.ui.res.theme.ComposeEmailAuthenticationTheme
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_IN
import emperorfin.android.militaryjet.ui.screens.authentication.enums.AuthenticationMode.SIGN_UP
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.EIGHT_CHARACTERS
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.CAPITAL_LETTER
import emperorfin.android.militaryjet.ui.screens.authentication.enums.PasswordRequirement.NUMBER
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATE_BUTTON
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_AUTHENTICATION_TITLE
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_FORM_CONTENT_AREA
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_PASSWORD_REQUIREMENT
import emperorfin.android.militaryjet.ui.screens.authentication.uicomponents.tags.Tags.TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 21st January, 2023.
 */


/**
 * The following classes are revisions of this class:
 * [AuthenticationFormTest3]
 * [AuthenticationFormTest4]
 *
 * The tests in this class are a subset of the ones in the [AuthenticationContentTest2] class but
 * with the tests in this class focused on testing the [AuthenticationForm] composable instead of
 * [AuthenticationContent] composable.
 *
 * Also, the other tests in the [AuthenticationContentTest2] class that are excluded in this class are
 * the ones that includes testing for error dialog (and error state) and progress indicator (and
 * loading state). These tests are excluded in this class since the [AuthenticationForm] composable
 * which is under test doesn't include the error dialog and progress indicator composables
 * (or components).
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
class AuthenticationFormTest2 {

    private companion object {

        private const val TRUE: Boolean = true
        private const val FALSE: Boolean = false

        private val NULL = null

        private const val THIS_STRING_MUST_BE_EMPTY: String = ""

        private const val INPUT_CONTENT_EMAIL_EMPTY: String = ""
        private const val INPUT_CONTENT_EMAIL: String = "contact@email.com"
        private const val INPUT_CONTENT_PASSWORD_EMPTY: String = ""
        private const val INPUT_CONTENT_PASSWORD: String = "passworD1"
        private const val INPUT_CONTENT_PASSWORD_PASSWORD: String = "password"
        private const val INPUT_CONTENT_PASSWORD_PASS: String = "Pass"
        private const val INPUT_CONTENT_PASSWORD_1PASS: String = "1pass"

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
        @StringRes
        private const val STRING_RES_SIGN_IN_TO_YOUR_ACCOUNT: Int = R.string.label_sign_in_to_account
        @StringRes
        private const val STRING_RES_SIGN_UP_FOR_AN_ACCOUNT: Int = R.string.label_sign_up_for_account
        @StringRes
        private const val STRING_RES_SIGN_IN: Int = R.string.action_sign_in
        @StringRes
        private const val STRING_RES_SIGN_UP: Int = R.string.action_sign_up
        @StringRes
        private const val STRING_RES_NEED_AN_ACCOUNT: Int = R.string.action_need_account
        @StringRes
        private const val STRING_RES_ALREADY_HAVE_AN_ACCOUNT: Int = R.string.action_already_have_account

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

    private fun setContentAsAuthenticationFormAndAssertItIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        authenticationMode: AuthenticationMode,
        email: String? = NULL,
        password: String? = NULL,
        passwordRequirements: List<PasswordRequirement> = emptyList(),
        enableAuthentication: Boolean
    ) {

        setContentAsAuthenticationForm(
            composeTestRule = composeTestRule,
            authenticationMode = authenticationMode,
            email = email,
            password = password,
            passwordRequirements = passwordRequirements,
            enableAuthentication = enableAuthentication
        )

        assertAuthenticationFormIsDisplayed(composeTestRule)

        if (authenticationMode == SIGN_IN) {
            assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed()
        } else if (authenticationMode == SIGN_UP) {
            assertAuthenticationTitleAndTextExactlySignUpForAnAccountIsDisplayed()
        }

    }

    private fun setContentAsAuthenticationForm(
        composeTestRule: ComposeContentTestRule,
        authenticationMode: AuthenticationMode,
        email: String?,
        password: String?,
        passwordRequirements: List<PasswordRequirement>,
        enableAuthentication: Boolean
    ) {

        composeTestRule.setContent {
            ComposeEmailAuthenticationTheme {
                AuthenticationForm(
                    authenticationMode = authenticationMode,
                    onToggleMode = { },
                    email = email,
                    onEmailChanged = { },
                    password = password,
                    onPasswordChanged = { },
                    completedPasswordRequirements = passwordRequirements,
                    enableAuthentication = enableAuthentication,
                    onAuthenticate = { }
                )
            }
        }

    }

    private fun assertSignInModeIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            composeTestRule = composeTestRule,
            authenticationMode = SIGN_IN,
            enableAuthentication = FALSE
        )

        /**
         * No longer necessary since it's now part of the implementation of
         * [setContentAsAuthenticationFormAndAssertItIsDisplayed]
         */
//        assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed(composeTestRule)

    }

    private fun assertSignUpModeIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            composeTestRule = composeTestRule,
            authenticationMode = SIGN_UP,
            enableAuthentication = FALSE
        )

        /**
         * No longer necessary since it's now part of the implementation of
         * [setContentAsAuthenticationFormAndAssertItIsDisplayed]
         */
//        assertAuthenticationTitleAndTextExactlySignUpForAnAccountIsDisplayed(composeTestRule)

    }

    /**
     * This should be called in all test cases immediately after composing the [AuthenticationForm]
     * composable in the [ComposeContentTestRule.setContent]
     */
    private fun assertAuthenticationFormIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationForm()
            .assertIsDisplayed()

    }

    private fun assertAuthenticationTitleAndTextExactlySignInToYourAccountIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationTitleAndTextExactlySignInToYourAccount()
            .assertIsDisplayed()

    }

    private fun assertAuthenticationTitleAndTextExactlySignUpForAnAccountIsDisplayed(
        composeTestRule: ComposeContentTestRule = this.composeTestRule
    ) {

        onNodeWithAuthenticationTitleAndTextExactlySignUpForAnAccount()
            .assertIsDisplayed()

    }

    private fun onNodeWithAuthenticationForm(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        val thisStringCouldBeAnything = ""

        return onNodeWithAuthenticationFormAnd(
            otherMatcher = hasTextExactly(
                thisStringCouldBeAnything,
                includeEditableText = FALSE
            ).not(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationTitleAndTextExactlySignInToYourAccount(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationTitleAnd(
            otherMatcher = hasTextExactlySignInToYourAccount(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationTitleAndTextExactlySignUpForAnAccount(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationTitleAnd(
            otherMatcher = hasTextExactlySignUpForAnAccount(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationButtonAndTextExactlySignIn(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationButtonAnd(
            otherMatcher = hasTextExactlySignIn(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationButtonAndTextExactlySignUp(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationButtonAnd(
            otherMatcher = hasTextExactlySignUp(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationToggleModeAndTextExactlyNeedAnAccount(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationToggleModeAnd(
            otherMatcher = hasTextExactlyNeedAnAccount(),
            useUnmergedTree = useUnmergedTree
        )

    }

    private fun onNodeWithAuthenticationToggleModeAndTextExactlyAlreadyHaveAnAccount(
        useUnmergedTree: Boolean = FALSE
    ): SemanticsNodeInteraction {

        return onNodeWithAuthenticationToggleModeAnd(
            otherMatcher = hasTextExactlyAlreadyHaveAnAccount(),
            useUnmergedTree = useUnmergedTree
        )

    }

    // ------- FOR ..._AnotherApproach()

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

    // ------- /FOR ..._AnotherApproach()

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

    // ------- FOR ..._AnotherApproach()

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

    // ------- /FOR ..._AnotherApproach()

    private fun onNodeWithAuthenticationFormAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagAuthenticationForm().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithAuthenticationTitleAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagAuthenticationTitle().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithAuthenticationButtonAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagAuthenticationButton().and(
                    other = otherMatcher
                ),
                useUnmergedTree = useUnmergedTree
            )

    }

    private fun onNodeWithAuthenticationToggleModeAnd(
        composeTestRule: ComposeContentTestRule = this.composeTestRule,
        useUnmergedTree: Boolean = FALSE,
        otherMatcher: SemanticsMatcher
    ): SemanticsNodeInteraction {

        return composeTestRule
            .onNode(
                matcher = hasTestTagAuthenticationToggleMode().and(
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

    private fun hasTestTagAuthenticationForm(): SemanticsMatcher {

        return hasTestTagsAuthenticationFormAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagAuthenticationTitle(): SemanticsMatcher {

        return hasTestTagsAuthenticationTitleAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagAuthenticationButton(): SemanticsMatcher {

        return hasTestTagsAuthenticationButtonAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    private fun hasTestTagAuthenticationToggleMode(): SemanticsMatcher {

        return hasTestTagsAuthenticationToggleModeAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    //For the sake of pattern, this function was created.
    private fun hasTestTagPasswordRequirement(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = THIS_STRING_MUST_BE_EMPTY
        )

    }

    // ------- FOR ..._AnotherApproach()

    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharactersNeeded():
            SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_NEEDED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetterNeeded():
            SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_NEEDED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneDigitNeeded(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT_NEEDED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastEightCharactersSatisfied():
            SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_EIGHT_CHARACTERS_SATISFIED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneUppercaseLetterSatisfied():
            SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_UPPERCASE_LETTER_SATISFIED)
        )

    }

    private fun hasTestTagsPasswordRequirementAndAtLeastOneDigitSatisfied(): SemanticsMatcher {

        return hasTestTagsPasswordRequirementAnd(
            otherTestTag = mContext.getString(STRING_RES_AT_LEAST_ONE_DIGIT_SATISFIED)
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

    private fun hasTestTagsAuthenticationFormAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_FORM_CONTENT_AREA + otherTestTag
        )

    }

    private fun hasTestTagsAuthenticationTitleAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_AUTHENTICATION_TITLE + otherTestTag
        )

    }

    private fun hasTestTagsAuthenticationButtonAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_AUTHENTICATE_BUTTON + otherTestTag
        )

    }

    private fun hasTestTagsAuthenticationToggleModeAnd(otherTestTag: String): SemanticsMatcher {

        return hasTestTag(
            testTag = TAG_AUTHENTICATION_TOGGLE_MODE_BUTTON + otherTestTag
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

    private fun hasTextExactlySignInToYourAccount(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_SIGN_IN_TO_YOUR_ACCOUNT),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlySignUpForAnAccount(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_SIGN_UP_FOR_AN_ACCOUNT),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlySignIn(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_SIGN_IN),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlySignUp(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_SIGN_UP),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyNeedAnAccount(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_NEED_AN_ACCOUNT),
            includeEditableText = FALSE
        )

    }

    private fun hasTextExactlyAlreadyHaveAnAccount(): SemanticsMatcher {

        return hasTextExactly(
            mContext.getString(STRING_RES_ALREADY_HAVE_AN_ACCOUNT),
            includeEditableText = FALSE
        )

    }

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
    fun sign_In_Mode_Displayed() {

        assertSignInModeIsDisplayed()

    }

    @Test
    fun sign_Up_Mode_Displayed() {

        assertSignUpModeIsDisplayed()

    }

    @Test
    fun sign_In_Title_Displayed() {

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            email = INPUT_CONTENT_EMAIL_EMPTY,
            password = INPUT_CONTENT_PASSWORD_EMPTY,
            passwordRequirements = emptyList(),
            enableAuthentication = FALSE
        )

    }

    @Test
    fun sign_Up_Title_Displayed() {

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL_EMPTY,
            password = INPUT_CONTENT_PASSWORD_EMPTY,
            passwordRequirements = emptyList(),
            enableAuthentication = FALSE
        )

    }

    @Test
    fun sign_In_Disabled_Button_Displayed() {

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            email = INPUT_CONTENT_EMAIL_EMPTY,
            password = INPUT_CONTENT_PASSWORD_EMPTY,
            passwordRequirements = emptyList(),
            enableAuthentication = FALSE
        )

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Disabled_Button_Displayed() {

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL_EMPTY,
            password = INPUT_CONTENT_PASSWORD_EMPTY,
            passwordRequirements = emptyList(),
            enableAuthentication = FALSE
        )

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_In_Enabled_Button_Displayed() {

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD,
            passwordRequirements = emptyList(),
            enableAuthentication = TRUE
        )

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .apply {
                assertIsDisplayed()

                assertIsEnabled()
            }

    }

    @Test
    fun sign_Up_Enabled_Button_Displayed() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(
            EIGHT_CHARACTERS,
            CAPITAL_LETTER,
            NUMBER
        )

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements,
            enableAuthentication = TRUE
        )

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertIsEnabled()
            }

    }

    @Test
    fun sign_In_Button_Disabled_When_Email_Input_Has_No_Content() {

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            email = INPUT_CONTENT_EMAIL_EMPTY,
            password = INPUT_CONTENT_PASSWORD,
            passwordRequirements = emptyList(),
            enableAuthentication = FALSE
        )

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_In_Button_Disabled_When_Password_Input_Has_No_Content() {

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_EMPTY,
            passwordRequirements = emptyList(),
            enableAuthentication = FALSE
        )

        onNodeWithAuthenticationButtonAndTextExactlySignIn()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Button_Disabled_When_Email_Input_Has_No_Content() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(
            EIGHT_CHARACTERS,
            CAPITAL_LETTER,
            NUMBER
        )

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL_EMPTY,
            password = INPUT_CONTENT_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements,
            enableAuthentication = FALSE
        )

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun sign_Up_Button_Disabled_When_Password_Input_Has_No_Content() {

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_EMPTY,
            passwordRequirements = emptyList(),
            enableAuthentication = FALSE
        )

        onNodeWithAuthenticationButtonAndTextExactlySignUp()
            .apply {
                assertIsDisplayed()

                assertIsNotEnabled()
            }

    }

    @Test
    fun need_Account_Button_Displayed() {

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_IN,
            email = INPUT_CONTENT_EMAIL_EMPTY,
            password = INPUT_CONTENT_PASSWORD_EMPTY,
            passwordRequirements = emptyList(),
            enableAuthentication = FALSE
        )

        onNodeWithAuthenticationToggleModeAndTextExactlyNeedAnAccount()
            .assertIsDisplayed()

    }

    @Test
    fun already_Have_Account_Button_Displayed() {

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL_EMPTY,
            password = INPUT_CONTENT_PASSWORD_EMPTY,
            passwordRequirements = emptyList(),
            enableAuthentication = FALSE
        )

        onNodeWithAuthenticationToggleModeAndTextExactlyAlreadyHaveAnAccount()
            .assertIsDisplayed()

    }

    @Test
    fun sign_Up_No_Password_Requirement_Satisfied() {

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL_EMPTY,
            password = INPUT_CONTENT_PASSWORD_EMPTY,
            passwordRequirements = emptyList(),
            enableAuthentication = FALSE
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
    fun sign_Up_No_Password_Requirement_Satisfied_AnotherApproach() {

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL_EMPTY,
            password = INPUT_CONTENT_PASSWORD_EMPTY,
            passwordRequirements = emptyList(),
            enableAuthentication = FALSE
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
    fun sign_Up_Only_At_Least_Eight_Characters_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(EIGHT_CHARACTERS)

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements,
            enableAuthentication = FALSE
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
    fun sign_Up_Only_At_Least_Eight_Characters_Satisfied_AnotherApproach() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(EIGHT_CHARACTERS)

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements,
            enableAuthentication = FALSE
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
    fun sign_Up_Only_At_Least_One_Uppercase_Letter_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(CAPITAL_LETTER)

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_PASS,
            passwordRequirements = satisfiedPasswordRequirements,
            enableAuthentication = FALSE
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
    fun sign_Up_Only_At_Least_One_Uppercase_Letter_Satisfied_AnotherApproach() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(CAPITAL_LETTER)

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_PASS,
            passwordRequirements = satisfiedPasswordRequirements,
            enableAuthentication = FALSE
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
    fun sign_Up_Only_At_Least_One_Digit_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(NUMBER)

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_1PASS,
            passwordRequirements = satisfiedPasswordRequirements,
            enableAuthentication = FALSE
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
    fun sign_Up_Only_At_Least_One_Digit_Satisfied_AnotherApproach() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(NUMBER)

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD_1PASS,
            passwordRequirements = satisfiedPasswordRequirements,
            enableAuthentication = FALSE
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
    fun sign_Up_All_Password_Requirements_Satisfied() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(
            EIGHT_CHARACTERS,
            CAPITAL_LETTER,
            NUMBER
        )

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements,
            enableAuthentication = TRUE
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
    fun sign_Up_All_Password_Requirements_Satisfied_AnotherApproach() {

        val satisfiedPasswordRequirements: List<PasswordRequirement> = listOf(
            EIGHT_CHARACTERS,
            CAPITAL_LETTER,
            NUMBER
        )

        setContentAsAuthenticationFormAndAssertItIsDisplayed(
            authenticationMode = SIGN_UP,
            email = INPUT_CONTENT_EMAIL,
            password = INPUT_CONTENT_PASSWORD,
            passwordRequirements = satisfiedPasswordRequirements,
            enableAuthentication = TRUE
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

}