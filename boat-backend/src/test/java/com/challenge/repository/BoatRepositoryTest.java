package com.challenge.repository;

import com.challenge.models.Boat;
import com.challenge.repositories.BoatRepository;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@ExtendWith({DBUnitExtension.class, SpringExtension.class})
@SpringBootTest
@ActiveProfiles("test")
public class BoatRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BoatRepository repository;

    public ConnectionHolder getConnectionHolder() {
        // Return a function that retrieves a connection from our data source
        return () -> dataSource.getConnection();
    }

    @Test
    @DataSet("boats.yml")
    @DisplayName(" findAll- Found")
    void testFindAll() {
        List<Boat> products = repository.findAll();
        Assertions.assertEquals(2, products.size(), "We should have 2 products in our database");
    }

    @Test
    @DataSet(value = "boats.yml")
    @DisplayName(" save- Found")
    void testSave() {
        // Create a new product and save it to the database
        Boat boat = new Boat("Boat 5", "Description 5");
        boat.setVersion(1);
        Boat savedBoat = repository.save(boat);

        // Validate the saved product
        Assertions.assertEquals("Boat 5", savedBoat.getName());
        Assertions.assertEquals("Description 5", savedBoat.getDescription());

        // Validate that we can get it back out of the database
        Optional<Boat> loadedBoat = repository.findById(savedBoat.getId());
        Assertions.assertTrue(loadedBoat.isPresent(), "Could not reload product from the database");
        Assertions.assertEquals("Boat 5", loadedBoat.get().getName(), "Boat name does not match");
        Assertions.assertEquals("Description 5", loadedBoat.get().getDescription(), "Boat quantity does not match");
        Assertions.assertEquals(1, loadedBoat.get().getVersion().intValue(), "Boat version is incorrect");
    }

    @Test
    @DataSet(value = "boats.yml")
    void testUpdateSuccess() {
        // Update product 1's name, quantity, and version
        Boat boat = new Boat(1L, "Name 3", "Description 3", 5);
        boolean result  = repository.update(boat);

        // Validate that our boat is returned by update()
        Assertions.assertTrue(result, "The boat should have been updated");

        // Retrieve boat 1 from the database and validate its fields
        Optional<Boat> loadedBoat = repository.findById(1L);
        Assertions.assertTrue(loadedBoat.isPresent(), "Updated boat should exist in the database");
        Assertions.assertEquals("Name 3", loadedBoat.get().getName(), "The boat name does not match");
        Assertions.assertEquals("Description 3", loadedBoat.get().getDescription(), "The quantity should now be 100");
        Assertions.assertEquals(5, loadedBoat.get().getVersion().intValue(), "The version should now be 5");
    }

}
