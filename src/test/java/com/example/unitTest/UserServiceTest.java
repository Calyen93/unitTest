package com.example.unitTest;

import com.example.unitTest.models.User;
import com.example.unitTest.repositories.UserRepository;
import com.example.unitTest.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllUsers() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = userService.getAllUsers();

        assertEquals(1, users.size());
    }

    @Test
    void shouldGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(1L);

        assertEquals("John Doe", foundUser.orElse(new User()).getName());
    }

    @Test
    void shouldCreateUser() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertEquals("John Doe", createdUser.getName());
    }

    @Test
    void shouldUpdateUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        User userDetails = new User();
        userDetails.setName("Jane Doe");
        userDetails.setEmail("jane.doe@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        Optional<User> updatedUser = userService.updateUser(1L, userDetails);

        assertEquals("Jane Doe", updatedUser.orElse(new User()).getName());
    }

    @Test
    void shouldDeleteUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean isDeleted = userService.deleteUser(1L);

        assertEquals(true, isDeleted);
    }

    @Test
    void shouldNotDeleteUserIfNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        boolean isDeleted = userService.deleteUser(1L);

        assertEquals(false, isDeleted);
    }
}
