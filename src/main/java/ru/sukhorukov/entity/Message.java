package ru.sukhorukov.entity;

import javax.persistence.*;

@Entity
@Table(name = "messages")
@NamedQueries({
@NamedQuery(name = "Message.findAllByUser", query = "select m from Message m where m.user = :user"),
@NamedQuery(name = "Message.findById", query = "select m from Message m where m.user = :user and m.message_id = :msg_id"),
@NamedQuery(name = "Message.findLastMessage", query = "select m from Message m where m.user = :user and m.last_message = true")
 })
public class Message {
    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long message_id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;
    @Column(name = "message_text")
    String message_text;
    @Column(name = "last_message")
    boolean last_message;

    public Message(){
    }

    public Message(User user, String text) {
        this.user = user;
        this.message_text = text;
        this.last_message = true;
    }

    public Long getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Long message_id) {
        this.message_id = message_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public boolean isLast_message() {
        return last_message;
    }

    public void setLast_message(boolean last_message) {
        this.last_message = last_message;
    }
}
