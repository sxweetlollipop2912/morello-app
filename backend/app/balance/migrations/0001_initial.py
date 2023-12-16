# Generated by Django 4.1 on 2023-12-16 10:16

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('group', '0003_remove_collectentry_member_id_and_more'),
        ('collect_session', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='BalanceEntry',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('amount', models.DecimalField(decimal_places=2, max_digits=10)),
                ('description', models.CharField(max_length=255)),
                ('date', models.DateTimeField()),
                ('group_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='balance_entries', to='group.group')),
                ('session_id', models.ForeignKey(null=True, on_delete=django.db.models.deletion.SET_NULL, related_name='balance_entries', to='collect_session.collectsession')),
            ],
        ),
    ]
