from rest_framework import viewsets, status
from .models import BalanceEntry
from .serializers import (
    BalanceEntryListSerializer,
    BalanceEntryDetailSerializer,
    BalanceEntryCreateSerializer,
    BalanceEntryUpdateSerializer,
    BalanceEntryPartialUpdateSerializer,
)
from collect_session.models import CollectEntry
from django.db.models import Sum, F
from group.mixins import GroupPermissionMixin
from rest_framework.response import Response
from drf_spectacular.utils import extend_schema

# Create your views here.


@extend_schema(tags=["Balance endpoints"])
class BalanceViewSet(GroupPermissionMixin, viewsets.ViewSet):
    def list(self, request, group_pk=None):
        # Calculate the total amount of all BalanceEntry objects for the given group
        balance_entry_total = (
            BalanceEntry.objects.filter(group_id=group_pk).aggregate(Sum("amount"))[
                "amount__sum"
            ]
            or 0
        )

        # Calculate the currently collected amount of all CollectEntry objects
        # with a status of True for all open sessions in the group
        open_collect_session_current = (
            CollectEntry.objects.filter(
                status=True, session_id__is_open=True, session_id__group_id=group_pk
            ).aggregate(total=Sum(F("session_id__payment_per_member")))["total"]
            or 0
        )

        # Calculate the current_balance
        current_balance = balance_entry_total + open_collect_session_current

        # Calculate the total amount of all CollectEntry objects, regardless of the payment status
        open_collect_session_expected = (
            CollectEntry.objects.filter(
                session_id__is_open=True, session_id__group_id=group_pk
            ).aggregate(total=Sum(F("session_id__payment_per_member")))["total"]
            or 0
        )

        # Calculate the expected_balance
        expected_balance = balance_entry_total + open_collect_session_expected

        return Response(
            {
                "current_balance": current_balance,
                "expected_balance": expected_balance,
            }
        )


@extend_schema(tags=["Balance Entry endpoints"])
class BalanceEntryViewSet(GroupPermissionMixin, viewsets.ModelViewSet):
    def get_queryset(self):
        group_id = self.kwargs["group_pk"]
        return BalanceEntry.objects.filter(group_id=group_id)

    def list(self, request, *args, **kwargs):
        queryset = self.get_queryset()
        serializer = BalanceEntryListSerializer(queryset, many=True)
        return Response(serializer.data)

    def retrieve(self, request, *args, **kwargs):
        instance = self.get_object()
        serializer = BalanceEntryDetailSerializer(instance)
        return Response(serializer.data)

    def create(self, request, *args, **kwargs):
        group_id = self.kwargs["group_pk"]
        serializer = BalanceEntryCreateSerializer(
            data=request.data, context={"group_id": group_id}
        )
        serializer.is_valid(raise_exception=True)
        balance_entry = serializer.save()
        serializer = BalanceEntryDetailSerializer(balance_entry)
        return Response(serializer.data, status=status.HTTP_201_CREATED)

    def update(self, request, *args, **kwargs):
        instance = self.get_object()
        serializer = BalanceEntryUpdateSerializer(instance, data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(serializer.data)

    def partial_update(self, request, *args, **kwargs):
        instance = self.get_object()
        serializer = BalanceEntryPartialUpdateSerializer(instance, data=request.data)
        serializer.is_valid(raise_exception=True)
        serializer.save()
        return Response(serializer.data)
