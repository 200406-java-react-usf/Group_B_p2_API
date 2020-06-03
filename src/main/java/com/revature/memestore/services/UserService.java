package com.revature.memestore.services;

import com.revature.memestore.exceptions.ResourceNotFoundException;
import com.revature.memestore.models.User;
import com.revature.memestore.models.UserRole;
import com.revature.memestore.repos.UserRepository;
import com.revature.memestore.web.dtos.Credentials;
import com.revature.memestore.web.dtos.Principal;
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

    @Transactional(readOnly = true)
    public List<User> getAllUsers(){

        List<User> result = userRepo.getAll();

        if(result.isEmpty() == true){
            throw new ResourceNotFoundException("Database contains no Users");
        }

        return result;

    }

    @Transactional(readOnly = true)
    public User getUserById(int id){

        //Validation

        return userRepo.findById(id);

    }

    @Transactional
    public User register(User newUser){

        //VALIDATION

        newUser.setRole_id(UserRole.USER);
        return userRepo.save(newUser);

    }

    @Transactional
    public boolean updateUser(User updatedUser){

        //Validation

        return userRepo.update(updatedUser);

    }

    @Transactional
    public boolean deleteUserById(int id){

        return userRepo.deleteById(id);

    }

    @Transactional
    public Principal authenticate(Credentials creds){

        Principal retrievedUser = new Principal(userRepo.getByCredentials(creds));

        return retrievedUser;

    }

}
