from datetime import datetime
from django.db import models
from collect_session.models import CollectSession
from group.models import Group

# Create your models here.


class BalanceEntry(models.Model):
    group_id = models.ForeignKey(
        Group, on_delete=models.CASCADE, related_name="balance_entries"
    )
    name = models.CharField(max_length=255)
    amount = models.IntegerField()
    description = models.CharField(max_length=255)
    date = models.DateTimeField(default=str(datetime.now()))
    session_id = models.ForeignKey(
        CollectSession,
        on_delete=models.CASCADE,
        blank=True,
        null=True,
        related_name="balance_entries",
    )
