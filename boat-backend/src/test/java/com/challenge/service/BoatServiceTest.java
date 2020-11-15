package com.challenge.service;

import com.challenge.models.Boat;
import com.challenge.repositories.BoatRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BoatServiceTest {

    /**
     * The service that we want to test.
     */
    @Autowired
    private BoatService service;

    /**
     * A mock version of the ProductRepository for use in our tests.
     */
    @MockBean
    private BoatRepository repository;


    @Test
    @DisplayName("Test findById Success")
    void testFindByIdSuccess() {
        // Setup our mock
        Boat boatProduct = new Boat(1L, "Product Name", "Description Name", 1);
        doReturn(Optional.of(boatProduct)).when(repository).findById(1L);

        // Execute the service call
        Optional<Boat> returnedProduct = service.findById(1L);

        // Assert the response
        Assertions.assertTrue(returnedProduct.isPresent(), "Product was not found");
        Assertions.assertSame(returnedProduct.get(), boatProduct, "Products should be the same");
    }
}
