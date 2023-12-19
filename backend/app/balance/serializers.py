from rest_framework import serializers
from .models import (
    BalanceEntry,
)


class BalanceEntrySerializer(serializers.ModelSerializer):
    class Meta:
        model = BalanceEntry
        fields = [
            'id',
            'group_id',
            'amount',
            'description',
            'date',
            'session_id'
        ]
