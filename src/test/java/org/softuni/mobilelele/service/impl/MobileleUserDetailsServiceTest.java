package org.softuni.mobilelele.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.softuni.mobilelele.model.entity.User;
import org.softuni.mobilelele.model.entity.UserRole;
import org.softuni.mobilelele.model.enums.RolesEnum;
import org.softuni.mobilelele.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MobileleUserDetailsServiceTest {

    private MobileleUserDetailsServiceImpl serviceTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    public void setUp() {
        this.serviceTest = new MobileleUserDetailsServiceImpl(this.mockUserRepository);
    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> this.serviceTest.loadUserByUsername("example@test.com"));
    }

    @Test
    void testUserFound() {
        // Arrange
        User testUser = createTestUser();

        when(this.mockUserRepository.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.of(testUser));

        // Act
        UserDetails userDetails = this.serviceTest.loadUserByUsername(testUser.getEmail());

        // Assert
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(testUser.getEmail(), userDetails.getUsername());
        Assertions.assertEquals(testUser.getPassword(), userDetails.getPassword());

        Assertions.assertEquals(2, userDetails.getAuthorities().size());

        Assertions.assertTrue(
                containsAuthority(userDetails, "ROLE_" + RolesEnum.ADMIN),
                "The user is not admin!");

        Assertions.assertTrue(
                containsAuthority(userDetails, "ROLE_" + RolesEnum.USER),
                "The user is not user!");

    }

    private boolean containsAuthority(UserDetails userDetails, String authority) {
        return userDetails
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(authority));
    }


//    @Test
//    void testMock() {
//
//        User userEntity = new User()
//                .setFirstName("Anna");
//
//        when(this.mockUserRepository.findByEmail("voltron773@gmail.com"))
//                .thenReturn(Optional.of(userEntity));
//
//        Optional<User> userOpt = this.mockUserRepository.findByEmail("voltron773@gmail.com");
//
//        User user = userOpt.get();
//
//        Assertions.assertEquals("Anna", user.getFirstName());
//    }

    private static User createTestUser() {
        return new User()
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmail("email@test.com")
                .setPassword("password")
                .setIsActive(false)
                .setRoles(List.of(
                        new UserRole().setRole(RolesEnum.ADMIN),
                        new UserRole().setRole(RolesEnum.USER)
                ));
    }

}
