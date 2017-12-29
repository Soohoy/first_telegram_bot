package ru.sukhorukov.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sukhorukov.entity.User;
import javax.annotation.Resource;
import java.util.List;

@Transactional
@Repository("userDao")
public class UserDaoImpl implements UserDao {

    @Resource(name = "sessionFactory")
    SessionFactory sessionFactory;

    public UserDaoImpl() {
    }

    public void save(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    public User getUser(Long chatId) {
        List<Object> list = sessionFactory.getCurrentSession().
                getNamedQuery("User.findById").setParameter("id", chatId.toString()).getResultList();
        return (User)list.get(list.size()-1);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
