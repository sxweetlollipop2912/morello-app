from rest_framework import serializers
from .models import (
    Member,
)


class MemberSerializer(serializers.ModelSerializer):
    class Meta:
        model = Member
        fields = ["id", "group_id", "name", "created_at"]
