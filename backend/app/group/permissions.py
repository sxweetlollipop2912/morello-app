from rest_framework import permissions


class IsGroupAdmin(permissions.BasePermission):
    def has_object_permission(self, request, view, obj):
        # Check if the user is a moderator of the group
        return obj.leader_user_id == request.user


class IsGroupAdminOrModerator(permissions.BasePermission):
    def has_object_permission(self, request, view, obj):
        # Check if the user is the leader of the group or a moderator
        return obj.moderators.filter(
            user_id=request.user).exists() or obj.leader_user_id == request.user
