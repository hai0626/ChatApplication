package com.demo.chat.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<ChatMessage,Long> {
    List<ChatMessage> findByChatRoomId(Long chatRoomId);
}
