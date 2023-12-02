from __future__ import annotations
from django.db import models
from django.contrib.auth.models import User


# Create your models here.


class Group(models.Model):
    name = models.CharField(max_length=255, blank=False, null=False)
    leader = models.ForeignKey(User, on_delete=models.CASCADE)
    description = models.TextField()
    moderators = models.ManyToManyField(
        User, through="Moderator", related_name="moderators"
    )

    class Meta:
        constraints = [
            models.UniqueConstraint(fields=["name", "leader"], name="unique_group"),
            models.CheckConstraint(
                check=models.Q(name__isnull=False) & ~models.Q(name=""),
                name="not_empty_name",
            ),
        ]


class Moderator(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    group = models.ForeignKey(Group, on_delete=models.CASCADE)
    added = models.DateTimeField(auto_now_add=True)


class Member(models.Model):
    name = models.CharField(max_length=255)
    group = models.ForeignKey(Group, on_delete=models.CASCADE, related_name="members")
    added = models.DateTimeField(auto_now_add=True)

    class Meta:
        constraints = [
            models.UniqueConstraint(fields=["name", "group"], name="unique_member")
        ]


class MoneyCollectSession(models.Model):
    group = models.ForeignKey(
        Group, on_delete=models.CASCADE, related_name="money_collect_sessions"
    )
    amount = models.DecimalField(max_digits=10, decimal_places=2)
    description = models.TextField()
    created = models.DateTimeField(auto_now_add=True)
    start = models.DateTimeField()
    due = models.DateTimeField()


class MoneyCollectSessionEntry(models.Model):
    session = models.ForeignKey(
        MoneyCollectSession, on_delete=models.CASCADE, related_name="entries"
    )
    member = models.ForeignKey(Member, on_delete=models.CASCADE)
    amount = models.DecimalField(max_digits=10, decimal_places=2)
    paid = models.BooleanField(default=False)
