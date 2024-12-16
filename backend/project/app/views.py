from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status, permissions
from rest_framework.permissions import AllowAny
from django.contrib.auth import authenticate
from rest_framework_simplejwt.tokens import RefreshToken

from .models import User, Organization, BloodRequestModel
from .serializers import UserSerializer, OrganizationSerializer, LoginSerializer, BloodRequestSerializer


# Helper function to generate JWT tokens
def get_tokens_for_user(user):
    refresh = RefreshToken.for_user(user)
    return {
        'refresh': str(refresh),
        'access': str(refresh.access_token),
    }


# Register User
class RegisterUserView(APIView):
    permission_classes = [AllowAny]

    def post(self, request):
        serializer = UserSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response({"message": "User registered successfully"}, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


# Register Organization
class RegisterOrganizationView(APIView):
    permission_classes = [AllowAny]

    def post(self, request):
        serializer = OrganizationSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response({"message": "Organization registered successfully"}, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


# User Login
class UserLoginView(APIView):
    permission_classes = [AllowAny]

    def post(self, request):
        serializer = LoginSerializer(data=request.data)
        if serializer.is_valid():
            email = serializer.validated_data['email']
            password = serializer.validated_data['password']
            user = authenticate(email=email, password=password)

            if user and isinstance(user, User):  # Ensure the authenticated user is of type User
                tokens = get_tokens_for_user(user)
                return Response({
                    "message": "Login successful",
                    "tokens": tokens
                }, status=status.HTTP_200_OK)
            return Response({"message": "Invalid email or password"}, status=status.HTTP_401_UNAUTHORIZED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


# Organization Login
class OrganizationLoginView(APIView):
    permission_classes = [AllowAny]

    def post(self, request):
        serializer = LoginSerializer(data=request.data)
        if serializer.is_valid():
            email = serializer.validated_data['email']
            password = serializer.validated_data['password']
            org = Organization.objects.filter(email=email).first()

            if org and org.check_password(password):
                tokens = get_tokens_for_user(org)
                return Response({
                    "message": "Login successful",
                    "tokens": tokens
                }, status=status.HTTP_200_OK)
            return Response({"message": "Invalid email or password"}, status=status.HTTP_401_UNAUTHORIZED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)



class BloodRequestCreateView(APIView):
    permission_classes = [permissions.IsAuthenticated]

    def post(self, request):
        serializer = BloodRequestSerializer(data=request.data , context={'request': request})
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

        serializer = BloodRequestSerializer(queryset, many=True)
        return Response(serializer.data)