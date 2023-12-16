from django.urls import path, include
from rest_framework_nested import routers
from .views import (
    GroupViewSet,
    # CollectEntryViewSet
)

router = routers.SimpleRouter()
router.register(r'groups', GroupViewSet, basename='groups')

urlpatterns = [
    path('', include(router.urls)),
]
