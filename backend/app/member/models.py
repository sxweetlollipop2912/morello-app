from django.db import models
from group.models import Group
# Create your models here.


class Member(models.Model):
    group_id = models.ForeignKey(
        Group,
        on_delete=models.CASCADE,
        related_name='members')
    name = models.CharField(max_length=255, unique=True)
    created_at = models.DateTimeField(auto_now_add=True)
