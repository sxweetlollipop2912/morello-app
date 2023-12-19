from django.contrib import admin
from .models import (
    CollectSession,
    CollectEntry,
)

# Register your models here.
admin.site.register(CollectSession)
admin.site.register(CollectEntry)
