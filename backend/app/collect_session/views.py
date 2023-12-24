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
    CollectSessionDetailSerializer,
    CollectSessionCloseSerializer,
)
from group.mixins import GroupPermissionMixin

# Create your views here.


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

    def create(self, request, *args, **kwargs):
        serializer = CollectSessionCreateSerializer(data=request.data)
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

    @action(
        detail=True, methods=["post"], serializer_class=CollectSessionCloseSerializer
    )
    def close(self, request, *args, **kwargs):
        session = self.get_object()
        session.close()
        return Response({"status": "Collect session closed"}, status=status.HTTP_200_OK)
