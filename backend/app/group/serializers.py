from rest_framework import serializers
from .models import (
    Group,
)
from collect_session.models import CollectSession
from collect_session.serializers import CollectSessionListSerializer
from balance.serializers import BalanceEntryListSerializer


class GroupListSerializer(serializers.ModelSerializer):
    is_leader = serializers.SerializerMethodField()

    class Meta:
        model = Group
        fields = ["id", "name", "description", "is_leader", "created_at", "updated_at"]

    def get_is_leader(self, obj):
        return obj.leader_user_id == self.context["request"].user


class GroupCreateSerializer(serializers.ModelSerializer):
    class Meta:
        model = Group
        fields = ["name", "description"]
        extra_kwargs = {
            "name": {"required": True},
            "description": {"required": True},
        }

    def create(self, validated_data):
        # Add the leader_user_id from the context to the validated_data
        validated_data["leader_user_id"] = self.context["request"].user
        return super().create(validated_data)


class GroupDetailSerializer(serializers.ModelSerializer):
    recent_open_sessions = serializers.SerializerMethodField()
    recent_balance_entries = serializers.SerializerMethodField()
    DEFAULT_OPEN_SESSION_COUNT = 5
    DEFAULT_OPEN_BALANCE_ENTRY_COUNT = 5

    class Meta:
        model = Group
        fields = [
            "id",
            "name",
            "description",
            "created_at",
            "updated_at",
            "recent_open_sessions",
            "recent_balance_entries",
        ]

    def get_recent_open_sessions(self, obj):
        open_session_count = self.context["request"].query_params.get(
            "open_session_count", self.DEFAULT_OPEN_SESSION_COUNT
        )

        recent_open_sessions = CollectSession.objects.filter(
            group_id=obj, is_open=True
        ).order_by("-start")[:open_session_count]
        return CollectSessionListSerializer(recent_open_sessions, many=True).data

    def get_recent_balance_entries(self, obj):
        balance_entry_count = self.context["request"].query_params.get(
            "balance_entry_count", self.DEFAULT_OPEN_BALANCE_ENTRY_COUNT
        )

        recent_balance_entries = obj.balance_entries.order_by("-recorded_at")[
            :balance_entry_count
        ]
        return BalanceEntryListSerializer(recent_balance_entries, many=True).data


class GroupUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = Group
        fields = ["name", "description"]
        extra_kwargs = {
            "name": {"required": True},
            "description": {"required": True},
        }


class GroupPartialUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = Group
        fields = ["name", "description"]
        extra_kwargs = {
            "name": {"required": False},
            "description": {"required": False},
        }
