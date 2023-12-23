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

# GET /users/: List all users. This corresponds to the list() method in the viewset. # noqa
# POST /users/: Create a new user. This corresponds to the create() method in the viewset. # noqa
# GET /users/{id}/: Retrieve a specific user by their ID. This corresponds to the retrieve() method in the viewset. # noqa
# PUT /users/{id}/: Update a specific user by their ID. This corresponds to the update() method in the viewset. # noqa
# PATCH /users/{id}/: Partially update a specific user by their ID. This corresponds to the partial_update() method in the viewset. # noqa
# DELETE /users/{id}/: Delete a specific user by their ID. This corresponds to the destroy() method in the viewset. # noqa
# See: https://www.django-rest-framework.org/api-guide/viewsets/#modelviewset


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
