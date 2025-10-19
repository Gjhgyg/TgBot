package org.example.tgbot;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tg_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(unique = true)
    private Long chatId;

    private String username;
    private String firstName;
    private LocalDateTime joinedAt = LocalDateTime.now();

    // Конструкторы, геттеры и сеттеры
    public User() {}
    public User(Long chatId, String username, String firstName) {
        this.chatId = chatId;
        this.username = username;
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}