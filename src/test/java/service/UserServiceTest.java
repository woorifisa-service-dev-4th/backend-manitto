package service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testSignup() {
        // given
        String email = "test@example.com";
        String password = "password123";
        String name = "Test User";
        when(userRepository.findByEmail(email)).thenReturn(null);

        // when
        boolean result = userService.signup(email, password, name);

        // then
        assertTrue(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testSignupWithExistingEmail() {
        // given
        String email = "test@example.com";
        String password = "password123";
        String name = "Test User";
        User existingUser = new User(email, name, "hashedPassword", "salt");
        when(userRepository.findByEmail(email)).thenReturn(existingUser);

        // when
        boolean result = userService.signup(email, password, name);

        // then
        assertFalse(result);
        verify(userRepository, never()).save(any(User.class));
    }
}