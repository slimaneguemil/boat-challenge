package com.challenge.repositories;

import com.challenge.models.Boat;

import java.util.List;
import java.util.Optional;

/**
 * Defines the persistence methods for a BoatRepository.
 */
public interface BoatRepository {
    /**
     * Returns the produce with the specified id.
     *
     * @param id        ID of the Boat to retrieve.
     * @return          The requested boat if found.
     */
    Optional<Boat> findById(Long id);

    /**
     * Returns all Boats in the database.
     *
     * @return          All Boats in the database.
     */
    List<Boat> findAll();

    /**
     * Updates the specified Boat, identified by its id.
     *
     * @param Boat   The Boat to update.
     * @return          True if the update succeeded, otherwise false.
     */
    boolean update(Boat boat);

    /**
     * Saves the specified Boat to the database.
     *
     * @param Boat   The Boat to save to the database.
     * @return          The saved Boat.
     */
    Boat save(Boat boat);

    /**
     * Deletes the boat with the specified id.
     * @param id        The id of the boat to delete.
     * @return          True if the operation was successful.
     */
    boolean delete(Long id);
}
