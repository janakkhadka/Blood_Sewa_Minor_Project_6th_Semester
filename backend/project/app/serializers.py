from rest_framework import serializers
from .models import User, BloodRequestModel
from .utils import validate_password, validate_age , validate_phone_number

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'email', 'name', 'phone_number', 'blood_group', 'district', 'province', 'password', 'DOB']
        extra_kwargs = {
            'password': {'write_only': True},  # Ensure the password is not returned in API responses
            'DOB': {'required': True}  # If DOB is required for registration
        }

    def validate_phone_number(self , value):
        return validate_phone_number(value)

    def validate_password(self, value):
        return validate_password(value)

    def validate_DOB(self, value):
        return validate_age(value)

    def create(self, validated_data):
        password = validated_data.pop('password', None)
        user = User.objects.create(**validated_data)
        if password:
            user.set_password(password)  # Hash the password
            user.save()
        return user


class LoginSerializer(serializers.Serializer):
    email = serializers.EmailField()
    password = serializers.CharField(write_only=True)



class UserProfileUpdateSerializer(serializers.ModelSerializer):
    password = serializers.CharField(write_only=True, min_length=8)

    class Meta:
        model = User
        fields = [
            'name', 'email', 'phone_number', 'blood_group', 'province', 'district', 'password' , 'DOB'
        ]
        extra_kwargs = {
            'email': {'read_only': True},
            'blood_group': {'read_only': True},
            'DOB': {'read_only':True},
            'name':{'read_only': True}
        }

    def validate_password(self, value):
        return validate_password(value)



    def update(self, instance, validated_data):
        password = validated_data.pop('password', None)
        if password:
            instance.set_password(password)

        for attr, value in validated_data.items():
            setattr(instance, attr, value)

        instance.save()
        return instance


class BloodRequestSerializer(serializers.ModelSerializer):
    user_name = serializers.SerializerMethodField()  # Custom field to get the user's name

    class Meta:
        model = BloodRequestModel
        fields = ['user_name', 'patient_name', 'contact', 'blood_group', 'location' ]
        read_only_fields = ['user_name']

    def get_user_name(self, obj):
        return obj.user.name

    def create(self, validated_data):
        # Automatically set the user to the currently logged-in user
        validated_data['user'] = self.context['request'].user
        return super().create(validated_data)


class LimitedUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['name', 'phone_number', 'blood_group']