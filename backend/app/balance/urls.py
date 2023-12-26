from django.urls import include, path
from rest_framework_nested import routers
from group.urls import router as group_router
from .views import BalanceViewSet, BalanceEntryViewSet

router = routers.NestedSimpleRouter(group_router, r"groups", lookup="group")
router.register(
    r"balance/entries", BalanceEntryViewSet, basename="group-balance-entries"
)
router.register(r"balance", BalanceViewSet, basename="group-balance")

urlpatterns = [
    path("", include(router.urls)),
]
