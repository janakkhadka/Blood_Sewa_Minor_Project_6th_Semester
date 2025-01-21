from django.db.models.signals import post_save
from django.dispatch import receiver
from .models import Bookings
from asgiref.sync import async_to_sync
from channels.layers import get_channel_layer
from django.db.models.signals import post_save
from django.dispatch import receiver
from .models import BloodRequestModel, Event

@receiver(post_save, sender=Bookings)
def populate_booking_user_details(sender, instance, created, **kwargs):
    if created:  # Only for new bookings
        instance.user_phone_number = instance.user.phone_number
        instance.user_blood_group = instance.user.blood_group
        instance.save()





channel_layer = get_channel_layer()

@receiver(post_save, sender=BloodRequestModel)
@receiver(post_save, sender=Event)
def notify_users(sender, instance, created, **kwargs):
    if created:
        async_to_sync(channel_layer.group_send)(
            'updates_group',
            {
                'type': 'send_update',
                'message': {
                    'title': 'New Update',
                    'body': f"{instance} has been created.",
                },
            }
        )
