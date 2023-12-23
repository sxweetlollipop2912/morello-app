from rest_framework import serializers
from .models import (
    Member,
)


class MemberListSerializer(serializers.ModelSerializer):
    class Meta:
        model = Member
        fields = ["id", "name", "is_archived", "created_at", "updated_at"]


class MemberCreateSerializer(serializers.ModelSerializer):
    class Meta:
        model = Member
        fields = ["name"]


class MemberDetailSerializer(serializers.ModelSerializer):
    # TODO: Add balance and collect entries + due amount
    class Meta:
        model = Member
        fields = ["id", "name", "is_archived", "created_at", "updated_at"]


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
