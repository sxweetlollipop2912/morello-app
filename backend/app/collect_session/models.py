from django.db import models
from member.models import Member

# Create your models here.


class CollectSession(models.Model):
    start = models.DateTimeField()
    due = models.DateTimeField()
    description = models.CharField(max_length=255)
    is_open = models.BooleanField(default=True)


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
