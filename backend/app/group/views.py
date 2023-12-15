from .models import BalanceEntry, CollectEntry
from rest_framework.response import Response
from django.shortcuts import render

from .models import (
    Group,
    Member,
    BalanceEntry,
    CollectEntry
)
from .serializers import (
    GroupSerializer,
    MemberSerializer,
    BalanceEntrySerializer,
    CollectEntrySerializer
)
from rest_framework import viewsets
from django.db.models import Sum

# Create your views here.


class GroupViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows groups to be viewed or edited.
    """
    queryset = Group.objects.all()
    serializer_class = GroupSerializer


class MemberViewSet(viewsets.ModelViewSet):
    serializer_class = MemberSerializer

    def get_queryset(self):
        group_id = self.kwargs['group_pk']
        return Member.objects.filter(group_id=group_id)


class BalanceViewSet(viewsets.ViewSet):
    def list(self, request, group_pk=None):
        print(group_pk)
        book_balance = BalanceEntry.objects.filter(
            group_id=group_pk).aggregate(
            Sum('amount'))['amount__sum']
        actual_balance = CollectEntry.objects.filter(
            status=True,
            session_id__balance_entries__group_id=group_pk
        ).aggregate(Sum('amount'))['amount__sum']

        return Response({
            'book_balance': book_balance if book_balance is not None else 0,
            'actual_balance': actual_balance if actual_balance is not None else 0
        })
