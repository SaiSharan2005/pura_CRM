// // package com.crm.springbootjwtimplementation.service.implementation;


// package com.crm.springbootjwtimplementation.RepositoryTests;
// import com.crm.springbootjwtimplementation.domain.SalesmanDetails;
// import com.crm.springbootjwtimplementation.domain.dto.SalesmanDetailsDTO;
// import com.crm.springbootjwtimplementation.exceptions.ValidationException;
// import com.crm.springbootjwtimplementation.repository.SalesmanDetailsRepository;
// import com.crm.springbootjwtimplementation.service.implementation.SalesmanDetailsServiceImpl;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.modelmapper.ModelMapper;

// import java.time.LocalDate;
// import java.util.List;
// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
// class SalesmanDetailsServiceImplTest {

//     @InjectMocks
//     private SalesmanDetailsServiceImpl salesmanDetailsService;

//     @Mock
//     private SalesmanDetailsRepository salesmanDetailsRepository;

//     @Mock
//     private ModelMapper modelMapper;

//     private SalesmanDetails salesmanDetails;
//     private SalesmanDetailsDTO salesmanDetailsDTO;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);

//         salesmanDetails = new SalesmanDetails();
//         salesmanDetails.setUserId(1L);
//         salesmanDetails.setPhoneNumber("1234567890");
//         salesmanDetails.setAddress("123 Test Street");
//         salesmanDetails.setDateOfBirth(LocalDate.of(1990, 1, 1));
//         salesmanDetails.setRegionAssigned("North");
//         salesmanDetails.setTotalSales(100);
//         salesmanDetails.setHireDate(LocalDate.of(2020, 1, 1));
//         salesmanDetails.setStatus("Active");

//         salesmanDetailsDTO = new SalesmanDetailsDTO();
//         salesmanDetailsDTO.setPhoneNumber("1234567890");
//         salesmanDetailsDTO.setAddress("123 Test Street");
//         salesmanDetailsDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
//         salesmanDetailsDTO.setRegionAssigned("North");
//         salesmanDetailsDTO.setTotalSales(100);
//         salesmanDetailsDTO.setHireDate(LocalDate.of(2020, 1, 1));
//         salesmanDetailsDTO.setStatus("Active");
//     }

//     @Test
//     void testCreateSalesmanDetails() {
//         when(modelMapper.map(salesmanDetailsDTO, SalesmanDetails.class)).thenReturn(salesmanDetails);
//         when(salesmanDetailsRepository.save(salesmanDetails)).thenReturn(salesmanDetails);
//         when(modelMapper.map(salesmanDetails, SalesmanDetailsDTO.class)).thenReturn(salesmanDetailsDTO);

//         SalesmanDetailsDTO result = salesmanDetailsService.createSalesmanDetails(salesmanDetailsDTO);

//         assertNotNull(result);
//         assertEquals(salesmanDetailsDTO.getPhoneNumber(), result.getPhoneNumber());
//         verify(salesmanDetailsRepository, times(1)).save(salesmanDetails);
//     }

//     @Test
//     void testGetAllSalesmanDetails() {
//         when(salesmanDetailsRepository.findAll()).thenReturn(List.of(salesmanDetails));
//         when(modelMapper.map(salesmanDetails, SalesmanDetailsDTO.class)).thenReturn(salesmanDetailsDTO);

//         List<SalesmanDetailsDTO> result = salesmanDetailsService.getAllSalesmanDetails();

//         assertNotNull(result);
//         assertEquals(1, result.size());
//         assertEquals(salesmanDetailsDTO.getPhoneNumber(), result.get(0).getPhoneNumber());
//     }

//     @Test
//     void testGetSalesmanDetailsById() {
//         when(salesmanDetailsRepository.findById(1L)).thenReturn(Optional.of(salesmanDetails));
//         when(modelMapper.map(salesmanDetails, SalesmanDetailsDTO.class)).thenReturn(salesmanDetailsDTO);

//         SalesmanDetailsDTO result = salesmanDetailsService.getSalesmanDetailsById(1L);

//         assertNotNull(result);
//         assertEquals(salesmanDetailsDTO.getPhoneNumber(), result.getPhoneNumber());
//     }

//     @Test
//     void testGetSalesmanDetailsByIdNotFound() {
//         when(salesmanDetailsRepository.findById(1L)).thenReturn(Optional.empty());

//         ValidationException exception = assertThrows(ValidationException.class,
//                 () -> salesmanDetailsService.getSalesmanDetailsById(1L));

//         assertEquals("Salesman details not found for ID: 1", exception.getMessage());
//     }

//     @Test
//     void testUpdateSalesmanDetailsById() {
//         when(salesmanDetailsRepository.findById(1L)).thenReturn(Optional.of(salesmanDetails));
//         when(modelMapper.map(salesmanDetailsDTO, SalesmanDetails.class)).thenReturn(salesmanDetails);
//         when(salesmanDetailsRepository.save(salesmanDetails)).thenReturn(salesmanDetails);
//         when(modelMapper.map(salesmanDetails, SalesmanDetailsDTO.class)).thenReturn(salesmanDetailsDTO);

//         SalesmanDetailsDTO result = salesmanDetailsService.updateSalesmanDetailsById(1L, salesmanDetailsDTO);

//         assertNotNull(result);
//         assertEquals(salesmanDetailsDTO.getPhoneNumber(), result.getPhoneNumber());
//     }

//     @Test
//     void testDeleteSalesmanDetailsById() {
//         when(salesmanDetailsRepository.existsById(1L)).thenReturn(true);

//         salesmanDetailsService.deleteSalesmanDetailsById(1L);

//         verify(salesmanDetailsRepository, times(1)).deleteById(1L);
//     }

//     @Test
//     void testDeleteSalesmanDetailsByIdNotFound() {
//         when(salesmanDetailsRepository.existsById(1L)).thenReturn(false);

//         ValidationException exception = assertThrows(ValidationException.class,
//                 () -> salesmanDetailsService.deleteSalesmanDetailsById(1L));

//         assertEquals("Salesman details not found for ID: 1", exception.getMessage());
//     }
// }