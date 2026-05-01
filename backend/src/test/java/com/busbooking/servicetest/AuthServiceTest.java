package com.busbooking.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.busbooking.dto.SignUpRequestDto;
import com.busbooking.entity.Address;
import com.busbooking.entity.Customer;
import com.busbooking.entity.User;
import com.busbooking.exception.DuplicateResourceException;
import com.busbooking.repository.AddressRepo;
import com.busbooking.repository.CustomerRepo;
import com.busbooking.repository.UserRepo;
import com.busbooking.service.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private AddressRepo addressRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private SignUpRequestDto dto;

    @BeforeEach
    void setup() {
        dto = new SignUpRequestDto();
        dto.setUserName("mukul");
        dto.setEmail("mukul@gmail.com");
        dto.setPassword("password123");
        dto.setName("Mukul Sharma");
        dto.setPhoneNo("9999999999");
        dto.setAddress("Street 1");
        dto.setCity("Delhi");
        dto.setState("Delhi");
        dto.setZipCode("110001");
    }


    //   SUCCESS CASE
   
    @Test
    void testSignup_Success() {

        when(userRepo.existsByUsername(dto.getUserName())).thenReturn(false);
        when(userRepo.existsByEmail(dto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPass");

        when(addressRepo.save(any(Address.class))).thenReturn(new Address());
        when(customerRepo.save(any(Customer.class))).thenReturn(new Customer());
        when(userRepo.save(any(User.class))).thenReturn(new User());

        String result = authService.signup(dto);

        assertEquals("User registered successfully with username mukul", result);

        verify(userRepo).save(any(User.class));
        verify(customerRepo).save(any(Customer.class));
        verify(addressRepo).save(any(Address.class));
    }

  
    //   DUPLICATE USERNAME
  
    @Test
    void testSignup_DuplicateUsername() {

        when(userRepo.existsByUsername(dto.getUserName())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> {
            authService.signup(dto);
        });

        verify(userRepo, never()).save(any());
    }

   
    //  3. DUPLICATE EMAIL
   
    @Test
    void testSignup_DuplicateEmail() {

        when(userRepo.existsByUsername(dto.getUserName())).thenReturn(false);
        when(userRepo.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> {
            authService.signup(dto);
        });

        verify(userRepo, never()).save(any());
    }

   
    //  NO ADDRESS PROVIDED
    
    @Test
    void testSignup_NoAddressProvided() {

        dto.setAddress(null);
        dto.setCity(null);

        when(userRepo.existsByUsername(dto.getUserName())).thenReturn(false);
        when(userRepo.existsByEmail(dto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPass");

        when(customerRepo.save(any(Customer.class))).thenReturn(new Customer());
        when(userRepo.save(any(User.class))).thenReturn(new User());

        String result = authService.signup(dto);

        assertEquals("User registered successfully with username mukul", result);

        // Address should NOT be saved
        verify(addressRepo, never()).save(any());
    }
}