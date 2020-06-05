package com.revature.memestore.services;

import com.revature.memestore.exceptions.AuthenticationException;
import com.revature.memestore.exceptions.BadRequestException;
import com.revature.memestore.exceptions.ResourceNotFoundException;

import com.revature.memestore.exceptions.ResourcePersistenceException;
import com.revature.memestore.models.Invoice;
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
            throw new ResourceNotFoundException("No user found with that ID");
        }

        return retrievedUser;

    }

    @Transactional
    public List<Invoice> getUsersInvoicesById(int id){

        if(id <= 0){
            throw new BadRequestException("Invalid Id was input into getUsersInvoicesById");
        }

        User retrievedUser = userRepo.findById(id);

        if(retrievedUser == null){
            throw new ResourceNotFoundException("No user found to get their invoices");
        }

        return userRepo.getInvoicesById(id);

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

        boolean usernameConflict = isUsernameAvailable(newUser.getUsername());

        if(!usernameConflict){
            throw new ResourcePersistenceException("Username is already taken");
        }

        boolean emailConflict = isEmailAvailable(newUser.getEmail());

        if(!emailConflict){
            throw new ResourcePersistenceException("Email is already taken");
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

        User persistedUser = getUserById(updatedUser.getUser_id());

        boolean usernameConflict = isUsernameAvailable(updatedUser.getUsername());

        if(persistedUser.getUsername().equals(updatedUser.getUsername())){
            usernameConflict = true;
        }

        if(!usernameConflict){
            throw new ResourcePersistenceException("Username is already taken");
        }

        boolean emailConflict = isEmailAvailable(updatedUser.getEmail());

        if(persistedUser.getEmail().equals(updatedUser.getEmail())){
            emailConflict = true;
        }

        if(!emailConflict){
            throw new ResourcePersistenceException("Email is already taken");
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

    private boolean isUsernameAvailable(String username){

        User result;

        try{
            result = userRepo.getByUsername(username);
        }catch(NoResultException e){
            return true;
        }

        return false;

    }

    private boolean isEmailAvailable(String email){

        User result;

        try{
            result = userRepo.getByEmail(email);
        }catch(NoResultException e){
            return true;
        }

        return false;

    }

}
