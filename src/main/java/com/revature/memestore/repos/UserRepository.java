package com.revature.memestore.repos;

import com.revature.memestore.models.Invoice;
import com.revature.memestore.models.User;
import com.revature.memestore.web.dtos.Credentials;
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

    public List<Invoice> getInvoicesById(int id){

        Session session = sessionFactory.getCurrentSession();

        User retrievedUser = session.get(User.class, id);

        return retrievedUser.getInvoices();

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

    public User getByCredentials(Credentials creds){

        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM User u " + "WHERE u.username = :un AND u.password = :pw", User.class)
                .setParameter("un", creds.getUsername())
                .setParameter("pw", creds.getPassword())
                .getSingleResult();

    }

    public User getByUsername(String username){

        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("FROM User u WHERE u.username = :un", User.class)
                .setParameter("un", username)
                .getSingleResult();

    }

    public User getByEmail(String email){

        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();

    }

}
