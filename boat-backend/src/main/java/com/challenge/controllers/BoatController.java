package com.challenge.controllers;

import com.challenge.models.Boat;
import com.challenge.service.BoatService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/boats")
public class BoatController {

    private static final Logger logger = LogManager.getLogger(BoatController.class);

    private final BoatService boatService;

    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }

    /**
     * Returns the boat with the specified ID.
     *
     * @param id    The ID of the boat to retrieve.
     * @return      The boat with the specified ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBoat(@PathVariable Long id) {

        return boatService.findById(id)
                .map(boat -> {
                    try {
                        return ResponseEntity
                                .ok()
                                //.eTag(Integer.toString(boat.getVersion()))
                                .location(new URI("/boat/" + boat.getId()))
                                .body(boat);
                    } catch (URISyntaxException e ) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Returns all boats in the database.
     *
     * @return  All boats in the database.
     */
    @GetMapping
    public Iterable<Boat> getBoats() {
        return boatService.findAll();
    }

    /**
     * Creates a new boat.
     * @param boat   The boat to create.
     * @return          The created boat.
     */
    @PostMapping
    public ResponseEntity<Boat> createBoat(@RequestBody Boat boat) {
        logger.info("Creating new boat with name: {}, quantity: {}",
                boat.getName(), boat.getDescription());

        // Create the new boat
        Boat newBoat = boatService.save(boat);

        try {
            // Build a created response
            return ResponseEntity
                    .created(new URI("/api/v1/boats" ))
                    //.eTag(Integer.toString(newBoat.getVersion()))
                    .body(newBoat);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Updates the fields in the specified boat with the specified ID.
     * @param boat   The boat field values to update.
     * @return          A ResponseEntity that contains the updated boat or one of the following error statuses:
     *                  NOT_FOUND if there is no boat in the database with the specified ID
     *                  CONFLICT if the eTag does not match the version of the boat to update
     *                  INTERNAL_SERVICE_ERROR if there is a problem creating the location URI
     */
    @PutMapping
    public ResponseEntity<?> updateBoat(@RequestBody Boat boat
                                          // @PathVariable Long id,
                                          // @RequestHeader("If-Match") Integer ifMatch
    ) {
        logger.info("Updating boat with id: {}, name: {}, quantity: {}",
                boat.getId(), boat.getName(), boat.getDescription());

        // Get the existing boat
        Optional<Boat> existingBoat = boatService.findById(boat.getId());

        return existingBoat.map(p -> {
            // Compare the etags
            logger.info("Boat with ID: " + boat.getId() + " has a version of " + p.getVersion()
                    + ". Update is for If-Match: " + boat.getVersion());
            if (!p.getVersion().equals(boat.getVersion())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            // Update the boat
            p.setName(boat.getName());
            p.setDescription(boat.getDescription());
            p.setVersion(p.getVersion() + 1);

            logger.info("Updating boat with ID: " + p.getId()
                    + " -> name=" + p.getName()
                    + ", description=" + p.getDescription()
                    + ", version=" + p.getVersion());

            try {
                // Update the boat and return an ok response
                if (boatService.update(p)) {
                    return ResponseEntity.ok()
                            .location(new URI("/api/v1/boats" ))
                            .eTag(Integer.toString(p.getVersion()))
                            .body(p);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (URISyntaxException e) {
                // An error occurred trying to create the location URI, return an error
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes the boat with the specified ID.
     * @param id    The ID of the boat to delete.
     * @return      A ResponseEntity with one of the following status codes:
     *              200 OK if the delete was successful
     *              404 Not Found if a boat with the specified ID is not found
     *              500 Internal Service Error if an error occurs during deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoat(@PathVariable Long id) {

        logger.info("Deleting boat with ID {}", id);

        // Get the existing boat
        Optional<Boat> existingBoat = boatService.findById(id);

        return existingBoat.map(p -> {
            if (boatService.delete(p.getId())) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }
}
