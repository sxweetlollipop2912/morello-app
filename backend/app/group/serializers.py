from rest_framework import serializers
from .models import (
    Group,
)


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


class GroupDetailSerializer(serializers.ModelSerializer):
    # Group Detail Serializer
    # TODO: Fix this to match the specfications
    class Meta:
        model = Group
        fields = ["id", "name", "description", "created_at", "updated_at"]
        extra_kwargs = {
            "name": {"required": True},
            "description": {"required": True},
        }


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
