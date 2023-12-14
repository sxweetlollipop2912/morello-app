from django.contrib import admin
from group.models import (
    Group,
    Moderator,
    Member,
    CollectSession,
    BalanceEntry,
    CollectEntry,
)


class GroupAdmin(admin.ModelAdmin):
    # Display these fields in the list view
    list_display = ('name', 'leader_user_id')

# Register your models here.


admin.site.register(Group, GroupAdmin)
admin.site.register(Moderator)
admin.site.register(Member)
admin.site.register(CollectSession)
admin.site.register(BalanceEntry)
admin.site.register(CollectEntry)
