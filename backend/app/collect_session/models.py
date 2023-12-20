from django.db import models
from member.models import Member

# Create your models here.


class CollectSession(models.Model):
    start = models.DateTimeField()
    due = models.DateTimeField()
    is_open = models.BooleanField(default=True)
    amount = models.DecimalField(max_digits=10, decimal_places=2)


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

    class Meta:
        unique_together = ('session_id', 'member_id')
