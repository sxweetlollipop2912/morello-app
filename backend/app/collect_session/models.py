from django.db import models
from member.models import Member
from group.models import Group
from balance.models import BalanceEntry

# Create your models here.


class CollectSession(models.Model):
    group_id = models.ForeignKey(
        Group, on_delete=models.CASCADE, related_name="collect_sessions"
    )
    name = models.CharField(max_length=255)
    description = models.CharField(max_length=255)
    start = models.DateTimeField()
    due = models.DateTimeField()
    payment_per_member = models.IntegerField()
    is_open = models.BooleanField(default=True)
    balance_entry_id = models.OneToOneField(
        BalanceEntry,
        on_delete=models.CASCADE,
        null=True,
        related_name="balance_entry",
    )
    updated_at = models.DateTimeField(auto_now=True)
    created_at = models.DateTimeField(auto_now_add=True)

    # enforce constraints for the balance_entry_id and is_open fields
    def save(self, *args, **kwargs):
        if not self.is_open and self.balance_entry_id is None:
            raise ValidationError(
                "balance_entry_id cannot be null when is_open is False"
            )
        if self.is_open and self.balance_entry_id is not None:
            raise ValidationError("balance_entry_id must be null when is_open is True")
        super().save(*args, **kwargs)


class CollectEntry(models.Model):
    session_id = models.ForeignKey(
        CollectSession, on_delete=models.CASCADE, related_name="collect_entries"
    )
    member_id = models.ForeignKey(
        Member, on_delete=models.CASCADE, related_name="collect_entries"
    )
    status = models.BooleanField(default=False)
    updated_at = models.DateTimeField(auto_now=True)
    created_at = models.DateTimeField(auto_now_add=True)

    class Meta:
        unique_together = ("session_id", "member_id")
