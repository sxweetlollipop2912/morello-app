from user.models.administrator import Administrator
from user.models.moderator import Moderator


def is_administrator(user):
    """
    Return true if user is administrator
    """

    if Administrator.objects.filter(user=user):
        return True
    return False


def is_moderator(user):
    """
    Return true if user is moderator or higher
    """

    if Moderator.objects.filter(user=user):
        return True
    return is_administrator(user)
