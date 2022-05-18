package uz.paynet.plumber.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlumberDto {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;

    @JsonProperty("houses")
    @JsonIgnoreProperties("plumber")
    private List<HouseDto> houses;

}