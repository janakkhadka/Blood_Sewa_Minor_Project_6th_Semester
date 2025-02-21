import os
from django.core.asgi import get_asgi_application
from channels.routing import ProtocolTypeRouter, URLRouter
from channels.auth import AuthMiddlewareStack
from app.routing import websocket_urlpatterns  # Use your actual app name

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "project.settings")  # Use your actual project name

application = ProtocolTypeRouter({
    "http": get_asgi_application(),  # For normal HTTP requests
    "websocket": AuthMiddlewareStack(
        URLRouter(websocket_urlpatterns)  # WebSocket routing
    ),
})
