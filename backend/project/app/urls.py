from django.urls import path

from .views import ( RegisterUserView, UserLoginView, UserProfileUpdateView, BloodRequestListView, BloodRequestCreateView , FilterUserBloodGroup , LogoutView , UserListView , CreateEventView , JoinEventView ,CheckInView , ListEventsView , ActivateAccountView , UserJoinedEventHistoryView)

urlpatterns = [
    path('user/register/', RegisterUserView.as_view(), name='register_user'),
    path('activate/<str:uidb64>/<str:token>/', ActivateAccountView.as_view(), name='activate_account'),
    #path('organization/register/', RegisterOrganizationView.as_view(), name='register_organization'),
    path('user/login/', UserLoginView.as_view(), name='user-login'),
    #path('organization/login/', OrganizationLoginView.as_view(), name='organization-login'),
    path('user/profile/update/' , UserProfileUpdateView.as_view() , name="user-profile-update"),
    path('blood-requests/', BloodRequestListView.as_view(), name='blood-request-list'),
    path('blood-requests/create/', BloodRequestCreateView.as_view(), name='blood-request-create'),
    path('user/blood-group/' , FilterUserBloodGroup.as_view() , name="filter-user-blood-group"),
    path('user/logout/', LogoutView.as_view(), name='auth_logout'),
    path('user/district/' , UserListView.as_view() , name="search"),
    path("events/create/", CreateEventView.as_view(), name="create_event"),
    path("events/<slug:slug>/join/", JoinEventView.as_view(), name="join_event"),
    path("events/<slug:slug>/checkin/", CheckInView.as_view(), name="checkin_event"),
    path("events/" , ListEventsView.as_view(), name="event-lists"),
    path('my-events-history/', UserJoinedEventHistoryView.as_view(), name="history"),
    ]