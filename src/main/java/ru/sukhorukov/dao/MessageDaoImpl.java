package ru.sukhorukov.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sukhorukov.entity.Message;
import ru.sukhorukov.entity.User;
import javax.annotation.Resource;
import java.util.List;

@Transactional
@Repository("messageDao")
public class MessageDaoImpl implements MessageDao {

    @Resource(name = "sessionFactory")
    SessionFactory sessionFactory;

    public MessageDaoImpl() {
    }

    public long save(Message message) {
        sessionFactory.getCurrentSession().saveOrUpdate(message);
        return message.getMessage_id();
    }

    @Transactional(readOnly = true)
    public String getLastMessage(User user) {
        Message message = (Message)sessionFactory.getCurrentSession().getNamedQuery("Message.findLastMessage")
                .setParameter("user", user).uniqueResult();
        return message.getMessage_text();
    }

    @Transactional(readOnly = true)
    public String getMessageById(User user, Long msg_id) {
        Message message = (Message)sessionFactory.getCurrentSession().getNamedQuery("Message.findById")
                .setParameter("user", user).setParameter("msg_id", msg_id)
                .uniqueResult();
        if(message == null){
            return "заметка <" + msg_id + "> не найдена";
        }
        return message.getMessage_text();
    }

    @Transactional(readOnly = true)
    public List<Message> getAllMessages(User user) {
        List<Message> messages = sessionFactory.getCurrentSession().getNamedQuery("Message.findAllByUser")
                    .setParameter("user", user).getResultList();
        return messages;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
