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
            "recorded_at",
            "created_at",
            "updated_at",
        ]
