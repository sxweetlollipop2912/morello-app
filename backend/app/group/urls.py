from django.urls import path, include
from rest_framework_nested import routers
from .views import (
    GroupViewSet,
    # ModeratorViewSet,
    MemberViewSet,
    # CollectSessionViewSet,
    # BalanceEntryViewSet,
    # CollectEntryViewSet
)

router = routers.DefaultRouter()
router.register(r'groups', GroupViewSet)
# router.register(r'moderators', ModeratorViewSet)
# router.register(r'collectsessions', CollectSessionViewSet)
# router.register(r'balanceentries', BalanceEntryViewSet)
# router.register(r'collectentries', CollectEntryViewSet)

groups_router = routers.NestedSimpleRouter(router, r'groups', lookup='group')
groups_router.register(r'members', MemberViewSet, basename='group-members')

urlpatterns = [
    path('', include(router.urls)),
    path('', include(groups_router.urls)),
]
