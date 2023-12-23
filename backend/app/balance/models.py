from django.db import models
from group.models import Group
from collect_session.models import CollectSession

# Create your models here.


class BalanceEntry(models.Model):
    group_id = models.ForeignKey(
        Group, on_delete=models.CASCADE, related_name="balance_entries"
    )
    amount = models.DecimalField(max_digits=10, decimal_places=2)
    description = models.CharField(max_length=255)
    date = models.DateTimeField()
    session_id = models.ForeignKey(
        CollectSession,
        on_delete=models.SET_NULL,
        null=True,
        related_name="balance_entries",
    )
