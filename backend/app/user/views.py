"""
Views for the user API.
"""
from rest_framework import viewsets, permissions
from user.models import User

from user.serializers import (
    UserSerializer,
)

from rest_framework.response import Response
from django.shortcuts import get_object_or_404
from django.http import Http404
from rest_framework import status
from rest_framework.views import APIView
from drf_spectacular.utils import extend_schema


@extend_schema(tags=["User endpoints"])
class UserViewSet(viewsets.ModelViewSet):
    serializer_class = UserSerializer
    permission_classes = [permissions.IsAuthenticated]

    def get_queryset(self):
        return User.objects.filter(id=self.request.user.id)

    def get_object(self):
        obj = get_object_or_404(User, id=self.request.user.id)
        self.check_object_permissions(self.request, obj)
        return obj

    def check_instance_id(self, instance):
        if instance.id != self.request.user.id:
            raise Http404

    def list(self, request, *args, **kwargs):
        instance = self.get_object()
        serializer = self.get_serializer(instance)
        return Response(serializer.data)

    def create(self, request, *args, **kwargs):
        raise Http404

    def update(self, request, *args, **kwargs):
        instance = self.get_object()
        self.check_instance_id(instance)
        return super().update(request, *args, **kwargs)

    def partial_update(self, request, *args, **kwargs):
        instance = self.get_object()
        self.check_instance_id(instance)
        return super().partial_update(request, *args, **kwargs)

    def destroy(self, request, *args, **kwargs):
        instance = self.get_object()
        self.check_instance_id(instance)
        return super().destroy(request, *args, **kwargs)


@extend_schema(tags=["User endpoints"])
class UserCreateViewSet(APIView):
    permission_classes = [permissions.AllowAny]

    def post(self, request, *args, **kwargs):
        serializer = UserSerializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        user = serializer.save()
        serializer = UserSerializer(user)
        return Response(serializer.data, status=status.HTTP_201_CREATED)
