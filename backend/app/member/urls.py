from django.urls import include, path
from rest_framework_nested import routers
from group.urls import router as group_router
from .views import MemberViewSet

router = routers.NestedSimpleRouter(group_router, r'groups', lookup='group')
router.register(r'members', MemberViewSet, basename='group-members')

urlpatterns = [
    path('', include(router.urls)),
]
