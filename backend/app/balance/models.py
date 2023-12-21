from django.db import models
from group.models import Group
from collect_session.models import CollectSession

# Create your models here.


class BalanceEntry(models.Model):
    group_id = models.ForeignKey(
        Group, on_delete=models.CASCADE, related_name="balance_entries"
    )
    amount = models.IntegerField()
    description = models.CharField(max_length=255)
    date = models.DateTimeField()
