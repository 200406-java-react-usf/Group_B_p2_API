package com.revature.memestore.services;

import com.revature.memestore.exceptions.AuthenticationException;
import com.revature.memestore.exceptions.BadRequestException;
import com.revature.memestore.exceptions.ResourceNotFoundException;
import com.revature.memestore.models.User;
import com.revature.memestore.models.UserRole;
import com.revature.memestore.repos.UserRepository;
import com.revature.memestore.web.dtos.Credentials;
import com.revature.memestore.web.dtos.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
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

        if(result.isEmpty()){
            throw new ResourceNotFoundException("Database contains no Users");
        }

        return result;

    }

    @Transactional(readOnly = true)
    public User getUserById(int id){

        if(id <= 0){
            throw new BadRequestException("Invalid ID was input into getUserById");
        }

        User retrievedUser = userRepo.findById(id);

        if(retrievedUser == null){
            throw new ResourceNotFoundException();
        }

        return retrievedUser;

    }

    @Transactional
    public User register(User newUser){

        if(
                newUser.getUsername() == null || newUser.getUsername().trim().equals("") ||
                newUser.getPassword() == null || newUser.getPassword().trim().equals("") ||
                newUser.getEmail() == null || newUser.getEmail().trim().equals("") ||
                newUser.getFirst_name() == null || newUser.getFirst_name().trim().equals("") ||
                newUser.getLast_name() == null || newUser.getLast_name().trim().equals("")
        ){
            throw new BadRequestException("Invalid User was input into registerUser");
        }

        newUser.setRole_id(UserRole.USER);
        return userRepo.save(newUser);

    }

    @Transactional
    public boolean updateUser(User updatedUser){

        if(
                updatedUser.getUsername() == null || updatedUser.getUsername().trim().equals("") ||
                updatedUser.getPassword() == null || updatedUser.getPassword().trim().equals("") ||
                updatedUser.getEmail() == null || updatedUser.getEmail().trim().equals("") ||
                updatedUser.getFirst_name() == null || updatedUser.getFirst_name().trim().equals("") ||
                updatedUser.getLast_name() == null || updatedUser.getLast_name().trim().equals("")
        ){
            throw new BadRequestException("Invalid User was input into updateUser");
        }

        return userRepo.update(updatedUser);

    }

    @Transactional
    public boolean deleteUserById(int id){

        if(id <= 0){
            throw new BadRequestException("Invalid ID was input into deleteUserById");
        }

        User retrievedUser = userRepo.findById(id);

        if(retrievedUser == null){
            throw new ResourceNotFoundException("No user found to delete");
        }

        return userRepo.deleteById(id);

    }

    @Transactional
    public Principal authenticate(Credentials creds){

        if(
                creds.getUsername() == null || creds.getUsername().trim().equals("") ||
                creds.getPassword() == null || creds.getPassword().trim().equals("")
        ){
            throw new BadRequestException("Invalid username or password was input into authenticate");
        }

        User retrievedUser;

        try{
            retrievedUser = userRepo.getByCredentials(creds);
        } catch(NoResultException e){
            throw new AuthenticationException("No user found with those credentials");
        }

        return new Principal(retrievedUser);

    }

}
