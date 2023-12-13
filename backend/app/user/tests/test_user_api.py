"""
Tests for the user API.
"""
from django.test import TestCase
from django.contrib.auth import get_user_model
from django.urls import reverse

from rest_framework.test import APIClient
from rest_framework import status


CREATE_USER_URL = reverse('user:user-list')


class PublicUserApiTests(TestCase):
    """Test the public features of the user API."""

    def setUp(self):
        self.client = APIClient()

        # create a normal user
        self.normal_user = get_user_model().objects.create_user(
            email='normal@user.com',
            password='testpass123',
        )

        # create an admin user
        self.admin_user = get_user_model().objects.create_superuser(
            email='admin@user.com',
            password='testpass123',
        )

    def test_create_user_success(self):
        """Test creating a new user is successful."""
        payload = {
            'email': 'new@user.com',
            'password': 'testpass123',
            'name': 'New User',
        }
        res = self.client.post(CREATE_USER_URL, payload)

        self.assertEqual(res.status_code, status.HTTP_201_CREATED)

    def test_user_with_email_exists_error(self):
        """Test error returned if user with email exists"""
        payload = {
            'email': 'existed@user.com',
            'password': 'testpass123',
        }
        get_user_model().objects.create_user(**payload)

        res = self.client.post(CREATE_USER_URL, payload)
        self.assertEqual(res.status_code, status.HTTP_400_BAD_REQUEST)
        self.assertNotIn('access', res.data)
        self.assertNotIn('refresh', res.data)

    def test_password_too_short_error(self):
        """Test an error is returned if password less than 8 chars."""
        payload = {
            'email': 'test@example.com',
            'password': 'pw',
        }
        res = self.client.post(CREATE_USER_URL, payload)

        self.assertEqual(res.status_code, status.HTTP_400_BAD_REQUEST)

    def test_create_token_for_user(self):
        """Test a token is created for the user."""
        payload = {
            'email': 'first@user.com',
            'password': 'testpass123',
        }
        get_user_model().objects.create_user(**payload)
        res = self.client.post(reverse('user:token_obtain_pair'), payload)
        self.assertIn('access', res.data)
        self.assertIn('refresh', res.data)
        self.assertEqual(res.status_code, status.HTTP_200_OK)

    def test_create_token_invalid_credentials(self):
        """Test that token is not created if invalid credentials are given."""
        payload = {
            'email': 'normal@user.com',
            'password': 'wrongpass',
        }
        res = self.client.post(reverse('user:token_obtain_pair'), payload)
        self.assertNotIn('access', res.data)
        self.assertNotIn('refresh', res.data)
        self.assertEqual(res.status_code, status.HTTP_401_UNAUTHORIZED)

    def test_list_users_unauthorized(self):
        """Test that authentication is required for retrieving users."""
        res = self.client.get(CREATE_USER_URL)
        self.assertEqual(res.status_code, status.HTTP_401_UNAUTHORIZED)

    def test_list_users_admin(self):
        """Test that admin users can retrieve users."""
        self.client.force_authenticate(self.admin_user)
        res = self.client.get(CREATE_USER_URL)
        self.assertEqual(res.status_code, status.HTTP_200_OK)

    def test_list_users_authenticated(self):
        """Test that authenticated users can retrieve users."""

        # use JWT
        payload = {
            'email': 'normal@user.com',
            'password': 'testpass123',
        }

        # get token
        res = self.client.post(reverse('user:token_obtain_pair'), payload)
        self.assertIn('access', res.data)
        self.assertIn('refresh', res.data)
        self.assertEqual(res.status_code, status.HTTP_200_OK)

        # use token
        token = res.data['access']

        # get users
        self.client.credentials(HTTP_AUTHORIZATION='Bearer ' + token)
        res = self.client.get(CREATE_USER_URL)

        self.assertEqual(res.status_code, status.HTTP_200_OK)
        self.assertEqual(len(res.data), 2)
