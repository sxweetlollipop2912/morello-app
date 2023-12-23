from django.urls import include, path
from rest_framework_nested import routers
from group.urls import router as group_router
from .views import ModeratorViewSet

router = routers.NestedSimpleRouter(group_router, r"groups", lookup="group")
router.register(r"moderators", ModeratorViewSet, basename="group-moderators")

urlpatterns = [
    path("", include(router.urls)),
]
