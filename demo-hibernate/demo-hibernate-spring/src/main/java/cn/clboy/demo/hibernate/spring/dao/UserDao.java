package cn.clboy.demo.hibernate.spring.dao;


import cn.clboy.demo.hibernate.spring.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    HibernateTemplate hibernateTemplate;

    public Integer addUser(User user) {
        Integer id = (Integer) hibernateTemplate.save(user);
        return id;
    }

    public List<User> getAllUser() {
        List<User> users = hibernateTemplate.findByExample(new User());
        return users;
    }

    public void deleteUser(Integer id) {
        User user = new User();
        user.setId(id);
        hibernateTemplate.delete(user);
    }

    public void updateUser(User user) {
        hibernateTemplate.update(user);

    }

    public User getUser(Integer id) {
        return hibernateTemplate.get(User.class, id);
    }
}