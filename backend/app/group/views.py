
from .models import (
    Group,
    # Member,
    # BalanceEntry,
    # CollectEntry,
    # CollectSession,
)
from .serializers import (
    GroupSerializer,
    # MemberSerializer,
    # BalanceEntrySerializer,
    # CollectSessionSerializer,
)
from rest_framework import viewsets

from .mixins import GroupPermissionMixin

# Create your views here.


class GroupViewSet(GroupPermissionMixin, viewsets.ModelViewSet):
    """
    API endpoint that allows groups to be viewed or edited.
    """
    serializer_class = GroupSerializer
