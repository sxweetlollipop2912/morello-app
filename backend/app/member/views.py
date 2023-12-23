from django.shortcuts import render

# Create your views here.

from .models import (
    Member,
)
from .serializers import (
    MemberSerializer,
)
from rest_framework import viewsets
from group.mixins import GroupPermissionMixin


class MemberViewSet(GroupPermissionMixin, viewsets.ModelViewSet):
    serializer_class = MemberSerializer

    def get_queryset(self):
        group_id = self.kwargs["group_pk"]
        return Member.objects.filter(group_id=group_id)
