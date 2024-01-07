from django.test import TestCase
from django.urls import reverse
from django.contrib.auth import get_user_model
from rest_framework.test import APIClient
from group.models import Group
from member.models import Member
from collect_session.models import CollectSession
from django.utils import timezone
import datetime


class CollectSessionViewSetTestCase(TestCase):
    def setUp(self):
        self.client = APIClient()
        self.user = get_user_model().objects.create_user(
            email="testuser@test.com", password="testpass"
        )
        self.client.force_authenticate(user=self.user)
        self.group = Group.objects.create(name="Test Group", leader_user_id=self.user)
        self.member = Member.objects.create(group_id=self.group, name="Test Member")
        start_date = timezone.make_aware(datetime.datetime(2022, 1, 1))
        due_date = timezone.make_aware(datetime.datetime(2022, 1, 31))
        self.collect_session = CollectSession.objects.create(
            group_id=self.group,
            name="Test Session",
            description="Test Description",
            start=start_date,
            due=due_date,
            payment_per_member=100,
            is_open=True,
        )

    def test_list_collect_sessions(self):
        response = self.client.get(reverse("group-sessions-list", args=[self.group.id]))
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.data), 1)

    def test_retrieve_collect_session(self):
        response = self.client.get(
            reverse(
                "group-sessions-detail", args=[self.group.id, self.collect_session.id]
            )
        )
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["name"], "Test Session")

    def test_create_collect_session(self):
        start_date = timezone.make_aware(datetime.datetime(2022, 2, 1))
        due_date = timezone.make_aware(datetime.datetime(2022, 2, 28))
        data = {
            "name": "New Session",
            "description": "New Description",
            "start": start_date.isoformat(),
            "due": due_date.isoformat(),
            "payment_per_member": 200,
            "member_ids": [self.member.id],
        }
        response = self.client.post(
            reverse("group-sessions-list", args=[self.group.id]), data
        )
        self.assertEqual(response.status_code, 201)
        self.assertEqual(CollectSession.objects.count(), 2)

    def test_update_collect_session(self):
        start_date = timezone.make_aware(datetime.datetime(2022, 1, 1))
        due_date = timezone.make_aware(datetime.datetime(2022, 1, 31))
        data = {
            "name": "Updated Session",
            "description": "Updated Description",
            "start": start_date.isoformat(),
            "due": due_date.isoformat(),
            "payment_per_member": 150,
            "is_open": False,
        }
        response = self.client.put(
            reverse(
                "group-sessions-detail", args=[self.group.id, self.collect_session.id]
            ),
            data,
        )
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["name"], "Updated Session")

    def test_partial_update_collect_session(self):
        data = {
            "name": "Partially Updated Session",
        }
        response = self.client.patch(
            reverse(
                "group-sessions-detail", args=[self.group.id, self.collect_session.id]
            ),
            data,
        )
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.data["name"], "Partially Updated Session")

    def test_close_collect_session(self):
        response = self.client.post(
            reverse(
                "group-sessions-close", args=[self.group.id, self.collect_session.id]
            )
        )
        self.assertEqual(response.status_code, 200)
        self.assertFalse(CollectSession.objects.get(id=self.collect_session.id).is_open)
