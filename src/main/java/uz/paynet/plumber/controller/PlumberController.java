package uz.paynet.plumber.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uz.paynet.plumber.dto.PlumberDto;
import uz.paynet.plumber.entity.Plumber;
import uz.paynet.plumber.repository.PlumberRepository;
import uz.paynet.plumber.service.HouseService;

import static uz.paynet.plumber.exception.ExceptionUtils.throwNotFound;

@RequestMapping(PlumberController.ENDPOINT)
@RestController
@RequiredArgsConstructor
public class PlumberController {

    public static final String ENDPOINT = "/plumbers";

    private final ModelMapper modelMapper;
    private final PlumberRepository plumberRepository;
    private final HouseService houseService;


    @GetMapping("/{id}")
    public PlumberDto findById(@PathVariable("id") Long id) {
        Plumber plumber = plumberRepository
                .findById(id)
                .orElseThrow(throwNotFound(Plumber.class, id));
        return toDto(plumber);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlumberDto save(@RequestBody PlumberDto plumberDto) {
        Plumber plumber = plumberRepository.save(toEntity(plumberDto));
        return toDto(plumber);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        plumberRepository.deleteById(id);
    }


    @PutMapping("/{plumberId}/houses/{houseId}")
    @ResponseStatus(HttpStatus.OK)
    public PlumberDto addHouse(@PathVariable("plumberId") Long plumberId,
                         @PathVariable("houseId") Long houseId) {
        houseService.setPlumber(houseId, plumberId);
        return findById(plumberId);
    }


    private Plumber toEntity(PlumberDto plumber) {
        return modelMapper.map(plumber, Plumber.class);
    }

    private PlumberDto toDto(Plumber plumber) {
        return modelMapper.map(plumber, PlumberDto.class);
    }

}