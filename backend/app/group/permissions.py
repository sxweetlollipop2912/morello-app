from rest_framework import permissions


class IsGroupAdmin(permissions.BasePermission):
    def has_object_permission(self, request, view, obj):
        if hasattr(obj, 'leader_user_id'):
            return obj.leader_user_id == request.user
        elif hasattr(obj, 'group_id'):
            return obj.group_id.leader_user_id == request.user
        return False


class IsGroupAdminOrModerator(permissions.BasePermission):
    def has_object_permission(self, request, view, obj):
        if hasattr(obj, 'moderators') and hasattr(obj, 'leader_user_id'):
            return obj.moderators.filter(
                user_id=request.user).exists() or obj.leader_user_id == request.user
        elif hasattr(obj, 'group_id'):
            return obj.group_id.moderators.filter(
                user_id=request.user).exists() or obj.group_id.leader_user_id == request.user
        return False
