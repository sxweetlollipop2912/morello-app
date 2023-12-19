from rest_framework import serializers
from .models import Moderator


class ModeratorSerializer(serializers.ModelSerializer):
    user_email = serializers.SerializerMethodField()

    class Meta:
        model = Moderator
        fields = ['id', 'user_id', 'user_email', 'group_id', 'created_at']

    def get_user_email(self, obj):
        return obj.user_id.email
