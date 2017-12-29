package ru.sukhorukov.dao;

import ru.sukhorukov.entity.User;

public interface UserDao {

    void save(User user);

    User getUser(Long chatId);
}
