package uz.paynet.plumber.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uz.paynet.plumber.dto.HouseDto;
import uz.paynet.plumber.entity.House;
import uz.paynet.plumber.repository.HouseRepository;
import uz.paynet.plumber.service.HouseService;

import static uz.paynet.plumber.exception.ExceptionUtils.throwNotFound;

@RequestMapping(HouseController.ENDPOINT)
@RestController
@RequiredArgsConstructor
public class HouseController {

    public static final String ENDPOINT = "/houses";

    private final ModelMapper modelMapper;
    private final HouseRepository houseRepository;
    private final HouseService houseService;


    @GetMapping("/{id}")
    public HouseDto findById(@PathVariable("id") Long id) {
        House house = houseRepository
                .findById(id)
                .orElseThrow(throwNotFound(House.class, id));
        return toDto(house);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HouseDto save(@RequestBody HouseDto houseDto) {
        House house = houseRepository.save(toEntity(houseDto));
        return toDto(house);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        houseRepository.deleteById(id);
    }


    @PutMapping("/{houseId}/plumber/{plumberId}")
    @ResponseStatus(HttpStatus.OK)
    public HouseDto setPlumber(@PathVariable("houseId") Long houseId,
                         @PathVariable("plumberId") Long plumberId) {
        houseService.setPlumber(houseId, plumberId);
        return findById(houseId);
    }


    private House toEntity(HouseDto houseDto) {
        return modelMapper.map(houseDto, House.class);
    }

    private HouseDto toDto(House house) {
        return modelMapper.map(house, HouseDto.class);
    }


}