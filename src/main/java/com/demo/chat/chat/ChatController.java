package com.demo.chat.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private  RoomRepository roomRepository;
    @Autowired
    private  MessageRepository messageRepository;

    public ChatController(RoomRepository roomRepository, MessageRepository messageRepository) {
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
    }


    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable Long roomId,
            @Payload ChatMessage chatMessage
    ) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        chatMessage.setRoom(room);
        messageRepository.save(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @PostMapping("/rooms")
    public Room createRoom(@RequestBody Room room) {
        return roomRepository.save(room);
    }

    @GetMapping("/messages/{roomId}")
    public List<ChatMessage> getMessagesByRoom(@PathVariable Long roomId) {
        return messageRepository.findByChatRoomId(roomId);
    }
}
