from django.urls import path
from .views import (
    RegisterUserView,
    RegisterOrganizationView,
    UserLoginView,
    OrganizationLoginView, BloodRequestListView, BloodRequestCreateView
)

urlpatterns = [
    path('user/register/', RegisterUserView.as_view(), name='register_user'),
    path('organization/register/', RegisterOrganizationView.as_view(), name='register_organization'),
    path('user/login/', UserLoginView.as_view(), name='user-login'),
    path('organization/login/', OrganizationLoginView.as_view(), name='view_blood_requests'),
    path('blood-requests/', BloodRequestListView.as_view(), name='blood-request-list'),
    path('blood-requests/create/', BloodRequestCreateView.as_view(), name='blood-request-create'),
]


from rest_framework_simplejwt.views import TokenObtainPairView, TokenRefreshView, TokenVerifyView

urlpatterns += [
    path('api/token/', TokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('api/token/refresh/', TokenRefreshView.as_view(), name='token_refresh'),
]