from django.db import models
from member.models import Member

# Create your models here.


class CollectSession(models.Model):
    start = models.DateTimeField()
    due = models.DateTimeField()
    payment_per_member = models.IntegerField()
    is_open = models.BooleanField(default=True)
    balance_entry_id = models.OneToOneField(
        "CollectEntry", on_delete=models.CASCADE, related_name="balance_entry"
    )


class CollectEntry(models.Model):
    session_id = models.ForeignKey(
        CollectSession, on_delete=models.CASCADE, related_name="collect_entries"
    )
    member_id = models.ForeignKey(
        Member, on_delete=models.CASCADE, related_name="collect_entries"
    )
    status = models.BooleanField(default=False)
