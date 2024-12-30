
from django.contrib import admin
from django.urls import path, include
from app.views import ActivateAccountView

urlpatterns = [
    path("admin/", admin.site.urls),
    path("api/" , include('app.urls')),
     path('activate/<uidb64>/<token>/', ActivateAccountView.as_view(), name='activate-account'),
]
