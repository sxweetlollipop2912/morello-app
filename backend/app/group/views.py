from .models import BalanceEntry, CollectEntry
from rest_framework.response import Response
from django.shortcuts import render
from .permissions import IsGroupAdminOrModerator, IsGroupAdmin
from rest_framework.permissions import IsAuthenticated

from .models import (
    Group,
    Member,
    BalanceEntry,
    CollectEntry,
    CollectSession,
    Moderator
)
from .serializers import (
    GroupSerializer,
    MemberSerializer,
    BalanceEntrySerializer,
    CollectEntrySerializer,
    CollectSessionSerializer,
    ModeratorSerializer
)
from rest_framework import viewsets
from django.db.models import (
    Sum,
    Q
)

# Create your views here.


class GroupPermissionMixin:
    def get_queryset(self):
        user = self.request.user
        return Group.objects.filter(
            Q(leader_user_id=user) | Q(moderators__user_id=user)
        ).distinct().select_related('leader_user_id')

    def get_permissions(self):
        if self.request.method in ['PUT', 'PATCH', 'DELETE']:
            self.permission_classes = [IsAuthenticated, IsGroupAdmin]
        else:
            self.permission_classes = [IsAuthenticated, IsGroupAdminOrModerator]
        return super().get_permissions()


class GroupViewSet(GroupPermissionMixin, viewsets.ModelViewSet):
    """
    API endpoint that allows groups to be viewed or edited.
    """
    serializer_class = GroupSerializer

    def get_queryset(self):
        user = self.request.user
        return Group.objects.filter(
            Q(leader_user_id=user) | Q(moderators__user_id=user)
        ).distinct().select_related('leader_user_id')


class MemberViewSet(GroupPermissionMixin, viewsets.ModelViewSet):
    serializer_class = MemberSerializer

    def get_queryset(self):
        group_id = self.kwargs['group_pk']
        groups = super().get_queryset()
        if group_id not in groups.values_list('id', flat=True):
            return Member.objects.none()  # Return an empty queryset
        return Member.objects.filter(group_id=group_id)


class BalanceViewSet(viewsets.ViewSet):
    def list(self, request, group_pk=None):
        print(group_pk)
        book_balance = BalanceEntry.objects.filter(
            group_id=group_pk).aggregate(
            Sum('amount'))['amount__sum']
        actual_balance = CollectEntry.objects.filter(
            status=True,
            session_id__balance_entries__group_id=group_pk
        ).aggregate(Sum('amount'))['amount__sum']

        return Response({
            'book_balance': book_balance if book_balance is not None else 0,
            'actual_balance': actual_balance if actual_balance is not None else 0
        })


class BalanceEntryViewSet(viewsets.ModelViewSet):
    serializer_class = BalanceEntrySerializer

    def get_queryset(self):
        group_id = self.kwargs['group_pk']
        return BalanceEntry.objects.filter(group_id=group_id)


class CollectSessionViewSet(viewsets.ModelViewSet):
    serializer_class = CollectSessionSerializer

    def get_queryset(self):
        group_id = self.kwargs['group_pk']
        return CollectSession.objects.filter(balance_entries__group_id=group_id)


class ModeratorViewSet(viewsets.ModelViewSet):
    serializer_class = ModeratorSerializer

    def get_queryset(self):
        group_id = self.kwargs['group_pk']
        return Moderator.objects.filter(
            group_id=group_id).select_related('user_id')
