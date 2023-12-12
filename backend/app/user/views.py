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
from django.contrib.auth import authenticate, login, logout

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


class LoginView(APIView):
    def post(self, request):
        username = request.data.get('username')
        password = request.data.get('password')
        user = authenticate(request, username=username, password=password)
        if user is not None:
            login(request, user)
            return Response({"status": "Logged in"})
        else:
            return Response({"status": "Invalid credentials"}, status=400)


class LogoutView(APIView):
    def post(self, request):
        logout(request)
        return Response({"status": "Logged out"})
