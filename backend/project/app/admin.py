from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
from .models import User, BloodRequestModel , Event , UserEvent


class CustomUserAdmin(UserAdmin):
    model = User
    list_display = ['email', 'name', 'phone_number', 'blood_group', 'district', 'province', 'is_active', 'is_staff', 'DOB', 'user_type']  # Ensure 'user_type' is correct
    ordering = ['email']
    fieldsets = (
        (None, {'fields': ('email', 'password')}),
        ('Personal Info', {'fields': ('name', 'phone_number', 'blood_group', 'district', 'province', 'DOB', 'user_type')}),
        ('Permissions', {'fields': ('is_active', 'is_staff', 'is_superuser', 'groups', 'user_permissions')}),
        ('Important dates', {'fields': ('last_login',)}),
    )
    add_fieldsets = (
        (None, {
            'classes': ('wide',),
            'fields': ('email', 'password1', 'password2', 'name', 'phone_number', 'blood_group', 'district', 'province', 'is_active', 'is_staff', 'DOB', 'user_type'),
        }),
    )

admin.site.register(User, CustomUserAdmin)





@admin.register(BloodRequestModel)
class BloodRequestAdmin(admin.ModelAdmin):
    list_display = ['patient_name', 'blood_group', 'location', 'user']
    search_fields = ['patient_name', 'blood_group', 'location', 'user__email']



@admin.register(Event)
class EventAdmin(admin.ModelAdmin):
    list_display = ("name", "organizer", "date", "location")

@admin.register(UserEvent)
class UserEventAdmin(admin.ModelAdmin):
    list_display = ("user", "event", "checked_in")