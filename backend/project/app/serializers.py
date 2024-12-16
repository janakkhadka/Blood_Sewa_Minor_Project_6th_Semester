from rest_framework import serializers
from .models import User, Organization, BloodRequestModel


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'email', 'name', 'phone_number', 'blood_group', 'district', 'province', 'password']
        extra_kwargs = {
            'password': {'write_only': True}  # Ensure the password is not returned in API responses
        }

    def create(self, validated_data):
        # Create the user using the manager to hash the password
        password = validated_data.pop('password', None)
        user = User.objects.create(**validated_data)
        if password:
            user.set_password(password)
            user.save()
        return user


class OrganizationSerializer(serializers.ModelSerializer):
    class Meta:
        model = Organization
        fields = ['id', 'email', 'organization_name', 'phone_number', 'district', 'province', 'password']
        extra_kwargs = {
            'password': {'write_only': True}  # Ensure the password is not returned in API responses
        }

    def create(self, validated_data):
        # Create the organization using the manager to hash the password
        password = validated_data.pop('password', None)
        org = Organization.objects.create(**validated_data)
        if password:
            org.set_password(password)
            org.save()
        return org


class LoginSerializer(serializers.Serializer):
    email = serializers.EmailField()
    password = serializers.CharField(write_only=True)


class BloodRequestSerializer(serializers.ModelSerializer):
    user_name = serializers.SerializerMethodField()  # Custom field to get the user's name

    class Meta:
        model = BloodRequestModel
        fields = ['user_name', 'patient_name', 'contact', 'blood_group', 'location']
        read_only_fields = ['user_name']

    def get_user_name(self, obj):
        return obj.user.name  # Or obj.user.username if you want the username instead of full name

    def create(self, validated_data):
        # Automatically set the user to the currently logged-in user
        validated_data['user'] = self.context['request'].user
        return super().create(validated_data)

