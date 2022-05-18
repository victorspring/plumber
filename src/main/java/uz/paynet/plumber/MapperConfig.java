package uz.paynet.plumber;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.paynet.plumber.dto.HouseDto;
import uz.paynet.plumber.dto.PlumberDto;
import uz.paynet.plumber.entity.House;
import uz.paynet.plumber.entity.Plumber;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        PropertyMap<HouseDto, House> skipHouseFields = new PropertyMap<>() {
            protected void configure() {
                skip().setPlumber(null);
            }
        };

        PropertyMap<PlumberDto, Plumber> skipPlumberFields = new PropertyMap<>() {
            protected void configure() {
                skip().setHouses(null);
            }
        };

        modelMapper.addMappings(skipHouseFields);
        modelMapper.addMappings(skipPlumberFields);


        return modelMapper;
    }
}
