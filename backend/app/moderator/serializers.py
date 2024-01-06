from rest_framework import serializers
from .models import Moderator
from user.models import User
from django.shortcuts import get_object_or_404


class ModeratorListSerializer(serializers.ModelSerializer):
    user_email = serializers.SerializerMethodField()

    class Meta:
        model = Moderator
        fields = ["id", "user_id", "user_email", "created_at", "updated_at"]

    def get_user_email(self, obj):
        return obj.user_id.email


class ModeratorDetailSerializer(serializers.ModelSerializer):
    user_email = serializers.SerializerMethodField()

    class Meta:
        model = Moderator
        fields = ["id", "user_id", "user_email", "created_at", "updated_at"]

    def get_user_email(self, obj):
        return obj.user_id.email


class ModeratorCreateSerializer(serializers.ModelSerializer):
    user_email = serializers.EmailField()

    class Meta:
        model = Moderator
        fields = ["user_email"]

    def create(self, validated_data):
        user_email = validated_data["user_email"]
        user = get_object_or_404(User, email=user_email)
        group_id = self.context["group_id"]
        moderator = Moderator.objects.create(user_id=user, group_id_id=group_id)
        return moderator
