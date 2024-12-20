import django_filters
from django.contrib.auth import authenticate
from django_filters.rest_framework import DjangoFilterBackend
from rest_framework import status, permissions
from rest_framework.generics import ListAPIView
from rest_framework.permissions import AllowAny
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework_simplejwt.tokens import RefreshToken
from .utils import UserFilter
from .models import User, BloodRequestModel
from .serializers import UserSerializer, LoginSerializer, UserProfileUpdateSerializer, BloodRequestSerializer , LimitedUserSerializer


class RegisterUserView(APIView):
    permission_classes = [AllowAny]

    def post(self, request):
        serializer = UserSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response({"message": "User registered successfully"}, status=status.HTTP_201_CREATED)
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
                # Generate JWT tokens
                refresh = RefreshToken.for_user(user)

                user_details = {
                    "name": user.name,
                    "email": user.email,
                    "phone_number": user.phone_number,
                    "blood_group": user.blood_group,
                    "district": user.district,
                    "province": user.province,
                    "DOB": user.DOB,
                }

                return Response({
                    "message": "Login successful",
                    "user_detail": user_details ,
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


class UserFilter(django_filters.FilterSet):
    district = django_filters.CharFilter(field_name='district', lookup_expr='icontains')
    blood_group = django_filters.CharFilter(field_name='blood_group', lookup_expr='icontains')

    class Meta:
        model = User
        fields = ['district', 'blood_group']


class UserListView(ListAPIView):
    permission_classes = [permissions.IsAuthenticated]
    queryset = User.objects.all()
    serializer_class = LimitedUserSerializer
    filter_backends = (DjangoFilterBackend)
    filterset_class = UserFilter




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