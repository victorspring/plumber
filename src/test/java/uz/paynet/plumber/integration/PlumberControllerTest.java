package uz.paynet.plumber.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uz.paynet.plumber.controller.PlumberController;
import uz.paynet.plumber.dto.PlumberDto;
import uz.paynet.plumber.entity.House;
import uz.paynet.plumber.entity.Plumber;
import uz.paynet.plumber.repository.HouseRepository;
import uz.paynet.plumber.repository.PlumberRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Sql(value = {"/liquibase/schema.sql", "/liquibase/data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PlumberControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private PlumberRepository plumberRepository;

    @Test
    void findById() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get(PlumberController.ENDPOINT + "/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.middleName").value("Ivanich"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.houses[0].id").value(1))
                .andExpect(jsonPath("$.houses[0].address.street").value("Pushkina"))
                .andExpect(jsonPath("$.houses[0].address.building").value("1"))
                .andExpect(jsonPath("$.houses[0].address.zipCode").value("999"));
    }

    @Test
    void create() throws Exception {
        String body = """
                {
                    "firstName": "Sergey",
                    "middleName": "Sergeich",
                    "lastName": "Sergeev"
                }
                """;

        String response = mvc.perform(MockMvcRequestBuilders
                        .post(PlumberController.ENDPOINT)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PlumberDto plumberDto = objectMapper.readValue(response, PlumberDto.class);
        Plumber plumber = plumberRepository.findById(plumberDto.getId()).orElseThrow();

        assertEquals(3, plumberRepository.count());
        assertEquals("Sergey", plumber.getFirstName());
        assertEquals("Sergeich", plumber.getMiddleName());
        assertEquals("Sergeev", plumber.getLastName());
    }

    @Test
    void setPlumber() throws Exception {
        String response = mvc.perform(MockMvcRequestBuilders
                        .put(PlumberController.ENDPOINT + "/1/houses/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PlumberDto plumberDto = objectMapper.readValue(response, PlumberDto.class);
        Plumber plumber = plumberRepository.findById(plumberDto.getId()).orElseThrow();
        House house = houseRepository.findById(plumberDto.getHouses().get(0).getId()).orElseThrow();

        assertEquals(2, plumberRepository.count());
        assertEquals(3, houseRepository.count());
        assertEquals(plumber.getId(), house.getPlumber().getId());
    }

    @Test
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete(PlumberController.ENDPOINT + "/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertEquals(1, plumberRepository.count());
    }

}
