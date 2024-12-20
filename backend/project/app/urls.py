from django.urls import path

from .views import RegisterUserView, UserLoginView, UserProfileUpdateView, BloodRequestListView, BloodRequestCreateView , FilterUserBloodGroup , LogoutView , UserListView

urlpatterns = [
    path('user/register/', RegisterUserView.as_view(), name='register_user'),
    path('user/login/', UserLoginView.as_view(), name='user-login'),
    path('user/profile/update/' , UserProfileUpdateView.as_view() , name="user-profile-update"),
    path('blood-requests/', BloodRequestListView.as_view(), name='blood-request-list'),
    path('blood-requests/create/', BloodRequestCreateView.as_view(), name='blood-request-create'),
    path('user/blood-group/' , FilterUserBloodGroup.as_view() , name="filter-user-blood-group"),
    path('user/logout/', LogoutView.as_view(), name='auth_logout'),
    path('user/district/' , UserListView.as_view() , name="search"),
    ]