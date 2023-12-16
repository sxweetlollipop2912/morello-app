from django.contrib import admin
from .models import (
    Group,
    # CollectEntry,
)


class GroupAdmin(admin.ModelAdmin):
    # Display these fields in the list view
    list_display = ('name', 'leader_user_id')

# Register your models here.


admin.site.register(Group, GroupAdmin)
