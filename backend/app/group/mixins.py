from django.db.models import Q
from .models import (
    Group,
)
from rest_framework.permissions import IsAuthenticated
from .permissions import IsGroupAdminOrModerator, IsGroupAdmin


class GroupPermissionMixin:
    def get_queryset(self):
        user = self.request.user
        return Group.objects.filter(
            Q(leader_user_id=user) | Q(moderators__user_id=user)
        ).distinct().select_related('leader_user_id')

    def get_permissions(self):
        if self.request.method in ['PUT', 'PATCH', 'DELETE']:
            self.permission_classes = [IsAuthenticated, IsGroupAdmin]
        else:
            self.permission_classes = [
                IsAuthenticated, IsGroupAdminOrModerator]
        return super().get_permissions()
