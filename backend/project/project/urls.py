
from django.contrib import admin
from django.urls import path, include
from app.views import ActivateAccountView , qr_code_view
from django.conf import settings
from django.conf.urls.static import static

urlpatterns = [
    path("admin/", admin.site.urls),
    path("api/" , include('app.urls')),
     path('activate/<uidb64>/<token>/', ActivateAccountView.as_view(), name='activate-account'),
     path('qrcodes/<str:filename>/', qr_code_view, name='qr-code'),
]

if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
