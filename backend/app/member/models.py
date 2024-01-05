from django.db import models
from group.models import Group

# Create your models here.


class Member(models.Model):
    group_id = models.ForeignKey(
        Group, on_delete=models.CASCADE, related_name="members"
    )
    name = models.CharField(max_length=255)
    is_archived = models.BooleanField(default=False)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        unique_together = ("group_id", "name")
