from django.utils.text import slugify
from django.conf import settings
from django.contrib.auth import get_user_model
from django.db import models
from django.core.files import File
from io import BytesIO
import qrcode
from django.contrib.auth.models import AbstractBaseUser, PermissionsMixin, BaseUserManager, UserManager


# Custom user manager
class CustomUserManager(BaseUserManager):
    def create_user(self, email, name, phone_number, password=None, **extra_fields):
        if not email:
            raise ValueError('The Email field must be set')
        email = self.normalize_email(email)
        user = self.model(email=email, name=name, phone_number=phone_number, **extra_fields)
        user.set_password(password)
        user.save(using=self._db)
        return user

    def create_superuser(self, email, name, phone_number, password=None, **extra_fields):
        extra_fields.setdefault('is_staff', True)
        extra_fields.setdefault('is_superuser', True)

        if extra_fields.get('is_staff') is not True:
            raise ValueError('Superuser must have is_staff=True.')
        if extra_fields.get('is_superuser') is not True:
            raise ValueError('Superuser must have is_superuser=True.')

        # Create a superuser without requiring DOB
        return self.create_user(email=email, name=name, phone_number=phone_number, password=password, **extra_fields)


# Abstract base user
class BaseUser(AbstractBaseUser, PermissionsMixin):
    email = models.EmailField(unique=True)
    is_active = models.BooleanField(default=True)
    is_staff = models.BooleanField(default=False)

    objects = CustomUserManager()

    USERNAME_FIELD = 'email'

    class Meta:
        abstract = True


# User model
class User(AbstractBaseUser, PermissionsMixin):
    BLOOD_GROUP_CHOICES = [
        ('A+', 'A+'),
        ('A-', 'A-'),
        ('B+', 'B+'),
        ('B-', 'B-'),
        ('AB+', 'AB+'),
        ('AB-', 'AB-'),
        ('O+', 'O+'),
        ('O-', 'O-'),
    ]
    USER_TYPES = (
        ('user', 'User'),
        ('organization', 'Organization'),
    )
    GENDER_CHOICES = (
        ('male', 'Male'),
        ('female', 'Female'),
        ('other', 'Other'),
    )

    name = models.CharField(max_length=255)
    phone_number = models.CharField(max_length=20, unique=True)
    blood_group = models.CharField(max_length=10 , choices=BLOOD_GROUP_CHOICES)
    district = models.CharField(max_length=100)
    province = models.CharField(max_length=100)
    DOB = models.DateField(null=True, blank=True)
    gender = models.CharField(max_length=10, choices=GENDER_CHOICES , default='')
    file = models.FileField(upload_to='user_file/', blank=True, null=True)
    org_type = models.CharField(max_length=10 , default='' , null=True)
    city = models.CharField(max_length=50 , default='' , null=True)
    local_address = models.CharField(max_length=50 , default='' , null=True)
    groups = models.ManyToManyField('auth.Group', related_name='user_groups', blank=True)
    user_permissions = models.ManyToManyField('auth.Permission', related_name='user_permissions_set', blank=True)
    user_type = models.CharField(max_length=20, choices=USER_TYPES , default='user')

    # Define additional fields
    is_active = models.BooleanField(default=True)
    is_staff = models.BooleanField(default=False)
    email = models.EmailField(unique=True)

    # Set custom manager
    objects = CustomUserManager()

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = ['name', 'phone_number']

    def __str__(self):
        return self.name



class BloodRequestModel(models.Model):
    BLOOD_GROUP_CHOICES = [
        ('A+', 'A+'),
        ('A-', 'A-'),
        ('B+', 'B+'),
        ('B-', 'B-'),
        ('AB+', 'AB+'),
        ('AB-', 'AB-'),
        ('O+', 'O+'),
        ('O-', 'O-'),
    ]

    user = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE, related_name='blood_requests')
    patient_name = models.CharField(max_length=30)
    contact = models.CharField(max_length=15)
    blood_group = models.CharField(max_length=3, choices=BLOOD_GROUP_CHOICES)
    location = models.CharField(max_length=40)

    def __str__(self):
        return f"Blood request for {self.patient_name} - {self.blood_group}"




class Event(models.Model):
    name = models.CharField(max_length=255)
    slug = models.SlugField(unique=True, blank=True, null=True)
    description = models.TextField()
    location = models.CharField(max_length=255)
    date = models.DateField()
    collabrator = models.ForeignKey(User , on_delete=models.CASCADE , related_name="collabrator" , limit_choices_to={'user_type':'organization'} , null=True , default=None,blank=True)
    organizer = models.ForeignKey(User, on_delete=models.CASCADE, related_name="organized_events")
    qr_code = models.ImageField(upload_to="qrcodes/", blank=True, null=True)
    attendee_count = models.PositiveIntegerField(default=0)

    def save(self, *args, **kwargs):
        # Automatically generate a unique slug if not provided
        if not self.slug:
            base_slug = slugify(self.name)
            unique_slug = base_slug
            counter = 1

            # Ensure the slug is unique
            while Event.objects.filter(slug=unique_slug).exists():
                unique_slug = f"{base_slug}-{counter}"
                counter += 1

            self.slug = unique_slug

        if not self.collabrator and self.organizer.user_type == 'normal_user':
            self.collabrator = None

        # Generate QR code if not already generated
        if not self.qr_code:
            qr = qrcode.QRCode(version=1, box_size=10, border=5)
            qr.add_data(f"event_slug:{self.slug}")  # QR code data contains the unique slug
            qr.make(fit=True)
            img = qr.make_image(fill="black", back_color="white")
            buffer = BytesIO()

            # Use sanitized event name for the QR code filename
            sanitized_name = slugify(self.name)  # Converts the name to a safe format
            filename = f"{sanitized_name}.png"  # Event name as the filename

            img.save(buffer, "PNG")
            buffer.seek(0)
            self.qr_code.save(filename, File(buffer), save=False)  # Save without calling super().save yet
            buffer.close()

        # Call the parent class's save method to persist the event
        super().save(*args, **kwargs)

    def __str__(self):
        return self.name



class UserEvent(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    event = models.ForeignKey(Event, on_delete=models.CASCADE)
    checked_in = models.BooleanField(default=False)

    def __str__(self):
        return f"{self.user.name} - {self.event.name}"




class BloodInventory(models.Model):
    organization = models.OneToOneField(
        'User',
        on_delete=models.CASCADE,
        related_name='blood_inventory',
        limit_choices_to={'user_type': 'organization'}
    )
    inventory = models.JSONField(default=dict)  # Stores blood groups and their available pints

    def __str__(self):
        return f"{self.organization.name}'s Blood Inventory"



class Bookings(models.Model):
    SHIFT_CHOICES = [('morning' , 'morning') , ('afternoon' , 'afternoon') , ('evening' , 'evening')]
    user = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE , related_name='bookings' , limit_choices_to={'user_type':'user'})
    organization = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE , related_name='organizatio_name' , limit_choices_to={'user_type':'organization'})
    booking_date = models.DateField()
    shift = models.CharField(max_length=10 , choices=SHIFT_CHOICES)
    user_phone_number = models.CharField(max_length=20 , default='')
    user_blood_group = models.CharField(max_length=10,default='')

    def save(self, *args, **kwargs):
        # Populate user details before saving
        if not self.pk:  # Only for new bookings
            self.user_phone_number = self.user.phone_number
            self.user_blood_group = self.user.blood_group
        super().save(*args, **kwargs)

    def __str__(self):
        return f"Booking by {self.user.name} on {self.booking_date} for {self.user.name}"