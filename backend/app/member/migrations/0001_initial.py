# Generated by Django 4.1 on 2023-12-16 09:04

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('group', '0003_remove_collectentry_member_id_and_more'),
    ]

    operations = [
        migrations.CreateModel(
            name='Member',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=255, unique=True)),
                ('created_at', models.DateTimeField(auto_now_add=True)),
                ('group_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='members', to='group.group')),
            ],
        ),
    ]
