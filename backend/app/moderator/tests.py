from django.test import TestCase
from django.urls import reverse
from django.contrib.auth import get_user_model
from rest_framework.test import APIClient
from group.models import Group
from moderator.models import Moderator
from moderator.serializers import ModeratorDetailSerializer


class ModeratorViewSetTestCase(TestCase):
    def setUp(self):
        self.client = APIClient()
        self.user = get_user_model().objects.create_user(
            email="testuser@morello.app", password="testpass"
        )
        self.client.force_authenticate(user=self.user)
        self.group = Group.objects.create(name="Test Group", leader_user_id=self.user)
        self.moderator = Moderator.objects.create(
            user_id=self.user, group_id=self.group
        )

    def test_list_moderators(self):
        response = self.client.get(
            reverse("group-moderators-list", args=[self.group.id])
        )
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.data), 1)

    def test_create_moderator(self):
        new_user = get_user_model().objects.create_user(
            email="newuser@morello.app", password="newpass"
        )
        data = {
            "user_email": new_user.email,
        }
        response = self.client.post(
            reverse("group-moderators-list", args=[self.group.id]), data
        )
        self.assertEqual(response.status_code, 201)
        self.assertEqual(Moderator.objects.count(), 2)

    def test_delete_moderator(self):
        response = self.client.delete(
            reverse("group-moderators-detail", args=[self.group.id, self.moderator.id])
        )
        self.assertEqual(response.status_code, 204)
        self.assertEqual(Moderator.objects.count(), 0)
