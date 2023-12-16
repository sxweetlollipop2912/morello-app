from rest_framework import serializers
from .models import (
    Group,
    # Member,
    # CollectSession,
    # BalanceEntry,
    # CollectEntry
)


class GroupSerializer(serializers.ModelSerializer):
    leader_user_email = serializers.SerializerMethodField()

    class Meta:
        model = Group
        fields = [
            'id',
            'name',
            'description',
            'leader_user_id',
            'leader_user_email',
            'created_at']

    def get_leader_user_email(self, obj):
        return obj.leader_user_id.email
