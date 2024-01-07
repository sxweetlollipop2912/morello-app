import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface NavRoute {
    val base: String
    val routeWithArgs: String
        get() = base
    val args: List<NamedNavArgument>
        get() = emptyList()
}

object LoginRoute : NavRoute {
    override val base
        get() = "login"
}

object RegisterRoute : NavRoute {
    override val base
        get() = "register"
}

object ForgotPasswordRoute : NavRoute {
    override val base
        get() = "forgotPassword"
}

object ForgotPasswordCodeRoute : NavRoute {
    override val base
        get() = "forgotPasswordCode"
}

object OwnerGroupHomeRoute : NavRoute {
    val groupId
        get() = "groupId"
    override val base
        get() = "ownerGroupHome"
    override val routeWithArgs
        get() = "$base/{$groupId}"
    override val args
        get() = listOf(
            navArgument(groupId) {
                type = NavType.IntType
            }
        )
}

object GroupOwnerHomeRoute : NavRoute {
    override val base
        get() = "groupOwnerHome"
}

object CreateExpenseRoute : NavRoute {
    override val base
        get() = "createBalanceEntry/expense"
}

object CreateIncomeRoute : NavRoute {
    override val base
        get() = "createBalanceEntry/income"
}

object AddMemberRoute : NavRoute {
    override val base
        get() = "addMember"
}

object AuthorizedHomeRoute : NavRoute {
    override val base
        get() = "authorizedHome"
}

object HomeRoute : NavRoute {
    override val base
        get() = "home"
}

object CreateGroupRoute : NavRoute {
    override val base
        get() = "createGroup"
}

object SessionListRoute : NavRoute {
    override val base
        get() = "sessions"
}

object BalanceEntryListRoute : NavRoute {
    override val base
        get() = "balanceEntries"
}

object GroupSettingsRoute : NavRoute {
    override val base
        get() = "groupOwnerHome/groupSettings"
}

object GroupMembersRoute: NavRoute {
    override val base: String
        get() = "groupOwnerHome/groupMembers"
}

object GroupModeratorsRoute: NavRoute {
    override val base: String
        get() = "groupOwnerHome/groupModerators"
object SessionDetailRoute : NavRoute {
    val sessionId
        get() = "sessionId"
    override val base
        get() = "sessionDetail"
    override val routeWithArgs
        get() = "$base/{$sessionId}"
    override val args
        get() = listOf(
            navArgument(sessionId) {
                type = NavType.IntType
            }
        )
}

object BalanceEntryDetailRoute : NavRoute {
    val entryId
        get() = "entryId"
    override val base
        get() = "balanceEntryDetail"
    override val routeWithArgs
        get() = "$base/{$entryId}"
    override val args
        get() = listOf(
            navArgument(entryId) {
                type = NavType.IntType
            }
        )
}