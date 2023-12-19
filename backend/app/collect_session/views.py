from rest_framework import viewsets

from .models import (
    CollectSession,
)
from .serializers import (
    CollectSessionSerializer,
)
# Create your views here.


class CollectSessionViewSet(viewsets.ModelViewSet):
    serializer_class = CollectSessionSerializer

    def get_queryset(self):
        group_id = self.kwargs['group_pk']
        return CollectSession.objects.filter(
            balance_entries__group_id=group_id)
