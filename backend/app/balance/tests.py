from django.test import TestCase
from django.urls import reverse
from rest_framework.test import APIClient
from user.models import User
from group.models import Group
from .models import BalanceEntry
from .serializers import BalanceEntryListSerializer


class BalanceEntryViewSetTestCase(TestCase):
    def setUp(self):
        self.client = APIClient()
        self.user = User.objects.create_user(
            email="testuser@morello.app", password="testpassword"
        )
        self.client.force_authenticate(user=self.user)
        self.group = Group.objects.create(name="Test Group", leader_user_id=self.user)
        self.balance_entry = BalanceEntry.objects.create(
            group_id=self.group,
            name="Test Entry",
            amount=100.0,
            description="Test Description",
            recorded_at="2022-01-01T00:00:00Z",
        )

    def test_list_balance_entries(self):
        response = self.client.get(
            reverse("group-balance-entries-list", args=[self.group.id])
        )
        self.assertEqual(response.status_code, 200)
        self.assertEqual(len(response.data), 1)

    def test_retrieve_balance_entry(self):
        response = self.client.get(
            reverse(
                "group-balance-entries-detail",
                args=[self.group.id, self.balance_entry.id],
            )
        )
        self.assertEqual(response.status_code, 200)
        self.assertEqual(
            response.data, BalanceEntryListSerializer(self.balance_entry).data
        )

    def test_create_balance_entry(self):
        data = {
            "name": "New Entry",
            "amount": 200.0,
            "description": "New Description",
            "recorded_at": "2022-01-02T00:00:00Z",
        }
        response = self.client.post(
            reverse("group-balance-entries-list", args=[self.group.id]), data
        )
        self.assertEqual(response.status_code, 201)
        self.assertEqual(BalanceEntry.objects.count(), 2)

    def test_update_balance_entry(self):
        data = {
            "name": "Updated Entry",
            "amount": 200,
            "description": "Updated Description",
            "recorded_at": "2022-01-02T00:00:00Z",
        }
        response = self.client.put(
            reverse(
                "group-balance-entries-detail",
                args=[self.group.id, self.balance_entry.id],
            ),
            data,
        )
        self.assertEqual(response.status_code, 200)
        self.balance_entry.refresh_from_db()
        self.assertEqual(self.balance_entry.name, "Updated Entry")

    def test_partial_update_balance_entry(self):
        data = {
            "name": "Partially Updated Entry",
        }
        response = self.client.patch(
            reverse(
                "group-balance-entries-detail",
                args=[self.group.id, self.balance_entry.id],
            ),
            data,
        )
        self.assertEqual(response.status_code, 200)
        self.balance_entry.refresh_from_db()
        self.assertEqual(self.balance_entry.name, "Partially Updated Entry")

    def test_delete_balance_entry(self):
        response = self.client.delete(
            reverse(
                "group-balance-entries-detail",
                args=[self.group.id, self.balance_entry.id],
            )
        )
        self.assertEqual(response.status_code, 204)
        self.assertEqual(BalanceEntry.objects.count(), 0)
