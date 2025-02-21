from django.http import HttpResponse, Http404
from django.contrib.auth import authenticate
from django.contrib.auth.tokens import default_token_generator
from django.contrib.sites.shortcuts import get_current_site
from django.core.mail import send_mail
from rest_framework import serializers
from django.utils.http import urlsafe_base64_encode
from django.utils.http import urlsafe_base64_decode
from django.utils.encoding import force_bytes
from django.template.loader import render_to_string
from django.utils.html import strip_tags
from django.conf import settings
from django_filters.rest_framework import DjangoFilterBackend
from rest_framework import status, permissions
from rest_framework.generics import ListAPIView
from rest_framework.permissions import AllowAny
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework_simplejwt.tokens import RefreshToken
from .utils import UserFilter , DistrictFilter , OrganizationFilter , is_date_in_past
from .models import User, Volunteer ,BloodRequestModel , Event , UserEvent , BloodInventory , Bookings 
import os
from django.shortcuts import get_object_or_404
from django.utils.timezone import now
from datetime import date , timedelta , datetime
from rest_framework_simplejwt.exceptions import TokenError, InvalidToken
from .serializers import (EventUpdateSerializer , PublicBloodRequestSerializer,UserSerializer,LoginSerializer, UserProfileUpdateSerializer, BloodRequestSerializer , LimitedUserSerializer , EventSerializer, MyEventSerializer , UserEventSerializer , OrganizationSerializer , BloodInventorySerializer , BookingSerializer , OrganizationBookingSerializer , UserEventCreateSerializer)


class RegisterUserView(APIView):
    permission_classes = [AllowAny]

    def post(self, request):
        serializer = UserSerializer(data=request.data)

        if serializer.is_valid():
            
            myuser = serializer.save(is_active=False)

            
            uid = urlsafe_base64_encode(force_bytes(myuser.pk))
            token = default_token_generator.make_token(myuser)

            
            current_site = get_current_site(request)
            verification_url = f"http://{current_site.domain}/activate/{uid}/{token}/"

            
            email_subject = "Activate Your Account"
            html_message = render_to_string('email_confirmation.htm', {
                'name': myuser.name,
                'domain': current_site.domain,
                'uid': uid,
                'token': token,
            })

            
            plain_message = strip_tags(html_message)

            
            send_mail(
                email_subject,
                plain_message,  
                settings.DEFAULT_FROM_EMAIL,
                [myuser.email],
                fail_silently=False,
                html_message=html_message, 
            )

            return Response(
                {"message": "Your account has been successfully created. Check your email to activate your account."},
                status=status.HTTP_201_CREATED,
            )

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)





class ActivateAccountView(APIView):
    permission_classes = [AllowAny]

    def get(self, request, uidb64, token):
        try:
            # Decode the UID
            uid = urlsafe_base64_decode(uidb64).decode()
            user = User.objects.get(pk=uid)
        except (User.DoesNotExist, ValueError):
            return Response({"error": "Invalid activation link."}, status=400)

        # Check the token validity
        if default_token_generator.check_token(user, token):
            user.is_active = True
            user.save()
            return Response({"message": "Account activated successfully!"}, status=200)
        else:
            return Response({"error": "Invalid or expired token."}, status=400)








# Registr Orgnization
class RegisterOrganizationView(APIView):
    permission_classes = [AllowAny]

    def post(self, request):
        serializer = OrganizationSerializer(data=request.data)
        print(serializer)
        if serializer.is_valid():
            serializer.save()
            return Response({"message": "Organization registered successfully"}, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)



class UserLoginView(APIView):
    permission_classes = [AllowAny]

    def post(self, request):
        serializer = LoginSerializer(data=request.data)
        if serializer.is_valid():
            email = serializer.validated_data['email']
            password = serializer.validated_data['password']
            user = authenticate(email=email, password=password)

            if user and isinstance(user, User):  # Ensure the authenticated user is of type User
                 if user.user_type == 'user':
                    refresh = RefreshToken.for_user(user)

                    user_details = {
                        "name": user.name,
                        "email": user.email,
                        "phone_number": user.phone_number,
                        "blood_group": user.blood_group,
                        "district": user.district,
                        "province": user.province,
                        "DOB": user.DOB,
                        "gender": user.gender
                    }

                    return Response({
                        "message": "Login successful",
                        "user_detail": user_details ,
                        "access_token": str(refresh.access_token),
                        "refresh_token": str(refresh),
                    }, status=status.HTTP_200_OK)

            return Response({"message": "Invalid email or password"}, status=status.HTTP_401_UNAUTHORIZED)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)



class GetUserDetails(APIView):
    permission_classes = [permissions.IsAuthenticated]
    
    def get(self, request):
        users = User.objects.filter(user_type='user')
        serializer = LimitedUserSerializer(users , many=True)
        return Response(serializer.data)



class OrganizationLoginView(APIView):
    permission_classes = [AllowAny]

    def post(self, request):
        serializer = LoginSerializer(data=request.data)
        if serializer.is_valid():
            email = serializer.validated_data['email']
            password = serializer.validated_data['password']
            user = authenticate(email=email, password=password)

            if user and isinstance(user, User):  # Ensure the authenticated user is of type User
                if user.user_type == 'organization':
                    # Generate JWT tokens
                    refresh = RefreshToken.for_user(user)

                    
                    organization_details = {
                    "name": user.name,
                    "email": user.email,
                    "phone_number": user.phone_number,
                    "district": user.district,
                    "province": user.province,
                    "city":user.city,
                    "local_address": user.local_address
                }

                    return Response({
                        "message": "Login successful",
                        "organization_details": organization_details,
                        "access_token": str(refresh.access_token),
                        "refresh_token": str(refresh),
                    }, status=status.HTTP_200_OK)

            return Response({"message": "Invalid email or password"}, status=status.HTTP_401_UNAUTHORIZED)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)






class UserProfileUpdateView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get_object(self):
        return self.request.user

    def put(self, request):
        user = self.get_object()
        if user:  # Ensure that this is a User type
            serializer = UserProfileUpdateSerializer(user, data=request.data, partial=True)
            if serializer.is_valid():
                serializer.save()
                return Response({'message': 'Profile updated successfully'}, status=status.HTTP_200_OK)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)




# class BloodRequestCreateView(APIView):
#     permission_classes = [permissions.IsAuthenticated]

#     def post(self, request):
#         serializer = BloodRequestSerializer(data=request.data, context={'request': request})
#         if serializer.is_valid():
#             # Associate the current user with the blood request
#             serializer.save(user=request.user)
#             return Response(serializer.data, status=status.HTTP_201_CREATED)
#         return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
from rest_framework import permissions, status
from rest_framework.response import Response
from rest_framework.views import APIView
from channels.layers import get_channel_layer
from asgiref.sync import async_to_sync
import json
from app.serializers import BloodRequestSerializer

class BloodRequestCreateView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def post(self, request):
        serializer = BloodRequestSerializer(data=request.data, context={'request': request})
        if serializer.is_valid():
            # Save the blood request
            blood_request = serializer.save(user=request.user)

            # Prepare WebSocket message
            message = {
                "message": f"New Blood Request: {blood_request.blood_group} needed in {blood_request.city}, {blood_request.district}."
            }

            # Send WebSocket notification
            channel_layer = get_channel_layer()
            async_to_sync(channel_layer.group_send)(
                "blood_requests",  # Group name from
                {
                    "type": "send_notification",
                    "message": json.dumps(message),
                },
            )

            return Response(serializer.data, status=status.HTTP_201_CREATED)

        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)



class PublicBloodRequestCreateView(APIView):
    permission_classes = [permissions.AllowAny]

    def post(self, request):
        serializer = PublicBloodRequestSerializer(data=request.data, context={'request': request})
        if serializer.is_valid():
            # Associate the current user with the blood request
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class BloodRequestListView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self, request):
        # Return all blood requests regardless of user type
        queryset = BloodRequestModel.objects.all()

        # Check if queryset is empty and return a custom message or empty list
        if not queryset:
            return Response({"message": "No blood requests found."}, status=status.HTTP_404_NOT_FOUND)

        serializer = BloodRequestSerializer(queryset, many=True)
        return Response(serializer.data)





class FilterUserBloodGroup(ListAPIView):
    permission_classes = [permissions.IsAuthenticated]
    queryset = User.objects.all()
    serializer_class = LimitedUserSerializer
    filter_backends = [DjangoFilterBackend]
    filterset_class = UserFilter




class UserListView(ListAPIView):
    permission_classes = [permissions.IsAuthenticated]
    queryset = User.objects.all()
    serializer_class = LimitedUserSerializer
    filter_backends = [DjangoFilterBackend]
    filterset_class = DistrictFilter




class LogoutView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def post(self, request, *args, **kwargs):
        refresh_token = request.data.get("refresh_token")
        if not refresh_token:
            return Response(
                {"error": "Refresh token is required for logout"},
                status=status.HTTP_400_BAD_REQUEST,
            )

        try:
            # Blacklist the RefreshToken
            token = RefreshToken(refresh_token)
            token.blacklist()

            return Response(
                {"message": "Logout successful"},
                status=status.HTTP_205_RESET_CONTENT,
            )
        except TokenError:
            return Response(
                {"error": "Invalid or expired refresh token"},
                status=status.HTTP_400_BAD_REQUEST,
            )
        except Exception:
            return Response(
                {"error": "An unexpected error occurred"},
                status=status.HTTP_500_INTERNAL_SERVER_ERROR,
            )


#Event Create View For User
# class UserEventCreateView(APIView):
#     permission_classes = [permissions.IsAuthenticated]

#     def post(self, request):
#         serializer = UserEventCreateSerializer(data=request.data)
#         if serializer.is_valid():
#             event = serializer.save(organizer=request.user)
#             return Response(EventSerializer(event).data, status=status.HTTP_201_CREATED)
#         return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class UserEventCreateView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def post(self, request):
        serializer = UserEventCreateSerializer(data=request.data, context={'request': request})
        if serializer.is_valid():
            event = serializer.save()
            return Response({"message": "Collaboration request sent.", "event": "Event Created Successfully"}, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


# Colabration Request Management
class ManageCollaborationView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def post(self, request, slug):

        # Ensure the user is the organizer of the event
        event = get_object_or_404(Event, slug=slug)


        # Extract data from request
        action = request.data.get("action")  # Action should be 'approve' or 'reject'
        
        # Only proceed if the action is valid
        if action not in ["approve", "reject"]:
            return Response({"error": "Invalid action. Use 'approve' or 'reject'."}, 
                            status=status.HTTP_400_BAD_REQUEST)

        # Ensure the event has a pending collaboration
        if event.collaboration_status != "pending":
            return Response({"error": "No pending collaboration request for this event."}, 
                            status=status.HTTP_400_BAD_REQUEST)

        # Perform action
        if action == "approve":
            event.collaboration_status = "approved"
            # You might want to set the `collaborator` here as well
            event.collaborator = request.user
        else:  # action == "reject"
            event.collaboration_status = "rejected"

        # Save the updated event
        event.save()

        return Response({"message": f"Collaboration request {action}d successfully."}, 
                        status=status.HTTP_200_OK)




#pending Colabration request
class PendingCollaborationListView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self, request):
        
        # Fetch events where the user is a collaborator and the collaboration status is pending
        pending_events = Event.objects.filter(collabrator=request.user, collaboration_status="pending")

        response_data = [
            {
                "slug":event.slug,
                "organizer":event.organizer.name,
                "event_name": event.name,
                "event_date": event.date,
                "event_location": event.location,
                "collaboration_status": event.collaboration_status  # Add the status of the collaboration request
            }
            for event in pending_events
        ]

        return Response({"pending_requests": response_data}, status=status.HTTP_200_OK)








class CreateEventView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def post(self, request):
        serializer = EventSerializer(data=request.data)
        if serializer.is_valid():
            event = serializer.save(organizer=request.user)
            return Response(EventSerializer(event).data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)




# class JoinEventView(APIView):
#     permission_classes = [permissions.IsAuthenticated]

#     def post(self, request, slug):
#         try:
#             # Fetch the event by slug
#             event = Event.objects.get(slug=slug)
            
#             # Create or fetch the UserEvent object for the current user
#             UserEvent.objects.get_or_create(user=request.user, event=event)
            
#             # Update the event's status to True if it isn't already
#             if not event.status:
#                 event.status = True
#                 event.save()
            
#             if event.status == True:
#                 event.expected_donor_count += 1
#                 event.save()

#             return Response({"message": "Successfully joined the event." , "status":event.status}, status=status.HTTP_200_OK)

#         except Event.DoesNotExist:
#             return Response({"error": "Event not found."}, status=status.HTTP_404_NOT_FOUND)


from datetime import timedelta
from django.utils.timezone import now
from rest_framework.response import Response
from rest_framework import status, permissions
from rest_framework.views import APIView
from .models import Event, UserEvent

class JoinEventView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def post(self, request, slug):
        try:
            # Fetch the event by slug
            event = Event.objects.get(slug=slug)
            
            # Check if the user has already joined this event
            if UserEvent.objects.filter(user=request.user, event=event).exists():
                return Response({"error": "You have already joined this event."}, status=status.HTTP_400_BAD_REQUEST)

            # Get all events the user has joined in the last 90 days
            ninety_days_ago = now() - timedelta(days=90)
            recent_events = UserEvent.objects.filter(user=request.user, event__date__gte=ninety_days_ago)

            if recent_events.exists():
                return Response(
                    {"error": "You cannot join this event as you have joined another event in the last 90 days."},
                    status=status.HTTP_400_BAD_REQUEST
                )

            # Create a new UserEvent entry
            UserEvent.objects.create(user=request.user, event=event)

            # Update the event's status to True if it isn't already
            if not event.status:
                event.status = True
                event.save()

            # Increase the expected donor count
            event.expected_donor_count += 1
            event.save()

            return Response({"message": "Successfully joined the event.", "status": event.status}, status=status.HTTP_200_OK)

        except Event.DoesNotExist:
            return Response({"error": "Event not found."}, status=status.HTTP_404_NOT_FOUND)














class CheckInView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def post(self, request, slug):
        try:
            # Fetch the event
            event = Event.objects.get(slug=slug)
            
            # Check if the user is registered for the event
            user_event = UserEvent.objects.filter(user=request.user, event=event).first()
            if not user_event:
                return Response({"error": "Not registered for this event."}, status=status.HTTP_400_BAD_REQUEST)

            if user_event.checked_in:
                return Response({"error": "Already checked in."}, status=status.HTTP_400_BAD_REQUEST)

            # Mark the user as checked in
            user_event.checked_in = True
            user_event.save()
            event.donor_attendee_count = event.donor_attendee_count + 1
            event.save()

            return Response({"message": "Check-in successful."}, status=status.HTTP_200_OK)

        except Event.DoesNotExist:
            return Response({"error": "Event not found."}, status=status.HTTP_404_NOT_FOUND)


class CheckedInListView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self, request , slug):
        try:
            # Fetch the event
            event = Event.objects.get(slug=slug)

            # Fetch all users who have checked in for this event
            checked_in_users = UserEvent.objects.filter(event=event, checked_in=True).select_related('user')
            
            # Prepare the response data
            users_with_blood_group = [
                {
                    "name": user_event.user.name,  
                    "blood_group": user_event.user.blood_group  
                }
                for user_event in checked_in_users
            ]

            return Response(
                {"checked_in_users": users_with_blood_group},
                status=status.HTTP_200_OK
            )

        except Event.DoesNotExist:
            return Response({"error": "Event not found."}, status=status.HTTP_404_NOT_FOUND)




class VolunteerCheckinList(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self, request, slug):
        try:                  
            event = Event.objects.get(slug=slug)

            # Fetch all users who have checked in for this event
            checked_in_users = Volunteer.objects.filter(event=event, confirmed=True).select_related('user')
            
            # Prepare the response data
            volunteers_with_contact = [
                {
                    "name": us.user.name,  
                    "contact": us.user.phone_number 
                }
                for us in checked_in_users
            ]

            return Response(
                {"checked_in_users": volunteers_with_contact},
                status=status.HTTP_200_OK
            )

        except Event.DoesNotExist:
            return Response({"error": "Event not found."}, status=status.HTTP_404_NOT_FOUND)  



# class ListPastEventsView(APIView):
#     permission_classes = [permissions.IsAuthenticated]

#     def get(self, request):
#         user = request.user  # Get the authenticated user
#         past_events = Event.objects.filter(date__lt=now().date())
#         serializer = EventSerializer(past_events, many=True)

#         serialized_data = serializer.data
#         for event in serialized_data:
#             event_obj = past_events.get(name=event['name'])
            
#             if user.user_type == 'user':  # Only check status for normal users
#                 event["status"] = "Joined" if UserEvent.objects.filter(user=user, event=event_obj).exists() else "Not Joined"


#             if not event.get("collabrator_name"):  
#                 event.pop("collabrator_name", None)

#             if event.get("qr_code"):
#                 event.pop("qr_code", None)

#             if event.get("slug"):
#                 event.pop("slug", None)

#         return Response(serialized_data, status=status.HTTP_200_OK)












from django.db.models import Q

class ListPastEventsView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self, request):
        user = request.user

        past_events = Event.objects.filter(
            date__lt=now().date()
        ).filter(
            Q(organizer__user_type="organization") | Q(collaboration_status="approved")
        )

        serializer = EventSerializer(past_events, many=True)

        serialized_data = serializer.data
        for event in serialized_data:
            event_obj = past_events.get(name=event['name'])

            if user.user_type == 'user':  
                event["status"] = "Joined" if UserEvent.objects.filter(user=user, event=event_obj).exists() else "Not Joined"

            # Remove unnecessary fields
            event.pop("collabrator_name", None)  
            event.pop("qr_code", None)
            event.pop("slug", None)

        return Response(serialized_data, status=status.HTTP_200_OK)




class ListTodayEventsView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self, request):
        user = request.user  
        today = now().date()

        # Show events by organizations OR user events that are approved
        today_events = Event.objects.filter(
            date=today
        ).filter(
            Q(organizer__user_type="organization") | Q(collaboration_status="approved")
        )

        serializer = EventSerializer(today_events, many=True)
        serialized_data = serializer.data

        for event in serialized_data:
            event_obj = today_events.get(name=event['name'])

            if user.user_type == 'user':  
                event["status"] = "Joined" if UserEvent.objects.filter(user=user, event=event_obj).exists() else "Not Joined"

            event.pop("collabrator_name", None)  
            event.pop("qr_code", None)

        return Response(serialized_data, status=status.HTTP_200_OK)


class ListUpcommingEventsView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self, request):
        today = now().date()

        # Show events by organizations OR user events that are approved
        upcoming_events = Event.objects.filter(
            date__gt=today
        ).filter(
            Q(organizer__user_type="organization") | Q(collaboration_status="approved")
        )

        serializer = EventSerializer(upcoming_events, many=True)
        user = request.user  

        serialized_data = serializer.data
        for event in serialized_data:
            event_obj = upcoming_events.get(name=event['name'])

            if user.user_type == 'user':  
                event["status"] = "Joined" if UserEvent.objects.filter(user=user, event=event_obj).exists() else "Not Joined"

            event.pop("collabrator_name", None)
            event.pop("qr_code", None)

        return Response(serialized_data, status=status.HTTP_200_OK)




class UserJoinedEventHistoryView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self, request):
        user_events = UserEvent.objects.filter(user=request.user)
        
        data = [
            {

                    "event_name": ue.event.name,
                    "joined_on": ue.event.date,
                    "Donated" : ue.checked_in
                  
            }
            for ue in user_events
        
        ]
        return Response(data, status=status.HTTP_200_OK)





class MyeventInfo(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self, request):
        # Fetch events where the user is either the organizer or a collaborator
        my_events = Event.objects.filter(
            Q(organizer=request.user) | Q(collabrator=request.user)
        )

        serializers = MyEventSerializer(my_events, many=True)
        
        return Response(serializers.data , status=status.HTTP_200_OK)








def qr_code_view(request, filename):
    # Define the directory where QR codes are stored
    qr_code_dir = os.path.join(settings.MEDIA_ROOT, 'qrcodes')
    
    # Construct the full file path
    file_path = os.path.join(qr_code_dir, filename)
    
    # Check if the file exists
    if not os.path.exists(file_path):
        raise Http404("QR code file not found.")
    
    # Open the file and return it as a response
    with open(file_path, 'rb') as file:
        response = HttpResponse(file.read(), content_type="image/png")
        response['Content-Disposition'] = f'inline; filename="{filename}"'
        return response




class PasswordResetRequestView(APIView):
    def post(self, request):
        email = request.data.get("email")

        if not email:
            return Response({"error": "Email is required."}, status=status.HTTP_400_BAD_REQUEST)

        try:
            user = User.objects.get(email=email)
        except User.DoesNotExist:
            return Response({"error": "User with this email does not exist."}, status=status.HTTP_404_NOT_FOUND)

        # Generate password reset token and URL
        uid = urlsafe_base64_encode(force_bytes(user.pk))
        token = default_token_generator.make_token(user)
        reset_url = f"{request.build_absolute_uri('/reset-password/')}{uid}/{token}/"

        # Render and send email
        subject = "Password Reset Request"
        html_message = render_to_string("email_password_reset.htm", {"reset_url": reset_url, "name": user.name})
        plain_message = f"Click the link to reset your password: {reset_url}"

        send_mail(
            subject,
            plain_message,
            settings.DEFAULT_FROM_EMAIL,
            [email],
            html_message=html_message,
        )

        return Response({"message": "Password reset email sent."}, status=status.HTTP_200_OK)


class PasswordResetConfirmView(APIView):
    def get(self, request, uidb64, token):
        try:
            uid = urlsafe_base64_decode(uidb64).decode()
            user = User.objects.get(pk=uid)
        except (User.DoesNotExist, ValueError, TypeError):
            return Response({"error": "Invalid link."}, status=status.HTTP_400_BAD_REQUEST)

        if not default_token_generator.check_token(user, token):
            return Response({"error": "Invalid token."}, status=status.HTTP_400_BAD_REQUEST)

        form = SetPasswordForm(user)
        return render(request, "password_reset_confirm.html", {"form": form})

    def post(self, request, uidb64, token):
        try:
            uid = urlsafe_base64_decode(uidb64).decode()
            user = User.objects.get(pk=uid)
        except (User.DoesNotExist, ValueError, TypeError):
            return Response({"error": "Invalid link."}, status=status.HTTP_400_BAD_REQUEST)

        if not default_token_generator.check_token(user, token):
            return Response({"error": "Invalid token."}, status=status.HTTP_400_BAD_REQUEST)

        form = SetPasswordForm(user, request.POST)
        if form.is_valid():
            form.save()
            return Response({"message": "Your password has been reset successfully."}, status=status.HTTP_200_OK)

        return render(request, "password_reset_confirm.htm", {"form": form})



class IsOwner(permissions.BasePermission):
    def has_object_permission(self, request, view, obj):
        # Allow viewing for all authenticated users
        if request.method in permissions.SAFE_METHODS:  # GET, HEAD, OPTIONS
            return True

        # Allow modification only for the owner (organization)
        return obj.organization == request.user


class BloodInventoryDetail(APIView):
    permission_classes = [permissions.IsAuthenticated, IsOwner]

    def get(self, request):
        try:
            # Get the blood inventory for the logged-in user (organization)
            inventory = BloodInventory.objects.get(organization=request.user)
        except BloodInventory.DoesNotExist:
            return Response({"detail": "Blood inventory not found."}, status=status.HTTP_404_NOT_FOUND)

        # Serialize and return the blood inventory
        serializer = BloodInventorySerializer(inventory)
        return Response(serializer.data)

    def patch(self, request):
        # Check if the logged-in user is an organization
        if request.user.user_type != 'organization':
            return Response({"detail": "Only organizations can update the blood inventory."}, status=status.HTTP_403_FORBIDDEN)

        try:
            # Get the blood inventory for the logged-in user (organization)
            inventory = BloodInventory.objects.get(organization=request.user)
        except BloodInventory.DoesNotExist:
            return Response({"detail": "Blood inventory not found."}, status=status.HTTP_404_NOT_FOUND)

        # Ensure the request data is a dictionary
        new_inventory = request.data
        if not isinstance(new_inventory, dict):
            return Response({"detail": "Invalid data format. Expected a dictionary."}, status=status.HTTP_400_BAD_REQUEST)

        # Update the current inventory with the new values
        current_inventory = inventory.inventory  # Assuming inventory is a JSONField
        if isinstance(current_inventory, dict):
            current_inventory.update(new_inventory)  # Update only specified fields
        else:
            current_inventory = new_inventory  # If no existing inventory, use new data

        # Save the updated inventory
        inventory.inventory = current_inventory
        inventory.save()

        # Serialize and return the updated blood inventory
        serializer = BloodInventorySerializer(inventory)
        return Response(serializer.data, status=status.HTTP_200_OK)



class BloodInventoryByOrganization(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self, request, *args, **kwargs):
        # Apply filtering
        filterset = OrganizationFilter(request.GET, queryset=BloodInventory.objects.all())
        if not filterset.is_valid():
            return Response(filterset.errors, status=status.HTTP_400_BAD_REQUEST)

        # Serialize filtered results
        serializer = BloodInventorySerializer(filterset.qs, many=True)
        return Response(serializer.data)


class OrganizationListView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self , request, *args, **kwargs):
        organization = User.objects.filter(user_type='organization').values_list('name', flat=True)
        return Response({"organization": organization}, status=status.HTTP_200_OK)





class BookingCreateView(APIView):
    permissions_classes = [permissions.IsAuthenticated] 

    def post(self, request):
        if request.user.user_type != 'user':
            return Response({"error": "Only users can create bookings."}, status=status.HTTP_403_FORBIDDEN)

        data = request.data
        data['user'] = request.user

        booking_date = data.get('booking_date')

        if booking_date and is_date_in_past(date.fromisoformat(booking_date)):
            return Response({"detail": "Booking date cannot be in the past."}, status=status.HTTP_400_BAD_REQUEST)


        today = date.today()
        three_months_ago = today - timedelta(days=90)
        if Bookings.objects.filter(user=data['user'], booking_date__gte=three_months_ago).exists():
            return Response(
                {"detail": "You can only make one booking in any organization every 3 months."},
                status=status.HTTP_400_BAD_REQUEST
            )

        serializers = BookingSerializer(data=data , context = {'request' : request})
        if serializers.is_valid():
            booking = serializers.save(user=request.user)

            subject = "Booking Confirmation"
            message = f"Dear {request.user.name}, \n\n Your booking has been successfully created.\n\n Booking Details : \n\n Organization Name : {booking.organization} \n Date : {booking.booking_date} \n\n  Thank You for Chosing Our Service!!!"
            recipient_email = request.user.email

            send_mail(
                subject,
                message,
                settings.DEFAULT_FROM_EMAIL,  # Sender email
                [recipient_email],  # Recipient email
                fail_silently=False,
            )

            return Response(serializers.data, status=status.HTTP_201_CREATED)
        return Response(serializers.errors, status=status.HTTP_400_BAD_REQUEST)


class MyBookings(APIView):
    permissions = [permissions.IsAuthenticated]
    def get(self, request):
        bookings = Bookings.objects.filter(user=request.user)
        serializer = BookingSerializer(bookings, many=True)
        return Response(serializer.data)





class OrganizationBookings(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self, request):
        bookings = Bookings.objects.filter(organization=request.user)
        serializer = OrganizationBookingSerializer(bookings, many=True)
        return Response(serializer.data)





from .models import BulkRequestmodel
from .serializers import BulkBloodRequestSerializer

class AddBulkRequestView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def post(self, request, *args, **kwargs):
        if request.user.user_type != 'organization':
            return Response({"message": "Only organizations can make bulk requests."}, status=status.HTTP_403_FORBIDDEN)

        # Pass the raw input to the serializer
        serializer = BulkBloodRequestSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save(organization=request.user)
            return Response({"message": "Bulk blood request added successfully.", "data": serializer.data}, status=status.HTTP_201_CREATED)
        return Response({"message": "Invalid data.", "errors": serializer.errors}, status=status.HTTP_400_BAD_REQUEST)


class ViewBulkRequestsView(APIView):
    permission_classes = [permissions.IsAuthenticated]  # Ensure the user is logged in

    def get(self, request, *args, **kwargs):
        # Retrieve all bulk blood requests
        bulk_requests = BulkRequestmodel.objects.all()
        serializer = BulkBloodRequestSerializer(bulk_requests, many=True)
        return Response( serializer.data, status=status.HTTP_200_OK)







class VolunteerJoinAPIView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def post(self, request, slug):
        try:
            event = Event.objects.get(slug=slug)
            
            # Check if the volunteer count has been reached
            if event.volunteer_attendee_count >= event.volunteer_required_count:
                return Response(
                    {"error": "Volunteer spots are full."},
                    status=status.HTTP_400_BAD_REQUEST
                )
            
            # Check if the user has already joined as a volunteer
            if Volunteer.objects.filter(user=request.user, event=event).exists():
                return Response(
                    {"error": "You have already joined as a volunteer."},
                    status=status.HTTP_400_BAD_REQUEST
                )
            
            # Register user as volunteer
            volunteer = Volunteer.objects.create(user=request.user, event=event)
            event.volunteer_attendee_count += 1
            event.save()

            return Response({"message": "Successfully joined as volunteer."}, status=status.HTTP_201_CREATED)
        
        except Event.DoesNotExist:
            return Response({"error": "Event not found."}, status=status.HTTP_404_NOT_FOUND)



class VolunteerConfirmAPIView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def post(self, request, slug):
        try:
            event = Event.objects.get(slug=slug)
            volunteer = Volunteer.objects.get(user=request.user, event=event)
            
            if volunteer.confirmed:
                return Response({"message": "You are already confirmed."}, status=status.HTTP_200_OK)

            # Mark volunteer as confirmed
            volunteer.confirmed = True
            volunteer.save()

            return Response({"message": "Volunteer confirmed."}, status=status.HTTP_200_OK)

        except Event.DoesNotExist:
            return Response({"error": "Event not found."}, status=status.HTTP_404_NOT_FOUND)
        except Volunteer.DoesNotExist:
            return Response({"error": "You are not a volunteer for this event."}, status=status.HTTP_400_BAD_REQUEST)






class MyDonationProfile(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self , request):
        user_events = UserEvent.objects.filter(user=request.user)

        donations = [ue for ue in user_events if ue.checked_in]

        donation_count = len(donations)

        last_donation_date = None
        last_doantion_event = None
        eligibility_date = None
        days_left = None

        if donations:
            last_donation = max(donations , key= lambda ue: ue.event.date)
            last_donation_date = last_donation.event.date
            last_donation_event = last_donation.event.name
            

            eligibility_date = last_donation_date + timedelta(days=90)

            days_left = (eligibility_date - datetime.today().date()).days
            days_left = max(days_left , 0)
        else:
            last_donation_date = None
            last_donation_event = None
            days_left = None

        return Response(
            {
                "last_donation_event":last_donation_event,
                "last_donation_date":last_donation_date,
                "donation_count":donation_count,
                "eligibility_date":eligibility_date,
                "days_left":days_left
            },status = status.HTTP_200_OK
        )


class MyVolunteeringHistory(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self , request):
        user_volunteering = Volunteer.objects.filter(user=request.user)
        data = [
            {
                "event_name":ue.event.name,
                "event_date": ue.event.date,
                "Voluntered": ue.confirmed
            }
            for ue in user_volunteering
        ]
        return Response(data, status=status.HTTP_200_OK)




class UpdateEventView(APIView):
    permission_classes = [permissions.IsAuthenticated]
    def patch(self , request , slug):
        event = get_object_or_404(Event, slug=slug)
        serializer = EventUpdateSerializer(evnt , data = request.data , partial = True)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_200_OK)



class DeleteventView(APIView):
    permission_classes = [permissions.IsAuthenticated]
    def delete(self , request , slug):
        event = get_object_or_404(Event, slug=slug)
        
        if event.date > now().date():
            event.delete()
            return Response({"message": "Event deleted successfully."}, status=status.HTTP_204_NO_CONTENT)
        
        else:
            return Response(
                {"message": "Cannot delete past or ongoing events"},
                status=status.HTTP_400_BAD_REQUEST
            )




class MyEventAccToSlugView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self , request , slug):

        event = get_object_or_404(Event, slug=slug)
        serializer = MyEventSerializer(event)
        return Response(serializer.data, status=status.HTTP_200_OK)






class SendBloodForRequestView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def post(self, request, organization_name):
        # Replace hyphens with spaces for organization name
        organization_name = organization_name.replace('-', ' ')

        # Get the bulk request associated with the given organization
        bulk_request = get_object_or_404(BulkRequestmodel, organization__name=organization_name)

        # Get the blood sent data from the request
        sent_blood = request.data.get("send_blood", {})

        if not sent_blood:
            return Response({"message": "No blood data provided."}, status=400)

        # Deduct blood from the bulk request's blood_request field
        for blood_type, amount in sent_blood.items():
            # Check if the blood type exists in the bulk request's blood_request field
            current_amount = bulk_request.blood_request.get(blood_type, 0)

            if current_amount < amount:
                return Response({"message": f"Not enough {blood_type} blood in inventory."}, status=400)

            # Deduct blood from the bulk request's blood_request
            bulk_request.blood_request[blood_type] -= amount

        # Save the updated blood request field
        bulk_request.save()

        # Return success response
        return Response({"message": "Blood sent successfully and inventory updated."}, status=200)




