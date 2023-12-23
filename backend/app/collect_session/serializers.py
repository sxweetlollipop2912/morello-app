from rest_framework import serializers
from .models import CollectSession, CollectEntry


class CollectSessionSerializer(serializers.ModelSerializer):
    class Meta:
        model = CollectSession
        fields = ["id", "start", "due", "is_open"]


class CollectEntrySerializer(serializers.ModelSerializer):
    class Meta:
        model = CollectEntry
        fields = ["id", "session_id", "member_id", "status", "amount"]


class OpenCollectSessionOverviewSerializer(serializers.ModelSerializer):
    paid_count = serializers.SerializerMethodField()
    member_count = serializers.SerializerMethodField()
    current_amount = serializers.SerializerMethodField()
    expected_amount = serializers.SerializerMethodField()

    class Meta:
        model = CollectSession
        fields = [
            "id",
            "name",
            "description",
            "start",
            "due",
            "is_open",
            "payment_per_member",
            "paid_count",
            "member_count",
            "current_amount",
            "expected_amount",
            "created_at",
            "updated_at",
        ]

    def get_paid_count(self, obj):
        return obj.collect_entries.filter(status=True).count()

    def get_member_count(self, obj):
        return obj.collect_entries.count()

    def get_current_amount(self, obj):
        return obj.collect_entries.filter(status=True).count() * obj.payment_per_member

    def get_expected_amount(self, obj):
        return obj.collect_entries.count() * obj.payment_per_member
