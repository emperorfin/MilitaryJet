package emperorfin.android.composeemailauthentication.ui.screens.authentication.events


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


sealed class AuthenticationEvent {

    object AuthenticationToggleModeEvent : AuthenticationEvent()

    class EmailChangedEvent(val emailAddress: String) : AuthenticationEvent()

    class PasswordChangedEvent(val password: String) : AuthenticationEvent()

    object AuthenticateEvent : AuthenticationEvent()

    object ErrorDismissedEvent : AuthenticationEvent()

}
