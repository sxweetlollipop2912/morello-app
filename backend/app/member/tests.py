from django.test import TestCase
from django.urls import reverse
from django.contrib.auth import get_user_model
from rest_framework.test import APIClient
from group.models import Group
from member.models import Member
from member.serializers import MemberDetailSerializer


class MemberViewSetTestCase(TestCase):
    def setUp(self):
        self.client = APIClient()
        self.user = get_user_model().objects.create_user(
            email="testuser", password="testpass"
        )
        self.client.force_authenticate(user=self.user)
        self.group = Group.objects.create(name="Test Group", leader_user_id=self.user)
        self.member = Member.objects.create(name="Test Member", group_id=self.group)

    def test_list_members(self):
        response = self.client.get(reverse("group-members-list", args=[self.group.id]))
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.data), 1)

    def test_retrieve_member(self):
        response = self.client.get(
            reverse("group-members-detail", args=[self.group.id, self.member.id])
        )
        self.assertEqual(response.status_code, 200)
        self.assertEqual(
            response.data,
            MemberDetailSerializer(
                self.member, context={"member_id": self.member.id}
            ).data,
        )

    def test_create_member(self):
        data = {
            "name": "New Member",
        }
        response = self.client.post(
            reverse("group-members-list", args=[self.group.id]), data
        )
        self.assertEqual(response.status_code, 201)
        self.assertEqual(Member.objects.count(), 2)

    def test_update_member(self):
        data = {
            "name": "Updated Member",
            "is_archived": False,
        }
        response = self.client.put(
            reverse("group-members-detail", args=[self.group.id, self.member.id]), data
        )
        self.assertEqual(response.status_code, 200)
        self.member.refresh_from_db()
        self.assertEqual(self.member.name, "Updated Member")

    def test_partial_update_member(self):
        data = {
            "name": "Partially Updated Member",
        }
        response = self.client.patch(
            reverse("group-members-detail", args=[self.group.id, self.member.id]), data
        )
        self.assertEqual(response.status_code, 200)
        self.member.refresh_from_db()
        self.assertEqual(self.member.name, "Partially Updated Member")

    def test_delete_member(self):
        response = self.client.delete(
            reverse("group-members-detail", args=[self.group.id, self.member.id])
        )
        self.assertEqual(response.status_code, 204)
        self.assertEqual(Member.objects.count(), 0)
