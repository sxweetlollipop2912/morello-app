"""
URL mappings for the user API.
"""
from rest_framework.routers import DefaultRouter
from .views import UserViewSet

app_name = 'user'
router = DefaultRouter()
router.register(r'^', UserViewSet)

urlpatterns = router.urls
