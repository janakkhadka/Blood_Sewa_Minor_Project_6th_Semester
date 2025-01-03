import re
from django.core.exceptions import ValidationError
from django.utils import timezone
import django_filters
from .models import User , BloodInventory


def validate_password(value):

    if len(value) < 8:
        raise ValidationError("Password must be at least 8 characters long.")


    if not re.search(r'[A-Z]', value):
        raise ValidationError("Password must contain at least one uppercase letter.")


    if not re.search(r'[0-9]', value):
        raise ValidationError("Password must contain at least one number.")


    if not re.search(r'[\W_]', value):  # Matches any non-word character (e.g., !@#$%)
        raise ValidationError("Password must contain at least one special character.")

    return value




def validate_phone_number(value):

    if len(value) < 10:
        raise ValidationError("Must be of 10 digit")

    if not re.fullmatch(r'^(\+977[- ]?)?9[6-9]\d{8}$', value):
        raise ValidationError("Given phone number is not valid. It must start with +977 or 9 and match Nepali format.")
    return value



def validate_age(value):

    today = timezone.now().date()
    age = today.year - value.year - ((today.month, today.day) < (value.month, value.day))

    if age < 18:
        raise ValidationError("User must be at least 18 years old.")

    return value




class UserFilter(django_filters.FilterSet):
    blood_group = django_filters.CharFilter(field_name='blood_group', lookup_expr='iexact')  # Case-insensitive exact match

    class Meta:
        model = User
        fields = ['blood_group']



class DistrictFilter(django_filters.FilterSet):
    blood_group = django_filters.CharFilter(field_name='blood_group', lookup_expr='iexact')  # Case-insensitive exact match
    district = django_filters.CharFilter(field_name='district' , lookup_expr='iexact')
    class Meta:
        model = User
        fields = ['blood_group' , 'district']


class OrganizationFilter(django_filters.FilterSet):
    organization_name = django_filters.CharFilter(field_name='organization__name',lookup_expr='iexact') # Case-insensitive exact match
    class Meta:
        model = BloodInventory
        fields = ['organization']
