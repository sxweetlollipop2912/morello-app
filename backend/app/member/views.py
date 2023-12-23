from django.shortcuts import render

# Create your views here.

from .models import (
    Member,
)
from .serializers import (
    MemberListSerializer,
    MemberCreateSerializer,
    MemberDetailSerializer,
    MemberUpdateSerializer,
    MemberPartialUpdateSerializer,
)
from rest_framework import viewsets, status
from group.mixins import GroupPermissionMixin
from rest_framework.response import Response


class MemberViewSet(GroupPermissionMixin, viewsets.ModelViewSet):
    def get_queryset(self):
        group_id = self.kwargs["group_pk"]
        queryset = Member.objects.filter(group_id=group_id)
        is_archived = self.request.query_params.get("is_archived", None)

        if is_archived is not None:
            queryset = queryset.filter(is_archived=is_archived == "true")

        return queryset

    def list(self, request, *args, **kwargs):
        queryset = self.get_queryset()
        serializer = MemberListSerializer(queryset, many=True)
        return Response(serializer.data)

    def create(self, request, *args, **kwargs):
        serializer = MemberCreateSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        group_id = self.kwargs["group_pk"]
        member = Member.objects.create(
            name=serializer.validated_data.get("name"),
            group_id=group_id,
        )
        serializer = MemberDetailSerializer(member)
        return Response(serializer.data, status=status.HTTP_201_CREATED)

    def retrieve(self, request, *args, **kwargs):
        instance = self.get_object()
        serializer = MemberDetailSerializer(instance)
        return Response(serializer.data)

    def update(self, request, *args, **kwargs):
        instance = self.get_object()
        serializer = MemberUpdateSerializer(instance, data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(serializer.data)

    def partial_update(self, request, *args, **kwargs):
        instance = self.get_object()
        serializer = MemberPartialUpdateSerializer(instance, data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(serializer.data)
