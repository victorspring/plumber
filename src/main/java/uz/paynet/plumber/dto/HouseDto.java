package uz.paynet.plumber.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseDto {

    private Long id;

    @JsonProperty("address")
    private HouseAddressDto address;

    @JsonProperty("plumber")
    @JsonIgnoreProperties("houses")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PlumberDto plumber;

}