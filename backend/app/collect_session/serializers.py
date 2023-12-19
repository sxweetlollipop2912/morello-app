from rest_framework import serializers
from .models import (
    CollectSession,
    CollectEntry
)


class CollectSessionSerializer(serializers.ModelSerializer):
    class Meta:
        model = CollectSession
        fields = ['id', 'start', 'due', 'is_open']


class CollectEntrySerializer(serializers.ModelSerializer):
    class Meta:
        model = CollectEntry
        fields = ['id', 'session_id', 'member_id', 'status', 'amount']
