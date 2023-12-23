from django.db import models
from user.models import User
from group.models import Group

# Create your models here.


class Moderator(models.Model):
    user_id = models.ForeignKey(
        User, on_delete=models.CASCADE, related_name="moderated_groups"
    )
    group_id = models.ForeignKey(
        Group, on_delete=models.CASCADE, related_name="moderators"
    )
    created_at = models.DateTimeField(auto_now_add=True)
