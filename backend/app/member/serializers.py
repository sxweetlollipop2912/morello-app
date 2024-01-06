from rest_framework import serializers
from .models import (
    Member,
)
from collect_session.models import CollectSession
from collect_session.serializers import CollectSessionMemberViewSerializer

from django.db.models import Sum


class MemberListSerializer(serializers.ModelSerializer):
    class Meta:
        model = Member
        fields = ["id", "name", "is_archived", "created_at", "updated_at"]


class MemberCreateSerializer(serializers.ModelSerializer):
    class Meta:
        model = Member
        fields = ["name"]

    def create(self, validated_data):
        group_id = self.context["group_id"]
        member = Member.objects.create(group_id_id=group_id, **validated_data)
        return member


class MemberDetailSerializer(serializers.ModelSerializer):
    related_sessions = serializers.SerializerMethodField()
    total_due_amount = serializers.SerializerMethodField()

    class Meta:
        model = Member
        fields = [
            "id",
            "name",
            "is_archived",
            "created_at",
            "updated_at",
            "total_due_amount",
            "related_sessions",
        ]

    def get_related_sessions(self, obj):
        related_sessions = CollectSession.objects.filter(
            collect_entries__member_id=obj.id
        )
        member_id = self.context["member_id"]
        return CollectSessionMemberViewSerializer(
            related_sessions, many=True, context={"member_id": member_id}
        ).data

    def get_total_due_amount(self, obj):
        return (
            CollectSession.objects.filter(
                collect_entries__member_id=obj.id, collect_entries__status=False
            ).aggregate(total_due_amount=Sum("payment_per_member"))["total_due_amount"]
            or 0
        )


class MemberUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = Member
        fields = ["name", "is_archived"]
        extra_kwargs = {
            "name": {"required": True},
            "is_archived": {"required": True},
        }


class MemberPartialUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = Member
        fields = ["name", "is_archived"]
        extra_kwargs = {
            "name": {"required": False},
            "is_archived": {"required": False},
        }
