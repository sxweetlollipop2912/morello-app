from django.test import TestCase
from django.urls import reverse
from django.contrib.auth import get_user_model
from rest_framework.test import APIClient
from user.models import User
from .models import Group
from .serializers import GroupDetailSerializer
from rest_framework.request import Request
from rest_framework.test import APIRequestFactory


class GroupViewSetTestCase(TestCase):
    def setUp(self):
        self.client = APIClient()
        self.user = User.objects.create_user(
            email="testuser@morello.app", password="testpass"
        )
        self.client.force_authenticate(self.user)
        self.group = Group.objects.create(name="Test Group", leader_user_id=self.user)

    def test_list_groups(self):
        response = self.client.get(reverse("groups-list"))
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.data), 1)

    def test_retrieve_group(self):
        response = self.client.get(reverse("groups-detail", args=[self.group.id]))
        self.assertEqual(response.status_code, 200)

        # Create a request object from the response
        request = Request(response.wsgi_request)

        self.assertEqual(
            response.data,
            GroupDetailSerializer(self.group, context={"request": request}).data,
        )

    def test_create_group(self):
        data = {
            "name": "New Group",
            "description": "New Description",
        }
        response = self.client.post(reverse("groups-list"), data)
        self.assertEqual(response.status_code, 201)
        self.assertEqual(Group.objects.count(), 2)

    def test_update_group(self):
        data = {
            "name": "Updated Group",
            "description": "Updated Description",
        }
        response = self.client.put(reverse("groups-detail", args=[self.group.id]), data)
        self.assertEqual(response.status_code, 200)
        self.group.refresh_from_db()
        self.assertEqual(self.group.name, "Updated Group")

    def test_partial_update_group(self):
        data = {
            "name": "Partially Updated Group",
        }
        response = self.client.patch(
            reverse("groups-detail", args=[self.group.id]), data
        )
        self.assertEqual(response.status_code, 200)
        self.group.refresh_from_db()
        self.assertEqual(self.group.name, "Partially Updated Group")

    def test_delete_group(self):
        response = self.client.delete(reverse("groups-detail", args=[self.group.id]))
        self.assertEqual(response.status_code, 204)
        self.assertEqual(Group.objects.count(), 0)
