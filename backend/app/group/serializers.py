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
    class Meta:
        model = Group
        fields = ['id', 'name', 'description', 'leader_user_id', 'created_at']


class ModeratorSerializer(serializers.ModelSerializer):
    class Meta:
        model = Moderator
        fields = ['id', 'user_id', 'group_id', 'created_at']


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
