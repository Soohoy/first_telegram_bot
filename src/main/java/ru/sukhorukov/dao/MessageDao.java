package ru.sukhorukov.dao;

import ru.sukhorukov.entity.Message;
import ru.sukhorukov.entity.User;

import java.util.List;

public interface MessageDao {
    long save(Message message);

    String getLastMessage(User user);

    String getMessageById(User user, Long msg_id);

    List<Message> getAllMessages(User user);
}
