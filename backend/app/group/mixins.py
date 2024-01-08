from rest_framework.permissions import IsAuthenticated, SAFE_METHODS
from .permissions import IsGroupAdminOrModerator, IsGroupAdmin


class GroupPermissionMixin:
    def get_permissions(self):
        if self.action == "update_member_status":
            self.permission_classes = [IsAuthenticated, IsGroupAdminOrModerator]
        elif self.request.method in SAFE_METHODS:
            self.permission_classes = [IsAuthenticated, IsGroupAdminOrModerator]
        else:
            self.permission_classes = [IsAuthenticated, IsGroupAdmin]
        return super().get_permissions()
