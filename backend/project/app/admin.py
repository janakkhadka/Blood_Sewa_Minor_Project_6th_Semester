from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
from .models import User, BulkRequestmodel ,BloodRequestModel , Event , UserEvent , BloodInventory , Bookings 


class CustomUserAdmin(UserAdmin):
    model = User
    list_display = ['email', 'name', 'phone_number', 'blood_group', 'district', 'province', 'is_active', 'is_staff', 'DOB', 'user_type' , 'gender' ]  # Ensure 'user_type' is correct
    ordering = ['email']
    fieldsets = (
        (None, {'fields': ('email', 'password')}),
        ('Personal Info', {'fields': ('name', 'phone_number', 'blood_group', 'district', 'province', 'DOB', 'user_type' , 'gender' , 'file' , 'city' , 'local_address' , 'org_type')}),
        ('Permissions', {'fields': ('is_active', 'is_staff', 'is_superuser', 'groups', 'user_permissions')}),
        ('Important dates', {'fields': ('last_login',)}),
    )
    add_fieldsets = (
        (None, {
            'classes': ('wide',),
            'fields': ('email', 'password1', 'password2', 'name', 'phone_number', 'blood_group', 'district', 'province', 'is_active', 'is_staff', 'DOB', 'user_type' , 'gender'),
        }),
    )

admin.site.register(User, CustomUserAdmin)


@admin.register(Bookings)
class BookingsAdmin(admin.ModelAdmin):
    list_display = ('user', 'organization', 'booking_date', 'shift')  # Fields to display in the admin list view
    list_filter = ('booking_date', 'shift')  # Filters for the admin list view
    search_fields = ('user__username', 'organization__username')  # Search fields for quick lookup
    ordering = ('-booking_date',)  # Default ordering in admin panel





@admin.register(BloodRequestModel)
class BloodRequestAdmin(admin.ModelAdmin):
    list_display = ['patient_name', 'blood_group', 'location', 'user']
    search_fields = ['patient_name', 'blood_group', 'location', 'user__email']



@admin.register(Event)
class EventAdmin(admin.ModelAdmin):
    list_display = ("name", "organizer", "date", "location")
    fields = ('name', 'slug', 'description', 'location', 'date', 'collabrator', 'organizer', 'qr_code', 'donor_attendee_count' , 'start_time' , 'end_time' , 'expected_donor_count')

@admin.register(UserEvent)
class UserEventAdmin(admin.ModelAdmin):
    list_display = ("user", "event", "checked_in")


class BloodInventoryAdmin(admin.ModelAdmin):
    # List display to show the organization and inventory in the list view
    list_display = ('organization', 'inventory_display')

    # Optional: Add filtering by blood group or organization
    list_filter = ('organization',)

    # Optional: Search functionality
    search_fields = ('organization__name',)

    # Custom method to display inventory in a readable format
    def inventory_display(self, obj):
        return ', '.join([f"{key}: {value}" for key, value in obj.inventory.items()])
    inventory_display.short_description = 'Blood Inventory'

admin.site.register(BloodInventory, BloodInventoryAdmin)



@admin.register(BulkRequestmodel)
class BulkRequestmodelAdmin(admin.ModelAdmin):
    list_display = ('organization','date')
    search_fields = ('organization__name',)
    list_filter = ('organization__user_type',)




