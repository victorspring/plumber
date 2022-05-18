package uz.paynet.plumber.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.paynet.plumber.entity.House;
import uz.paynet.plumber.entity.Plumber;
import uz.paynet.plumber.exception.HouseLimitExceedException;
import uz.paynet.plumber.repository.HouseRepository;
import uz.paynet.plumber.repository.PlumberRepository;
import uz.paynet.plumber.service.HouseService;
import uz.paynet.plumber.service.HouseServiceImpl;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HouseServiceTest {

    @Mock
    private HouseRepository houseRepository;
    @Mock
    private PlumberRepository plumberRepository;

    private HouseService houseService;

    @BeforeEach
    void setUp() {
        houseService = new HouseServiceImpl(5, houseRepository, plumberRepository);
    }

    @Test
    void setPlumber_noHouses() {
        House house = new House();
        Plumber plumber = new Plumber();
        plumber.setHouses(Collections.emptyList());

        when(houseRepository.findById(1L)).thenReturn(Optional.of(house));
        when(plumberRepository.findById(1L)).thenReturn(Optional.of(plumber));

        houseService.setPlumber(1L, 1L);

        assertEquals(plumber, house.getPlumber());
    }


    @Test
    void setPlumber_limitNotExceeded() {
        House house = new House();
        Plumber plumber = new Plumber();
        plumber.setHouses(Stream.generate(House::new).limit(4).toList());

        when(houseRepository.findById(1L)).thenReturn(Optional.of(house));
        when(plumberRepository.findById(1L)).thenReturn(Optional.of(plumber));

        houseService.setPlumber(1L, 1L);

        assertEquals(plumber, house.getPlumber());
    }

    @Test
    void setPlumber_limitExceeded_exceptionThrown() {
        House house = new House();
        Plumber plumber = new Plumber();
        plumber.setHouses(Stream.generate(House::new).limit(5).toList());

        when(houseRepository.findById(1L)).thenReturn(Optional.of(house));
        when(plumberRepository.findById(1L)).thenReturn(Optional.of(plumber));

        assertThrows(HouseLimitExceedException.class,
                () -> houseService.setPlumber(1L, 1L));
    }
}
