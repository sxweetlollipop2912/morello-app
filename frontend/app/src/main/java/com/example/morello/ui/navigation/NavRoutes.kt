import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface NavRoute {
    val base: String
    val routeWithArgs: String
    val args: List<NamedNavArgument>
}

object LoginRoute : NavRoute {
    override val base
        get() = "login"
    override val routeWithArgs
        get() = base
    override val args
        get() = emptyList<NamedNavArgument>()
}

object RegisterRoute : NavRoute {
    override val base
        get() = "register"
    override val routeWithArgs
        get() = base
    override val args
        get() = emptyList<NamedNavArgument>()
}

object ForgotPasswordRoute : NavRoute {
    override val base
        get() = "forgotPassword"
    override val routeWithArgs
        get() = base
    override val args
        get() = emptyList<NamedNavArgument>()
}

object ForgotPasswordCodeRoute : NavRoute {
    override val base
        get() = "forgotPasswordCode"
    override val routeWithArgs
        get() = base
    override val args
        get() = emptyList<NamedNavArgument>()
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
    override val routeWithArgs
        get() = base
    override val args
        get() = emptyList<NamedNavArgument>()
}

object CreateExpenseRoute : NavRoute {
    override val base
        get() = "createBalanceEntry/expense"
    override val routeWithArgs
        get() = base
    override val args
        get() = emptyList<NamedNavArgument>()
}

object CreateIncomeRoute : NavRoute {
    override val base
        get() = "createBalanceEntry/income"
    override val routeWithArgs
        get() = base
    override val args
        get() = emptyList<NamedNavArgument>()
}

object AddMemberRoute : NavRoute {
    override val base
        get() = "addMember"
    override val routeWithArgs
        get() = base
    override val args
        get() = emptyList<NamedNavArgument>()
}

object AuthorizedHomeRoute : NavRoute {
    override val base
        get() = "authorizedHome"
    override val routeWithArgs
        get() = base
    override val args
        get() = emptyList<NamedNavArgument>()
}

object HomeRoute : NavRoute {
    override val base
        get() = "home"
    override val routeWithArgs
        get() = base
    override val args
        get() = emptyList<NamedNavArgument>()
}

object CreateGroupRoute : NavRoute {
    override val base
        get() = "createGroup"
    override val routeWithArgs
        get() = base
    override val args
        get() = emptyList<NamedNavArgument>()
}