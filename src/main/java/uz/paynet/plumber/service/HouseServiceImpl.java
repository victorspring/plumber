package uz.paynet.plumber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.paynet.plumber.entity.House;
import uz.paynet.plumber.entity.Plumber;
import uz.paynet.plumber.exception.HouseLimitExceedException;
import uz.paynet.plumber.repository.HouseRepository;
import uz.paynet.plumber.repository.PlumberRepository;

import static uz.paynet.plumber.exception.ExceptionUtils.throwNotFound;

@Service
@Transactional
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    @Value("${house.limit:5}")
    private final int houseLimit;

    private final HouseRepository houseRepository;
    private final PlumberRepository plumberRepository;

    @Override
    public void setPlumber(Long houseId, Long plumberId)  {
        House house = houseRepository
                .findById(houseId)
                .orElseThrow(throwNotFound(House.class, houseId));
        Plumber plumber = plumberRepository
                .findById(plumberId)
                .orElseThrow(throwNotFound(Plumber.class, plumberId));
        if (plumber.getHouses().size() == houseLimit && !plumber.getHouses().contains(house)){
            throw new HouseLimitExceedException("Plumber can't serve more than " + houseLimit + " houses");
        }
        house.setPlumber(plumber);
    }


}
