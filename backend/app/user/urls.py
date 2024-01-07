"""
URL mappings for the user API.
"""
from django.urls import path
from .views import UserViewSet, UserCreateViewSet
from rest_framework_simplejwt.views import (
    TokenObtainPairView,
    TokenRefreshView,
)

urlpatterns = [
    path(
        "users/me/",
        UserViewSet.as_view(
            {
                "get": "list",
                "put": "update",
                "patch": "partial_update",
                "delete": "destroy",
            }
        ),
        name="user_me",
    ),
    path(
        "users/create/",
        UserCreateViewSet.as_view(),
        name="user-create",
    ),
    path("token/", TokenObtainPairView.as_view(), name="token_obtain_pair"),
    path("token/refresh/", TokenRefreshView.as_view(), name="token_refresh"),
]
