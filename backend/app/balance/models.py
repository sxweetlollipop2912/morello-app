from datetime import datetime
from django.db import models
from group.models import Group

# Create your models here.


class BalanceEntry(models.Model):
    group_id = models.ForeignKey(
        Group, on_delete=models.CASCADE, related_name="balance_entries"
    )
    name = models.CharField(max_length=255)
    amount = models.IntegerField()
    description = models.CharField(max_length=255)
    recorded_at = models.DateTimeField(default=datetime.now)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
