package ru.sukhorukov.service;

import ru.sukhorukov.dao.MessageDao;
import ru.sukhorukov.dao.UserDao;
import ru.sukhorukov.entity.Message;
import ru.sukhorukov.entity.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServiceBot {
    UserDao userDao;
    MessageDao messageDao;

    // Сохраняет telegram id пользователя в таблицу users и присваивает пользователю id
    public void saveId(Long chatId) {
        User user = new User(chatId);
        userDao.save(user);
    }

    // Записывает сообщение в таблицу messages, присваивает ему id, делает поле last_message = true
    public long writeMessage(Long chatId, String s) {
        User user = userDao.getUser(chatId);
        List<Message> messages = messageDao.getAllMessages(user);
        for(Message m : messages){
            m.setLast_message(false);
            messageDao.save(m);
        }
        Message message = new Message(user, s);
        return messageDao.save(message);
    }

    // Выводит последнее сообщение данного пользователя
    public String getLastMessage(Long chatId) {
        User user = userDao.getUser(chatId);
        return messageDao.getLastMessage(user);
    }

    // Выводит сообщения с id для пользователя с chatId
    public String getMessageById(Long chatId, Long msg_id) {
        User user = userDao.getUser(chatId);
        return messageDao.getMessageById(user, msg_id);
    }

    // Выводит все сообщения пользователя с Id = chatId
    public String getAllMessages(Long chatId) {
        User user = userDao.getUser(chatId);
        StringBuilder result = new StringBuilder();
        List<Message> messages = messageDao.getAllMessages(user);
        for(Message m : messages){
            result.append(m.getMessage_text());
            result.append("\n");
        }
        return result.toString();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public MessageDao getMessageDao() {
        return messageDao;
    }

    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }
}
