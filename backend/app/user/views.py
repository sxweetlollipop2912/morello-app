"""
Views for the user API.
"""
from rest_framework import viewsets
from user.models import User

from user.serializers import (
    UserSerializer,
)
from rest_framework.permissions import IsAuthenticated, AllowAny

# GET /users/: List all users. This corresponds to the list() method in the viewset. # noqa
# POST /users/: Create a new user. This corresponds to the create() method in the viewset. # noqa
# GET /users/{id}/: Retrieve a specific user by their ID. This corresponds to the retrieve() method in the viewset. # noqa
# PUT /users/{id}/: Update a specific user by their ID. This corresponds to the update() method in the viewset. # noqa
# PATCH /users/{id}/: Partially update a specific user by their ID. This corresponds to the partial_update() method in the viewset. # noqa
# DELETE /users/{id}/: Delete a specific user by their ID. This corresponds to the destroy() method in the viewset. # noqa
# See: https://www.django-rest-framework.org/api-guide/viewsets/#modelviewset


class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all().order_by('id')
    serializer_class = UserSerializer
