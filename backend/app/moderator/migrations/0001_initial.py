# Generated by Django 4.1 on 2023-12-16 06:40

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('group', '0002_delete_moderator'),
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='Moderator',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('created_at', models.DateTimeField(auto_now_add=True)),
                ('group_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='moderators', to='group.group')),
                ('user_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='moderated_groups', to=settings.AUTH_USER_MODEL)),
            ],
        ),
    ]