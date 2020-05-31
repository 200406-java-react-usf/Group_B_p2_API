package com.revature.memestore.services;

import com.revature.memestore.models.User;
import com.revature.memestore.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepository){

        this.userRepo = userRepository;

    }

    @Transactional
    public List<User> getAllUsers(){

        return userRepo.getAll();

    }

    @Transactional
    public User register(User newUser){

        //VALIDATION

        newUser.setRole_id(2);
        return userRepo.save(newUser);

    }


}
