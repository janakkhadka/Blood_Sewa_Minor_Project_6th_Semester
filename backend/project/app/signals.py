from django.db.models.signals import post_save
from django.dispatch import receiver
from .models import Bookings

@receiver(post_save, sender=Bookings)
def populate_booking_user_details(sender, instance, created, **kwargs):
    if created:  # Only for new bookings
        instance.user_phone_number = instance.user.phone_number
        instance.user_blood_group = instance.user.blood_group
        instance.save()
