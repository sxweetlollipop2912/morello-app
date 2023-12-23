from rest_framework import serializers
from .models import (
    BalanceEntry,
)


class BalanceEntrySerializer(serializers.ModelSerializer):
    class Meta:
        model = BalanceEntry
        fields = [
            'id',
            'name',
            'amount',
            'description',
            'date',
        ]


class BalanceEntryCreateSerializer(serializers.ModelSerializer):
    class Meta:
        model = BalanceEntry
        fields = [
            'name',
            'amount',
            'date',
            'description',
        ]

    def validate(self, data):
        if data['amount'] <= 0:
            raise serializers.ValidationError("Amount must be greater than 0")
        return data