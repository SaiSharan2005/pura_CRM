// package com.crm.springbootjwtimplementation.RepositoryTests;
// import com.crm.springbootjwtimplementation.controller.AuthController;
// import com.crm.springbootjwtimplementation.controller.SalesmanDetailsController;
// import com.crm.springbootjwtimplementation.domain.SalesmanDetails;
// import com.crm.springbootjwtimplementation.domain.User;
// import com.crm.springbootjwtimplementation.domain.dto.SalesmanDetailsDTO;
// import com.crm.springbootjwtimplementation.domain.dto.UserLoginDto;
// import com.crm.springbootjwtimplementation.domain.dto.UserRegisterDto;
// import com.crm.springbootjwtimplementation.repository.UserRepository;
// import com.crm.springbootjwtimplementation.service.AuthService;
// import com.crm.springbootjwtimplementation.service.SalesmanDetailsService;
// import com.crm.springbootjwtimplementation.util.Security.AccessToken;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.ResultActions;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestBody;

// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @ExtendWith(MockitoExtension.class)
// @SpringBootTest
// @AutoConfigureMockMvc
// public class UserAndSalesmanIntegrationTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Mock
//     private AuthService authService;

//     @Mock
//     private SalesmanDetailsService salesmanDetailsService;

//     @InjectMocks
//     private AuthController authController;

//     @InjectMocks
//     private SalesmanDetailsController salesmanDetailsController;

//     private UserRegisterDto userRegisterDto;
//     private UserLoginDto userLoginDto;
//     private SalesmanDetailsDTO salesmanDTO;
//     private SalesmanDetails salesmanDetails;
//     private AccessToken accessToken;

//     @BeforeEach
//     public void setUp() {
//         // Initialize a user registration DTO
//         userRegisterDto = new UserRegisterDto();
//         userRegisterDto.setUsername("testuser");
//         userRegisterDto.setEmail("testuser@example.com");
//         userRegisterDto.setPassword("password123");

//         // Initialize a user login DTO
//         userLoginDto = new UserLoginDto();
//         userLoginDto.setUsername("testuser");
//         userLoginDto.setPassword("password123");

//         // Initialize the access token
//         accessToken = new AccessToken("testAccessToken");

//         // Initialize SalesmanDetailsDTO
//         salesmanDTO = new SalesmanDetailsDTO();
//         salesmanDTO.setPhoneNumber("123-456-7890");
//         salesmanDTO.setAddress("123 Main St");
//         salesmanDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
//         salesmanDTO.setRegionAssigned("East Coast");
//         salesmanDTO.setTotalSales(100);
//         salesmanDTO.setHireDate(LocalDate.of(2020, 1, 1));
//         salesmanDTO.setStatus("Active");
//         salesmanDTO.setNotes("Top performer");

//         // Initialize SalesmanDetails
//         salesmanDetails = new SalesmanDetails();
//         salesmanDetails.setUserId(1L);
//         salesmanDetails.setPhoneNumber(salesmanDTO.getPhoneNumber());
//         salesmanDetails.setAddress(salesmanDTO.getAddress());
//         salesmanDetails.setDateOfBirth(salesmanDTO.getDateOfBirth());
//         salesmanDetails.setRegionAssigned(salesmanDTO.getRegionAssigned());
//         salesmanDetails.setTotalSales(salesmanDTO.getTotalSales());
//         salesmanDetails.setHireDate(salesmanDTO.getHireDate());
//         salesmanDetails.setStatus(salesmanDTO.getStatus());
//         salesmanDetails.setNotes(salesmanDTO.getNotes());
//     }

//     @Test
//     public void testUserRegistration() throws Exception {
//         when(authService.register(any(UserRegisterDto.class))).thenReturn(accessToken);

//         mockMvc.perform(MockMvcRequestBuilders
//                 .post("/api/auth/register")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content("{ \"username\": \"testuser\", \"email\": \"testuser@example.com\", \"password\": \"password123\" }"))
//                 .andExpect(status().isOk());

//         verify(authService, times(1)).register(userRegisterDto);
//     }

//     @Test
//     public void testUserLogin() throws Exception {
//         when(authService.login(any(UserLoginDto.class))).thenReturn(accessToken);

//         mockMvc.perform(MockMvcRequestBuilders
//                 .post("/api/auth/login")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content("{ \"username\": \"testuser\", \"password\": \"password123\" }"))
//                 .andExpect(status().isOk());

//         verify(authService, times(1)).login(userLoginDto);
//     }

//     // @Test
//     // public void testCreateSalesman() throws Exception {
//     //     when(salesmanDetailsService.createSalesman(any(SalesmanDetailsDTO.class))).thenReturn(salesmanDetails);

//     //     mockMvc.perform(MockMvcRequestBuilders
//     //             .post("/api/salesmen")
//     //             .contentType(MediaType.APPLICATION_JSON)
//     //             .content("{ \"phoneNumber\": \"123-456-7890\", \"address\": \"123 Main St\", \"dateOfBirth\": \"1990-01-01\", \"regionAssigned\": \"East Coast\", \"totalSales\": 100, \"hireDate\": \"2020-01-01\", \"status\": \"Active\", \"notes\": \"Top performer\" }"))
//     //             .andExpect(status().isOk());

//     //     verify(salesmanDetailsService, times(1)).createSalesman(salesmanDTO);
//     // }

//     // @Test
//     // public void testGetSalesmanById() throws Exception {
//     //     when(salesmanDetailsService.getSalesmanById(anyLong())).thenReturn(salesmanDetails);

//     //     mockMvc.perform(MockMvcRequestBuilders
//     //             .get("/api/salesmen/{id}", 1L)
//     //             .contentType(MediaType.APPLICATION_JSON))
//     //             .andExpect(status().isOk());

//     //     verify(salesmanDetailsService, times(1)).getSalesmanById(1L);
//     // }

//     // @Test
//     // public void testDeleteSalesman() throws Exception {
//     //     doNothing().when(salesmanDetailsService).deleteSalesman(1L);

//     //     mockMvc.perform(MockMvcRequestBuilders
//     //             .delete("/api/salesmen/{id}", 1L)
//     //             .contentType(MediaType.APPLICATION_JSON))
//     //             .andExpect(status().isNoContent());

//     //     verify(salesmanDetailsService, times(1)).deleteSalesman(1L);
//     // }
// }
