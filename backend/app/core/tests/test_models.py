from datetime import datetime, timedelta
from django.conf import django
from django.test import TestCase
from django.contrib.auth import get_user_model

from core.models import Group, Member, MoneyCollectSession


class ModelTests(TestCase):
    def create_test_user(self, username="testuser"):
        """Create a test user"""
        email = "test@gmail.com"
        password = "Morello@2023"

        user = get_user_model().objects.create_user(
            username=username,
            email=email,
            password=password,
        )
        return user

    def test_create_group(self):
        """Test creating a new group"""
        name = "testgroup"
        leader = self.create_test_user()
        description = "test description"
        group = Group.objects.create(
            name=name,
            leader=leader,
            description=description,
        )

        moderator1 = self.create_test_user(username="moderator1")
        moderator2 = self.create_test_user(username="moderator2")
        group.moderators.add(moderator1)
        group.moderators.add(moderator2)

        self.assertEqual(group.name, name)
        self.assertEqual(group.leader, leader)

        self.assertEqual(group.moderators.count(), 2)
        self.assertEqual(group.moderators.first(), moderator1)
        self.assertEqual(group.moderators.last(), moderator2)

    def test_create_group_without_leader(self):
        """Test creating a new group without leader"""
        name = "testgroup"
        description = "test description"
        with self.assertRaises(django.db.utils.IntegrityError):
            Group.objects.create(
                name=name,
                description=description,
            )

    def test_create_group_without_name(self):
        """Test creating a new group without name"""
        leader = self.create_test_user()
        description = "test description"
        with self.assertRaises(django.db.utils.IntegrityError):
            Group.objects.create(
                leader=leader,
                description=description,
            )

    def test_create_money_collect_session(self):
        """Test creating a new money collect session"""
        group = Group.objects.create(
            name="testgroup",
            leader=self.create_test_user(),
            description="test description",
        )
        amount = 100
        description = "test description"
        start = datetime.utcnow()
        due = datetime.utcnow() + timedelta(days=1)
        session = group.money_collect_sessions.create(
            amount=amount,
            description=description,
            start=start,
            due=due,
        )

        self.assertEqual(session.group, group)
        self.assertEqual(session.amount, amount)
        self.assertEqual(session.description, description)
        self.assertEqual(session.start, start)
        self.assertEqual(session.due, due)

    def test_create_money_collect_session_without_group(self):
        """Test creating a new money collect session without group"""
        amount = 100
        description = "test description"
        start = datetime.utcnow()
        due = datetime.utcnow() + timedelta(days=1)
        with self.assertRaises(django.db.utils.IntegrityError):
            MoneyCollectSession.objects.create(
                amount=amount,
                description=description,
                start=start,
                due=due,
            )

    def test_create_money_collect_session_without_amount(self):
        """Test creating a new money collect session without amount"""
        group = Group.objects.create(
            name="testgroup",
            leader=self.create_test_user(),
            description="test description",
        )
        description = "test description"
        start = "2021-01-01 00:00:00"
        due = "2021-01-01 00:00:00"
        with self.assertRaises(django.db.utils.IntegrityError):
            group.money_collect_sessions.create(
                description=description,
                start=start,
                due=due,
            )

    def test_group_members_crud(self):
        """Test group members CRUD"""
        group = Group.objects.create(
            name="testgroup",
            leader=self.create_test_user(),
            description="test description",
        )

        # Create
        Member.objects.create(
            name="member1",
            group=group,
        )
        Member.objects.create(
            name="member2",
            group=group,
        )
        # Read
        self.assertEqual(group.members.count(), 2)
        self.assertEqual(group.members.first().name, "member1")
        self.assertEqual(group.members.last().name, "member2")

        # Update
        first_member = group.members.first()
        first_member.name = "member3"
        first_member.save()
        self.assertEqual(group.members.first().name, "member3")

        # Delete
        group.members.first().delete()
        self.assertEqual(group.members.count(), 1)

    def test_dup_member(self):
        """Test creating a duplicate member"""
        group = Group.objects.create(
            name="testgroup",
            leader=self.create_test_user(),
            description="test description",
        )
        Member.objects.create(
            name="member1",
            group=group,
        )
        with self.assertRaises(django.db.utils.IntegrityError):
            Member.objects.create(
                name="member1",
                group=group,
            )

    def test_money_collect_session_entries_crud(self):
        """Test money collect session entries CRUD"""
        group = Group.objects.create(
            name="testgroup",
            leader=self.create_test_user(),
            description="test description",
        )
        session = group.money_collect_sessions.create(
            amount=100,
            description="test description",
            start="2021-01-01 00:00:00",
            due="2021-01-01 00:00:00",
        )

        Member.objects.create(
            name="member1",
            group=group,
        )
        Member.objects.create(
            name="member2",
            group=group,
        )
        # Create
        session.entries.create(
            member=group.members.first(),
            amount=100,
        )
        session.entries.create(
            member=group.members.last(),
            amount=100,
        )

        # Read
        self.assertEqual(session.entries.count(), 2)
        self.assertEqual(session.entries.first().member.name, "member1")
        self.assertEqual(session.entries.last().member.name, "member2")

        # Update
        session.entries.filter(member=group.members.first()).update(amount=200)
        session.entries.first().save()
        second_entry = session.entries.last()
        second_entry.amount = 150
        second_entry.save()
        self.assertEqual(session.entries.first().amount, 200)
        self.assertEqual(session.entries.last().amount, 150)

        # Delete
        session.entries.first().delete()
        self.assertEqual(session.entries.count(), 1)

    def test_new_user_email_normalized(self):
        """Test email is normalized for new users."""
        sample_emails = [
            ['test1@EXAMPLE.com', 'test1@example.com'],
            ['Test2@Example.com', 'Test2@example.com'],
            ['TEST3@EXAMPLE.com', 'TEST3@example.com'],
            ['test4@example.COM', 'test4@example.com'],
        ]
        for email, expected in sample_emails:
            user = get_user_model().objects.create_user(email, 'sample123')
            self.assertEqual(user.email, expected)

    def test_new_user_without_email_raises_error(self):
        """Test that creating a user without an email raises a ValueError."""
        with self.assertRaises(ValueError):
            get_user_model().objects.create_user('', 'test123')

    def test_create_superuser(self):
        """Test creating a superuser."""
        user = get_user_model().objects.create_superuser(
            'test@example.com',
            'test123',
        )

        self.assertTrue(user.is_superuser)
        self.assertTrue(user.is_staff)
