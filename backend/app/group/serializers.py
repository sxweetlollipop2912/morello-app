from rest_framework import serializers
from .models import (
    Group,
)
from collect_session.models import CollectSession
from collect_session.serializers import OpenCollectSessionOverviewSerializer


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


# {
#     "id": <int>,
#     "name": <string>,
#     "description": <string>,
#     "member_count": <int>,
#     "balance": {
#         "current": <float>,
#         "expected": <float>,
#     },
#     "recent_open_sessions": [ // contains at most <open_session_count> sessions
#         {
#             "id": <ID>,
#             "name": <string>,
#             "start": <datetime>,
#             "due": <datetime>,
#             "is_open": <bool>,
#             "member_count": <int>,
#             "paid_count": <int>,
#             "current_amount": <float>,
#             "expected_amount": <float>,
#             "amount_per_member": <float>,
#         },
#         {
#             "id": <ID>,
#             "name": <string>,
#             "start": <datetime>,
#             "due": <datetime>,
#             "is_open": <bool>,
#             "member_count": <int>,
#             "paid_count": <int>,
#             "current_amount": <float>,
#             "expected_amount": <float>,
#             "amount_per_member": <float>,
#         },
#     ],
#     "recent_balance_entries": [ // contains at most <balance_entry_count> balance entries
#         {
#             "id": <ID>,
#             "current_amount": <float>,
#             "expected_amount": <float>,
#             "description": <string>,
#             "created_at": <datetime>,
#             "session": {
#                 "id": <ID>,
#                 "start": <datetime>,
#                 "due": <datetime>,
#                 "is_open": <bool>,
#                 "member_count": <int>,
#                 "paid_count": <int>,
#                 "amount_per_member": <float>,
#             },
#         },
#         {
#             "id": <ID>,
#             "current_amount": <float>,
#             "expected_amount": <float>,
#             "description": <string>,
#             "created_at": <datetime>,
#             @optional "session": NULL,
#         },
#     ],
#     @optional "avatar_url": <url>,
# }
class GroupDetailSerializer(serializers.ModelSerializer):
    recent_open_sessions = serializers.SerializerMethodField()
    DEFAULT_OPEN_SESSION_COUNT = 5

    class Meta:
        model = Group
        fields = [
            "id",
            "name",
            "description",
            "created_at",
            "updated_at",
            "recent_open_sessions",
        ]

    def get_recent_open_sessions(self, obj):
        open_session_count = self.context["request"].query_params.get(
            "open_session_count", self.DEFAULT_OPEN_SESSION_COUNT
        )

        recent_open_sessions = CollectSession.objects.filter(
            group_id=obj, is_open=True
        ).order_by("-start")[:open_session_count]
        return OpenCollectSessionOverviewSerializer(
            recent_open_sessions, many=True
        ).data


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
