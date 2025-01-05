from django.urls import path
from django.conf import settings
from django.conf.urls.static import static

from .views import ( RegisterUserView, UserLoginView, UserProfileUpdateView, BloodRequestListView, BloodRequestCreateView , FilterUserBloodGroup , LogoutView , UserListView , CreateEventView , JoinEventView ,CheckInView , ListEventsView , ActivateAccountView , UserJoinedEventHistoryView , qr_code_view , PasswordResetRequestView , PasswordResetConfirmView , OrganizationLoginView , RegisterOrganizationView , BloodInventoryDetail ,BloodInventoryByOrganization , OrganizationListView , BookingCreateView,MyBookings , OrganizationBookings , UserEventCreateView)

urlpatterns = [
    path('user/register/', RegisterUserView.as_view(), name='register_user'),    #user registration ko lagi
    path('activate/<str:uidb64>/<str:token>/', ActivateAccountView.as_view(), name='activate_account'),   #registered user ko account activation ko lagi
    path('organization/register/', RegisterOrganizationView.as_view(), name='register_organization'),    
    path('user/login/', UserLoginView.as_view(), name='user-login'),       #user login ko lagi
    path('organization/login/', OrganizationLoginView.as_view(), name='organization-login'),
    path('user/profile/update/' , UserProfileUpdateView.as_view() , name="user-profile-update"),    #profile update garna ko lagi
    path('blood-requests/', BloodRequestListView.as_view(), name='blood-request-list'),         #blood request haru herna ko lagi
    path('blood-requests/create/', BloodRequestCreateView.as_view(), name='blood-request-create'),         #blood request garna ko lagi
    path('user/blood-group/' , FilterUserBloodGroup.as_view() , name="filter-user-blood-group"),            #get user according to blood group
    path('user/logout/', LogoutView.as_view(), name='auth_logout'),     #logout garna ko lagi
    path('user/district/' , UserListView.as_view() , name="search"),    #get user according to blood group and district
    path('user/event/create/' , UserEventCreateView.as_view() , name='user-event-create'), #api to create event for user
    path("events/create/", CreateEventView.as_view(), name="create_event"),    #event create garna ko lagi (organization)
    path("events/<slug:slug>/join/", JoinEventView.as_view(), name="join_event"),   #event join garna ko lagi
    path("events/<slug:slug>/checkin/", CheckInView.as_view(), name="checkin_event"),  #event checkin garna ko lagi
    path("events/" , ListEventsView.as_view(), name="event-lists"),   # existing event haru listout garna ko lagi
    path('my-events-history/', UserJoinedEventHistoryView.as_view(), name="history"),  #aafnu event history haru check garna ko lagi 
    path('my-blood-inventory/', BloodInventoryDetail.as_view(), name='blood-inventory-list'),  # List and Create
    path('blood-inventory/update/', BloodInventoryDetail.as_view(), name='blood-inventory-detail'),  #update available blood inventory
    path('blood-inventory/', BloodInventoryByOrganization.as_view(), name='blood-inventory-by-organization'), #list organization by blood group
    path('organization-list/',OrganizationListView.as_view(), name='organization-list'), #list of organizations
    path('make-bookings/' , BookingCreateView.as_view(), name='make-bookings'),  #create bookings
    path('my-user-bookings/' , MyBookings.as_view(), name='my-bookings'),  #list of bookings
    path('my-organization-bookings/' , OrganizationBookings.as_view() , name='my-organization-bookings')
    ]
