from django.urls import path, include
from rest_framework_nested import routers
from .views import (
    GroupViewSet,
    # ModeratorViewSet,
    MemberViewSet,
    BalanceViewSet,
    # CollectSessionViewSet,
    # BalanceEntryViewSet,
    # CollectEntryViewSet
)

router = routers.SimpleRouter()
router.register(r'groups', GroupViewSet)
# router.register(r'moderators', ModeratorViewSet)
# router.register(r'collectsessions', CollectSessionViewSet)
# router.register(r'balanceentries', BalanceEntryViewSet)
# router.register(r'collectentries', CollectEntryViewSet)

groups_router = routers.NestedSimpleRouter(router, r'groups', lookup='group')
groups_router.register(r'members', MemberViewSet, basename='group-members')
groups_router.register(r'balance', BalanceViewSet, basename='group-balance')

urlpatterns = [
    path('', include(router.urls)),
    path('', include(groups_router.urls)),
]
