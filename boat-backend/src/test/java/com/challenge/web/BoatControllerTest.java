package com.challenge.web;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.challenge.models.Boat;
import com.challenge.service.BoatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BoatControllerTest {
    @MockBean
    private BoatService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/v1/boats/1 - Found")
    void testGetBoatByIdFound() throws Exception {
        // Setup our mocked service
        Boat mockBoat= new Boat(1L, "Boat Name", "Description Name", 1);
        doReturn(Optional.of(mockBoat)).when(service).findById(1L);

        // Execute the GET request
        mockMvc.perform(get("/api/v1/boats/{id}", 1))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                //.andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
                //.andExpect(header().string(HttpHeaders.LOCATION, "/api/v1/boats/1"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Boat Name")))
                .andExpect(jsonPath("$.description", is("Description Name")))
                .andExpect(jsonPath("$.version", is(1)));
    }

    @Test
    @DisplayName("GET /api/v1/boats/1 - Not Found")
    void testGetBoatByIdNotFound() throws Exception {
        // Setup our mocked service
        doReturn(Optional.empty()).when(service).findById(1L);

        // Execute the GET request
        mockMvc.perform(get("/api/v1/boats/{id}", 1))

                // Validate that we get a 404 Not Found response
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/v1/boats - Success")
    void testCreateBoat() throws Exception {
        // Setup mocked service
        Boat postBoat= new Boat("Boat Name", "Description Name");
        Boat mockBoat= new Boat(1L, "Boat Name", "Description Name", 1);
        doReturn(mockBoat).when(service).save(any());

        mockMvc.perform(post("/api/v1/boats")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(postBoat)))

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
               // .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/v1/boats"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Boat Name")))
                .andExpect(jsonPath("$.description", is("Description Name")))
                .andExpect(jsonPath("$.version", is(1)));
    }

    @Test
    @DisplayName("PUT /boat/1 - Success")
    void testBoatPutSuccess() throws Exception {
        // Setup mocked service
        Boat putBoat= new Boat(1L,"Boat Name", "Description Name",1);
        Boat mockBoat= new Boat(1L, "Boat Name", "Description Name", 1);
        doReturn(Optional.of(mockBoat)).when(service).findById(1L);
        doReturn(true).when(service).update(any());

        mockMvc.perform(put("/api/v1/boats")
                .contentType(MediaType.APPLICATION_JSON)
                //.header(HttpHeaders.IF_MATCH, 1)
                .content(asJsonString(putBoat)))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))

                // Validate the headers
                .andExpect(header().string(HttpHeaders.ETAG, "\"2\""))
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/v1/boats"))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Boat Name")))
                .andExpect(jsonPath("$.description", is("Description Name")))
                .andExpect(jsonPath("$.version", is(2)));
    }

    @Test
    @DisplayName("PUT /api/v1/boats - Version Mismatch")
    void testBoatPutVersionMismatch() throws Exception {
        // Setup mocked service
        Boat putBoat= new Boat(1L, "Boat Name", "Description Name",1);
        Boat mockBoat= new Boat(1L, "Boat Name", "Description Name", 2);
        doReturn(Optional.of(mockBoat)).when(service).findById(1L);
        doReturn(true).when(service).update(any());

        mockMvc.perform(put("/api/v1/boats")
                .contentType(MediaType.APPLICATION_JSON)
                //.header(HttpHeaders.IF_MATCH, 1)
                .content(asJsonString(putBoat)))

                // Validate the response code and content type
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("PUT /api/v1/boats - Not Found")
    void testBoatPutNotFound() throws Exception {
        // Setup mocked service
        Boat putBoat= new Boat("Boat Name", "Description Name");
        doReturn(Optional.empty()).when(service).findById(1L);

        mockMvc.perform(put("/api/v1/boats", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.IF_MATCH, 1)
                .content(asJsonString(putBoat)))

                // Validate the response code and content type
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/boats/1 - Success")
    void testBoatDeleteSuccess() throws Exception {
        // Setup mocked Boat
        Boat mockBoat= new Boat(1L, "Boat Name", "Description Name", 1);

        // Setup the mocked service
        doReturn(Optional.of(mockBoat)).when(service).findById(1L);
        doReturn(true).when(service).delete(1L);

        // Execute our DELETE request
        mockMvc.perform(delete("/api/v1/boats/1", 1))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/v1/boats/1 - Not Found")
    void testBoatDeleteNotFound() throws Exception {
        // Setup the mocked service
        doReturn(Optional.empty()).when(service).findById(1L);

        // Execute our DELETE request
        mockMvc.perform(delete("/api/v1/boats/1", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v1/boats/1 - Failure")
    void testBoatDeleteFailure() throws Exception {
        // Setup mocked Boat
        Boat mockBoat= new Boat(1L, "Boat Name", "Description Name", 1);

        // Setup the mocked service
        doReturn(Optional.of(mockBoat)).when(service).findById(1L);
        doReturn(false).when(service).delete(1L);

        // Execute our DELETE request
        mockMvc.perform(delete("/api/v1/boats/1", 1))
                .andExpect(status().isInternalServerError());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
