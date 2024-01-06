from django.test import TestCase
from django.urls import reverse
from django.contrib.auth import get_user_model
from rest_framework.test import APIClient
from rest_framework import status


class UserViewSetTestCase(TestCase):
    def setUp(self):
        self.client = APIClient()
        self.user = get_user_model().objects.create_user(
            email="testuser@test.com", password="testpass"
        )
        self.client.force_authenticate(user=self.user)

    def test_retrieve_user(self):
        response = self.client.get(reverse("user_me"))
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data["email"], self.user.email)

    def test_update_user(self):
        data = {
            "email": "hello@hello.com",
            "name": "New Name",
            "password": "newpasslonggg",
        }
        response = self.client.put(reverse("user_me"), data)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data["name"], data["name"])

    def test_partial_update_user(self):
        data = {"name": "New Name"}
        response = self.client.patch(reverse("user_me"), data)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data["name"], data["name"])

    def test_delete_user(self):
        response = self.client.delete(reverse("user_me"))
        self.assertEqual(response.status_code, status.HTTP_204_NO_CONTENT)
        self.assertFalse(get_user_model().objects.filter(id=self.user.id).exists())


class UserCreateViewSetTestCase(TestCase):
    def setUp(self):
        self.client = APIClient()

    def test_create_user(self):
        data = {
            "email": "newuser@test.com",
            "name": "New User",
            "password": "newpassw",
        }
        response = self.client.post(reverse("user-create"), data)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(response.data["email"], data["email"])
        self.assertEqual(response.data["name"], data["name"])
        self.assertTrue(get_user_model().objects.filter(email=data["email"]).exists())
