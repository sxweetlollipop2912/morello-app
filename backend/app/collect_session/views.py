from rest_framework import viewsets, status
from rest_framework.decorators import action
from rest_framework.response import Response
from .models import (
    CollectSession,
)
from .serializers import (
    CollectSessionListSerializer,
    CollectSessionCreateSerializer,
    CollectSessionUpdateSerializer,
    CollectSessionPartialUpdateSerializer,
    CollectSessionDetailSerializer,
    CollectSessionOpenCloseSerializer,
    CollectEntryUpdateSerializer,
)
from group.mixins import GroupPermissionMixin
from drf_spectacular.utils import extend_schema
from rest_framework.permissions import IsAuthenticated
from group.permissions import IsGroupAdminOrModerator

# Create your views here.


@extend_schema(tags=["Collect Session endpoints"])
class CollectSessionViewSet(GroupPermissionMixin, viewsets.ModelViewSet):
    serializer_class = CollectSessionDetailSerializer

    def get_queryset(self):
        group_id = self.kwargs["group_pk"]
        return CollectSession.objects.filter(group_id=group_id)

    def list(self, request, *args, **kwargs):
        queryset = self.get_queryset()
        serializer = CollectSessionListSerializer(
            queryset, many=True, context={"request": request}
        )
        return Response(serializer.data)

    def retrieve(self, request, *args, **kwargs):
        instance = self.get_object()
        serializer = CollectSessionDetailSerializer(
            instance, context={"request": request}
        )
        return Response(serializer.data)

    def create(self, request, *args, **kwargs):
        group_id = self.kwargs["group_pk"]
        serializer = CollectSessionCreateSerializer(
            data=request.data, context={"group_id": group_id}
        )
        serializer.is_valid(raise_exception=True)
        collect_session = serializer.save()
        serializer = CollectSessionDetailSerializer(
            collect_session, context={"request": request}
        )
        return Response(serializer.data, status=status.HTTP_201_CREATED)

    def update(self, request, *args, **kwargs):
        instance = self.get_object()
        serializer = CollectSessionUpdateSerializer(instance, data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        serializer = CollectSessionDetailSerializer(
            instance, context={"request": request}
        )
        return Response(serializer.data)

    def partial_update(self, request, *args, **kwargs):
        instance = self.get_object()
        serializer = CollectSessionPartialUpdateSerializer(
            instance, data=request.data, partial=True
        )
        serializer.is_valid(raise_exception=True)
        serializer.save()
        serializer = CollectSessionDetailSerializer(
            instance, context={"request": request}
        )
        return Response(serializer.data)

    @action(
        detail=True,
        methods=["post"],
        serializer_class=CollectSessionOpenCloseSerializer,
        url_path="close",
    )
    def close(self, request, *args, **kwargs):
        session = self.get_object()
        session.close()
        return Response(
            {"message": "Collect session closed"}, status=status.HTTP_200_OK
        )

    @action(
        detail=True,
        methods=["post"],
        url_path="members/(?P<member_id>\d+)/status",  # noqa
        serializer_class=CollectEntryUpdateSerializer,
    )
    def update_member_status(self, request, member_id=None, *args, **kwargs):
        session = self.get_object()
        collect_entry = session.collect_entries.filter(member_id=member_id).first()
        if not collect_entry:
            return Response(
                {"message": "Member not found in this session"},
                status=status.HTTP_404_NOT_FOUND,
            )

        serializer = CollectEntryUpdateSerializer(collect_entry, data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response({"message": "Member status updated"}, status=status.HTTP_200_OK)
