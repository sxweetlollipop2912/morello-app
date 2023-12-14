from django.db import models
from user.models import User

# Create your models here.


class Group(models.Model):
    name = models.CharField(max_length=255)
    description = models.CharField(max_length=255)
    leader_user_id = models.ForeignKey(
        User,
        on_delete=models.CASCADE,
        related_name='led_groups')
    created_at = models.DateTimeField(auto_now_add=True)


class Moderator(models.Model):
    user_id = models.ForeignKey(
        User,
        on_delete=models.CASCADE,
        related_name='moderated_groups')
    group_id = models.ForeignKey(
        Group,
        on_delete=models.CASCADE,
        related_name='moderators')
    created_at = models.DateTimeField(auto_now_add=True)


class Member(models.Model):
    group_id = models.ForeignKey(
        Group,
        on_delete=models.CASCADE,
        related_name='members')
    name = models.CharField(max_length=255, unique=True)
    created_at = models.DateTimeField(auto_now_add=True)


class CollectSession(models.Model):
    start = models.DateTimeField()
    due = models.DateTimeField()
    description = models.CharField(max_length=255)
    is_open = models.BooleanField(default=True)


class BalanceEntry(models.Model):
    group_id = models.ForeignKey(
        Group,
        on_delete=models.CASCADE,
        related_name='balance_entries')
    amount = models.DecimalField(max_digits=10, decimal_places=2)
    description = models.CharField(max_length=255)
    date = models.DateTimeField()
    session_id = models.ForeignKey(
        CollectSession,
        on_delete=models.SET_NULL,
        null=True,
        related_name='balance_entries')


class CollectEntry(models.Model):
    session_id = models.ForeignKey(
        CollectSession,
        on_delete=models.CASCADE,
        related_name='collect_entries')
    member_id = models.ForeignKey(
        Member,
        on_delete=models.CASCADE,
        related_name='collect_entries')
    status = models.BooleanField(default=False)
    amount = models.DecimalField(max_digits=10, decimal_places=2)
