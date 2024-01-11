from django.core.management.base import BaseCommand
from faker import Faker
from user.models import User
from group.models import Group
from member.models import Member
from moderator.models import Moderator
from balance.models import BalanceEntry
from collect_session.models import CollectSession, CollectEntry
from django.db.utils import IntegrityError
from django.utils import timezone


class Command(BaseCommand):
    help = "Populates the database with mock data"

    def handle(self, *args, **options):
        Moderator.objects.all().delete()
        CollectEntry.objects.all().delete()
        CollectSession.objects.all().delete()
        Member.objects.all().delete()
        BalanceEntry.objects.all().delete()
        User.objects.all().delete()
        Group.objects.all().delete()
        self.stdout.write(self.style.SUCCESS("Successfully cleared the database"))

        self.stdout.write(self.style.SUCCESS("Populating the database..."))

        faker = Faker()

        # create superuser
        User.objects.create_superuser(
            email="admin@morello.app",
            password="admin",
            name="Admin",
        )

        # Create some mock users
        for _ in range(5):
            User.objects.create(
                email=faker.email(),
                name=faker.name(),
                # Add any other required fields here
            )

        users = User.objects.all()

        # Create some mock groups
        for _ in range(20):
            group = Group.objects.create(
                name=faker.word(),
                description=faker.sentence(),
                leader_user_id=users[faker.random_int(min=0, max=users.count() - 1)],
            )

            # Create some mock members
            members = []
            for _ in range(30):
                member = Member.objects.create(
                    name=faker.name(),
                    group_id=group,
                )
                members.append(member)

            # Create some mock balance entries
            for _ in range(30):
                BalanceEntry.objects.create(
                    group_id=group,
                    name=faker.word(),
                    amount=faker.random_int(min=10, max=2000) * (1 if faker.random_int(min=0, max=1) else -1) * 1000,
                    description=faker.sentence(),
                    recorded_at=timezone.make_aware(faker.date_time_this_year() - timezone.timedelta(days=faker.random_int(min=1, max=30))),
                )

            # Create some mock moderators
            for _ in range(5):
                Moderator.objects.create(
                    user_id=users[faker.random_int(min=0, max=users.count() - 1)],
                    group_id=group,
                )

            # Create some mock collect sessions
            for _ in range(20):
                is_open = faker.boolean()
                balance_entry = None
                if not is_open:
                    balance_entry = BalanceEntry.objects.create(
                        group_id=group,
                        name=faker.word(),
                        amount=faker.random_int(min=1, max=1000) * 1000,
                        description=faker.sentence(),
                        recorded_at=timezone.make_aware(faker.date_time_this_year()),
                    )

                session = CollectSession.objects.create(
                    group_id=group,
                    name=faker.word(),
                    description=faker.sentence(),
                    start=timezone.make_aware(faker.date_time_this_year() - timezone.timedelta(days=faker.random_int(min=1, max=30))),
                    due=timezone.make_aware(faker.date_time_this_year() + timezone.timedelta(days=faker.random_int(min=-30, max=30))),
                    payment_per_member=faker.random_int(min=1, max=1000) * 1000,
                    is_open=is_open,
                    balance_entry_id=balance_entry,
                )

                # Create some mock collect entries
                for _ in range(faker.random_int(min=0, max=len(members))):
                    unique_pair_created = False
                    while not unique_pair_created:
                        member = members[faker.random_int(min=0, max=len(members) - 1)]
                        try:
                            CollectEntry.objects.create(
                                session_id=session,
                                member_id=member,
                                status=faker.boolean(),
                            )
                            unique_pair_created = True
                        except IntegrityError:
                            continue

        self.stdout.write(self.style.SUCCESS("Successfully populated the database"))
