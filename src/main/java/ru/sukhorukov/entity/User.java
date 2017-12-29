package ru.sukhorukov.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.findById", query = "select u from User u where u.telegram_id = :id")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long user_id;
    @Column(name = "telegram_id")
    String telegram_id;
    @Column(name = "date_reg")
    Timestamp date_reg;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Message> messages;

    public User() {
    }

    public User(Long chatId) {
        this.telegram_id = chatId.toString();
        Calendar calendar = Calendar.getInstance();
        this.date_reg = new Timestamp(calendar.getTimeInMillis());
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getTelegram_id() {
        return telegram_id;
    }

    public void setTelegram_id(String telegram_id) {
        this.telegram_id = telegram_id;
    }

    public Date getDate_reg() {
        return date_reg;
    }

    public void setDate_reg(Timestamp date_reg) {
        this.date_reg = date_reg;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
