from rest_framework import serializers
from .models import (
    Group,
    Moderator,
    Member,
    CollectSession,
    BalanceEntry,
    CollectEntry
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


class ModeratorSerializer(serializers.ModelSerializer):
    user_email = serializers.SerializerMethodField()

    class Meta:
        model = Moderator
        fields = ['id', 'user_id', 'user_email', 'group_id', 'created_at']

    def get_user_email(self, obj):
        return obj.user_id.email


class MemberSerializer(serializers.ModelSerializer):
    class Meta:
        model = Member
        fields = ['id', 'group_id', 'name', 'created_at']


class CollectSessionSerializer(serializers.ModelSerializer):
    class Meta:
        model = CollectSession
        fields = ['id', 'start', 'due', 'description', 'is_open']


class BalanceEntrySerializer(serializers.ModelSerializer):
    class Meta:
        model = BalanceEntry
        fields = [
            'id',
            'group_id',
            'amount',
            'description',
            'date',
            'session_id'
        ]


class CollectEntrySerializer(serializers.ModelSerializer):
    class Meta:
        model = CollectEntry
        fields = ['id', 'session_id', 'member_id', 'status', 'amount']
