from rest_framework.permissions import BasePermission

class IsBookingOwnerOrOrganization(BasePermission):
    def has_object_permission(self, request, view, obj):
        user = request.user
        if user.user_type == 'normal_user':
            return obj.user == user  # Normal users can only see their own bookings
        elif user.user_type == 'organization':
            return obj.organization == user  # Organizations can see bookings made for them
        return False
