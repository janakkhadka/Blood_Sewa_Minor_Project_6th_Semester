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
from .utils import UserFilter , DistrictFilter , OrganizationFilter
from .models import User, BloodRequestModel , Event , UserEvent , BloodInventory
import os
from .serializers import (UserSerializer, LoginSerializer, UserProfileUpdateSerializer, BloodRequestSerializer , LimitedUserSerializer , EventSerializer , UserEventSerializer , OrganizationSerializer , BloodInventorySerializer)


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

                    
                    user_details = {
                    "name": user.name,
                    "email": user.email,
                    "phone_number": user.phone_number,
                    "district": user.district,
                    "province": user.province
                }

                    return Response({
                        "message": "Login successful",
                        "user_detail": user_details,
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




class BloodRequestCreateView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def post(self, request):
        serializer = BloodRequestSerializer(data=request.data, context={'request': request})
        if serializer.is_valid():
            # Associate the current user with the blood request
            serializer.save(user=request.user)
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

    def post(self, request):
        try:
            refresh_token = request.data["refresh_token"]
            token = RefreshToken(refresh_token)
            token.blacklist()

            return Response({"message": "Logout Successful"} , status=status.HTTP_205_RESET_CONTENT)
        except Exception as e:
            return Response(status=status.HTTP_400_BAD_REQUEST)







class CreateEventView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def post(self, request):
        serializer = EventSerializer(data=request.data)
        if serializer.is_valid():
            event = serializer.save(organizer=request.user)
            return Response(EventSerializer(event).data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)




class JoinEventView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def post(self, request, slug):
        try:
            event = Event.objects.get(slug=slug)
            UserEvent.objects.get_or_create(user=request.user, event=event)
            return Response({"message": "Successfully joined the event."}, status=status.HTTP_200_OK)
        except Event.DoesNotExist:
            return Response({"error": "Event not found."}, status=status.HTTP_404_NOT_FOUND)





class CheckInView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def post(self, request, slug):
        try:
            event = Event.objects.get(slug=slug)
            user_event = UserEvent.objects.filter(user=request.user, event=event).first()
            if not user_event:
                return Response({"error": "Not registered for this event."}, status=status.HTTP_400_BAD_REQUEST)

            if user_event.checked_in:
                return Response({"error": "Already checked in."}, status=status.HTTP_400_BAD_REQUEST)

            # # Simulate QR code validation (can be replaced with actual QR scanning logic)
            # scanned_slug = request.data.get("scanned_slug")
            # if scanned_slug != event.slug:
            #     return Response({"error": "Invalid QR code."}, status=status.HTTP_400_BAD_REQUEST)

            user_event.checked_in = True
            user_event.save()
            event.attendee_count += 1
            event.save()
            return Response({"message": "Check-in successful."}, status=status.HTTP_200_OK)

        except Event.DoesNotExist:
            return Response({"error": "Event not found."}, status=status.HTTP_404_NOT_FOUND)



class ListEventsView(APIView):
    permission_classes = [permissions.IsAuthenticated]
    def get(self, request):
        events = Event.objects.all()
        serializer = EventSerializer(events, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)



class UserJoinedEventHistoryView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def get(self, request):
        user_events = UserEvent.objects.filter(user=request.user)
        data = [
            {
                "event": {
                    "name": ue.event.name,
                    "description": ue.event.description,
                    "location": ue.event.location,
                    "joined_on": ue.event.date,
                },  
            }
            for ue in user_events
        ]
        return Response(data, status=status.HTTP_200_OK)



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

        # Get the current inventory and update only the fields provided in the request
        current_inventory = inventory.inventory

        # Get the new inventory data from the request
        new_inventory = request.data.get("inventory", {})

        # Merge the current inventory with the new data (preserving existing data)
        current_inventory.update(new_inventory)

        # Save the updated inventory
        inventory.inventory = current_inventory
        inventory.save()

        # Serialize and return the updated blood inventory
        serializer = BloodInventorySerializer(inventory)
        return Response(serializer.data)


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
        return Response({"organizations":list(organization)}, status=status.HTTP_200_OK)






