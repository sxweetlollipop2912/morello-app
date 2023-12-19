from django.db import models
from user.models import User

# Create your models here.


class Group(models.Model):
    name = models.CharField(max_length=255)
    description = models.CharField(max_length=255)
    leader_user_id = models.ForeignKey(
        User,
        on_delete=models.CASCADE,
        related_name='led_groups')
    created_at = models.DateTimeField(auto_now_add=True)
