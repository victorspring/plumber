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
import uz.paynet.plumber.controller.HouseController;
import uz.paynet.plumber.dto.HouseDto;
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
public class HouseControllerTest {

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
                        .get(HouseController.ENDPOINT + "/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.address.street").value("Pushkina"))
                .andExpect(jsonPath("$.address.building").value("1"))
                .andExpect(jsonPath("$.address.zipCode").value("999"))
                .andExpect(jsonPath("$.plumber.id").value(1))
                .andExpect(jsonPath("$.plumber.firstName").value("Ivan"))
                .andExpect(jsonPath("$.plumber.middleName").value("Ivanich"))
                .andExpect(jsonPath("$.plumber.lastName").value("Ivanov"));
    }

    @Test
    void create() throws Exception {
        String body = """
                {
                    "address": {
                            "street": "Kolotushkina",
                            "building": "1",
                            "zipCode": "777"
                    }
                }
                """;

        String response = mvc.perform(MockMvcRequestBuilders
                        .post(HouseController.ENDPOINT)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        HouseDto houseDto = objectMapper.readValue(response, HouseDto.class);
        House house = houseRepository.findById(houseDto.getId()).orElseThrow();

        assertEquals(4, houseRepository.count());
        assertEquals("Kolotushkina", house.getAddress().getStreet());
        assertEquals("1", house.getAddress().getBuilding());
        assertEquals("777", house.getAddress().getZipCode());
    }

    @Test
    void setPlumber() throws Exception {
        String response = mvc.perform(MockMvcRequestBuilders
                        .put(HouseController.ENDPOINT + "/2/plumber/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        HouseDto houseDto = objectMapper.readValue(response, HouseDto.class);
        House house = houseRepository.findById(houseDto.getId()).orElseThrow();
        Plumber plumber = plumberRepository.findById(1L).orElseThrow();


        assertEquals(2, plumberRepository.count());
        assertEquals(3, houseRepository.count());
        assertEquals(plumber.getId(), house.getPlumber().getId());
    }

    @Test
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete(HouseController.ENDPOINT + "/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertEquals(2, houseRepository.count());
    }

}
