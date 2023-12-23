from rest_framework import serializers
from .models import (
    BalanceEntry,
)


class BalanceEntrySerializer(serializers.ModelSerializer):
    class Meta:
        model = BalanceEntry
        fields = [
            "id",
            "name",
            "amount",
            "description",
            "date",
        ]


class BalanceEntryCreateSerializer(serializers.ModelSerializer):
    class Meta:
        model = BalanceEntry
        fields = [
            "name",
            "amount",
            "date",
            "description",
        ]
