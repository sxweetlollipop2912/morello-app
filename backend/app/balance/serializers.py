from rest_framework import serializers
from .models import (
    BalanceEntry,
)


class BalanceEntryListSerializer(serializers.ModelSerializer):
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


class BalanceEntryDetailSerializer(serializers.ModelSerializer):
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


class BalanceEntryCreateSerializer(serializers.ModelSerializer):
    class Meta:
        model = BalanceEntry
        fields = [
            "name",
            "amount",
            "description",
            "recorded_at",
        ]
        extra_kwargs = {
            "name": {"required": True},
            "amount": {"required": True},
            "description": {"required": False},
            "recorded_at": {"required": False},
        }

    def create(self, validated_data):
        group_id = self.context["group_id"]
        balance_entry = BalanceEntry.objects.create(
            group_id_id=group_id, **validated_data
        )
        return balance_entry


class BalanceEntryUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = BalanceEntry
        fields = [
            "name",
            "amount",
            "description",
            "recorded_at",
        ]
        extra_kwargs = {
            "name": {"required": True},
            "amount": {"required": True},
            "description": {"required": True},
            "recorded_at": {"required": True},
        }


class BalanceEntryPartialUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = BalanceEntry
        fields = [
            "name",
            "amount",
            "description",
            "recorded_at",
        ]
        extra_kwargs = {
            "name": {"required": False},
            "amount": {"required": False},
            "description": {"required": False},
            "recorded_at": {"required": False},
        }
