from rest_framework import viewsets
from .models import BalanceEntry
from .serializers import BalanceEntrySerializer
from collect_session.models import CollectEntry
from django.db.models import (
    Sum,
)
from rest_framework.response import Response

# Create your views here.


class BalanceViewSet(viewsets.ViewSet):
    def list(self, request, group_pk=None):
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


class BalanceEntryViewSet(viewsets.ModelViewSet):
    serializer_class = BalanceEntrySerializer

    def get_queryset(self):
        group_id = self.kwargs['group_pk']
        return BalanceEntry.objects.filter(group_id=group_id)
