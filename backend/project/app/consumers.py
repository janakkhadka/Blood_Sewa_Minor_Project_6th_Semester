import json
from channels.generic.websocket import AsyncWebsocketConsumer

class NotificationConsumer(AsyncWebsocketConsumer):
    async def connect(self):
        self.room_group_name = "blood_requests"
        
        # Add WebSocket to group
        await self.channel_layer.group_add(
            self.room_group_name,
            self.channel_name
        )
        await self.accept()
        print(" WebSocket Connected!")

    async def disconnect(self, close_code):
        # Remove WebSocket from group
        await self.channel_layer.group_discard(
            self.room_group_name,
            self.channel_name
        )
        print(" WebSocket Disconnected!")

    async def send_notification(self, event):
        """Send notification to all WebSocket clients"""
        message = event["message"]
        await self.send(text_data=json.dumps({"message": message}))




class EventNotificationConsumer(AsyncWebsocketConsumer):
    async def connect(self):
        self.room_group_name = "events"
        
        # Add WebSocket to group
        await self.channel_layer.group_add(
            self.room_group_name,
            self.channel_name
        )
        await self.accept()
        print("WebSocket Connected!")

    async def disconnect(self, close_code):
        await self.channel_layer.group_discard(
            self.room_group_name,
            self.channel_name
        )
        print("WebSocket Disconnected!")

    async def send_event_notification(self, event):
        message = event["message"]
        await self.send(text_data=json.dumps({"event_notification": message}))