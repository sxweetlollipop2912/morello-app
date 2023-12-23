from unittest.mock import patch

from django.core.management import call_command
from django.db.utils import OperationalError
from django.test import TestCase


# see:
# https://docs.djangoproject.com/en/4.2/howto/custom-management-commands/#django.core.management.BaseCommand.check
# https://stackoverflow.com/questions/52621819/django-unit-test-wait-for-database


class CommandTests(TestCase):
    """Test commands."""

    def test_wait_for_database_ready(self):
        """Test waiting for database if database ready."""
        with patch(
            "core.management.commands.wait_for_database.Command.check"
        ) as patched_check:  # noqa
            patched_check.return_value = True

            call_command("wait_for_database")

            patched_check.assert_called_once_with(databases=["default"])

    def test_wait_for_database_delay(self):
        """Test waiting for database when getting OperationalError."""
        with patch("time.sleep") as patched_sleep, patch(
            "core.management.commands.wait_for_database.Command.check"
        ) as patched_check:  # noqa
            patched_sleep.return_value = True
            patched_check.side_effect = [OperationalError] * 5 + [True]

            call_command("wait_for_database")

            self.assertEqual(patched_check.call_count, 6)
            patched_check.assert_called_with(databases=["default"])
