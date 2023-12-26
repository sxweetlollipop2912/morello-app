from .models import (
    Group,
    # Member,
    # BalanceEntry,
    # CollectEntry,
    # CollectSession,
)
from .serializers import (
    GroupListSerializer,
    GroupCreateSerializer,
    GroupDetailSerializer,
    GroupUpdateSerializer,
    GroupPartialUpdateSerializer,
)
from rest_framework import viewsets, status
from rest_framework.response import Response

from .mixins import GroupPermissionMixin
from django.db.models import Q
from drf_spectacular.utils import extend_schema

# Create your views here.


@extend_schema(tags=["Group endpoints"])
class GroupViewSet(GroupPermissionMixin, viewsets.ModelViewSet):
    """
    API endpoint that allows groups to be viewed or edited.
    """

    def get_queryset(self):
        user = self.request.user
        return (
            Group.objects.filter(Q(leader_user_id=user) | Q(moderators__user_id=user))
            .distinct()
            .select_related("leader_user_id")
        )

    def list(self, request, *args, **kwargs):
        queryset = self.get_queryset()
        serializer = GroupListSerializer(
            queryset, many=True, context={"request": request}
        )
        return Response(serializer.data)

    def create(self, request, *args, **kwargs):
        serializer = GroupCreateSerializer(
            data=request.data, context={"request": request}
        )
        serializer.is_valid(raise_exception=True)
        group = serializer.save()
        serializer = GroupDetailSerializer(group, context={"request": request})
        return Response(serializer.data, status=status.HTTP_201_CREATED)

    def retrieve(self, request, *args, **kwargs):
        instance = self.get_object()
        serializer = GroupDetailSerializer(instance, context={"request": request})
        return Response(serializer.data)

    def update(self, request, *args, **kwargs):
        instance = self.get_object()
        serializer = GroupUpdateSerializer(instance, data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        serializer = GroupDetailSerializer(instance, context={"request": request})
        return Response(serializer.data)

    def partial_update(self, request, *args, **kwargs):
        instance = self.get_object()
        serializer = GroupPartialUpdateSerializer(instance, data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        serializer = GroupDetailSerializer(instance, context={"request": request})
        return Response(serializer.data)
