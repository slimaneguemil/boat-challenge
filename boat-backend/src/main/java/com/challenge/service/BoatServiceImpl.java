package com.challenge.service;

import com.challenge.models.Boat;
import com.challenge.repositories.BoatRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoatServiceImpl implements BoatService {

    private static final Logger logger = LogManager.getLogger(BoatServiceImpl.class);

    private final BoatRepository boatRepository;

    public BoatServiceImpl(BoatRepository BoatRepository) {
        this.boatRepository = BoatRepository;
    }

    @Override
    public Optional<Boat> findById(Long id) {
        logger.info("Find Boat with id: {}", id);
        return boatRepository.findById(id);
    }

    @Override
    public List<Boat> findAll() {
        logger.info("Find all Boats");
        return boatRepository.findAll();
    }

    @Override
    public boolean update(Boat boat) {
        logger.info("Update Boat: {}", boat);
        return boatRepository.update(boat);
    }

    @Override
    public Boat save(Boat boat) {
        // Set the boatt version to 1 as we're adding a new boat to the database
        boat.setVersion(1);

        logger.info("Save Boat to the database: {}", boat);
        return boatRepository.save(boat);
    }

    @Override
    public  boolean delete(Long id) {
        logger.info("Delete Boat with id: {}", id);
        return boatRepository.delete(id);
    }
}
