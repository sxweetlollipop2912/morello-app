from rest_framework import serializers
from .models import CollectSession, CollectEntry
from member.models import Member


class CollectSessionListSerializer(serializers.ModelSerializer):
    paid_count = serializers.SerializerMethodField()
    member_count = serializers.SerializerMethodField()
    current_amount = serializers.SerializerMethodField()
    expected_amount = serializers.SerializerMethodField()

    class Meta:
        model = CollectSession
        fields = [
            "id",
            "name",
            "description",
            "start",
            "due",
            "is_open",
            "payment_per_member",
            "paid_count",
            "member_count",
            "current_amount",
            "expected_amount",
            "created_at",
            "updated_at",
        ]

    def get_paid_count(self, obj):
        return obj.get_paid_count()

    def get_member_count(self, obj):
        return obj.get_member_count()

    def get_current_amount(self, obj):
        return obj.get_current_amount()

    def get_expected_amount(self, obj):
        return obj.get_expected_amount()


class CollectSessionDetailSerializer(serializers.ModelSerializer):
    paid_count = serializers.SerializerMethodField()
    member_count = serializers.SerializerMethodField()
    current_amount = serializers.SerializerMethodField()
    expected_amount = serializers.SerializerMethodField()
    member_statuses = serializers.SerializerMethodField()

    class Meta:
        model = CollectSession
        fields = [
            "id",
            "name",
            "description",
            "start",
            "due",
            "is_open",
            "payment_per_member",
            "paid_count",
            "member_count",
            "current_amount",
            "expected_amount",
            "created_at",
            "updated_at",
            "member_statuses",
        ]

    def get_paid_count(self, obj):
        return obj.get_paid_count()

    def get_member_count(self, obj):
        return obj.get_member_count()

    def get_current_amount(self, obj):
        return obj.get_current_amount()

    def get_expected_amount(self, obj):
        return obj.get_expected_amount()

    def get_member_statuses(self, obj):
        return obj.collect_entries.annotate(
            id=F("member_id_id"), name=F("member_id__name"), status=F("status")
        ).values("id", "name", "status")


class CollectSessionCreateSerializer(serializers.ModelSerializer):
    member_ids = serializers.PrimaryKeyRelatedField(
        many=True, queryset=Member.objects.all(), write_only=True
    )

    class Meta:
        model = CollectSession
        fields = [
            "name",
            "description",
            "start",
            "due",
            "payment_per_member",
            "member_ids",
        ]

    def create(self, validated_data):
        member_ids = validated_data.pop("member_ids")
        collect_session = CollectSession.objects.create(**validated_data)
        for member_id in member_ids:
            CollectEntry.objects.create(session_id=collect_session, member_id=member_id)
        return collect_session


class CollectSessionUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = CollectSession
        fields = ["name", "description", "start", "due"]


class CollectSessionPartialUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = CollectSession
        fields = ["name", "description", "start", "due"]
        extra_kwargs = {
            "name": {"required": False},
            "description": {"required": False},
            "start": {"required": False},
            "due": {"required": False},
        }


class CollectSessionOpenCloseSerializer(serializers.ModelSerializer):
    class Meta:
        model = CollectSession
        fields = []


class CollectEntryUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = CollectEntry
        fields = ["status"]


class CollectSessionMemberViewSerializer(serializers.ModelSerializer):
    status = serializers.SerializerMethodField()

    class Meta:
        model = CollectSession
        fields = [
            "id",
            "name",
            "description",
            "start",
            "due",
            "payment_per_member",
            "status",
        ]

    def get_status(self, obj):
        return (
            obj.collect_entries.filter(member_id=self.context["member_id"])
            .first()
            .status
        )
