from rest_framework import viewsets
from group.mixins import GroupPermissionMixin

from .models import Moderator
from .serializers import ModeratorSerializer
# Create your views here.


class ModeratorViewSet(GroupPermissionMixin, viewsets.ModelViewSet):
    serializer_class = ModeratorSerializer

    def get_queryset(self):
        group_id = self.kwargs['group_pk']
        return Moderator.objects.filter(
            group_id=group_id).select_related('user_id')
