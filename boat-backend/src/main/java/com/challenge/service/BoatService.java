package com.challenge.service;

import com.challenge.models.Boat;

import java.util.List;
import java.util.Optional;

public interface BoatService {
    /**
     * Returns the boat with the specified id.
     *
     * @param id        ID of the boat to retrieve.
     * @return          The requested boat if found.
     */
    Optional<Boat> findById(Long id);

    /**
     * Returns all boats in the database.
     *
     * @return          All boats in the database.
     */
    List<Boat> findAll();

    /**
     * Updates the specified boat, identified by its id.
     *
     * @param boat   The boat to update.
     * @return          True if the update succeeded, otherwise false.
     */
    boolean update(Boat boat);

    /**
     * Saves the specified boat to the database.
     *
     * @param boat   The boat to save to the database.
     * @return          The saved boat.
     */
    Boat save(Boat boat);

    /**
     * Deletes the boat with the specified id.
     * @param id        The id of the boat to delete.
     * @return          True if the operation was successful.
     */
    boolean delete(Long id);
}
