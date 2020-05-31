package com.revature.memestore.repos;

import com.revature.memestore.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository implements CrudRepository<User> {

    private SessionFactory sessionFactory;

    @Autowired
    public UserRepository(SessionFactory factory){

        this.sessionFactory = factory;

    }

    @Override
    public List<User> getAll() {

        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM User", User.class).getResultList();

    }

    @Override
    public User findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }

    @Override
    public User save(User newUser) {
        Session session = sessionFactory.getCurrentSession();
        session.save(newUser);
        return newUser;
    }

    @Override
    public boolean update(User updatedUser) {

        Session session = sessionFactory.getCurrentSession();
        User userToUpdate = session.get(User.class, updatedUser.getUser_id());
        userToUpdate.setUsername(updatedUser.getUsername());
        userToUpdate.setPassword(updatedUser.getPassword());
        userToUpdate.setEmail(updatedUser.getEmail());
        userToUpdate.setFirst_name(updatedUser.getFirst_name());
        userToUpdate.setLast_name(updatedUser.getLast_name());
        return true;

    }

    @Override
    public boolean deleteById(int id) {

        Session session = sessionFactory.getCurrentSession();
        User userToDelete = session.get(User.class, id);
        session.delete(userToDelete);

        return true;
    }
}
