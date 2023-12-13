"""
Views for the user API.
"""
from rest_framework import viewsets
from core.models import (
    User,
)

from user.serializers import (
    UserSerializer,
)
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated, AllowAny

# GET /users/: List all users. This corresponds to the list() method in the viewset.
# POST /users/: Create a new user. This corresponds to the create() method in the viewset.
# GET /users/{id}/: Retrieve a specific user by their ID. This corresponds to the retrieve() method in the viewset.
# PUT /users/{id}/: Update a specific user by their ID. This corresponds to the update() method in the viewset.
# PATCH /users/{id}/: Partially update a specific user by their ID. This corresponds to the partial_update() method in the viewset.
# DELETE /users/{id}/: Delete a specific user by their ID. This corresponds to the destroy() method in the viewset.
# See: https://www.django-rest-framework.org/api-guide/viewsets/#modelviewset


class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all().order_by('id')
    serializer_class = UserSerializer
    permission_classes = [IsAuthenticated]

    def get_permissions(self):
        if self.action in ['list', 'retrieve']:
            # Actions that require authentication
            permission_classes = [IsAuthenticated]
        else:
            # Actions that don't require authentication
            permission_classes = [AllowAny]
        return [permission() for permission in permission_classes]
