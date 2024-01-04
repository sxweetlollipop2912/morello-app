from rest_framework import viewsets, status
from group.mixins import GroupPermissionMixin

from .models import Moderator
from .serializers import (
    ModeratorListSerializer,
    ModeratorDetailSerializer,
    ModeratorCreateSerializer,
)
from drf_spectacular.utils import extend_schema
from rest_framework.exceptions import MethodNotAllowed
from rest_framework.response import Response

# Create your views here.


@extend_schema(tags=["Moderator endpoints"])
class ModeratorViewSet(GroupPermissionMixin, viewsets.ModelViewSet):
    def get_queryset(self):
        group_id = self.kwargs["group_pk"]
        return Moderator.objects.filter(group_id=group_id).select_related("user_id")

    def create(self, request, *args, **kwargs):
        group_id = self.kwargs["group_pk"]
        serializer = ModeratorCreateSerializer(
            data=request.data, context={"group_id": group_id}
        )
        serializer.is_valid(raise_exception=True)
        moderator = serializer.save(group_id=group_id)
        serializer = ModeratorDetailSerializer(moderator)
        return Response(serializer.data, status=status.HTTP_201_CREATED)

    def list(self, request, *args, **kwargs):
        queryset = self.get_queryset()
        serializer = ModeratorListSerializer(queryset, many=True)
        return Response(serializer.data)

    def retrieve(self, request, *args, **kwargs):
        raise MethodNotAllowed("GET", detail="Moderator detail is not available")

    def update(self, request, *args, **kwargs):
        raise MethodNotAllowed("PUT", detail="Moderator update is not available")

    def partial_update(self, request, *args, **kwargs):
        raise MethodNotAllowed(
            "PATCH", detail="Moderator partial update is not available"
        )
