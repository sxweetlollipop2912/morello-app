from django.shortcuts import render

from .models import (
    Group,
    Member
)
from .serializers import (
    GroupSerializer,
    MemberSerializer
)
from rest_framework import viewsets

# Create your views here.


class GroupViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows groups to be viewed or edited.
    """
    queryset = Group.objects.all()
    serializer_class = GroupSerializer


class MemberViewSet(viewsets.ModelViewSet):
    serializer_class = MemberSerializer

    def get_queryset(self):
        group_id = self.kwargs['group_pk']
        return Member.objects.filter(group_id=group_id)
