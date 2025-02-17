from rest_framework import serializers
from .models import User, BulkRequestmodel ,BloodRequestModel , Event , UserEvent , BloodInventory , Bookings 
from .utils import validate_password, validate_age , validate_phone_number , is_event_date_in_past
from django.utils import timezone
from datetime import date , timedelta
from django.contrib.auth import get_user_model
from django.shortcuts import get_object_or_404


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'email', 'name', 'phone_number', 'blood_group', 'district', 'province', 'password', 'DOB' , 'gender']
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
        validated_data['user_type'] = 'user'
        user = User.objects.create(**validated_data)
        if password:
            user.set_password(password)  # Hash the password
            user.save()
        return user






class OrganizationSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'email', 'name', 'phone_number', 'district', 'province', 'password' , 'org_type' , 'city' , 'local_address']
        extra_kwargs = {
            'password': {'write_only': True},  # Ensure the password is not returned in API responses
                }

    def validate_phone_number(self , value):
        return validate_phone_number(value)

    def validate_password(self, value):
        return validate_password(value)


    def create(self, validated_data):
        password = validated_data.pop('password', None)
        validated_data.pop('DOB', None)
        validated_data.pop('blood_group', None)
        validated_data['user_type'] = 'organization'
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
            'name', 'email', 'phone_number', 'blood_group', 'province', 'district', 'password' , 'DOB' , 'gender'
        ]
        extra_kwargs = {
            'email': {'read_only': True},
            'blood_group': {'read_only': True},
            'DOB': {'read_only':True},
            'name':{'read_only': True},
            'gender':{'read_only': True},
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
    # date = serializers.DateField()
    class Meta:
        model = BloodRequestModel
        fields = ['user_name', 'patient_name', 'contact', 'blood_group', 'district', 'province', 'city' , 'date']
        read_only_fields = ['user_name']

    def get_user_name(self, obj):
        if obj.user:
            return obj.user.name
        return 'Anonymous'


    def create(self, validated_data):
        # Automatically set the user to the currently logged-in user
        validated_data['user'] = self.context['request'].user
        return super().create(validated_data)


class PublicBloodRequestSerializer(serializers.ModelSerializer):
    user_name = serializers.CharField(read_only=True)  # Display name of the user or Anonymous

    class Meta:
        model = BloodRequestModel
        fields = ['user_name', 'patient_name', 'contact', 'blood_group', 'district', 'province' , 'city' , 'date']
        read_only_fields = ['user_name']

    def get_user_name(self, obj):
        return obj.user.name if obj.user else "Anonymous"

    def create(self, validated_data):
        # Handle authenticated users
        if self.context['request'].user.is_authenticated:
            validated_data['user'] = self.context['request'].user
        else:
            validated_data['user'] = None  
        return super().create(validated_data)




class LimitedUserSerializer(serializers.ModelSerializer):
    age = serializers.SerializerMethodField()
    class Meta:
        model = User
        fields = ['name', 'phone_number', 'blood_group' , 'district' , 'province' , 'age']

    def get_age(self,obj):
        today = date.today()
        if obj.DOB:
            return today.year - obj.DOB.year - ((today.month, today.day) < (obj.DOB.month, obj.DOB.day))
        return None



#Event Creation serializer For User
# class UserEventCreateSerializer(serializers.ModelSerializer):
#     organizer = serializers.SerializerMethodField()
#     collabrator = serializers.CharField()
#     class Meta:
#         model = Event
#         fields = [ 'name', 'description', 'location', 'date', 'organizer', 'qr_code' , 'slug' , 'collabrator' , 'start_time' , 'end_time']
#         read_only_fields = ['organizer', 'qr_code' , 'collabrator']

#     def get_organizer(self, obj):
#         try:
#             organizer = User.objects.get(id=obj.organizer_id)
#             return organizer.name
#         except Organizer.DoesNotExist:
#             return None

#     def validate(self, data):
#         start_time = data.get('start_time')
#         end_time = data.get('end_time')
#         if start_time and end_time and end_time < start_time:
#             raise serializers.ValidationError("End time must be later than start time.")
#         return data
    
#     def create(self, validated_data):
#         collaborator_name = validated_data.pop('collabrator')  # Extract the name
#         # Attempt to find a single collaborator with this name
#         collaborator = get_object_or_404(User, name=collaborator_name)

#         # Create the event with the resolved collaborator
#         event = Event.objects.create(collabrator=collaborator, **validated_data)
#         return event
class UserEventCreateSerializer(serializers.ModelSerializer):
    organizer = serializers.SerializerMethodField()
    collabrator = serializers.CharField(write_only=True)  # Accepts name as input
    collaboration_status = serializers.CharField(read_only=True)  # Show status in response
    volunteer_required_count = serializers.IntegerField() 
    volunteer_attendee_count = serializers.IntegerField(read_only=True, required=False, default=0)  
    is_volunteer = serializers.SerializerMethodField()

    class Meta:
        model = Event
        fields = [
            'name', 'description', 'location', 'date', 'organizer', 'qr_code', 'slug',
            'collabrator', 'start_time', 'end_time', 'collaboration_status' , 'volunteer_required_count', 'is_volunteer' ,'volunteer_attendee_count'
        ]
        read_only_fields = ['organizer', 'qr_code', 'collaboration_status']

    def get_organizer(self, obj):
        return obj.organizer.name if obj.organizer else None

    def validate(self, data):
        start_time = data.get('start_time')
        end_time = data.get('end_time')
        if start_time and end_time and end_time < start_time:
            raise serializers.ValidationError("End time must be later than start time.")
        return data

    def create(self, validated_data):
        request = self.context.get('request')
        if not request:
            raise serializers.ValidationError("Request context is required.")

        # Get the authenticated user (event creator)
        organizer = request.user

        # Get the collaborator (organization) from input
        collaborator_name = validated_data.pop('collabrator', None)
        collaborator = get_object_or_404(User, name=collaborator_name)

        validated_data.pop('volunteer_attendee_count', None)

        # Create the event with `pending` status
        event = Event.objects.create(
            organizer=organizer,
            collabrator=collaborator,
            collaboration_status='pending',  # Awaiting approval
            **validated_data
        )

        return event
    



#Event Creation Serializer For Organization
class EventSerializer(serializers.ModelSerializer):
    organizer = serializers.SerializerMethodField()
    collabrator_name = serializers.SerializerMethodField()
    volunteer_required_count = serializers.IntegerField() 
    volunteer_attendee_count = serializers.IntegerField(read_only=True, required=False, default=0)  
    is_volunteer = serializers.SerializerMethodField()  

    class Meta:
        model = Event
        fields = [
            'name', 'description', 'location', 'date', 'organizer', 
            'qr_code', 'slug', 'collabrator_name', 'start_time', 
            'end_time', 'volunteer_required_count', 'is_volunteer' ,'volunteer_attendee_count'
        ]
        read_only_fields = ['organizer', 'qr_code']

    def get_organizer(self, obj):
        try:
            organizer = User.objects.get(id=obj.organizer_id)
            return organizer.name
        except User.DoesNotExist:
            return None

    def get_collabrator_name(self, obj):
        try:
            return obj.collabrator.name
        except AttributeError:
            return None

    def get_is_volunteer(self, obj):
        # Ensure this method is defined
        request = self.context.get('request')
        if request and request.user.is_authenticated:
            return obj.volunteers.filter(id=request.user.id).exists()
        return False

    def validate(self, data):
        start_time = data.get('start_time')
        end_time = data.get('end_time')
        if start_time and end_time and end_time < start_time:
            raise serializers.ValidationError("End time must be later than start time.")
        return data
    
    def validate_date(self, value):
        if is_event_date_in_past(value):
            raise serializers.ValidationError("The event date cannot be in the past.")
        return value

    def create(self, validated_data):
        validated_data.pop('volunteer_attendee_count', None)
        return super().create(validated_data)



class MyEventSerializer(serializers.ModelSerializer):
    organizer = serializers.SerializerMethodField()
    collabrator_name = serializers.SerializerMethodField()
    class Meta:
        model = Event
        fields = [ 'name','location', 'date', 'organizer', 'qr_code' , 'slug' , 'collabrator_name' , 'donor_attendee_count' , 'start_time' , 'end_time' , 'expected_donor_count' , 'volunteer_required_count', 'volunteer_attendee_count']
        read_only_fields = ['organizer', 'qr_code']

    def get_organizer(self, obj):
        try:
            organizer = User.objects.get(id=obj.organizer_id)
            return organizer.name
        except Organizer.DoesNotExist:
            return None
    
    def get_collabrator_name(self, obj):
        try:
            return obj.collabrator.name
        except AttributeError:
            return None


class UserEventSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserEvent
        fields = ['id', 'user', 'event', 'checked_in']
        read_only_fields = ['checked_in']


class BloodInventorySerializer(serializers.ModelSerializer):
    organization_name = serializers.CharField(source='organization.name', read_only=True)

    class Meta:
        model = BloodInventory
        fields = ['organization_name', 'inventory']
        read_only_fields = ['organization_name']





class BulkBloodRequestSerializer(serializers.ModelSerializer):
    organization_name = serializers.CharField(source='organization.name', read_only=True)

    class Meta:
        model = BulkRequestmodel
        fields = ['organization_name', 'blood_request' , 'date']
        read_only_fields = ['organization_name']


    def to_internal_value(self, data):
        if isinstance(data, dict):
            # Treat the entire payload as the `blood_request`
            return {'blood_request': data}
        return super().to_internal_value(data)








class BookingSerializer(serializers.ModelSerializer):
    User = get_user_model
    organization = serializers.CharField() 
    user_phone_number = serializers.CharField(source='user.phone_number', read_only=True)
    user_blood_group = serializers.CharField(source='user.blood_group', read_only=True)

    class Meta:
        model = Bookings
        fields = ['organization', 'booking_date', 'shift' , 'user_blood_group' , 'user_phone_number']
        read_only_fields = ['id', 'user']

    def validate(self,data):
        user = self.context['request'].user
        today = date.today()
        three_months_ago = today - timedelta(days=90)

        recent_booking = Bookings.objects.filter(user=user, booking_date__gte=three_months_ago).exists()
        if recent_booking:
            raise serializers.ValidationError("You can only have one booking every three months")

        return data


    def validate_organization(self, value):
        # Ensure the organization exists
        try:
            organization = User.objects.get(name=value, user_type='organization')
        except User.DoesNotExist:
            raise serializers.ValidationError(f"Organization with name '{value}' does not exist.")
        return organization

    def create(self, validated_data):
        organization = validated_data.pop('organization')
        validated_data['organization'] = User.objects.get(name=organization, user_type='organization')
        return super().create(validated_data)



class OrganizationBookingSerializer(serializers.ModelSerializer):
    user_name = serializers.CharField(source='user.name')
    class Meta:
        model = Bookings
        fields = ['user_name', 'booking_date', 'shift', 'user' ,   'user_phone_number','user_blood_group']  







