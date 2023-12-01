import time

from psycopg2 import OperationalError as Psycopg2OpError

from django.db.utils import OperationalError
from django.core.management.base import BaseCommand


class Command(BaseCommand):
    """Django command to wait for database."""

    def handle(self, *args, **options):
        """Entrypoint for command."""
        self.stdout.write('Waiting for database...')
        is_database_ready = False
        while is_database_ready is False:
            try:
                self.check(databases=['default'])
                is_database_ready = True
            except (Psycopg2OpError, OperationalError):
                self.stdout.write(
                    'Database is unavailable, waiting 1 second...')
                time.sleep(1)

        self.stdout.write(self.style.SUCCESS('Database is now available!'))
