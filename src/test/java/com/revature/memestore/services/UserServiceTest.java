package com.revature.memestore.services;

import com.revature.memestore.exceptions.BadRequestException;
import com.revature.memestore.exceptions.ResourceNotFoundException;
import com.revature.memestore.exceptions.ResourcePersistenceException;
import com.revature.memestore.models.Invoice;
import com.revature.memestore.models.User;
import com.revature.memestore.models.UserRole;
import com.revature.memestore.repos.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnAllUsers(){

        List<User> mockUsers = new ArrayList<User>();
        mockUsers.add(new User(1,"test1", "password", "test1@me.com", "test", "one", UserRole.USER));
        mockUsers.add(new User(2,"test2", "password", "test2@me.com", "test", "two", UserRole.USER));
        mockUsers.add(new User(3,"test3", "password", "test3@me.com", "test", "three", UserRole.USER));

        when(userRepository.getAll()).thenReturn(mockUsers);

        List<User> result = userService.getAllUsers();

        assertEquals(result, mockUsers);
        assertEquals(result.size(), mockUsers.size());

    }

    @Test
    public void shouldThrowResourceNotFoundException(){

        List<User> mockUsers = new ArrayList<User>();

        when(userRepository.getAll()).thenReturn(mockUsers);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            userService.getAllUsers();
        });

    }

    @Test
    public void shouldReturnUserWithIdThree(){

        User mockUser = new User(3,"test3", "password", "test3@me.com", "test", "three", UserRole.USER);

        when(userRepository.findById(3)).thenReturn(mockUser);

        User result = userService.getUserById(3);

        assertEquals(result, mockUser);
        assertEquals(result.getUser_id(), mockUser.getUser_id());

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenGivenIdOfZero(){

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
           userService.deleteUserById(0);
        });

    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenUserIsNotFound(){

        when(userRepository.findById(100)).thenReturn(null);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            User result = userService.getUserById(100);
        });

    }

    @Test
    public void shouldReturnListOfInvoicesWhenGivenAProperId(){

        User mockUser = new User(1,"test1", "password", "test1@me.com", "test", "one", UserRole.USER);

        List<Invoice> mockInvoices = new ArrayList<Invoice>();
        mockInvoices.add(new Invoice(1,mockUser,100,"mockdate1"));
        mockInvoices.add(new Invoice(2,mockUser,200,"mockdate2"));
        mockInvoices.add(new Invoice(3,mockUser,300,"mockdate3"));

        when(userRepository.findById(1)).thenReturn(mockUser);
        when(userRepository.getInvoicesById(1)).thenReturn(mockInvoices);

        List<Invoice> results = userService.getUsersInvoicesById(1);

        assertEquals(results, mockInvoices);

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenGivenBadId(){

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
           userService.getUserById(0);
        });

    }

    @Test
    public void shouldThrowNoResourceFoundWhenUnknownIdIsGiven(){

        when(userRepository.getInvoicesById(100)).thenReturn(null);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
           userService.getUsersInvoicesById(100);
        });

    }

    @Test
    public void shouldReturnNewUserWhenAddNewUserIsCalledSuccessfully(){

        User mockUser = new User(1,"test1", "password", "test1@me.com", "test", "one", UserRole.USER);

        when(userRepository.save(mockUser)).thenReturn(mockUser);
        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        User newUser = userService.register(mockUser);

        assertEquals(newUser, mockUser);

    }

    @Test
    public void shouldThrowResourcePersistenceExceptionWhenUsernameIsAlreadyTaken(){

        User mockUser1 = new User(1,"test1", "password", "test1@me.com", "test", "one", UserRole.USER);
        User mockUser2 = new User(2,"test1", "password", "test2@me.com", "test", "two", UserRole.USER);

        when(userRepository.getByUsername(mockUser1.getUsername())).thenReturn(mockUser2);
        when(userRepository.getByEmail(mockUser1.getEmail())).thenThrow(NoResultException.class);

        ResourcePersistenceException thrown = assertThrows(ResourcePersistenceException.class, () -> {
            User newUser = userService.register(mockUser1);
        });

    }

    @Test
    public void shouldThrowResourcePersistenceExceptionWhenEmailIsAlreadyTaken(){

        User mockUser1 = new User(1,"test1", "password", "test1@me.com", "test", "one", UserRole.USER);
        User mockUser2 = new User(2,"test2", "password", "test1@me.com", "test", "two", UserRole.USER);

        when(userRepository.getByUsername(mockUser1.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser1.getEmail())).thenReturn(mockUser2);

        ResourcePersistenceException thrown = assertThrows(ResourcePersistenceException.class, () -> {
            User newUser = userService.register(mockUser1);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUsernameIsNull(){

        User mockUser = new User(1,null, "password", "test1@me.com", "test", "one", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            User newUser = userService.register(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUsernameIsEmpty(){

        User mockUser = new User(1,"     ", "password", "test1@me.com", "test", "one", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            User newUser = userService.register(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenPasswordIsNull(){

        User mockUser = new User(1,"test1", null, "test1@me.com", "test", "one", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            User newUser = userService.register(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenPasswordIsEmpty(){

        User mockUser = new User(1,"test1", "     ", "test1@me.com", "test", "one", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            User newUser = userService.register(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenEmailIsNull(){

        User mockUser = new User(1,"test1", "password", null, "test", "one", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            User newUser = userService.register(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenEmailIsEmpty(){

        User mockUser = new User(1,"test1", "password", "      ", "test", "one", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            User newUser = userService.register(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenFirstNameIsNull(){

        User mockUser = new User(1,"test1", "password", "test1@me.com", null, "one", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            User newUser = userService.register(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenFirstNameIsEmpty(){

        User mockUser = new User(1,"test1", "password", "test1@me.com", "    ", "one", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            User newUser = userService.register(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenLastNameIsNull(){

        User mockUser = new User(1,"test1", "password", "test1@me.com", "test", null, UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            User newUser = userService.register(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenLastNameIsEmpty(){

        User mockUser = new User(1,"test1", "password", "test1@me.com", "test", "     ", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            User newUser = userService.register(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdatedUsernameIsNull(){

        User mockUser = new User(1,null, "password", "test1@me.com", "test", "one", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            userService.updateUser(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdatedUsernameIsEmpty(){

        User mockUser = new User(1,"     ", "password", "test1@me.com", "test", "one", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            userService.updateUser(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdatedPasswordIsNull(){

        User mockUser = new User(1,"test1", null, "test1@me.com", "test", "one", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            userService.updateUser(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdatedPasswordIsEmpty(){

        User mockUser = new User(1,"test1", "     ", "test1@me.com", "test", "one", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            userService.updateUser(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdatedEmailIsNull(){

        User mockUser = new User(1,"test1", "password", null, "test", "one", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            userService.updateUser(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdatedEmailIsEmpty(){

        User mockUser = new User(1,"test1", "password", "      ", "test", "one", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            userService.updateUser(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdatedFirstNameIsNull(){

        User mockUser = new User(1,"test1", "password", "test1@me.com", null, "one", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            userService.updateUser(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdatedFirstNameIsEmpty(){

        User mockUser = new User(1,"test1", "password", "test1@me.com", "    ", "one", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            userService.updateUser(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdatedLastNameIsNull(){

        User mockUser = new User(1,"test1", "password", "test1@me.com", "test", null, UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            userService.updateUser(mockUser);
        });

    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdatedLastNameIsEmpty(){

        User mockUser = new User(1,"test1", "password", "test1@me.com", "test", "     ", UserRole.USER);

        when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(NoResultException.class);

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            userService.updateUser(mockUser);
        });

    }

    @Test
    public void shouldThrowResourcePersistenceExceptionIfUsernameIsAlreadyTaken(){

        User mockUserPersisted = new User(1,"test1", "password", "test1@me.com", "test", "one", UserRole.USER);
        User mockUserUpdated = new User(1,"updated", "Update", "Update@me.com", "update", "update", UserRole.USER);
        User mockUserConflict = new User(2,"updated", "password", "test2@me.com", "test", "two", UserRole.USER);

        when(userRepository.getByUsername(mockUserPersisted.getUsername())).thenReturn(mockUserConflict);
        when(userRepository.getByEmail(mockUserUpdated.getEmail())).thenThrow(NoResultException.class);
        when(userRepository.findById(mockUserUpdated.getUser_id())).thenReturn(mockUserPersisted);

        ResourcePersistenceException thrown = assertThrows(ResourcePersistenceException.class, () -> {
            userService.updateUser(mockUserUpdated);
        });

    }

    @Test
    public void shouldThrowResourcePersistenceExceptionIfEmailIsAlreadyTaken(){

        User mockUserPersisted = new User(1,"test1", "password", "test1@me.com", "test", "one", UserRole.USER);
        User mockUserUpdated = new User(1,"updated", "Update", "Update@me.com", "update", "update", UserRole.USER);
        User mockUserConflict = new User(2,"test2", "password", "Update@me.com", "test", "two", UserRole.USER);

        when(userRepository.getByUsername(mockUserPersisted.getUsername())).thenReturn(mockUserConflict);
        when(userRepository.getByEmail(mockUserUpdated.getEmail())).thenThrow(NoResultException.class);
        when(userRepository.findById(mockUserUpdated.getUser_id())).thenReturn(mockUserPersisted);

        ResourcePersistenceException thrown = assertThrows(ResourcePersistenceException.class, () -> {
            userService.updateUser(mockUserUpdated);
        });

    }

    @Test
    public void shouldUpdateSuccessfullyWhenUpdatingEverythingButUsername(){

        User mockUserPersisted = new User(1,"test1", "password", "test1@me.com", "test", "one", UserRole.USER);
        User mockUserUpdated = new User(1,"test1", "Update", "Update@me.com", "update", "update", UserRole.USER);

        when(userRepository.update(mockUserUpdated)).thenReturn(true);
        when(userRepository.getByUsername(mockUserUpdated.getUsername())).thenReturn(mockUserPersisted);
        when(userRepository.getByEmail(mockUserUpdated.getEmail())).thenThrow(NoResultException.class);
        when(userRepository.findById(mockUserUpdated.getUser_id())).thenReturn(mockUserPersisted);

        boolean result = userService.updateUser(mockUserUpdated);

        assertTrue(result);

    }

    @Test
    public void shouldUpdateSuccessfullyWhenUpdatingEverythingButEmail(){

        User mockUserPersisted = new User(1,"test1", "password", "test1@me.com", "test", "one", UserRole.USER);
        User mockUserUpdated = new User(1,"update", "Update", "test1@me.com", "update", "update", UserRole.USER);

        when(userRepository.update(mockUserUpdated)).thenReturn(true);
        when(userRepository.getByUsername(mockUserUpdated.getUsername())).thenThrow(NoResultException.class);
        when(userRepository.getByEmail(mockUserUpdated.getEmail())).thenReturn(mockUserPersisted);
        when(userRepository.findById(mockUserUpdated.getUser_id())).thenReturn(mockUserPersisted);

        boolean result = userService.updateUser(mockUserUpdated);

        assertTrue(result);

    }

}
