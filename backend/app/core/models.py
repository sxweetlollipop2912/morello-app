from __future__ import annotations
from django.db import models

from django.contrib.auth.models import (
    AbstractBaseUser, PermissionsMixin, BaseUserManager
)


class UserModel(models.Model):
    email = models.EmailField()
    name = models.CharField(max_length=255)
    password = models.CharField(max_length=255)
    created_at = models.DateTimeField(auto_now_add=True)


class GroupModel(models.Model):
    name = models.CharField(max_length=255)
    description = models.CharField(max_length=255)
    leader_user_id = models.ForeignKey(
        UserModel,
        on_delete=models.CASCADE,
        related_name='led_groups')
    created_at = models.DateTimeField(auto_now_add=True)


class ModeratorModel(models.Model):
    user_id = models.ForeignKey(
        UserModel,
        on_delete=models.CASCADE,
        related_name='moderated_groups')
    group_id = models.ForeignKey(
        GroupModel,
        on_delete=models.CASCADE,
        related_name='moderators')
    created_at = models.DateTimeField(auto_now_add=True)


class MemberModel(models.Model):
    group_id = models.ForeignKey(
        GroupModel,
        on_delete=models.CASCADE,
        related_name='members')
    name = models.CharField(max_length=255, unique=True)
    created_at = models.DateTimeField(auto_now_add=True)


class CollectSessionModel(models.Model):
    start = models.DateTimeField()
    due = models.DateTimeField()
    description = models.CharField(max_length=255)
    is_open = models.BooleanField(default=True)


class BalanceEntryModel(models.Model):
    group_id = models.ForeignKey(
        GroupModel,
        on_delete=models.CASCADE,
        related_name='balance_entries')
    amount = models.DecimalField(max_digits=10, decimal_places=2)
    description = models.CharField(max_length=255)
    date = models.DateTimeField()
    session_id = models.ForeignKey(
        CollectSessionModel,
        on_delete=models.SET_NULL,
        null=True,
        related_name='balance_entries')


class CollectEntryModel(models.Model):
    session_id = models.ForeignKey(
        CollectSessionModel,
        on_delete=models.CASCADE,
        related_name='collect_entries')
    member_id = models.ForeignKey(
        MemberModel,
        on_delete=models.CASCADE,
        related_name='collect_entries')
    status = models.BooleanField(default=False)
    amount = models.DecimalField(max_digits=10, decimal_places=2)


class UserManager(BaseUserManager):
    """Manager for users."""

    def _create_user(self, email, password, **extra_fields):
        if not email:
            raise ValueError('User must have an email address.')

        email = self.normalize_email(email)
        is_staff = extra_fields.pop('is_staff', False)
        is_superuser = extra_fields.pop('is_superuser', False)

        user = self.model(
            email=email,
            is_active=True,
            is_staff=is_staff,
            is_superuser=is_superuser,
            **extra_fields
        )
        user.set_password(password)

        user.save(using=self._db)

        return user

    def create_user(self, email, password=None, **extra_fields):
        return self._create_user(email, password, **extra_fields)

    def create_superuser(self, email, password, **extra_fields):
        return self._create_user(
            email, password, is_staff=True, is_superuser=True, **extra_fields)


class User(AbstractBaseUser, PermissionsMixin):
    """User in the system."""
    email = models.EmailField(max_length=255, unique=True)
    name = models.CharField(max_length=255)
    created_at = models.DateTimeField(auto_now_add=True)
    is_active = models.BooleanField(default=True)
    is_staff = models.BooleanField(default=False)

    objects = UserManager()

    USERNAME_FIELD = 'email'
