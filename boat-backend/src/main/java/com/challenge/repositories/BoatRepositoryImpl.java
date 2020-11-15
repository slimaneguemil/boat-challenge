package com.challenge.repositories;

import com.challenge.models.Boat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BoatRepositoryImpl implements BoatRepository {

    private static final Logger logger = LogManager.getLogger(BoatRepositoryImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public BoatRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;

        // Build a SimpleJdbcInsert object from the specified data source
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("boat")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Boat> findById(Long id) {
        try {
            Boat boat = jdbcTemplate.queryForObject("SELECT * FROM boat WHERE id = ?",
                    new Object[]{id},
                    (rs, rowNum) -> {
                        Boat p = new Boat();
                        p.setId(rs.getLong("id"));
                        p.setName(rs.getString("name"));
                        p.setDescription(rs.getString("description"));
                        p.setVersion(rs.getInt("version"));
                        return p;
                    });
            return Optional.of(boat);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Boat> findAll() {
        return jdbcTemplate.query("SELECT * FROM boat",
                (rs, rowNumber) -> {
                    Boat boat = new Boat();
                    boat.setId(rs.getLong("id"));
                    boat.setName(rs.getString("name"));
                    boat.setDescription(rs.getString("description"));
                    boat.setVersion(rs.getInt("version"));
                    return boat;
                });
    }

    @Override
    public boolean update(Boat boat) {
        return jdbcTemplate.update("UPDATE boat SET name = ?, description = ?, version = ?  WHERE id = ?",
                boat.getName(),
                boat.getDescription(),
                boat.getVersion(),
                boat.getId()) == 1;
    }

    @Override
    public Boat save(Boat boat) {
        // Build the boat parameters we want to save
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("name", boat.getName());
        parameters.put("description", boat.getDescription());
        parameters.put("version", boat.getVersion());
        // Execute the query and get the generated key
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);

        logger.info("Inserting boat into database, generated key is: {}", newId);

        // Update the boat's ID with the new key
        boat.setId( newId.longValue());

        // Return the complete boat
        return boat;
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update("DELETE FROM boat WHERE id = ?", id) == 1;
    }
}
