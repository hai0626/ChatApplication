package com.demo.chat.chat;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String sender;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private Room room;

}
