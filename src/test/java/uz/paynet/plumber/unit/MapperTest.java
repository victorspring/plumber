package uz.paynet.plumber.unit;


import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import uz.paynet.plumber.MapperConfig;
import uz.paynet.plumber.dto.HouseAddressDto;
import uz.paynet.plumber.dto.HouseDto;
import uz.paynet.plumber.dto.PlumberDto;
import uz.paynet.plumber.entity.House;
import uz.paynet.plumber.entity.HouseAddress;
import uz.paynet.plumber.entity.Plumber;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ContextConfiguration(classes = MapperConfig.class)
public class MapperTest {

    @Autowired
    private ModelMapper modelMapper;

    @Test
    void plumber_toDto() {
        HouseAddress address1 = new HouseAddress("Pushkina", "1", "999");
        House house1 = new House();
        house1.setId(1L);
        house1.setAddress(address1);

        HouseAddress address2 = new HouseAddress("Kolotushkina", "2", "777");
        House house2 = new House();
        house2.setId(2L);
        house2.setAddress(address2);

        Plumber plumber = new Plumber();
        plumber.setId(1L);
        plumber.setFirstName("Ivan");
        plumber.setLastName("Ivanov");
        plumber.setMiddleName("Ivanich");
        plumber.setHouses(List.of(house1, house2));

        PlumberDto plumberDto = modelMapper.map(plumber, PlumberDto.class);

        assertEquals(plumber.getId(), plumberDto.getId());
        assertEquals(plumber.getFirstName(), plumberDto.getFirstName());
        assertEquals(plumber.getMiddleName(), plumberDto.getMiddleName());
        assertEquals(plumber.getLastName(), plumberDto.getLastName());
        assertEquals(plumber.getHouses().size(), plumberDto.getHouses().size());

        assertEquals(house1.getId(), plumberDto.getHouses().get(0).getId());
        assertEquals(house2.getId(), plumberDto.getHouses().get(1).getId());

        HouseAddressDto houseAddressDto1 = plumberDto.getHouses().get(0).getAddress();
        HouseAddressDto houseAddressDto2 = plumberDto.getHouses().get(1).getAddress();

        assertEquals(address1.getBuilding(), houseAddressDto1.getBuilding());
        assertEquals(address1.getStreet(), houseAddressDto1.getStreet());
        assertEquals(address1.getZipCode(), houseAddressDto1.getZipCode());

        assertEquals(address2.getBuilding(), houseAddressDto2.getBuilding());
        assertEquals(address2.getStreet(), houseAddressDto2.getStreet());
        assertEquals(address2.getZipCode(), houseAddressDto2.getZipCode());

    }

    @Test
    void plumber_toEntity() {
        PlumberDto plumberDto = new PlumberDto();
        plumberDto.setId(1L);
        plumberDto.setFirstName("Ivan");
        plumberDto.setLastName("Ivanov");
        plumberDto.setMiddleName("Ivanich");
        plumberDto.setHouses(List.of(new HouseDto()));

        Plumber plumber = modelMapper.map(plumberDto, Plumber.class);

        assertEquals(plumberDto.getId(), plumber.getId());
        assertEquals(plumberDto.getFirstName(), plumber.getFirstName());
        assertEquals(plumberDto.getMiddleName(), plumber.getMiddleName());
        assertEquals(plumberDto.getLastName(), plumber.getLastName());
        assertNull(plumber.getHouses());
    }

    @Test
    void house_toDto() {
        Plumber plumber = new Plumber();
        plumber.setId(1L);
        plumber.setFirstName("Ivan");
        plumber.setLastName("Ivanov");
        plumber.setMiddleName("Ivanich");

        HouseAddress address = new HouseAddress("Pushkina", "1", "999");
        House house = new House();
        house.setId(1L);
        house.setAddress(address);
        house.setPlumber(plumber);

        HouseDto houseDto = modelMapper.map(house, HouseDto.class);

        assertEquals (house.getId(), houseDto.getId());
        assertEquals(house.getAddress().getStreet(), houseDto.getAddress().getStreet());
        assertEquals(house.getAddress().getBuilding(), houseDto.getAddress().getBuilding());
        assertEquals(house.getAddress().getZipCode(), houseDto.getAddress().getZipCode());

        assertEquals(house.getPlumber().getId(), houseDto.getPlumber().getId());
        assertEquals(house.getPlumber().getFirstName(), houseDto.getPlumber().getFirstName());
        assertEquals(house.getPlumber().getMiddleName(), houseDto.getPlumber().getMiddleName());
        assertEquals(house.getPlumber().getLastName(), houseDto.getPlumber().getLastName());
    }

    @Test
    void house_toEntity() {
        PlumberDto plumberDto = new PlumberDto();
        plumberDto.setId(1L);
        plumberDto.setFirstName("Ivan");
        plumberDto.setLastName("Ivanov");
        plumberDto.setMiddleName("Ivanich");

        HouseAddressDto addressDto = new HouseAddressDto("Pushkina", "1", "999");
        HouseDto houseDto = new HouseDto();
        houseDto.setId(1L);
        houseDto.setAddress(addressDto);
        houseDto.setPlumber(plumberDto);

        House house = modelMapper.map(houseDto, House.class);

        assertEquals(houseDto.getId(), house.getId());
        assertEquals(houseDto.getAddress().getStreet(), house.getAddress().getStreet());
        assertEquals(houseDto.getAddress().getBuilding(), house.getAddress().getBuilding());
        assertEquals(houseDto.getAddress().getZipCode(), house.getAddress().getZipCode());
        assertNull(house.getPlumber());

    }
}
